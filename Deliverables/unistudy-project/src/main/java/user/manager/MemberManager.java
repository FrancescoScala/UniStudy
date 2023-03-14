package user.manager;

import user.beans.Role;
import user.beans.Member;
import connection.ConnectionPoolDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MemberManager {
    private static Connection conn; //final?

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String alphabeticRegex = "^[a-zA-Z ]+$";
    private static final String emailRegex = "[a-zA-z0-9._%+-]+@[a-zA-z0-9.-]+\\.[a-z]{2,15}$";
    private static final String pswRegex = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,12}$";

    public static boolean signupMember(String email, String password, String name, String surname) throws SQLException {
        //controllo formato -- AGGIUNGERE ECCEZIONI
        if (email.matches(emailRegex) &&
                password.matches(pswRegex) &&
                name.matches(alphabeticRegex) &&
                surname.matches(alphabeticRegex)) {
            //si controlla che l'email NON sia presente nel sistema...
            if (retrieveIdMemberByEmail(email) == -1) {
                String querySQL1 = "INSERT INTO user(user_email,user_password,user_name,user_surname) VALUES (?,?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);

                ps1.setString(1, email);
                ps1.setString(2, password);
                ps1.setString(3, name);
                ps1.setString(4, surname);
                ps1.executeUpdate();

                return true;
            }
            //se l'email è già presente...
            else {
                throw new RuntimeException("Email già errato");
            }
        }
        //se il formato è sbagliato
        else {
            throw new RuntimeException("Formato errato");
        }
    }

    public static Member loginMember(String email, String password) throws SQLException {
        if (email.matches(emailRegex) &&
                password.matches(pswRegex)) {
            int memberId = retrieveIdMemberByEmail(email);
            Member member;
            //se l'email è presente nel sistema...
            if (memberId != -1) {
                String querySQL1 = "SELECT *  FROM user WHERE user.user_id=?";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);
                ps1.setInt(1, memberId);
                ResultSet rs1 = ps1.executeQuery();
                rs1.next();
                String passwordDB = rs1.getString("user_password");
                //se la password è corretta...
                if (passwordDB.equals(password)) {
                    String emailDB = rs1.getString("user_email");
                    String nameDB = rs1.getString("user_name");
                    String surnameDB = rs1.getString("user_surname");

                    String querySQL2 = "SELECT distinct role.role_id, role_name FROM has_role, role WHERE has_role.user_id=? and has_role.role_id= role.role_id";
                    PreparedStatement ps2 = conn.prepareStatement(querySQL2);
                    ps2.setInt(1, memberId);

                    ResultSet rs2 = ps2.executeQuery();
                    Set<Role> rolesDB = new HashSet<Role>();
                    while (rs2.next()) {
                        int roleId = rs2.getInt("role_id");
                        String roleName = rs2.getString("role_name");
                        Role role = new Role(roleId, roleName);
                        rolesDB.add(role);
                    }
                    member = new Member(memberId, emailDB, passwordDB, nameDB, surnameDB, rolesDB);
                } else {
                    throw new RuntimeException("Password non corretta");
                }
                return member;
            } else {
                throw new RuntimeException("Email non presente");
            }
        } else {
            throw new RuntimeException("Formato errato");
        }
    }

    // per verificare se la email inserita in signup è già associata ad un utente del sistema
    public static int retrieveIdMemberByEmail(String email) {
        try {
            String querySQL = "SELECT user.user_id FROM user WHERE user_email=?";
            PreparedStatement ps = conn.prepareStatement(querySQL);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            } else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean modifyInfoMember(Member member, String name, String surname, String newPassword, String oldPassword) throws SQLException {
        if (oldPassword.matches(pswRegex) &&
                newPassword.matches(pswRegex) &&
                name.matches(alphabeticRegex) &&
                surname.matches(alphabeticRegex) &&
                retrieveIdMemberByEmail(member.getEmail()) != -1) {

            if (member.getPassword().equals(oldPassword)) {

                String querySQL = "UPDATE user SET user_name = ? , user_surname = ?, user_password=? WHERE user_id=?";
                PreparedStatement ps = conn.prepareStatement(querySQL);
                ps.setString(1, name);
                ps.setString(2, surname);
                ps.setString(3, newPassword);
                ps.setInt(4, member.getId());
                ps.executeUpdate();
                return true;

            } else {
                throw new RuntimeException("Password errata");
            }
        } else {
            throw new RuntimeException("Formato errato");
        }
    }
}


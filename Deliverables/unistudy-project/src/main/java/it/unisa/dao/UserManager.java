package it.unisa.dao;

import it.unisa.db.ConnectionPoolDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private static Connection conn; //final?

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean signupUser(String email, String password, String name, String surname)
    {
        try
        {
            //controllo formato?

            //se l'email non è presente nel sistema...
            if(retrieveIdUserByEmail(email)==-1) {
                String querySQL = "INSERT INTO user(user_email,user_password,user_name,user_surname) VALUES (?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(querySQL);

                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, name);
                ps.setString(4, surname);
                ps.executeUpdate();

                return true;
            }
            //se già presente...
            else
            {
                return false;
            }
        }

        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }

    // per verificare se la email inserita in signup è già associata ad un utente del sistema
    private static int retrieveIdUserByEmail(String email)
    {
        try
        {
            String querySQL = "SELECT user.user_id FROM user WHERE user_email=?";
            PreparedStatement ps = conn.prepareStatement(querySQL);

            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                return rs.getInt("user_id");
            }
            else
                return -1;
        }

        catch(SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}

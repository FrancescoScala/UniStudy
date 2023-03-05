package user.beans;

import java.util.Objects;

public class Role {

    public enum RoleType
    {
        GESTOREUTENTI,
        AMMINISTRATORE
    }
    private int id;
    private RoleType roleName;

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = createRoleType(roleName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleType roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName=" + roleName +
                '}';
    }
    private static RoleType createRoleType(String role) {
        RoleType type;
        switch (role) {
            case "GESTOREUTENTI" :
                type = RoleType.GESTOREUTENTI;
                break;
            case "AMMINISTRATORE" :
                type = RoleType.AMMINISTRATORE;
                break;
            default:
                type = null;
        }
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && roleName == role.roleName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}

package it.unisa.beans;

public class Role {

    enum RoleType
    {
        PARTECIPANTE,
        GESTOREUTENTE,
        AMMINISTRATORE
    }
    private int id;
    private RoleType roleName;

    public Role(int id, RoleType roleName) {
        this.id = id;
        this.roleName = roleName;
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
}

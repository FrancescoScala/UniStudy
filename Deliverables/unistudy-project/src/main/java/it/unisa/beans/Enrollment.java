package it.unisa.beans;

import java.util.Set;

public class Enrollment {
    public enum EnrollType {
        STUDENTE,
        GESTORECORSO
    }

    private int userId;
    private int courseId;
    private Set<EnrollType> roles;

    public Enrollment(int userId, int courseId, Set<EnrollType> roles) {
        this.userId = userId;
        this.courseId = courseId;
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Set<EnrollType> getRoles() {
        return roles;
    }

    public void setRoles(Set<EnrollType> roles) {
        this.roles = roles;
    }

    public static EnrollType createRoleType(String enrollType) {
        EnrollType type;
        switch (enrollType) {
            case "STUDENTE" :
                type =  EnrollType.STUDENTE;
                break;
            case "GESTORECORSO" :
                type = EnrollType.GESTORECORSO;
                break;
            default:
                type = null;
        }
        return type;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "userId=" + userId +
                ", courseId=" + courseId +
                ", roles=" + roles +
                '}';
    }
}

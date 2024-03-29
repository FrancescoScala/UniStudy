package course.beans;

import java.util.Objects;
import java.util.Set;

public class Enrollment {
    public enum EnrollType {
        STUDENTE,
        GESTORECORSO
    }

    private int memberId;
    private int courseId;
    private String courseTitle;
    private Set<EnrollType> roles;

    public Enrollment(int memberId, int courseId, String courseTitle, Set<EnrollType> roles) {
        this.memberId = memberId;
        this.courseId = courseId;
        this.roles = roles;
        this.courseTitle=courseTitle;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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
                "memberId=" + memberId +
                ", courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return memberId == that.memberId && courseId == that.courseId && Objects.equals(courseTitle, that.courseTitle) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, courseId, courseTitle, roles);
    }
}

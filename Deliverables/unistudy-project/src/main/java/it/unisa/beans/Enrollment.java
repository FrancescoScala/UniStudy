package it.unisa.beans;

import java.util.Set;

public class Enrollment {
    enum EnrollType
    {
        STUDENTE,
        GESTORECORSO
    }

    private User user;
    private Course course;
    private Set<EnrollType> roles;

    public Enrollment(User user, Course course, Set<EnrollType> roles) {
        this.user = user;
        this.course = course;
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<EnrollType> getRoles() {
        return roles;
    }

    public void setRoles(Set<EnrollType> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "user=" + user +
                ", course=" + course +
                ", roles=" + roles +
                '}';
    }
}

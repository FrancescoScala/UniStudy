package course.beans;

import java.util.Objects;
import java.util.Set;

public class Course {
    private int id;
    private String professors;
    private String timeSchedule;
    private String title;
    private Set<Notice> notices;
    private Set<Note> notes;

    public Course(int id, String professors, String timeSchedule, String title, Set<Notice> notices, Set<Note> notes) {
        this.id = id;
        this.professors = professors;
        this.timeSchedule = timeSchedule;
        this.title = title;
        this.notices = notices;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfessors() {
        return professors;
    }

    public void setProfessors(String professors) {
        this.professors = professors;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Notice> getNotices() {
        return notices;
    }

    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", professors='" + professors + '\'' +
                ", timeSchedule='" + timeSchedule + '\'' +
                ", title='" + title + '\'' +
                ", notices=" + notices +
                ", notes=" + notes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(professors, course.professors) && Objects.equals(timeSchedule, course.timeSchedule) && Objects.equals(title, course.title) && Objects.equals(notices, course.notices) && Objects.equals(notes, course.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, professors, timeSchedule, title, notices, notes);
    }
}

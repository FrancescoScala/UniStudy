package it.unisa.beans;

import java.util.Set;

public class Course {
    private int id;
    private String professors;
    private String timeSchedule;
    private String title;
    private Set<Notice> notices;
    private Set<Note> notes;
    //per il mapping model->code (deprecato):  private Set<Enrollment> enrollments;

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

    public boolean addNotice(Notice notice)
    {
        notices.add(notice);
        return true;
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

    public boolean addNote(Note note)
    {
        notes.add(note);
        return true;
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
}

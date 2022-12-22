package it.unisa.beans;

import java.util.Date;

public class Notice {
    private int id;
    private String title;
    private Date creationDate;
    private String description;

    public Notice(int id, String title, Date creationDate, String description) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                '}';
    }
}

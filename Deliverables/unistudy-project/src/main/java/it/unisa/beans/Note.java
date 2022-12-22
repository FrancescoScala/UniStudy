package it.unisa.beans;

import java.util.Date;

public class Note {
    private int id;
    private String description;
    private Date creationDate;
    private String filePath;
    private String title;
    private User author;

    public Note(int id, String description, Date creationDate, String filePath, String title, User author) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.filePath = filePath;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", filePath='" + filePath + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}

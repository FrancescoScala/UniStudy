package it.unisa.beans;

import java.sql.Timestamp;

public class Note {
    private int id;
    private String description;
    private Timestamp creationDate;
    private String filePath;
    private String title;
    private int authorId;
    private String authorInfo;

    public Note(int id, String description, Timestamp creationDate, String filePath, String title, int authorId, String authorInfo) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.filePath = filePath;
        this.title = title;
        this.authorId = authorId;
        this.authorInfo = authorInfo;
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorName(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", filePath='" + filePath + '\'' +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorInfo + '\'' +
                '}';
    }
}
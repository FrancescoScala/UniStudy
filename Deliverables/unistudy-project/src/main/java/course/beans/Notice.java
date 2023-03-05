package course.beans;

import java.sql.Timestamp;
import java.util.Objects;

public class Notice {
    private int id;
    private String title;
    private Timestamp creationDate;
    private String description;

    public Notice(int id, String title, Timestamp creationDate, String description) {
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return id == notice.id && Objects.equals(title, notice.title) && Objects.equals(creationDate, notice.creationDate) && Objects.equals(description, notice.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, creationDate, description);
    }
}

package by.itechart.logic.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Attachment implements Serializable {

    private long id;
    private long contactId;
    private String filePath;
    private LocalDate dateOfLoad;
    private String comment;

    public Attachment() {
    }

    public Attachment(String filePath, LocalDate dateOfLoad, String comment) {
        this.filePath = filePath;
        this.dateOfLoad = dateOfLoad;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDate getDateOfLoad() {
        return dateOfLoad;
    }

    public void setDateOfLoad(LocalDate dateOfLoad) {
        this.dateOfLoad = dateOfLoad;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return id == that.id &&
                contactId == that.contactId &&
                Objects.equals(filePath, that.filePath) &&
                Objects.equals(dateOfLoad, that.dateOfLoad) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactId, filePath, dateOfLoad, comment);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", filePath='" + filePath + '\'' +
                ", dateOfLoad=" + dateOfLoad +
                ", comment='" + comment + '\'' +
                '}';
    }

}

package by.itechart.logic.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Attachment implements Serializable {

    private long id;
    private long contactId;
    private String fileName;
    private LocalDate dateOfLoad;
    private String comment;

    public Attachment() {
    }

    public Attachment(long id, long contactId, String fileName, LocalDate dateOfLoad, String comment) {
        this.id = id;
        this.contactId = contactId;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(dateOfLoad, that.dateOfLoad) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactId, fileName, dateOfLoad, comment);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", fileName='" + fileName + '\'' +
                ", dateOfLoad=" + dateOfLoad +
                ", comment='" + comment + '\'' +
                '}';
    }
}

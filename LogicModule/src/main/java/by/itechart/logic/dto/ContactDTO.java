package by.itechart.logic.dto;

import by.itechart.logic.entity.Contact;
import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.util.Objects;

public class ContactDTO {

    private Contact contact;
    private FileItem avatar;
    private List<FileItem> attachments;

    public ContactDTO() {
    }

    public ContactDTO(Contact contact, FileItem avatar, List<FileItem> attachments) {
        this.contact = contact;
        this.avatar = avatar;
        this.attachments = attachments;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public FileItem getAvatar() {
        return avatar;
    }

    public void setAvatar(FileItem avatar) {
        this.avatar = avatar;
    }

    public List<FileItem> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<FileItem> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDTO that = (ContactDTO) o;
        return Objects.equals(contact, that.contact) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact, avatar, attachments);
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "contact=" + contact +
                ", avatar=" + avatar +
                ", attachments=" + attachments +
                '}';
    }

}

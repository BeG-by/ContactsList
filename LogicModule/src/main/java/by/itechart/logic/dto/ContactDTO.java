package by.itechart.logic.dto;

import by.itechart.logic.entity.Contact;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class ContactDTO implements Serializable {

    private Contact contact;
    private byte[] avatar;
    private Map<Long, byte[]> attachments;

    public ContactDTO() {
    }

    public ContactDTO(Contact contact, byte[] avatar, Map<Long, byte[]> attachments) {
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Map<Long, byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<Long, byte[]> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDTO that = (ContactDTO) o;
        return Objects.equals(contact, that.contact) &&
                Arrays.equals(avatar, that.avatar) &&
                Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(contact, attachments);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
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

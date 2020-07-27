package by.itechart.logic.dto;

import by.itechart.logic.entity.Contact;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactDTO {

    private Contact contact;
    private byte[] avatar;
    private List<byte[]> attachments;

    public ContactDTO() {
    }

    public ContactDTO(Contact contact, byte[] avatar, List<byte[]> attachments) {
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

    public List<byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<byte[]> attachments) {
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

        final List<String> attachmentsToString = attachments.stream()
                .map(Arrays::toString)
                .collect(Collectors.toList());

        return "ContactDTO{" +
                "contact=" + contact +
                ", avatar=" + Arrays.toString(avatar) +
                ", attachments=" + attachmentsToString +
                '}';
    }

}

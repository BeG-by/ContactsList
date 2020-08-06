package by.itechart.logic.dto;

import java.util.List;
import java.util.Objects;

public class MessageRequest {

    private List<String> emails;
    private String templateKey;
    private String subject;
    private String text;

    public MessageRequest() {
    }

    public MessageRequest(List<String> emails, String templateKey, String subject, String text) {
        this.emails = emails;
        this.templateKey = templateKey;
        this.subject = subject;
        this.text = text;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRequest that = (MessageRequest) o;
        return Objects.equals(emails, that.emails) &&
                Objects.equals(templateKey, that.templateKey) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emails, templateKey, subject, text);
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "emails=" + emails +
                ", templateKey='" + templateKey + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}

package by.itechart.logic.entity;

import java.io.Serializable;
import java.util.Objects;

public class Phone implements Serializable {

    private long id;
    private long contactId;
    private int countryCode;
    private int operatorCode;
    private long number;
    private String type;
    private String comment;

    public Phone() {
    }

    public Phone(long id, long contactId, int countryCode, int operatorCode, long number, String type, String comment) {
        this.id = id;
        this.contactId = contactId;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.type = type;
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

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(int operatorCode) {
        this.operatorCode = operatorCode;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        Phone phone = (Phone) o;
        return id == phone.id &&
                countryCode == phone.countryCode &&
                operatorCode == phone.operatorCode &&
                number == phone.number &&
                Objects.equals(type, phone.type) &&
                Objects.equals(comment, phone.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryCode, operatorCode, number, type, comment);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", countryCode=" + countryCode +
                ", operatorCode=" + operatorCode +
                ", number=" + number +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}

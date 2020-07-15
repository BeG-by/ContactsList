package by.itechart.logic.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Contact implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthday;
    private String sex;
    private String nationality;
    private String maritalStatus;
    private String urlWebSite;
    private String email;
    private String currentJob;
    private Address address;
    private List<Phone> phoneList;

    public Contact() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getUrlWebSite() {
        return urlWebSite;
    }

    public void setUrlWebSite(String urlWebSite) {
        this.urlWebSite = urlWebSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(middleName, contact.middleName) &&
                Objects.equals(birthday, contact.birthday) &&
                Objects.equals(sex, contact.sex) &&
                Objects.equals(nationality, contact.nationality) &&
                Objects.equals(maritalStatus, contact.maritalStatus) &&
                Objects.equals(urlWebSite, contact.urlWebSite) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(currentJob, contact.currentJob) &&
                Objects.equals(address, contact.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName, birthday, sex, nationality, maritalStatus, urlWebSite, email, currentJob, address);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", nationality='" + nationality + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", urlWebSite='" + urlWebSite + '\'' +
                ", email='" + email + '\'' +
                ", currentJob='" + currentJob + '\'' +
                ", address=" + address +
                ", phoneList=" + phoneList +
                '}';
    }

    public static class Builder {
        private Contact contact;

        public Builder() {
            contact = new Contact();
        }

        public Builder id(long id) {
            contact.setId(id);
            return this;
        }

        public Builder firstName(String firstName) {
            contact.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            contact.setLastName(lastName);
            return this;
        }

        public Builder middleName(String middleName) {
            contact.setMiddleName(middleName);
            return this;
        }

        public Builder birthday(LocalDate birthday) {
            contact.setBirthday(birthday);
            return this;
        }

        public Builder sex(String sex) {
            contact.setSex(sex);
            return this;
        }

        public Builder nationality(String nationality) {
            contact.setNationality(nationality);
            return this;
        }

        public Builder maritalStatus(String status) {
            contact.setMaritalStatus(status);
            return this;
        }

        public Builder urlWebSite(String url) {
            contact.setUrlWebSite(url);
            return this;
        }

        public Builder email(String email) {
            contact.setEmail(email);
            return this;
        }

        public Builder currentJob(String job) {
            contact.setCurrentJob(job);
            return this;
        }

        public Builder address(Address address) {
            contact.setAddress(address);
            return this;
        }

        public Builder phoneList(List<Phone> phones){
            contact.setPhoneList(phones);
            return this;
        }

        public Contact build() {
            return contact;
        }

    }

}

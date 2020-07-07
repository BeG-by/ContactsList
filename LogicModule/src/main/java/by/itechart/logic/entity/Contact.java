package by.itechart.logic.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

    public Contact() {
    }

    public Contact(long id,
                   String firstName,
                   String lastName,
                   String middleName,
                   LocalDate birthday,
                   String sex,
                   String nationality,
                   String maritalStatus,
                   String urlWebSite,
                   String email,
                   String currentJob,
                   Address address) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.sex = sex;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.urlWebSite = urlWebSite;
        this.email = email;
        this.currentJob = currentJob;
        this.address = address;
    }

    public Contact(String firstName, String lastName,
                   String middleName,
                   LocalDate birthday,
                   String sex,
                   String nationality,
                   String maritalStatus,
                   String urlWebSite,
                   String email,
                   String currentJob,
                   Address address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.sex = sex;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.urlWebSite = urlWebSite;
        this.email = email;
        this.currentJob = currentJob;
        this.address = address;
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
                '}';
    }

}

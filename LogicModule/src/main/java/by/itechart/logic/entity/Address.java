package by.itechart.logic.entity;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {

    private long id;
    private long contactId;
    private String country;
    private String city;
    private String street;
    private int postIndex;

    public Address() {
    }

    public Address(long id, long contactId, String country, String city, String street, int postIndex) {
        this.id = id;
        this.contactId = contactId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postIndex = postIndex;
    }

    public Address(String country, String city, String street, int postIndex) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postIndex = postIndex;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                postIndex == address.postIndex &&
                Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, postIndex);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", index=" + postIndex +
                '}';
    }

}

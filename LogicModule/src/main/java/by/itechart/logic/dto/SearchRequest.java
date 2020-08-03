package by.itechart.logic.dto;

import java.util.Objects;

public class SearchRequest {

    private String firstName;
    private String lastName;
    private String middleName;
    private String dateBefore;
    private String dateAfter;
    private String sex;
    private String maritalStatus;
    private String nationality;
    private String country;
    private String city;
    private String street;
    private String postIndex;

    public SearchRequest() {
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

    public String getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(String dateBefore) {
        this.dateBefore = dateBefore;
    }

    public String getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(String dateAfter) {
        this.dateAfter = dateAfter;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(String postIndex) {
        this.postIndex = postIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRequest that = (SearchRequest) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(dateBefore, that.dateBefore) &&
                Objects.equals(dateAfter, that.dateAfter) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(maritalStatus, that.maritalStatus) &&
                Objects.equals(nationality, that.nationality) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(postIndex, that.postIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, dateBefore, dateAfter, sex, maritalStatus, nationality, country, city, street, postIndex);
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", dateBefore='" + dateBefore + '\'' +
                ", dateAfter='" + dateAfter + '\'' +
                ", sex='" + sex + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", nationality='" + nationality + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postIndex='" + postIndex + '\'' +
                '}';
    }

    public static class Builder {
        private SearchRequest searchRequest;

        public Builder() {
            searchRequest = new SearchRequest();
        }


        public SearchRequest.Builder firstName(String firstName) {
            searchRequest.setFirstName(firstName);
            return this;
        }

        public SearchRequest.Builder lastName(String lastName) {
            searchRequest.setLastName(lastName);
            return this;
        }

        public SearchRequest.Builder middleName(String middleName) {
            searchRequest.setMiddleName(middleName);
            return this;
        }

        public SearchRequest.Builder dateBefore(String dateBefore) {
            searchRequest.setDateBefore(dateBefore);
            return this;
        }

        public SearchRequest.Builder dateAfter(String dateAfter) {
            searchRequest.setDateAfter(dateAfter);
            return this;
        }

        public SearchRequest.Builder nationality(String nationality) {
            searchRequest.setNationality(nationality);
            return this;
        }

        public SearchRequest.Builder maritalStatus(String status) {
            searchRequest.setMaritalStatus(status);
            return this;
        }

        public SearchRequest.Builder county(String country) {
            searchRequest.setCountry(country);
            return this;
        }

        public SearchRequest.Builder city(String city) {
            searchRequest.setCity(city);
            return this;
        }

        public SearchRequest.Builder street(String street) {
            searchRequest.setStreet(street);
            return this;
        }

        public SearchRequest.Builder postIndex(String index) {
            searchRequest.setPostIndex(index);
            return this;
        }

        public SearchRequest build() {
            return searchRequest;
        }

    }

}

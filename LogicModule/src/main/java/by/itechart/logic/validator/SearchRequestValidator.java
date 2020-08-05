package by.itechart.logic.validator;

import by.itechart.logic.dto.SearchRequest;

import java.time.LocalDate;

public class SearchRequestValidator {

    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private static final String SINGLE = "single";
    private static final String MARRIED = "married";

    public static void validate(SearchRequest searchRequest) {

        final String firstName = searchRequest.getFirstName();
        final String lastName = searchRequest.getLastName();
        final String middleName = searchRequest.getMiddleName();
        final String dateBefore = searchRequest.getDateBefore();
        final String dateAfter = searchRequest.getDateAfter();
        final String sex = searchRequest.getSex();
        final String maritalStatus = searchRequest.getMaritalStatus();
        final String nationality = searchRequest.getNationality();
        final String country = searchRequest.getCountry();
        final String city = searchRequest.getCity();
        final String street = searchRequest.getStreet();
        final String postIndex = searchRequest.getPostIndex();


        if (validateText(firstName, 16)) {
            searchRequest.setFirstName(firstName.trim());
        } else {
            searchRequest.setFirstName("");
        }

        if (validateText(lastName, 16)) {
            searchRequest.setLastName(lastName.trim());
        } else {
            searchRequest.setLastName("");
        }

        if (validateText(middleName, 16)) {
            searchRequest.setMiddleName(middleName.trim());
        } else {
            searchRequest.setMiddleName("");
        }

        if (validateText(nationality, 16)) {
            searchRequest.setNationality(nationality.trim());
        } else {
            searchRequest.setNationality("");
        }

        if (validateText(country, 16)) {
            searchRequest.setCountry(country.trim());
        } else {
            searchRequest.setCountry("");
        }

        if (validateText(city, 16)) {
            searchRequest.setCity(city.trim());
        } else {
            searchRequest.setCity("");
        }

        if (validateText(street, 24)) {
            searchRequest.setStreet(street.trim());
        } else {
            searchRequest.setStreet("");
        }

        if (!postIndex.matches("\\d{1,10}")) {
            searchRequest.setPostIndex("");
        }

        if (!sex.equalsIgnoreCase(MALE) && !sex.equalsIgnoreCase(FEMALE)) {
            searchRequest.setSex("");
        }

        if (!maritalStatus.equalsIgnoreCase(MARRIED) && !maritalStatus.equalsIgnoreCase(SINGLE)) {
            searchRequest.setMaritalStatus("");
        }

        try {
            LocalDate.parse(dateAfter);
        } catch (Exception e) {
            searchRequest.setDateAfter("");
        }

        try {
            LocalDate.parse(dateBefore);
        } catch (Exception e) {
            searchRequest.setDateBefore("");
        }


    }

    private static boolean validateText(String property, int propertyLength) {

        if (!property.matches("([A-Za-zА-Яа-я\\s-]){1," + propertyLength + "}")) {
            return false;
        }

        if (property.startsWith("-") || property.endsWith("-")) {
            return false;
        }

        String space = property.replaceAll("\\s", "");
        String hyphen = property.replaceAll("-", "");

        return property.length() - space.length() <= 1 && property.length() - hyphen.length() <= 1;

    }

}

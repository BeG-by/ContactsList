package by.itechart.logic.validator;

import by.itechart.logic.dto.SearchRequest;

public class SearchRequestValidator {

    public static SearchRequest validate(SearchRequest searchRequest) {

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

        return searchRequest;
    }

}

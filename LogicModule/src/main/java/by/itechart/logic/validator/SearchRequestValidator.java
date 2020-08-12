package by.itechart.logic.validator;

import by.itechart.logic.dto.SearchRequest;

import java.time.LocalDate;

public class SearchRequestValidator {

    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private static final String SINGLE = "single";
    private static final String MARRIED = "married";

    public static void validate(SearchRequest searchRequest) {

        final String dateBefore = searchRequest.getDateBefore();
        final String dateAfter = searchRequest.getDateAfter();
        final String sex = searchRequest.getSex();
        final String maritalStatus = searchRequest.getMaritalStatus();
        final String postIndex = searchRequest.getPostIndex();


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

}

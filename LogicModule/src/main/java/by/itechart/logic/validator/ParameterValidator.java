package by.itechart.logic.validator;

public class ParameterValidator {

    public static boolean pageValidate(String page, String pageLimit) {
        return page != null && pageLimit != null && page.matches("\\d+") &&
                pageLimit.matches("\\d+") && Integer.parseInt(page) > 0;
    }


    public static boolean contactIdValidate(String contactId) {
        return contactId != null && contactId.matches("\\d+");
    }

}

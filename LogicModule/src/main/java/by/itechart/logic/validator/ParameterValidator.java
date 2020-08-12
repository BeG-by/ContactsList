package by.itechart.logic.validator;

public class ParameterValidator {

    public static boolean pageValidate(String page, String pageLimit) {
        return page != null && pageLimit != null && page.matches("\\d+") &&
                pageLimit.matches("\\d+") && Integer.parseInt(page) > 0;
    }


    public static boolean contactIdValidate(String id) {
        return id != null && id.matches("\\d+") && Long.parseLong(id) > 0;
    }

    public static boolean attachmentIdValidate(String id) {
        return id != null && id.matches("-?\\d+");
    }

}

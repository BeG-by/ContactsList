package by.itechart.web.command;

public interface ConstantMessages {

    String PAGE_PARAMETER = "page";
    String PAGE_LIMIT_PARAMETER = "pageLimit";
    String CONTACT_ID_PARAMETER = "contactId";
    String ATTACHMENT_ID_PARAMETER = "attId";

    String CONTACT_SAVE = "Contact has been saved.";
    String CONTACT_UPDATE = "Contact has been updated.";
    String CONTACT_DELETE = "Contacts have been deleted.";
    String CONTACT_NOT_FOUND = "Contact not found.";
    String CONTACT_LIST_EMPTY = "Contacts list is empty.";

    String ID_INCORRECT = "Incorrect id.";
    String PAGE_INCORRECT = "Page and page limit must be digit and more then zero.";

    String EMAIL_SEND = "Emails have been sent.";

    String SERVICE_UNAVAILABLE = "Service is temporarily unavailable.";
    String EMAIL_EXISTS = "Email already exists.";
    String TEMPLATE_NOT_FOUND = "Template not found.";
    String ATTACHMENT_NOT_FOUND = "File hasn't been upload to the server.";

}

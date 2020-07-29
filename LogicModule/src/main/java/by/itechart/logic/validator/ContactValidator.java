package by.itechart.logic.validator;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.entity.Phone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactValidator {

    private List<String> errorList = new ArrayList<>();

    public ContactValidator() {
    }

    public ContactValidator(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> validateContact(ContactDTO contactDTO) {

        final Contact contact = contactDTO.getContact();
        final String firstName = contact.getFirstName();
        final String lastName = contact.getLastName();
        final String middleName = contact.getMiddleName();
        final LocalDate birthday = contact.getBirthday();
        final String sex = contact.getSex();
        final String nationality = contact.getNationality();
        final String maritalStatus = contact.getMaritalStatus();
        final String urlWebSite = contact.getUrlWebSite();
        final String email = contact.getEmail();
        final String currentJob = contact.getCurrentJob();
        final String imageName = contact.getImageName();


        validateRequired(firstName, 16, "First name");
        validateRequired(lastName, 16, "Last name");

        if (!middleName.isEmpty()) {
            validateNonRequired(middleName, 16, "Middle name");
        } else {
            contact.setMiddleName(null);
        }

        if (birthday.equals(LocalDate.of(1111, 11, 1))) {
            contact.setBirthday(null);
        } else if (birthday.isBefore(LocalDate.of(1900, 1, 1))) {
            errorList.add("Date should be after 01.01.1990");
        } else if (birthday.isAfter(LocalDate.now())) {
            errorList.add("Date should be before " + LocalDate.now());
        }

        if (sex.equalsIgnoreCase("male") || sex.equalsIgnoreCase("female")) {
            contact.setSex(sex.toLowerCase());
        } else {
            errorList.add("Sex should be \"male\" or \"female\"");
        }

        if (!nationality.isEmpty()) {
            validateNonRequired(nationality, 16, "Nationality");
        } else {
            contact.setNationality(null);
        }

        if (maritalStatus.equalsIgnoreCase("married") || maritalStatus.equalsIgnoreCase("single")) {
            contact.setSex(sex.toLowerCase());
        } else {
            errorList.add("Marital status should be \"married\" or \"single\"");
        }

        if (!urlWebSite.isEmpty()) {
            if (urlWebSite.length() > 200) {
                errorList.add(String.format("%s length can't be more than %d !\n", urlWebSite, 20));
            }
        } else {
            contact.setUrlWebSite(null);
        }

        if (!email.isEmpty()) {

            if (!email.matches("[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) {
                errorList.add("Invalid email !");
            }

        } else {
            contact.setEmail(null);
        }

        if (!currentJob.isEmpty()) {
            validateNonRequired(currentJob, 100, "Job place");
        } else {
            contact.setCurrentJob(null);
        }

        if (imageName != null && !imageName.isEmpty() && imageName.length() > 100) {
            errorList.add("Image name length can't be more than 100");
        }

        validateAddress(contact.getAddress());
        validatePhoneList(contact.getPhoneList());
        validateAttachmentList(contact.getAttachmentList());

        return errorList;

    }


    private void validateAddress(Address address) {

        final String country = address.getCountry();
        final String city = address.getCity();
        final String street = address.getStreet();
        final Integer postIndex = address.getPostIndex();

        if (!country.isEmpty()) {
            validateNonRequired(country, 16, "Country");
        } else {
            address.setCountry(null);
        }

        if (!city.isEmpty()) {
            validateNonRequired(city, 16, "City");
        } else {
            address.setCity(null);
        }

        if (street.isEmpty()) {
            address.setStreet(null);
        } else if (street.length() > 24) {
            errorList.add("Street length can't be more than 24");
        }

        if (postIndex != null && Integer.toString(postIndex).length() > 10) {
            errorList.add("Post index length can't be more than 10");
        }

    }


    private void validatePhoneList(List<Phone> list) {

        boolean isBreak = false;

        for (Phone phone : list) {
            if (isBreak) break;

            final String countryCode = Integer.toString(phone.getCountryCode());
            final String operatorCode = Integer.toString(phone.getOperatorCode());
            final String number = Integer.toString(phone.getNumber());
            final String comment = phone.getComment();

            if (countryCode.length() > 8) {
                errorList.add("Country code should be 1-8 characters");
                isBreak = true;
            }

            if (operatorCode.length() > 8) {
                errorList.add("Operator code should be 1-8 characters");
                isBreak = true;
            }

            if (number.length() < 4 || number.length() > 16) {
                errorList.add("Phone number should be 4-16 characters");
                isBreak = true;
            }

            if (comment.length() > 45) {
                errorList.add("Comment length can't be more than 45");
                isBreak = true;
            }

        }

    }

    private void validateAttachmentList(List<Attachment> attachmentList) {

        boolean isBreak = false;

        for (Attachment attachment : attachmentList) {
            if (isBreak) break;

            final String fileName = attachment.getFileName();
            final LocalDate dateOfLoad = attachment.getDateOfLoad();
            final String comment = attachment.getComment();

            if (fileName.length() > 100) {
                errorList.add("File name length can't be more than 100");
                isBreak = true;
            }

            if (dateOfLoad.equals(LocalDate.of(1111, 11, 1))) {
                attachment.setDateOfLoad(null);
            } else if (dateOfLoad.isBefore(LocalDate.of(1900, 1, 1))) {
                errorList.add("Date of load can't be before 01.01.1990");
                isBreak = true;
            } else if (dateOfLoad.isAfter(LocalDate.now())) {
                errorList.add("Date of load can't be after " + LocalDate.now());
                isBreak = true;
            }

            if (comment.length() > 45) {
                errorList.add("Comment length can't be more than 45");
                isBreak = true;
            }

        }

    }


    private void validateRequired(String property, int propertyLength, String propertyName) {

        if (property.isEmpty()) {
            errorList.add(String.format("%s can't be empty !\n", propertyName));
        } else if (property.length() > propertyLength) {
            errorList.add(String.format("%s length can't be more than %d !\n", propertyName, propertyLength));
        }

        if (!property.matches("[A-Za-zА-Яа-я\\s-]*")) {
            errorList.add(String.format("%s must have only English or Russian letters, hyphen or space !\n", propertyName));
        }
    }

    private void validateNonRequired(String property, int propertyLength, String propertyName) {

        if (property.length() > propertyLength) {
            errorList.add(String.format("%s length can't be more than %d !\n", propertyName, propertyLength));
        }
        if (!property.matches("[A-Za-zА-Яа-я\\s-]*")) {
            errorList.add(String.format("%s must have only English or Russian letters !\n", propertyName));
        }

    }

}

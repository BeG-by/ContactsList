"use strict";

var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";

document.getElementById("create-btn").addEventListener("click", function () {
    var validateContact = getFormContactData();

    if (validateContact !== false) {
        sendMultipartRequest("POST", saveUrl, validateContact, requestAvatar, attachmentsForRequest, getCommentsAttachment());
    }

});

document.getElementById("add-phone-btn").addEventListener("click", function () {
    createPhoneForm(null);
});

document.getElementById("delete-phone-btn").addEventListener("click", deletePhoneRow);

document.getElementById("update-phone-btn").addEventListener("click", function () {
    var phoneData = getDataForUpdatePhone();
    if (phoneData != null) {
        createPhoneForm(phoneData);
    }
});


document.getElementById("add-att-btn").addEventListener("click", showAttachmentForm);

document.getElementById("day").addEventListener("input", replaceLetters);
document.getElementById("month").addEventListener("input", replaceLetters);
document.getElementById("year").addEventListener("input", replaceLetters);
document.getElementById("postIndex").addEventListener("input", replaceLetters);








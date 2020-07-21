"use strict";

var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";

document.getElementById("create-btn").addEventListener("click", function () {
    var validateContact = getFormContactData();

    if (validateContact !== false) {
        sendMultipartRequest("POST", saveUrl, validateContact, requestAvatar, attachmentsForRequest, getCommentsAttachment());
    }

});

document.getElementById("add-phone-btn").addEventListener("click", showPhoneForm);

document.getElementById("add-att-btn").addEventListener("click", showAttachmentForm);








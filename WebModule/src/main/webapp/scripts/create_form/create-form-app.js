"use strict";

var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";

document.getElementById("create-btn").addEventListener("click", function () {
    sendMultipartRequest("POST", saveUrl, getFormContactData(), requestAvatar, attachmentsForRequest, getCommentsAttachment());
});

document.getElementById("add-phone-btn").addEventListener("click", showPhoneForm);

document.getElementById("add-att-btn").addEventListener("click", showAttachmentForm);








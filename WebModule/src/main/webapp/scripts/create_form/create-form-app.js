"use strict";

var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";

document.getElementById("create-btn").addEventListener("click", function () {
    // requests(saveUrl, "POST", getFormContactData(), null, "Contact has been added !");
    sendMultipartRequest("POST", saveUrl, getFormContactData(), requestAvatar);
});

document.getElementById("add-phone-btn").addEventListener("click", function () {
    showPhoneForm();
});

document.getElementById("add-attachment-btn").addEventListener("click", showAttachmentForm);








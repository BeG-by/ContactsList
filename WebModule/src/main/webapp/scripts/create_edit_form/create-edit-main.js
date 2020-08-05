"use strict";

document.getElementById("create-btn").addEventListener("click", function () {
    var validateContact = getFormContactData();

    if (validateContact !== false) {

        validateContact["attachmentList"] = getAttachmentsList();
        sendMultipartRequest("POST", saveUrl, validateContact, requestAvatar, attachmentsForRequest, "Contact has been saved.");

    }

});

document.getElementById("update-btn").addEventListener("click", function () {
    var validateContact = getFormContactData();

    if (validateContact !== false) {
        validateContact["attachmentList"] = getAttachmentsList();
        sendMultipartRequest("PUT", putUrl, validateContact, requestAvatar, attachmentsForRequest, "Contact has been updated.");
    }

});


document.getElementById("add-phone-btn").addEventListener("click", function () {
    createPhoneForm(null);
});

document.getElementById("delete-phone-btn").addEventListener("click", deletePhoneRow);

document.getElementById("update-phone-btn").addEventListener("click", function () {
    var phoneData = getDataForUpdatePhone();

    if (phoneData !== null) {
        createPhoneForm(phoneData);
    }
});


document.getElementById("add-att-btn").addEventListener("click", function () {
    createAttachmentForm(null);
});

document.getElementById("delete-att-btn").addEventListener("click", deleteAttachmentsRow);

document.getElementById("update-att-btn").addEventListener("click", function () {
    var attachmentData = getDataForUpdateAttachment();

    if (attachmentData !== null) {
        createAttachmentForm(attachmentData);
    }
});


// --- Validation number ---

document.getElementById("day").addEventListener("input", replaceLetters);
document.getElementById("month").addEventListener("input", replaceLetters);
document.getElementById("year").addEventListener("input", replaceLetters);
document.getElementById("postIndex").addEventListener("input", replaceLetters);

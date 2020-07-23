"use strict";

var attachmentsForRequest = {}; // --- Arr Files to request ---

var idAttachmentTableRow = 0; // --- Id for attachment table ---

var idPhoneTableRow = 0; // --- Id for phone table ---

var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";


function replaceLetters() {
    this.value = this.value.replace(/\D/, "");
}

function matchStrict(regExp, str) {
    var match = str.match(regExp);
    return match && str === match[0];
}
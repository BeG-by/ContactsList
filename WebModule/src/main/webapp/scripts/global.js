"use strict";

var attachmentsForRequest = {}; // --- Arr Files to request ---
var requestAvatar = null; //-- Avatar for request
var idAttachmentTableRow = -1; // --- Id for attachment table ---
var idPhoneTableRow = -1000; // --- Id for phone table ---

var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";
var putUrl = "http://localhost:8080/api/v1/contactsList/contacts/update";


function replaceLetters() {
    this.value = this.value.replace(/\D/, "");
}

function matchStrict(regExp, str) {
    var match = str.match(regExp);
    return match && str === match[0];
}
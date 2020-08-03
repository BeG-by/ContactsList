"use strict";

var attachmentsForRequest = {}; // --- Arr Files to request ---
var requestAvatar = null; //-- Avatar for request
var idAttachmentTableRow = -1; // --- Id for attachment table ---
var idPhoneTableRow = -1000; // --- Id for phone table ---


var findAllUrl = "http://localhost:8080/api/v1/contactsList/contacts/findAll";
var deleteAllUrl = "http://localhost:8080/api/v1/contactsList/contacts/deleteAll";
var countAllUrl = "http://localhost:8080/api/v1/contactsList/contacts/countAll";
var saveUrl = "http://localhost:8080/api/v1/contactsList/contacts/save";
var putUrl = "http://localhost:8080/api/v1/contactsList/contacts/update";
var findByIdUrl = "http://localhost:8080/api/v1/contactsList/contacts/findById";
var sendEmailUrl = "http://localhost:8080/api/v1/contactsList/contacts/email";
var searchUrl = "http://localhost:8080/api/v1/contactsList/contacts/search";

var pageLimit = 10;


var page = localStorage.getItem("currentPage");
var currentPage = page === null ? currentPage = 1 : currentPage = page;


function replaceLetters() {
    this.value = this.value.replace(/\D/, "");
}

function matchStrict(regExp, str) {
    var match = str.match(regExp);
    return match && str === match[0];
}
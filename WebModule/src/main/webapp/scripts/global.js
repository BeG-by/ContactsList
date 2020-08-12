"use strict";

var attachmentsForRequest = {}; // --- Arr Files to request ---
var requestAvatar = null; //-- Avatar for request
var idAttachmentTableRow = -1; // --- Id for attachment table ---
var idPhoneTableRow = -1000; // --- Id for phone table ---

var domain = "http://localhost:8080/";

var indexHref = domain + "index.html";

var findAllUrl = domain + "api/v1/contacts";
var deleteAllUrl = domain + "api/v1/contacts";
var countAllUrl = domain + "api/v1/contacts/quantity";
var saveUrl = domain + "api/v1/contacts";
var putUrl = domain + "api/v1/contacts";
var findByIdUrl = domain + "api/v1/contacts/id";
var sendEmailUrl = domain + "api/v1/contacts/email";
var searchUrl = domain + "api/v1/contacts/filter";
var searchCountAllUrl = domain + "api/v1/contacts/filter/quantity";
var findAllTemplatesUrl = domain + "api/v1/contacts/templates";
var downloadAttachmentUrl = domain + "api/v1/contacts/attachment";

var pageLimit = 10;

var page = localStorage.getItem("currentPage");
var currentPage = page === null ? currentPage = 1 : currentPage = page;

var filterRequestBody = null;

var contentTemplates = null;

var isSelectedCheckboxes = false;


function replaceLetters() {
    this.value = this.value.replace(/\D/, "");
}

function matchStrict(regExp, str) {
    var match = str.match(regExp);
    return match && str === match[0];
}
"use strict";
var findAllUrl = "http://localhost:8080/contactsList/contacts/findAll";
var deleteAllUrl = "http://localhost:8080/contactsList/contacts/deleteAll";

sendGetRequest(findAllUrl, getAllContacts);

var deleteBtn = document.getElementById("delete-btn");

deleteBtn.addEventListener("click", function () {
    var idList = deleteContacts();
    if (idList !== undefined) {
        sendRequest(deleteAllUrl, "DELETE", idList, "Contacts have been deleted");
    }
});
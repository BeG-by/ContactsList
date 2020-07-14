"use strict";
var findAllUrl = "http://localhost:8080/contactsList/contacts/findAll";
var deleteAllUrl = "http://localhost:8080/contactsList/contacts/deleteAll";

sendRequest(findAllUrl, "GET", null, getAllContacts, null);

var deleteBtn = document.getElementById("delete-btn");

deleteBtn.addEventListener("click", function () {
    var idList = deleteContacts();
    if (idList != null) {
        sendRequest(deleteAllUrl, "DELETE", idList, null, "Contacts have been deleted");
    } else {
        alert("You should choose the contacts !");
    }
});


// --- create contacts table ---
function getAllContacts(response) {

    var table = document.querySelector("#contacts-table");
    var jsonResponse = response;

    for (var jsonKey in jsonResponse) {
        var tr = document.createElement("tr");
        if (jsonResponse.hasOwnProperty(jsonKey)) {
            var contact = jsonResponse[jsonKey];
        }
        var address = contact["address"];
        var birthday = contact["birthday"];
        var day = birthday["day"];
        var month = birthday["month"];

        if (day < 10) {
            day = "0" + day;
        }

        if (month < 10) {
            month = "0" + month;
        }

        var birthdayToString = day + "." + month + "." + birthday["year"];

        var tableContact = {
            id: document.createElement("td"),
            checkbox: document.createElement("td"),
            firstName: document.createElement("td"),
            lastName: document.createElement("td"),
            middleName: document.createElement("td"),
            birthday: document.createElement("td"),
            country: document.createElement("td"),
            city: document.createElement("td"),
            street: document.createElement("td"),
            index: document.createElement("td"),
            currentJob: document.createElement("td")
        };

        var checkbox = document.createElement("input");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("value", contact["id"]);

        tableContact.id.style.display = "none";
        tableContact.checkbox.append(checkbox);
        tableContact.firstName.textContent = contact["firstName"];
        tableContact.lastName.textContent = contact["lastName"];
        tableContact.middleName.textContent = contact["middleName"];
        tableContact.birthday.textContent = birthdayToString;
        tableContact.country.textContent = address["country"];
        tableContact.city.textContent = address["city"];
        tableContact.street.textContent = address["street"];
        tableContact.index.textContent = address["postIndex"];
        tableContact.currentJob.textContent = contact["currentJob"];

        for (var propertyTdKey in tableContact) {
            tr.append(tableContact[propertyTdKey]);
        }

        table.append(tr);

    }

}


// --- delete selected contacts ---
function deleteContacts() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
    var deleteList = [];

    for (var i = 0; i < checkboxes.length; i++) {
        deleteList.push(checkboxes[i].getAttribute("value"));
    }

    if (deleteList.length === 0) {
        return null;
    } else {
        return deleteList;
    }

}
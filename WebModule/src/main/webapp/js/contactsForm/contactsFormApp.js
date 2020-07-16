"use strict";
var findAllUrl = "http://localhost:8080/v1/contactsList/contacts/findAll";
var deleteAllUrl = "http://localhost:8080/v1/contactsList/contacts/deleteAll";
var updateUrl = "#";

sendRequest(findAllUrl + "?page=1&pageLimit=10", "GET", null, createTableContacts, null);

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
function createTableContacts(response) {

    var tBody = document.createElement("tbody");
    tBody.id = "contacts-table";

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
            fullName: document.createElement("td"),
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

        var linkUpdate = document.createElement("a");
        linkUpdate.setAttribute("href", updateUrl);
        linkUpdate.textContent = contact["firstName"] + " " + contact["lastName"] + " " + contact["middleName"];
        tableContact.fullName.append(linkUpdate);

        tableContact.birthday.textContent = birthdayToString;
        tableContact.country.textContent = address["country"];
        tableContact.city.textContent = address["city"];
        tableContact.street.textContent = address["street"];
        tableContact.index.textContent = address["postIndex"];
        tableContact.currentJob.textContent = contact["currentJob"];

        for (var propertyTdKey in tableContact) {
            tr.append(tableContact[propertyTdKey]);
        }


        tBody.append(tr);
        var table = document.getElementsByClassName("table")[0];
        table.append(tBody);


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
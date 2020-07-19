"use strict";

var classNameTr = "trContact";
var classNameTd = "tdContact";
var idContactsBody = "contacts-body";
var countOfTd = 8;

var trBodyArr = [];

function createTableContactBody(rowCounts) {

    var tBody = document.getElementById(idContactsBody);

    for (var i = 0; i < rowCounts; i++) {
        var tr = document.createElement("tr");
        tr.className = classNameTr;

        for (var j = 0; j < countOfTd; j++) {
            var td = document.createElement("td");
            td.className = classNameTd;

            if (j === 0) {
                var checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.disabled = true;
                td.append(checkbox);
            }

            if (j === 1) {
                var linkUpdate = document.createElement("a");
                linkUpdate.setAttribute("href", updateUrl);
                td.append(linkUpdate);
            }

            tr.append(td);

        }

        trBodyArr.push(tr);
        tBody.append(tr);

    }

}

function fillTableContacts(response) {

    var jsonResponse = response;

    var count = 0;

    for (var jsonKey in jsonResponse) {
        var tr = trBodyArr[count];
        count++;

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
            checkbox: tr.children[0],
            fullName: tr.children[1],
            birthday: tr.children[2],
            country: tr.children[3],
            city: tr.children[4],
            street: tr.children[5],
            index: tr.children[6],
            currentJob: tr.children[7]
        };


        tableContact.checkbox.children[0].value = contact["id"];
        tableContact.checkbox.children[0].disabled = false;
        tableContact.fullName.children[0].textContent = contact["firstName"] + " " + contact["lastName"] + " " + contact["middleName"];
        tableContact.birthday.textContent = birthdayToString;
        tableContact.country.textContent = address["country"];
        tableContact.city.textContent = address["city"];
        tableContact.street.textContent = address["street"];
        tableContact.index.textContent = address["postIndex"];
        tableContact.currentJob.textContent = contact["currentJob"];

    }

}

function setEmptyValues() {

    for (var i = 1; i < trBodyArr.length; i++) {
        for (var j = 0; j < countOfTd; j++) {
            if (j === 0) {
                trBodyArr[i].children[0].children[0].value = "";
                trBodyArr[i].children[0].children[0].disabled = true;
            } else if (j === 1) {
                trBodyArr[i].children[j].children[0].textContent = "";
            } else {
                trBodyArr[i].children[j].textContent = "";
            }
        }
    }
}

function deleteContacts() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
    var deleteList = [];

    for (var i = 0; i < checkboxes.length; i++) {
        deleteList.push(checkboxes[i].value);
    }

    if (deleteList.length === 0) {
        return null;
    } else {
        return deleteList;
    }

}
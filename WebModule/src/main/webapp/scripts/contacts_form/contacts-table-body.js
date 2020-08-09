"use strict";

var classNameTr = "trContact";
var classNameTd = "tdContact";
var idContactsBody = "contacts-body";
var countOfTd = 8;

var updateUrl = "create-edit-form.html";

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
                td.appendChild(checkbox);
            }

            if (j === 1) {
                var linkUpdate = document.createElement("a");
                linkUpdate.setAttribute("href", "");
                td.appendChild(linkUpdate);
            }

            tr.appendChild(td);

        }

        trBodyArr.push(tr);
        tBody.appendChild(tr);

    }

}

function fillTableContacts(response) {

    var count = 0;

    for (var jsonKey in response) {
        var tr = trBodyArr[count];
        count++;

        if (response.hasOwnProperty(jsonKey)) {
            var contact = response[jsonKey];
        }

        var address = contact["address"];
        var birthday = contact["birthday"];

        var birthdayToString = "";

        if (birthday !== undefined) {
            var day = birthday["day"];
            var month = birthday["month"];

            if (day < 10) {
                day = "0" + day;
            }

            if (month < 10) {
                month = "0" + month;
            }

            birthdayToString = day + "." + month + "." + birthday["year"];
        }

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


        if (contact["middleName"] === undefined) {
            contact["middleName"] = "";
        }

        tableContact.checkbox.className = contact["email"];
        tableContact.checkbox.children[0].value = contact["id"];
        tableContact.checkbox.children[0].disabled = false;
        tableContact.fullName.children[0].textContent = contact["firstName"] + " " + contact["lastName"] + " " + contact["middleName"];
        tableContact.fullName.querySelector("a").href = updateUrl + "?id=" + contact["id"];
        tableContact.birthday.textContent = birthdayToString;
        tableContact.country.textContent = address["country"];
        tableContact.city.textContent = address["city"];
        tableContact.street.textContent = address["street"];

        tr.id = contact["id"];

        if (address["postIndex"] !== 0) {
            tableContact.index.textContent = address["postIndex"];
        } else {
            tableContact.index.textContent = "";
        }
        tableContact.currentJob.textContent = contact["currentJob"];

    }

}

function setEmptyValues() {

    for (var i = 0; i < trBodyArr.length; i++) {
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

function getCheckedCheckbox() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
    var list = [];

    for (var i = 0; i < checkboxes.length; i++) {
        list.push(checkboxes[i].value);
    }

    return list;

}

function clearCheckboxes() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]:checked");

    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = false;
    }

}

function selectAllCheckboxes() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]");

    if (isSelectedCheckboxes) {
        for (var j = 0; j < checkboxes.length; j++) {
            checkboxes[j].checked = false;
        }

        isSelectedCheckboxes = false;

    } else {
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].disabled === false) {
                checkboxes[i].checked = true;
            }
        }

        isSelectedCheckboxes = true;
    }

}
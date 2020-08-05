"use strict";

// --- Get and validate data from inputs ---

function getDataFromPhoneForm() {

    var phone = {};

    var country = document.getElementById("countryCode-form");
    if (country.value.length === 0 || country.value.length > 8) {
        alert("Country code may be between 1 and 8 characters.");
        return false;
    }

    var operator = document.getElementById("operatorCode-form");
    if (operator.value.length === 0 || operator.value.length > 8) {
        alert("Operator code may be between 1 and 8 characters.");
        return false;
    }

    var number = document.getElementById("number-form");
    if (number.value.length < 4 || number.value.length > 16) {
        alert("Phone number may be between 4 and 16 characters.");
        return false;
    }

    var checkBox = document.getElementById("mobile-form").checked ? "mobile" : "home";

    var comment = document.getElementById("comment-form");
    if (comment.value.length > 45) {
        alert("Comment is too long (maximum is 45 characters).");
        return false;
    }

    phone["countryCode"] = country.value;
    phone["operatorCode"] = operator.value;
    phone["number"] = number.value;
    phone["type"] = checkBox;
    phone["comment"] = comment.value;

    return phone;

}

// --- Get checked checkboxes and delete them ---

function deletePhoneRow() {

    var items = document.querySelectorAll(".checkbox-phone");

    if (items.length === 0) {
        alert("You should add a contact phone.");
        return;
    }

    var checkedExist = false;

    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            checkedExist = true;
            items[i].parentNode.parentNode.removeChild(items[i].parentNode);
        }
    }

    if (!checkedExist) {
        alert("You should choose a contact phone.");
    }

}

// --- Get phone data from the table ---

function getDataForUpdatePhone() {

    var items = document.querySelectorAll(".checkbox-phone");

    if (items.length === 0) {
        alert("You should add a contact phone.");
        return null;
    }

    var checkedExist = 0;
    var phoneRow = {};
    var id = -1;

    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            checkedExist++;
            phoneRow = items[i].parentNode;
            id = items[i].parentNode.id;
        }
    }

    if (checkedExist === 0) {
        alert("You should choose a contact phone.");
        return null;
    } else if (checkedExist !== 1) {
        alert("You should choose one contact phone.");
        return null;

    } else {

        var phone = {};

        var fullNumber = phoneRow.children[1].textContent;
        var strings = fullNumber.split(" ");
        phone["id"] = id;
        phone["countryCode"] = strings[0];
        phone["operatorCode"] = strings[1];
        phone["number"] = strings[2];
        phone["type"] = phoneRow.children[2].textContent;
        phone["comment"] = phoneRow.children[3].textContent;

        return phone;

    }

}

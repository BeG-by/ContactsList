"use strict";

function getDataFromPhoneForm() {

    var phone = {};

    var country = document.getElementById("countryCode-form");
    if (country.value.length === 0 || country.value.length > 5) {
        alert("Country code should be 1-5 symbols !");
        return false;
    }

    var operator = document.getElementById("operatorCode-form");
    if (operator.value.length === 0 || operator.value.length > 5) {
        alert("Operator code should be 1-5 symbols !");
        return false;
    }

    var number = document.getElementById("number-form");
    if (number.value.length < 5 || number.value.length > 16) {
        alert("Phone number should be 5-16 symbols !");
        return false;
    }

    var checkBox = document.getElementById("mobile-form").checked ? "mobile" : "home";

    var comment = document.getElementById("comment-form");
    if (comment.value.length > 60) {
        alert("Max length of comment is 60 symbols !");
        return false;
    }

    phone["countryCode"] = country.value;
    phone["operatorCode"] = operator.value;
    phone["number"] = number.value;
    phone["type"] = checkBox;
    phone["comment"] = comment.value;

    return phone;

}

function deletePhoneRow() {

    var items = document.querySelectorAll(".checkbox-phone");

    if (items.length === 0) {
        alert("You should add a contact phone !");
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
        alert("You should choose a contact phone !");
    }

}

function getDataForUpdatePhone() {

    var items = document.querySelectorAll(".checkbox-phone");

    if (items.length === 0) {
        alert("You should add a contact phone !");
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
        alert("You should choose a contact phone !");
        return null;
    } else if (checkedExist !== 1) {
        alert("You should choose one contact phone !");
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

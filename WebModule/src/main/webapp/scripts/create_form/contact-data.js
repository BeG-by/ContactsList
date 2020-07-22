"use strict";

//--- Contact, address ---

function getFormContactData() {

    var contact = {};
    var birthday = {};
    var address = {};
    var phonesArr = [];

    var firstName = document.getElementById("firstName").value;

    if (firstName.length < 1 || firstName.length > 16) {
        alert("First name length should be 1-16 symbols !");
        return false;
    }

    var lastName = document.getElementById("lastName").value;

    if (lastName.length < 1 || lastName.length > 16) {
        alert("Last name length should be 1-16 symbols !");
        return false;
    }

    var middleName = document.getElementById("middleName").value;

    if (middleName.length > 16) {
        alert("Middle name length should be less than 16 !");
        return false;
    }

    var day = document.getElementById("day").value;

    if (!matchStrict(/^0[1-9]|[12][0-9]|3[01]/, day)) {
        alert("Incorrect day !");
        return false;
    }

    var month = document.getElementById("month").value;

    if (!matchStrict(/0[1-9]|1[0-2]/, month)) {
        alert("Incorrect month !");
        return false;
    }

    var year = document.getElementById("year").value;

    if (!matchStrict(/[12]\d{3}/, year)) {
        alert("Incorrect year !");
        return false;
    }

    var sex = document.getElementById("male").checked ? "male" : "female";
    var nationality = document.getElementById("nationality").value;

    if (nationality.length > 16) {
        alert("Nationality length should be less than 16 !");
        return false;
    }

    var maritalStatus = document.getElementById("single").checked ? "single" : "married";
    var webSiteUrl = document.getElementById("urlWebSite").value;
    var email = document.getElementById("email").value;

    var regExpEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (email !== "" && !regExpEmail.test(email)) {
        alert("Incorrect email !");
        return false;
    }

    var currentJob = document.getElementById("currentJob").value;

    if (currentJob.length > 16) {
        alert("Job name length should be less than 16 !");
        return false;
    }

    var country = document.getElementById("country").value;

    if (country.length > 16) {
        alert("Country length should be less than 16 !");
        return false;
    }

    var city = document.getElementById("city").value;

    if (city.length > 16) {
        alert("Country length should be less than 16 !");
        return false;
    }

    var street = document.getElementById("street").value;

    if (street.length > 16) {
        alert("Street length should be less than 16 !");
        return false;
    }

    var postIndex = document.getElementById("postIndex").value;

    if (!matchStrict(/\d{0,10}/, postIndex)) {
        alert("Index length should be less than 10 !");
        return false;
    } else if (postIndex === "") {
        postIndex = -1;
    }

    contact["firstName"] = firstName;
    contact["lastName"] = lastName;
    contact["middleName"] = middleName;
    birthday["day"] = day;
    birthday["month"] = month;
    birthday["year"] = year;
    contact["birthday"] = birthday;
    contact["sex"] = sex;
    contact["nationality"] = nationality;
    contact["maritalStatus"] = maritalStatus;
    contact["urlWebSite"] = webSiteUrl;
    contact["email"] = email;
    contact["currentJob"] = currentJob;
    address["country"] = country;
    address["city"] = city;
    address["street"] = street;
    address["postIndex"] = postIndex;
    contact["address"] = address;


    var phonesTr = document.querySelectorAll(".phone-number");

    for (var i = 0; i < phonesTr.length; i++) {
        var tr = phonesTr[i];
        var phone = {};
        var number = tr.children[1].textContent.split(" ");
        phone["countryCode"] = number[0];
        phone["operatorCode"] = number[1];
        phone["number"] = number[2];
        phone["type"] = tr.children[2].textContent;
        phone["comment"] = tr.children[3].textContent;

        phonesArr.push(phone);
    }

    contact["phoneList"] = phonesArr;

    return contact;

}

// function isValidDate(year, month, day) {
//     month = month - 1;
//     var date = new Date(year, month, day);
//     return date.getFullYear() === year && date.getMonth() === month && date.getDate() === day;
//
// }

// var number1 = new Date(year, month - 1, day);
// console.log(number1.getTime());
// console.log(number1);
// console.log(number1.toISOString());
// console.log(number1.getFullYear());
// console.log(number1.getMonth());
// console.log(number1.getDay());
// console.log(number1.getDate());

function matchStrict(regExp, str) {
    var match = str.match(regExp);
    return match && str === match[0];
}

// --- Comments for attachments ---

function getCommentsAttachment() {

    var comments = document.querySelectorAll(".comment-att");

    var arrComments = [];

    for (var i = 0; i < comments.length; i++) {
        arrComments.push(comments[i].textContent);
    }

    return arrComments;

}
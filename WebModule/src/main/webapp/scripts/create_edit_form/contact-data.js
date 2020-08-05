"use strict";

//--- Contact, address ---

function getFormContactData() {

    var contact = {};
    var birthday = {};
    var address = {};
    var phonesArr = [];

    var firstName = document.getElementById("firstName").value;
    if (!validateInput("First name", firstName, 1, 16)) return false;

    var lastName = document.getElementById("lastName").value;
    if (!validateInput("Last name", lastName, 1, 16)) return false;

    var middleName = document.getElementById("middleName").value;
    if (!validateInput("Middle name", middleName, 0, 16)) return false;


    var day = document.getElementById("day").value;
    var month = document.getElementById("month").value;
    var year = document.getElementById("year").value;

    if (day !== "" || month !== "" || year !== "") {

        if (!matchStrict(/0[1-9]|[12][0-9]|3[01]/, day)) {
            alert("Incorrect day.");
            return false;
        }

        if (!matchStrict(/0[1-9]|1[0-2]/, month)) {
            alert("Incorrect month.");
            return false;
        }


        if (!matchStrict(/[12]\d{3}/, year)) {
            alert("Incorrect year.");
            return false;
        }

        var today = new Date();
        var inputDate = new Date(year, month - 1, day);

        if (year < 1900 || inputDate > today) {
            alert("Your birthday can't be in " + inputDate.getDate() + "-" + (inputDate.getMonth() + 1) + "-" + inputDate.getFullYear());
            return false;
        }

        if (!isValidDate(year, month, day)) {
            alert("This date doesn't exist.");
            return false;
        }

    } else {
        day = 1;
        month = 11;
        year = 1111;
    }

    var male = document.getElementById("male");
    var female = document.getElementById("female");
    var sex = "";

    if (male.checked) {
        sex = "male";
    } else if (female.checked) {
        sex = "female";
    }


    var nationality = document.getElementById("nationality").value;
    if (!validateInput("Nationality", nationality, 0, 16)) return false;


    var single = document.getElementById("single");
    var married = document.getElementById("married");
    var maritalStatus = "";

    if (single.checked) {
        maritalStatus = "single";
    } else if (married.checked) {
        maritalStatus = "married";
    }


    var webSiteUrl = document.getElementById("urlWebSite").value;

    if (webSiteUrl.length > 200) {
        alert("URL is too long (maximum is 200 characters).");
        return false;
    }

    var email = document.getElementById("email").value;

    var regExpEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (!regExpEmail.test(email)) {
        alert("Incorrect email.");
        return false;
    }

    var currentJob = document.getElementById("currentJob").value;

    if (currentJob.length > 100) {
        alert("Job is too long (maximum is 100 characters).");
        return false;
    }

    var country = document.getElementById("country").value;
    if (!validateInput("Country", country, 0, 16)) return false;

    var city = document.getElementById("city").value;
    if (!validateInput("City", city, 0, 16)) return false;

    var street = document.getElementById("street").value;

    if (street.length > 24) {
        alert("Street is too long (maximum is 24 characters).");
        return false;
    }

    var postIndex = document.getElementById("postIndex").value;

    if (!matchStrict(/\d{0,10}/, postIndex)) {
        alert("Index may only contain digits (between 1 and 10 digits).");
        return false;
    } else if (postIndex === "") {
        postIndex = null;
    }


    if (updateContactId != null) {
        contact["id"] = updateContactId;
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
    if (requestAvatar != null) {
        contact["imageName"] = requestAvatar.name;
    }

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

function isValidDate(year, month, day) {
    month = month - 1;
    var date = new Date(year, month, day);
    return date.getFullYear() === Number(year) && date.getMonth() === Number(month) && date.getDate() === Number(day);
}

function validateInput(propertyName, value, minLength, maxLength) {

    if (value.length < minLength || value.length > maxLength) {
        alert(propertyName + " may be between " + minLength + " and " + maxLength + " characters long.");
        return false;
    }

    if (!matchStrict(/[A-Za-zА-Яа-я\s-]*/, value) || value.startsWith("-") || value.endsWith("-") || value.startsWith(" ")
        || value.endsWith(" ") || value.split("-").length > 2 || value.split(" ").length > 2) {

        alert(propertyName + " may only contain English or Russian characters or single hyphens or space, and cannot begin or end with a hyphen or space.");
        return false;
    }

    return true;

}

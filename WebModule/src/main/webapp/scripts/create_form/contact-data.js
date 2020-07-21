"use strict";

//--- Contact, address ---

function getFormContactData() {

    var contactData = document.getElementById("contact-data");
    var inputs = contactData.querySelectorAll("input");
    var phonesTr = document.querySelectorAll(".phone-number");

    var contact = {};
    var birthday = {};
    var address = {};
    var phonesArr = [];

    for (var i = 0; i < inputs.length; i++) {
        var formElement = inputs[i];
        if (formElement.type === "radio" && !formElement.checked) {
            continue;
        }

        if (formElement.name === "day" || formElement.name === "month" || formElement.name === "year") {
            birthday[formElement.name] = formElement.value;
            continue;
        }

        if (formElement.name === "country" || formElement.name === "city" || formElement.name === "street" || formElement.name === "postIndex") {
            address[formElement.name] = formElement.value;
            continue;
        }

        contact[formElement.name] = formElement.value;
    }


    contact["birthday"] = birthday;
    contact["address"] = address;

    if (phonesTr !== undefined) {

        for (i = 0; i < phonesTr.length; i++) {
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

    }

    console.log(contact);

    return contact;

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
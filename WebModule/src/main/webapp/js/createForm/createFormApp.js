"use strict";

var saveUrl = "http://localhost:8080/contactsList/contacts/save";

document.getElementById("create-btn").addEventListener("click", function () {
    sendRequest(saveUrl, "POST", getFormContactData(), null, "Contact has been added !");
});

document.getElementById("add-phone-btn").addEventListener("click", function () {
    showPhoneForm();
});


function getFormContactData() {

    var contactForm = document.forms.namedItem("contact-form");
    var addressForm = document.forms.namedItem("address-form");
    var phonesTr = document.querySelectorAll(".phone-number");
    console.log(phonesTr);

    var contact = {};
    var address = {};
    var phonesArr = [];

    for (var i = 0; i < contactForm.length; i++) {
        var formElement = contactForm[i];
        if (formElement.type === "radio" && !formElement.checked) {
            continue;
        }

        if (formElement.name === "birthday") {
            var arr = formElement.value.split("-");
            contact["birthday"] = {
                year: arr[0],
                month: arr[1],
                day: arr[2]
            };

            continue;
        }

        contact[formElement.name] = formElement.value;
    }

    for (var i = 0; i < addressForm.length; i++) {
        address[addressForm[i].name] = addressForm[i].value;
    }

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





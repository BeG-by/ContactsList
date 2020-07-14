"use strict";

var saveUrl = "http://localhost:8080/contactsList/contacts/save";

document.getElementById("create-btn").addEventListener("click", function () {
    sendRequest(saveUrl, "POST", getFormContact(), null, "Contact has been added !");
});


function getFormContact() {

    var contactForm = document.forms.namedItem("contact-form");
    var addressForm = document.forms.namedItem("address-form");

    var contact = {};
    var address = {};

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

    return contact;

}



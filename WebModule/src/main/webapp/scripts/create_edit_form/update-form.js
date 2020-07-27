"use strict";

var href = window.location.href;
var url = new URL(href);
var id = url.searchParams.get("id");

var updateUrl = "http://localhost:8080/api/v1/contactsList/contacts/findById";

if (id != null) {
    sendRequest(updateUrl + "?contactId=" + id, "GET", fillContact);
}

function fillContact(contact) {

    loadImage(contact);
    switchDisplay();

    var flag = true;

    for (var property in contact) {

        var address = contact["address"];
        var birthday = contact["birthday"];


        if (birthday !== undefined && flag) {

            if (birthday["day"] < 10) {
                birthday["day"] = "0" + birthday["day"];
            }

            if (birthday["month"] < 10) {
                birthday["month"] = "0" + birthday["month"];
            }

            flag = false;

        }

        var inputs = document.querySelectorAll("input");

        label:
            for (var i = 0; i < inputs.length; i++) {
                var input = inputs[i];

                switch (input.id) {
                    case "male":
                        if (contact["sex"] === "male") {
                            inputs[i].checked = true;
                        }
                        continue;
                    case "female":
                        if (contact["sex"] === "female") {
                            inputs[i].checked = true;
                        }
                        continue;
                    case "single":
                        if (contact["maritalStatus"] === "single") {
                            inputs[i].checked = true;
                        }
                        continue;
                    case "married":
                        if (contact["maritalStatus"] === "married") {
                            inputs[i].checked = true;
                        }
                        continue;
                }


                if (property === input.id) {
                    input.value = contact[input.id];
                    continue;
                }

                for (var key in birthday) {
                    if (key === input.id) {
                        input.value = birthday[key];
                        continue label
                    }
                }

                for (var key in address) {
                    if (key === input.id) {
                        input.value = address[key];
                        continue label;
                    }
                }
            }
    }

    fillPhones(contact);

}


function switchDisplay() {
    document.getElementById("create-title").style.display = "none";
    document.getElementById("update-title").style.display = "";
    document.getElementById("create-btn").style.display = "none";
    document.getElementById("update-btn").style.display = "";
}

function loadImage(contact) {
    var avatar = document.getElementById("avatar");

    if (contact["imageName"] != null) {
        avatar.src = "data:image/png;base64," + contact["imageName"];
    }
}


function fillPhones(contact) {

    var phoneList = contact["phoneList"];
    console.log(phoneList);

    for (var i = 0; i < phoneList.length; i++) {
        createPhoneRow(phoneList[i], phoneList[i]["id"]);
    }

}
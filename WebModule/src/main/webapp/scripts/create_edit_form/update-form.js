"use strict";

var href = window.location.href;
var url = new URL(href);
var updateContactId = url.searchParams.get("id");


if (updateContactId != null) {
    sendRequest(findByIdUrl + "?contactId=" + updateContactId, "GET", fillContact);
}

function fillContact(contact) {

    loadImage(contact);
    switchDisplay();

    var address = contact["address"];
    var birthday = contact["birthday"];

    if (address["postIndex"] === 0) {
        address["postIndex"] = "";
    }

    if (birthday !== undefined) {

        if (birthday["day"] < 10) {
            birthday["day"] = "0" + birthday["day"];
        }

        if (birthday["month"] < 10) {
            birthday["month"] = "0" + birthday["month"];
        }
    }

    for (var property in contact) {

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
    fillAttachments(contact)

}


function switchDisplay() {
    document.getElementById("create-title").style.display = "none";
    document.getElementById("update-title").style.display = "";
    document.getElementById("create-btn").style.display = "none";
    document.getElementById("update-btn").style.display = "";
    document.getElementById("download-att-btn").style.display = "";
}

function loadImage(contact) {
    var avatar = document.getElementById("avatar");

    if (contact["imageName"] != null) {
        avatar.src = "data:image/png;base64," + contact["imageName"];
    }
}


function fillPhones(contact) {

    var phoneList = contact["phoneList"];

    for (var i = 0; i < phoneList.length; i++) {
        createPhoneRow(phoneList[i], phoneList[i]["updateContactId"]);
    }

}

function fillAttachments(contact) {

    var attList = contact["attachmentList"];

    for (var i = 0; i < attList.length; i++) {
        createRowAttachment(attList[i]["comment"], attList[i]);
    }

}
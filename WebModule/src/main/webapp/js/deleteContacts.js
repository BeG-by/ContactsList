"use strict";

function deleteContacts() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
    var deleteList = [];

    for (var i = 0; i < checkboxes.length; i++) {
        deleteList.push(checkboxes[i].getAttribute("value"));
    }

    if (deleteList.length === 0) {
        alert("You should choose the contacts");
    } else {
        return deleteList;
    }

}
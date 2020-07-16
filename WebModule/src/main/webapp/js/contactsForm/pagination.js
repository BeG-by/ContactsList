"use strict";

var countAllUrl = "http://localhost:8080/v1/contactsList/contacts/countAll";
var findAllUrlPag = "http://localhost:8080/v1/contactsList/contacts/findAll";

sendRequestForPagination(createPagination);

function createPagination(contactsCount, pageLimit) {

    var countOfPages = Math.ceil(contactsCount / pageLimit) + 1;

    var paginationDiv = document.getElementById("pagination-div");
    var paginationButtons = document.createElement("div");


    if (countOfPages < 10) {

        for (var i = 1; i < countOfPages; i++) {
            var button = document.createElement("button");

            button.textContent = i.toString();

            button.addEventListener("click", function () {
                document.getElementById("contacts-table").remove();
                sendRequest(findAllUrlPag + "?page=" + this.textContent + "&pageLimit=" + pageLimit, "GET", null, createTableContacts, null);
            });

            paginationButtons.append(button);

        }

        paginationDiv.append(paginationButtons);

    }

}

function sendRequestForPagination(callback) {

    var xhr = new XMLHttpRequest();

    xhr.open("GET", countAllUrl);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Accept", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            if (callback !== null) {
                callback(xhr.response, 10);
            }

        }
    };

    xhr.send();

}


"use strict";

var findAllUrl = "http://localhost:8080/api/v1/contactsList/contacts/findAll";
var deleteAllUrl = "http://localhost:8080/api/v1/contactsList/contacts/deleteAll";
var updateUrl = "#";
var countAllUrl = "http://localhost:8080/api/v1/contactsList/contacts/countAll";
var pageLimit = 10;

createTableContactBody(pageLimit);
sendRequest(findAllUrl + "?page=1&pageLimit=10", "GET", fillTableContacts);
sendRequest(countAllUrl, "GET", createPagination);


document.getElementById("delete-btn").addEventListener("click", function () {
    var idList = getCheckedCheckbox();

    if (idList != null) {
        sendRequestWithBody(deleteAllUrl, "DELETE", idList, false, "Contacts have been deleted");
    } else {
        alert("You should choose the contacts !");
    }
});


// --- Pagination ---

function createPagination(contactsCountResponse) {

    var countOfPages = Math.ceil(contactsCountResponse / pageLimit) + 1;

    var paginationDiv = document.getElementById("pagination-div");
    var paginationButtons = document.createElement("div");

    if (countOfPages < 10) {

        for (var i = 1; i < countOfPages; i++) {
            var button = document.createElement("button");

            button.textContent = i.toString();

            button.addEventListener("click", function () {
                setEmptyValues();
                sendRequest(findAllUrl + "?page=" + this.textContent + "&pageLimit=" + pageLimit, "GET", fillTableContacts);
            });

            paginationButtons.appendChild(button);

        }

        paginationDiv.appendChild(paginationButtons);

    }

}
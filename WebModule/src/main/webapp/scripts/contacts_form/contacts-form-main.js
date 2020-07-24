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
    var paginationList = document.createElement("ul");
    paginationList.className = "pagination pagination-sm";

    // if (countOfPages < 10) {

        for (var i = 1; i < countOfPages; i++) {
            var li = document.createElement("li");
            li.className = "page-item";

            var a = document.createElement("a");
            a.className = "page-link";
            a.textContent = i.toString();
            li.appendChild(a);

            a.addEventListener("click", function (e) {
                e.preventDefault();
                setEmptyValues();
                sendRequest(findAllUrl + "?page=" + this.textContent + "&pageLimit=" + pageLimit, "GET", fillTableContacts);
            });

            paginationList.appendChild(li);

        }

        paginationDiv.appendChild(paginationList);

    // }

}
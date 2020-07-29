"use strict";

createTableContactBody(pageLimit);
sendRequest(findAllUrl + "?page=" + currentPage + "&pageLimit=" + pageLimit, "GET", fillTableContacts);
sendRequest(countAllUrl, "GET", createPagination);


document.getElementById("delete-btn").addEventListener("click", function () {
    var idList = getCheckedCheckbox();

    if (idList.length > 0) {
        sendRequestWithBody(deleteAllUrl, "DELETE", idList, false, "Contacts have been deleted");
    } else {
        alert("You should choose the contacts !");
    }
});

document.getElementById("update-btn").addEventListener("click", function (e) {

    var idList = getCheckedCheckbox();

    if (idList.length === 1) {
        this.parentNode.href = findByIdUrl + "?id=" + idList[0];
        this.click();

    } else if (idList.length === 0) {
        e.preventDefault();
        alert("You should choose the contacts !");
    } else if (idList.length > 1) {
        e.preventDefault();
        alert("You should choose one contact !");
    }

});


// --- Pagination ---



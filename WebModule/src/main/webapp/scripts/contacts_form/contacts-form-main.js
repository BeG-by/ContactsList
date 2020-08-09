"use strict";

var updateUrlHref = "create-edit-form.html";

createTableContactBody(pageLimit);

sendRequest(findAllUrl + "?page=" + currentPage + "&pageLimit=" + pageLimit, "GET", fillTableContacts);
sendRequest(countAllUrl, "GET", createPagination);

sendRequest(findAllTemplatesUrl, "GET", getTemplatesContent);


document.getElementById("delete-btn").addEventListener("click", function (e) {
    var idList = getCheckedCheckbox();

    if (idList.length > 0) {
        sendRequestWithBody(deleteAllUrl, "DELETE", idList, false, "Contacts have been deleted.");
    } else {
        e.preventDefault();
        alert("You should choose the contacts !");
    }
});

document.getElementById("update-btn").addEventListener("click", function (e) {

    var idList = getCheckedCheckbox();

    if (idList.length === 1) {
        this.parentNode.href = updateUrlHref + "?id=" + idList[0];
        this.click();

    } else if (idList.length === 0) {
        e.preventDefault();
        alert("You should choose the contacts !");
    } else if (idList.length > 1) {
        e.preventDefault();
        alert("You should choose one contact !");
    }

});

document.getElementById("filter-btn").addEventListener("click", function (e) {

    e.preventDefault();

    var filterForm = document.getElementsByClassName("window-main-filter")[0];

    if (filterForm === undefined) {
        createFilterForm();
    } else {
        filterForm.style.display = "";
    }


});

document.getElementById("email-btn").addEventListener("click", function (e) {

    e.preventDefault();

    var emails = getEmails();

    if (emails.length > 0) {
        createEmailForm(emails);
    } else {
        alert("You should choose the contacts or selected contacts haven't email.");
    }


});

document.getElementById("logo").addEventListener("click", function () {
    location.reload();
});

document.getElementById("select-all").addEventListener("click", selectAllCheckboxes);

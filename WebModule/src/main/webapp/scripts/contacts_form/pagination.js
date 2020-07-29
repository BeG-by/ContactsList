"use strict";

function createPagination(contactsCountResponse) {

    var countOfPages = Math.ceil(contactsCountResponse / pageLimit) + 1;

    var paginationDiv = document.getElementById("pagination-div");
    var paginationList = document.createElement("ul");
    paginationList.className = "pagination pagination-sm";
    paginationList.id = "paginationList";

    // --- Active page ---

    var active = document.createElement("li");
    active.className = "page-item active";
    active.setAttribute("aria-current", "page");
    var span1 = document.createElement("span");
    span1.className = "page-link";
    span1.textContent = currentPage;
    var span2 = document.createElement("span");
    span2.className = "sr-only";
    span2.textContent = "(current)";
    span1.appendChild(span2);
    active.appendChild(span1);
    paginationList.appendChild(active);

    // --- Pages ---

    var numberPage = Number(currentPage);

    var limitByCount = numberPage + countOfPages;
    var limitByPage = numberPage + pageLimit;
    var flag = false;
    var minus = numberPage - 1;
    var plus = numberPage + 1;


    for (var i = Number(currentPage) + 1; i < limitByCount - 1 && i < limitByPage; i++) {

        var li = document.createElement("li");
        li.className = "page-item";

        var a = document.createElement("a");
        a.className = "page-link";

        if (minus < 1) {
            flag = true
        } else if (plus >= countOfPages) {
            flag = false;
        }

        if (flag) {
            a.textContent = plus;
            plus++;
            li.appendChild(a);
            paginationList.appendChild(li);

        } else {
            a.textContent = minus;
            minus--;
            li.appendChild(a);
            paginationList.insertBefore(li, paginationList.firstChild);
        }

        flag = !flag;

        a.addEventListener("click", function (e) {
            e.preventDefault();
            clickToPageBtn(this.textContent, paginationList);
        });

    }


    // --- First and Last page ---

    var liFirst = createLi("First page", 1, paginationList);
    var liLast = createLi("Last page", countOfPages - 1, paginationList);

    paginationList.insertBefore(liFirst, paginationList.firstChild);
    paginationList.appendChild(liLast);

    paginationDiv.appendChild(paginationList);

}


function createLi(text, numberOfPage, list) {
    var li = document.createElement("li");
    li.className = "page-item pagination-btn";
    var a = document.createElement("a");
    a.className = "page-link";
    a.textContent = text;
    li.appendChild(a);

    a.addEventListener("click", function (e) {
        e.preventDefault();
        clickToPageBtn(numberOfPage, list)
    });

    return li;
}

function clickToPageBtn(page, list) {
    setEmptyValues();
    currentPage = page;
    sendRequest(findAllUrl + "?page=" + currentPage + "&pageLimit=" + pageLimit, "GET", fillTableContacts);
    sendRequest(countAllUrl, "GET", createPagination);
    localStorage.setItem("currentPage", currentPage);
    list.parentNode.removeChild(list);
}

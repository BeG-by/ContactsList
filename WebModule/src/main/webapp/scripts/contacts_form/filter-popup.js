"use strict";

var formFilterClassName = "window-form shadow p-3 mb-5 bg-white rounded";
var saveBtnClassName = "btn btn-success save-btn";
var cancelBtnClassName = "btn btn-danger close-btn";

function createFilterForm() {

    //--- Search data ---

    var mainWindow = document.createElement("div");
    mainWindow.className = "window-main-filter";

    var windowFilter = document.createElement("div");
    windowFilter.className = "window-filter";

    mainWindow.appendChild(windowFilter);

    var headerFilter = document.createElement("header");
    var h2 = document.createElement("h2");
    h2.innerText = "Filters";
    headerFilter.appendChild(h2);
    windowFilter.appendChild(headerFilter);

    var mainContent = document.createElement("main");
    var h3 = document.createElement("h3");
    h3.textContent = "Information";
    mainContent.appendChild(h3);
    windowFilter.appendChild(mainContent);


    var mainWrapper = document.createElement("div");
    mainWrapper.className = "main-wrapper";
    mainContent.appendChild(mainWrapper);

    var filterFirstForm = document.createElement("div");
    filterFirstForm.className = formFilterClassName;
    mainWrapper.appendChild(filterFirstForm);

    var firstName = createInput("text", "firstName", "firstName", "First name");
    filterFirstForm.appendChild(firstName);

    var lastName = createInput("text", "lastName", "lastName", "Last name");
    filterFirstForm.appendChild(lastName);

    var middleName = createInput("text", "middleName", "middleName", "Middle name");
    filterFirstForm.appendChild(middleName);

    var before = createBlockDate("Date of birth (Before)", "Before");
    var after = createBlockDate("Date of birth (After)", "After");
    filterFirstForm.appendChild(after);
    filterFirstForm.appendChild(before);

    var sexDiv = document.createElement("div");
    sexDiv.className = "form-check";
    var male = createRadioInput("sex", "male", "Male", false);
    var female = createRadioInput("sex", "female", "Female", false);
    sexDiv.appendChild(male);
    sexDiv.appendChild(female);
    filterFirstForm.appendChild(sexDiv);


    var filterSecondForm = document.createElement("div");
    filterSecondForm.className = formFilterClassName;
    mainWrapper.appendChild(filterSecondForm);

    var statusDiv = document.createElement("div");
    statusDiv.className = "form-check";

    var single = createRadioInput("maritalStatus", "single", "Single", false);
    var married = createRadioInput("maritalStatus", "married", "Married", false);
    statusDiv.appendChild(single);
    statusDiv.appendChild(married);
    filterSecondForm.appendChild(statusDiv);

    var nationality = createInput("text", "nationality", "nationality", "Nationality");
    filterSecondForm.appendChild(nationality);

    var country = createInput("text", "country", "country", "Country");
    filterSecondForm.appendChild(country);

    var city = createInput("text", "city", "city", "City");
    filterSecondForm.appendChild(city);

    var street = createInput("text", "street", "street", "Street");
    filterSecondForm.appendChild(street);

    var postIndex = createInput("text", "postIndex", "postIndex", "Post index", replaceLetters);
    filterSecondForm.appendChild(postIndex);


    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.className = saveBtnClassName;
    mainContent.appendChild(saveBtn);

    //--- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "Cancel";
    closeBtn.className = cancelBtnClassName;
    mainContent.appendChild(closeBtn);

    closeBtn.addEventListener("click", function () {
        localStorage.setItem("currentPage", "1");
        location.reload();
    });


    document.body.appendChild(mainWindow);


    saveBtn.addEventListener("click", function () {

        var searchRequest = getSearchRequest();

        currentPage = 1;

        setEmptyValues();

        sendFilterRequest(searchCountAllUrl, "POST", searchRequest, createPagination);
        sendFilterRequest(searchUrl + "?page=1&pageLimit=" + pageLimit, "POST", searchRequest, fillTableContacts);

        var paginationList = document.getElementById("paginationList");
        paginationList.parentNode.removeChild(paginationList);

        var filterBtn = document.getElementById("filter-btn");
        filterBtn.classList.add("filter-btn-on");

        filterRequestBody = searchRequest;
        mainWindow.style.display = "none";

    });

}

function createBlockDate(text, postFix) {
    var dataDiv = document.createElement("div");
    dataDiv.className = "form-group";
    var dataLabel = document.createElement("label");
    dataLabel.textContent = text;
    dataDiv.appendChild(dataLabel);
    var dataForm = document.createElement("div");
    dataForm.className = "date-form";
    var day = createDateDiv("day", "", 1, postFix, "2");
    var month = createDateDiv("month", "", 1, postFix, "2");
    var year = createDateDiv("year", "", 3, postFix, "4");
    dataForm.appendChild(day);
    dataForm.appendChild(month);
    dataForm.appendChild(year);
    dataDiv.appendChild(dataForm);
    return dataDiv;
}


function createDateDiv(name, placeHolder, size, postFix, maxLength) {

    var div = document.createElement("div");
    var input = document.createElement("input");
    input.type = "text";
    input.name = name;
    input.id = name + postFix;
    input.placeholder = placeHolder;
    input.maxLength = maxLength;
    input.setAttribute("aria-describedby", name + "Help");
    input.size = size;
    input.addEventListener("input", replaceLetters);
    div.appendChild(input);

    var small = document.createElement("small");
    small.id = name + "Help";
    small.className = "form-text text-muted";
    small.textContent = name;
    div.appendChild(small);
    return div;

}

function getSearchRequest() {

    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var middleName = document.getElementById("middleName").value;

    var dayBefore = document.getElementById("dayBefore").value;
    var monthBefore = document.getElementById("monthBefore").value;
    var yearBefore = document.getElementById("yearBefore").value;

    var dateBefore = yearBefore + "-" + monthBefore + "-" + dayBefore;

    if (dateBefore === "--") {
        dateBefore = "";
    }

    var dayAfter = document.getElementById("dayAfter").value;
    var monthAfter = document.getElementById("monthAfter").value;
    var yearAfter = document.getElementById("yearAfter").value;

    var dateAfter = yearAfter + "-" + monthAfter + "-" + dayAfter;

    if (dateAfter === "--") {
        dateAfter = "";
    }

    var male = document.getElementById("male");
    var female = document.getElementById("female");

    var sex = "";

    if (male.checked) {
        sex = "male"
    } else if (female.checked) {
        sex = "female";
    }

    var maritalStatus = "";

    var single = document.getElementById("single");
    var married = document.getElementById("married");

    if (single.checked) {
        maritalStatus = "single"
    } else if (married.checked) {
        maritalStatus = "married";
    }

    var nationality = document.getElementById("nationality").value;
    var country = document.getElementById("country").value;
    var city = document.getElementById("city").value;
    var street = document.getElementById("street").value;
    var postIndex = document.getElementById("postIndex").value;

    return {
        "firstName": firstName,
        "lastName": lastName,
        "middleName": middleName,
        "dateBefore": dateBefore,
        "dateAfter": dateAfter,
        "sex": sex,
        "maritalStatus": maritalStatus,
        "nationality": nationality,
        "country": country,
        "city": city,
        "street": street,
        "postIndex": postIndex
    };

}

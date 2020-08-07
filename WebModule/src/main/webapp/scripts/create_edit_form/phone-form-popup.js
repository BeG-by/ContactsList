"use strict";

var formPhoneClassName = "phone-form shadow p-3 mb-5 bg-white rounded";
var saveBtnPhoneClassName = "btn btn-success save-btn";
var cancelBtnPhoneClassName = "btn btn-danger close-btn";

function createPhoneForm(phoneDTO) {

    //--- Phone data ---

    var windowForm = document.createElement("div");
    windowForm.className = "window-form-phone show-form";

    var headerPhone = document.createElement("header");
    var h2 = document.createElement("h2");
    h2.innerText = "Contact phone";
    headerPhone.appendChild(h2);
    windowForm.appendChild(headerPhone);

    var mainContent = document.createElement("main");
    windowForm.appendChild(mainContent);

    var phoneForm = document.createElement("div");
    phoneForm.className = formPhoneClassName;
    mainContent.appendChild(phoneForm);

    var countryCode = createInput("text", "countryCode", "countryCode-form", "Country code", "8", replaceLetters);
    phoneForm.appendChild(countryCode);

    var operatorCode = createInput("text", "operatorCode", "operatorCode-form", "Operator code", "8", replaceLetters);
    phoneForm.appendChild(operatorCode);

    var number = createInput("text", "number", "number-form", "Phone Number", "16", replaceLetters);
    phoneForm.appendChild(number);

    var typePhoneDiv = document.createElement("div");
    typePhoneDiv.className = "form-check";

    var typeMobile = createRadioInput("type", "mobile-form", "Mobile", true);
    var typeHome = createRadioInput("type", "home-form", "Home", false);
    typePhoneDiv.appendChild(typeMobile);
    typePhoneDiv.appendChild(typeHome);
    phoneForm.appendChild(typePhoneDiv);

    var comment = createTextArea("comment-form", "Comment");
    comment.className = "comment-phone";

    comment.querySelector("textarea").maxLength = "45";

    phoneForm.appendChild(comment);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.className = saveBtnPhoneClassName;
    phoneForm.appendChild(saveBtn);

    //--- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "Cancel";
    closeBtn.className = cancelBtnPhoneClassName;
    phoneForm.appendChild(closeBtn);

    closeBtn.addEventListener("click", function () {

        mainWindow.classList.add("delete-window");
        windowForm.style.transform = "translate(-50%,-100%)";

        setTimeout(function () {
            mainWindow.parentNode.removeChild(mainWindow);
        }, 650);

    });

    // --- Show window ---

    var mainWindow = document.createElement("div");
    mainWindow.appendChild(windowForm);
    mainWindow.className = "window-main-phone";

    mainWindow.classList.add("show-window");
    document.body.appendChild(mainWindow);

    if (phoneDTO !== null) {
        fillPhoneInputs(phoneDTO);
    }

    saveBtn.addEventListener("click", function (e) {

        var phone = getDataFromPhoneForm();

        if (phone === false) {
            e.preventDefault();
            return;
        }

        mainWindow.classList.add("delete-window");
        windowForm.style.transform = "translate(-50%,-100%)";

        phoneDTO === null ? createPhoneRow(phone) : updatePhoneRow(phone, phoneDTO["id"]);

        setTimeout(function () {
            mainWindow.parentNode.removeChild(mainWindow);
        }, 650);


    });

}


// --- Insert phone row into the table ---

function createPhoneRow(phone, id) {

    var phoneTable = document.getElementById("phone-body");
    var tr = document.createElement("tr");

    tr.className = "phone-number";
    if (id === undefined) {
        tr.id = idPhoneTableRow.toString();
        idPhoneTableRow--;
    } else {
        tr.id = id + "p";
    }

    phoneTable.appendChild(tr);

    var checkBox = document.createElement("input");
    checkBox.type = "checkbox";
    checkBox.className = "checkbox-phone";
    tr.appendChild(checkBox);

    var phoneNumber = document.createElement("td");
    phoneNumber.innerText = phone["countryCode"] + " " + phone["operatorCode"] + " " + phone["number"];
    tr.appendChild(phoneNumber);

    var type = document.createElement("td");
    type.innerText = phone["type"];
    tr.appendChild(type);

    var comment = document.createElement("td");
    comment.innerText = phone["comment"];
    tr.appendChild(comment);

}


/// --- Update phone row in the table ---

function updatePhoneRow(phone, id) {

    var tr = document.getElementById(id);

    tr.children[0].checked = false;
    tr.children[1].textContent = phone["countryCode"] + " " + phone["operatorCode"] + " " + phone["number"];
    tr.children[2].textContent = phone["type"];
    tr.children[3].textContent = phone["comment"]

}


// -- Fill inputs value for update ---

function fillPhoneInputs(phone) {

    var country = document.getElementById("countryCode-form");
    var operator = document.getElementById("operatorCode-form");
    var number = document.getElementById("number-form");
    var mobile = document.getElementById("mobile-form");
    mobile.checked = false;
    var home = document.getElementById("home-form");
    var comment = document.getElementById("comment-form");

    country.value = phone["countryCode"];
    operator.value = phone["operatorCode"];
    number.value = phone["number"];

    if (phone["type"] === "mobile") {
        mobile.checked = true;
    } else {
        home.checked = true;
    }

    comment.textContent = phone["comment"];

}
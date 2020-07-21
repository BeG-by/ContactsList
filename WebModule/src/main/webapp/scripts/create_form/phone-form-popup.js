"use strict";

function showPhoneForm() {

    //--- Phone data ---

    var windowForm = document.createElement("div");
    windowForm.className = "window-form-phone";
    var h1 = document.createElement("h1");
    h1.innerText = "Phone form";
    windowForm.appendChild(h1);

    var phoneForm = document.createElement("form");
    phoneForm.name = "phone-form";
    windowForm.appendChild(phoneForm);

    var countryCode = createInput("number", "countryCode", "Country code");
    phoneForm.appendChild(countryCode);

    var operatorCode = createInput("number", "operatorCode", "Operator code");
    phoneForm.appendChild(operatorCode);

    var number = createInput("text", "number", "Number");
    phoneForm.appendChild(number);

    var p = document.createElement("p");

    var typeMobile = createRadioInput("type", "mobile", "mobile");
    typeMobile.checked = true;

    var labelForMobile = createLabel("mobile" , "Mobile");
    labelForMobile.appendChild(typeMobile);
    p.appendChild(labelForMobile);

    var typeHome = createRadioInput("type", "home" , "home");

    var labelForHome = createLabel("home" , "Home");
    labelForHome.appendChild(typeHome);
    p.appendChild(labelForHome);

    phoneForm.appendChild(p);

    var comment = createInput("text" , "comment" , "Comment");
    phoneForm.appendChild(comment);

    //--- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "X";
    closeBtn.className = "close-btn";

    closeBtn.addEventListener("click", function () {
        mainWindow.parentNode.removeChild(mainWindow);
    });

    var closeDiv = document.createElement("div");
    closeDiv.appendChild(closeBtn);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.className = "save-btn";
    windowForm.appendChild(saveBtn);

    saveBtn.addEventListener("click", function () {

        //validation ???
        var phone = getDataFromPhoneForm();

        var phoneTable = document.getElementById("phone-table");
        var tr = document.createElement("tr");
        tr.className = "phone-number";
        phoneTable.appendChild(tr);

        var checkBox = document.createElement("input");
        checkBox.setAttribute("type", "checkbox");
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

        mainWindow.parentNode.removeChild(mainWindow);

    });

    // --- Show window ---

    var mainWindow = document.createElement("div");
    mainWindow.appendChild(windowForm);
    mainWindow.appendChild(closeDiv);
    mainWindow.className = "window-main-phone";
    document.body.appendChild(mainWindow);

}

function createInput(type, name, placeholder) {
    var input = document.createElement("input");
    input.setAttribute("type", type);
    input.setAttribute("name", name);
    input.setAttribute("placeholder", placeholder);
    return input;
}

function createRadioInput(name, value, id) {
    var input = document.createElement("input");
    input.setAttribute("type", "radio");
    input.setAttribute("name", name);
    input.setAttribute("value", value);
    input.setAttribute("id", id);
    return input;
}

function createLabel(id, text) {
    var label = document.createElement("label");
    label.innerText = text;
    label.setAttribute("for", id);
    return label;
}


function getDataFromPhoneForm() {
    var phoneForm = document.forms.namedItem("phone-form");

    var phone = {};

    for (var i = 0; i < phoneForm.length; i++) {
        var formElement = phoneForm[i];
        if (formElement.type === "radio" && !formElement.checked) {
            continue;
        }

        phone[phoneForm[i].name] = phoneForm[i].value;
    }

    return phone;

}



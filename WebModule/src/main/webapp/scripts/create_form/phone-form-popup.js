"use strict";

var idCheckBoxRow = 0;

function createPhoneForm(phoneDTO) {

    //--- Phone data ---

    var windowForm = document.createElement("div");
    windowForm.className = "window-form-phone";

    var headerPhone = document.createElement("header");
    var h1 = document.createElement("h2");
    h1.innerText = "Contact phone";
    headerPhone.appendChild(h1);
    windowForm.appendChild(headerPhone);

    var mainContent = document.createElement("main");
    windowForm.appendChild(mainContent);

    var phoneForm = document.createElement("div");
    phoneForm.className = "phone-form shadow p-3 mb-5 bg-white rounded";
    mainContent.appendChild(phoneForm);

    var countryCode = createInput("text", "countryCode", "countryCode-form", "Country code");
    phoneForm.appendChild(countryCode);

    var operatorCode = createInput("text", "operatorCode", "operatorCode-form", "Operator code");
    phoneForm.appendChild(operatorCode);

    var number = createInput("text", "number", "number-form", "Phone Number");
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
    phoneForm.appendChild(comment);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.className = "btn btn-success save-btn";
    phoneForm.appendChild(saveBtn);

    //--- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "Cancel";
    closeBtn.className = "btn btn-danger close-btn";
    phoneForm.appendChild(closeBtn);

    closeBtn.addEventListener("click", function () {
        mainWindow.parentNode.removeChild(mainWindow);
    });

    // --- Show window ---

    var mainWindow = document.createElement("div");
    mainWindow.appendChild(windowForm);
    mainWindow.className = "window-main-phone";


    document.body.appendChild(mainWindow);

    if (phoneDTO !== null) {
        fillInput(phoneDTO);
    }

    saveBtn.addEventListener("click", function (e) {

        var phone = getDataFromPhoneForm();

        if (phone === false) {
            e.preventDefault();
            return;
        }

        if (phoneDTO === null) {
            createUpdatePhoneRow(phone, null);
        } else {
            createUpdatePhoneRow(phone, phoneDTO["id"]);
        }

        mainWindow.parentNode.removeChild(mainWindow);

    });

}

function createUpdatePhoneRow(phone, id) {

    var phoneTable = document.getElementById("phone-body");
    var tr;

    if (id === null) {

        tr = document.createElement("tr");
        tr.className = "phone-number";
        tr.id = idCheckBoxRow.toString() + "tr";
        idCheckBoxRow++;
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


    } else {

        tr = document.getElementById(id);

        tr.children[0].checked = false;
        tr.children[1].textContent = phone["countryCode"] + " " + phone["operatorCode"] + " " + phone["number"];
        tr.children[2].textContent = phone["type"];
        tr.children[3].textContent = phone["comment"]

    }


}


function createInput(type, name, id, textLabel) {

    var div = document.createElement("div");
    div.className = "form-group";
    var label = document.createElement("label");
    label.for = id;
    label.innerText = textLabel;

    div.appendChild(label);

    var p = document.createElement("p");
    var input = document.createElement("input");
    input.type = type;
    input.name = name;
    input.id = id;
    input.addEventListener("input", replaceLetters);

    p.appendChild(input);
    div.appendChild(p);

    return div;
}

function createRadioInput(name, value, labelText, isChecked) {

    var div = document.createElement("div");
    div.id = value + "-div";

    var input = document.createElement("input");
    input.type = "radio";
    input.name = name;
    input.id = value;
    input.value = value;
    input.className = "form-check-input";
    input.checked = isChecked;

    var label = createLabel(value, labelText);
    label.className = "form-check-label";

    div.appendChild(input);
    div.appendChild(label);

    return div;
}

function createTextArea(name, textLabel) {

    var div = document.createElement("div");
    var textArea = document.createElement("textarea");
    textArea.name = name;
    textArea.id = name;
    textArea.cols = 30;
    textArea.rows = 2;

    var p = document.createElement("p");
    var label = createLabel(name, textLabel);

    p.appendChild(textArea);
    div.appendChild(label);
    div.appendChild(p);
    return div;
}

function createLabel(id, text) {
    var label = document.createElement("label");
    label.innerText = text;
    label.setAttribute("for", id);
    return label;
}

function replaceLetters() {
    this.value = this.value.replace(/\D/, "");
}


function fillInput(phone) {

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
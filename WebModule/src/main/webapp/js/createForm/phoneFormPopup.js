"use strict";

function showPhoneForm() {

    var windowForm = document.createElement("div");
    windowForm.className = "window-form";
    var h1 = document.createElement("h1");
    h1.innerText = "Phone form";
    windowForm.append(h1);

    var phoneForm = document.createElement("form");
    phoneForm.setAttribute("name", "phone-form");
    windowForm.append(phoneForm);

    var countryCode = document.createElement("input");
    countryCode.setAttribute("type", "number");
    countryCode.setAttribute("name", "countryCode");
    countryCode.setAttribute("placeholder", "Country code");
    phoneForm.append(countryCode);

    var operatorCode = document.createElement("input");
    operatorCode.setAttribute("type", "number");
    operatorCode.setAttribute("name", "operatorCode");
    operatorCode.setAttribute("placeholder", "Operator code");
    phoneForm.append(operatorCode);

    var number = document.createElement("input");
    number.setAttribute("type", "text");
    number.setAttribute("name", "number");
    number.setAttribute("placeholder", "Number");
    phoneForm.append(number);

    var p = document.createElement("p");

    var typeMobile = document.createElement("input");
    typeMobile.setAttribute("type", "radio");
    typeMobile.setAttribute("name", "type");
    typeMobile.setAttribute("value", "mobile");
    typeMobile.setAttribute("id", "mobile");
    typeMobile.setAttribute("checked", "true");

    var labelForMobile = document.createElement("label");
    labelForMobile.innerText = "Mobile";
    labelForMobile.setAttribute("for", "mobile");
    labelForMobile.append(typeMobile);
    p.append(labelForMobile);

    var typeHome = document.createElement("input");
    typeHome.setAttribute("type", "radio");
    typeHome.setAttribute("name", "type");
    typeHome.setAttribute("value", "home");
    typeHome.setAttribute("id", "home");

    var labelForHome = document.createElement("label");
    labelForHome.innerText = "Home";
    labelForHome.setAttribute("for", "home");
    labelForHome.append(typeHome);
    p.append(labelForHome);

    phoneForm.append(p);

    var comment = document.createElement("input");
    comment.setAttribute("type", "text");
    comment.setAttribute("name", "comment");
    comment.setAttribute("placeholder", "Comment");
    phoneForm.append(comment);

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "X";
    closeBtn.className = "close-btn";

    closeBtn.addEventListener("click", function () {
        mainWindow.remove();
    });

    var closeDiv = document.createElement("div");
    closeDiv.append(closeBtn);

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.className = "save-btn";
    windowForm.append(saveBtn);

    saveBtn.addEventListener("click", function () {

        //validation ???
        var phone = getDataFromPhoneForm();

        var phoneTable = document.getElementById("phone-table");
        var tr = document.createElement("tr");
        tr.className = "phone-number";
        phoneTable.append(tr);

        var checkBox = document.createElement("input");
        checkBox.setAttribute("type", "checkbox");
        tr.append(checkBox);

        var phoneNumber = document.createElement("td");
        phoneNumber.innerText = phone["countryCode"] + " " + phone["operatorCode"] + " " + phone["number"];
        tr.append(phoneNumber);

        var type = document.createElement("td");
        type.innerText = phone["type"];
        tr.append(type);

        var comment = document.createElement("td");
        comment.innerText = phone["comment"];
        tr.append(comment);

        mainWindow.remove();

    });


    var mainWindow = document.createElement("div");
    mainWindow.append(windowForm);
    mainWindow.append(closeDiv);
    mainWindow.className = "window-main";
    document.body.append(mainWindow);

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



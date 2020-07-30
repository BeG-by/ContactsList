"use strict";

var formClassEmailName = "window-form-email shadow p-3 mb-5 bg-white rounded";

function createEmailForm(emails) {

    var mainWindow = document.createElement("div");
    mainWindow.className = "window-main-email";

    var windowEmail = document.createElement("div");
    windowEmail.className = "window-email";

    mainWindow.appendChild(windowEmail);

    var header = document.createElement("header");
    var h2 = document.createElement("h2");
    h2.innerText = "Message";
    header.appendChild(h2);
    windowEmail.appendChild(header);

    var mainContent = document.createElement("main");
    var h3 = document.createElement("h3");
    h3.textContent = "Data";
    mainContent.appendChild(h3);
    windowEmail.appendChild(mainContent);

    var divForm = document.createElement("div");
    divForm.className = formClassEmailName;

    var recipients = createTextArea("recipient", "Recipients", true);
    recipients.cols = 40;
    recipients.rows = 3;

    for (var i = 0; i < emails.length; i++) {
        recipients.textContent += emails[i] + "\n";
    }
    recipients.readOnly = true;

    var recipientDiv = document.createElement("div");
    var pRecipient = document.createElement("p");
    var labelRecipient = createLabel("recipient", "Recipients");

    pRecipient.appendChild(recipients);
    recipientDiv.appendChild(labelRecipient);
    recipientDiv.appendChild(pRecipient);

    divForm.appendChild(recipientDiv);

    var subjectInput = createInput("text", "subject", "subject", "Subject");
    divForm.appendChild(subjectInput);


    var selectTemplate = document.createElement("select");
    selectTemplate.id = "template";
    var div = document.createElement("div");
    div.className = "form-group";
    var label = document.createElement("label");
    label.for = "template";
    label.innerText = "Templates";
    div.appendChild(label);
    var pTemplate = document.createElement("p");
    pTemplate.appendChild(selectTemplate);
    div.appendChild(pTemplate);
    divForm.appendChild(div);

    var textArea = createTextArea("textMessage", "Text", true);
    textArea.cols = 50;
    textArea.rows = 8;

    var recipientText = document.createElement("div");
    var pText = document.createElement("p");
    var labelText = createLabel("textMessage", "Text");

    pText.appendChild(textArea);
    recipientText.appendChild(labelText);
    recipientText.appendChild(pText);

    divForm.appendChild(recipientText);

    mainContent.appendChild(divForm);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Send";
    saveBtn.className = saveBtnClassName;
    mainContent.appendChild(saveBtn);

    saveBtn.addEventListener("click", function () {

        var messageRequest = getMessage();

        if (messageRequest != null) {
            sendRequestWithBody(sendEmailUrl, "POST", messageRequest, true, "Emails has been sent");
            clearCheckboxes();
            mainWindow.parentNode.removeChild(mainWindow);
        }

    });

    //--- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "Cancel";
    closeBtn.className = cancelBtnClassName;
    mainContent.appendChild(closeBtn);

    closeBtn.addEventListener("click", function () {
        clearCheckboxes();
        mainWindow.parentNode.removeChild(mainWindow);
    });

    document.body.appendChild(mainWindow);

}

function getMessage() {

    var emailTextArea = document.getElementById("recipient");
    var emails = emailTextArea.textContent.split("\n");
    emails.length = emails.length - 1;

    var subject = document.getElementById("subject").value;
    var text = document.getElementById("textMessage").value;

    if (subject.length === 0) {
        alert("Subject can't be empty !");
        return null;
    }

    if (text.length === 0) {
        alert("Text can't be empty !");
        return null;
    }

    return {
        "emails": emails,
        "subject": subject,
        "text": text
    };

}


function getEmails() {

    var checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
    var list = [];

    for (var i = 0; i < checkboxes.length; i++) {
        var email = checkboxes[i].parentNode.className;
        if (email !== "undefined") {
            list.push(email);
        }
    }

    return list;

}
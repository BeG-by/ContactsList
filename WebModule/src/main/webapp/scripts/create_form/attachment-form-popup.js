"use strict";

var attachmentsForRequest = [];
var currentFileName = null;

function showAttachmentForm() {

    var windowForm = document.createElement("div");
    windowForm.className = "window-form-att";

    //--- File load ---

    var inputFile = document.createElement("input");
    inputFile.type = "file";
    inputFile.id = "upload-attachment";
    inputFile.style.display = "none";
    windowForm.append(inputFile);


    var loadAttachmentBtn = document.createElement("button");
    loadAttachmentBtn.id = "add-att-btn";
    loadAttachmentBtn.textContent = "Choose a file";
    windowForm.append(loadAttachmentBtn);

    loadAttachmentBtn.addEventListener("click", function () {
        inputFile.click()
    });

    inputFile.addEventListener("click", function () {
        var file = this.files[0];
        var reader = new FileReader();

        if (file !== undefined) {
            reader.readAsDataURL(file);
        }

        reader.onload = function () {
            console.log("loaded");
            if (reader.result !== undefined) {
                currentFileName = file.name;
                attachmentsForRequest.push(file);
                loadAttachmentBtn.textContent = currentFileName;
            }
        }
    });

    // --- Comment ---

    var comment = document.createElement("input");
    comment.type = "text";
    comment.placeholder = "Comment";
    comment.name = "comment-att";
    windowForm.append(comment);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    windowForm.append(saveBtn);

    saveBtn.addEventListener("click", function () {

        if (currentFileName == null) {
            alert("Choose a file from disk !");
            return;
        }

        var attachmentBody = document.getElementById("attachment-body");

        var tr = document.createElement("tr");

        var checkBox = document.createElement("input");
        checkBox.setAttribute("type", "checkbox");
        tr.append(checkBox);

        var fileNameTd = document.createElement("td");
        fileNameTd.textContent = currentFileName;
        tr.append(fileNameTd);

        var dateTd = document.createElement("td");
        var today = new Date();
        dateTd.textContent = today.getDate() + "-" + today.getMonth() + "-" + today.getFullYear();
        tr.append(dateTd);

        var commentTd = document.createElement("td");
        commentTd.textContent = comment.value;
        tr.append(commentTd);

        attachmentBody.append(tr);

        windowForm.remove();
    });

    // --- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "X";
    closeBtn.className = "close-btn";
    windowForm.append(closeBtn);

    closeBtn.addEventListener("click", function () {
        windowForm.remove();
    });

    document.body.append(windowForm);

}

function getDataFromAttachmentForm() {
    // var phoneForm = document.forms.namedItem("phone-form");
    //
    // var phone = {};
    //
    // for (var i = 0; i < phoneForm.length; i++) {
    //     var formElement = phoneForm[i];
    //     if (formElement.type === "radio" && !formElement.checked) {
    //         continue;
    //     }
    //
    //     phone[phoneForm[i].name] = phoneForm[i].value;
    // }
    //
    // return phone;

}
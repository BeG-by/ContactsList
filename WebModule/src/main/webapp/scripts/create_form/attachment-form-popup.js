"use strict";

var attachmentsForRequest = [];
var currentFileName = null;

var maxSize = 500000;

function showAttachmentForm() {

    var windowForm = document.createElement("div");
    windowForm.className = "window-form-att";

    //--- File load ---

    var inputFile = document.createElement("input");
    inputFile.type = "file";
    inputFile.id = "upload-attachment";
    inputFile.style.display = "none";
    windowForm.appendChild(inputFile);


    var loadAttachmentBtn = document.createElement("button");
    loadAttachmentBtn.id = "add-att-btn";
    loadAttachmentBtn.textContent = "Choose a file";
    windowForm.appendChild(loadAttachmentBtn);

    loadAttachmentBtn.addEventListener("click", function () {
        inputFile.click()
    });

    inputFile.addEventListener("change", function () {
        var file = this.files[0];
        var reader = new FileReader();

        if (file !== undefined) {

            if(file.size > maxSizeOfImg){
                alert("File is to large ! Max size is :" + maxSize/1000 + "Kb");
                return;
            }

            reader.readAsDataURL(file);
        }

        reader.onload = function () {
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
    windowForm.appendChild(comment);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    windowForm.appendChild(saveBtn);

    saveBtn.addEventListener("click", function () {

        if (currentFileName == null) {
            alert("Choose a file from disk !");
            return;
        }

        var attachmentBody = document.getElementById("attachment-body");

        var tr = document.createElement("tr");

        var checkBox = document.createElement("input");
        checkBox.type = "checkbox";
        tr.appendChild(checkBox);

        var fileNameTd = document.createElement("td");
        fileNameTd.textContent = currentFileName;
        tr.appendChild(fileNameTd);

        var dateTd = document.createElement("td");
        var today = new Date();
        dateTd.textContent = today.getDate() + "-" + today.getMonth() + "-" + today.getFullYear();
        tr.appendChild(dateTd);

        var commentTd = document.createElement("td");
        commentTd.textContent = comment.value;
        commentTd.className = "comment-att";
        tr.appendChild(commentTd);

        attachmentBody.appendChild(tr);

        windowForm.parentNode.removeChild(windowForm);
    });

    // --- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "X";
    closeBtn.className = "close-btn";
    windowForm.appendChild(closeBtn);

    closeBtn.addEventListener("click", function () {
        windowForm.parentNode.removeChild(windowForm);
    });

    document.body.appendChild(windowForm);

}
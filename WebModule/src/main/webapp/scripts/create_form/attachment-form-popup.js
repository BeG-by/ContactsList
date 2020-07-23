"use strict";

var currentLoadedFile = null;

var maxSize = 500000;

var formDivAttClassName = "att-form shadow p-3 mb-5 bg-white rounded";
var saveBtnAttClassName = "btn btn-success save-btn";
var cancelBtnAttClassName = "btn btn-danger close-btn";

function createAttachmentForm(attachmentDTO) {

    var mainWindow = document.createElement("div");
    mainWindow.className = "window-main-att";

    var windowForm = document.createElement("div");
    windowForm.className = "window-form-att";
    mainWindow.appendChild(windowForm);

    var headerAtt = document.createElement("header");
    var h2 = document.createElement("h2");
    h2.innerText = "Attachment";
    headerAtt.appendChild(h2);
    windowForm.appendChild(headerAtt);

    var mainContent = document.createElement("main");
    windowForm.appendChild(mainContent);

    var formDiv = document.createElement("div");
    formDiv.className = formDivAttClassName;
    mainContent.appendChild(formDiv);


    //--- File load ---

    var inputFile = document.createElement("input");
    inputFile.type = "file";
    inputFile.id = "upload-attachment";
    inputFile.style.display = "none";
    formDiv.appendChild(inputFile);


    var loadAttachmentBtn = document.createElement("button");
    loadAttachmentBtn.id = "load-att-btn";
    loadAttachmentBtn.textContent = "Choose a file";
    formDiv.appendChild(loadAttachmentBtn);

    loadAttachmentBtn.addEventListener("click", function () {
        inputFile.click()
    });

    inputFile.addEventListener("change", function () {
        var file = this.files[0];
        var reader = new FileReader();

        if (file !== undefined) {

            if (file.size > maxSizeOfImg) {
                alert("File is to large ! Max size is :" + maxSize / 1000 + "Kb");
                return;
            }

            reader.readAsDataURL(file);
        }

        reader.onload = function () {
            if (reader.result !== undefined) {
                currentLoadedFile = file;
                loadAttachmentBtn.textContent = currentLoadedFile.name;
            }
        }

    });

    // --- Comment ---

    var comment = createTextArea("comment-att", "Comment");
    formDiv.appendChild(comment);


    var divBtn = document.createElement("div");
    divBtn.className = "att-btn-div";
    formDiv.appendChild(divBtn);

    // --- Save button ---

    var saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.className = saveBtnAttClassName;
    divBtn.appendChild(saveBtn);

    saveBtn.addEventListener("click", function () {

        if (currentLoadedFile == null) {
            alert("Choose a file from disk !");
            return;
        }

        var commentText = document.getElementById("comment-att").value;

        if (commentText.length > 45) {
            alert("Max length of comment is 45 symbols !");
            return false;
        }

        var id = attachmentDTO === null ?
            createRowAttachment(commentText) :
            updateRowAttachment(commentText, attachmentDTO["id"]);

        attachmentsForRequest[id] = currentLoadedFile;
        currentLoadedFile = null;

        mainWindow.parentNode.removeChild(mainWindow);
    });

    // --- Close button ---

    var closeBtn = document.createElement("button");
    closeBtn.textContent = "Cancel";
    closeBtn.className = cancelBtnAttClassName;
    divBtn.appendChild(closeBtn);

    closeBtn.addEventListener("click", function () {
        mainWindow.parentNode.removeChild(mainWindow);
    });

    // -- Show window ---
    document.body.appendChild(mainWindow);

    if (attachmentDTO !== null) {
        fillAttachmentInputs(attachmentDTO);
    }

}

// --- Insert attachment row into the table ---

function createRowAttachment(commentText, id) {

    var attachmentBody = document.getElementById("attachment-body");

    var tr = document.createElement("tr");
    tr.id = idAttachmentTableRow + "-tr-att";
    idAttachmentTableRow++;

    var checkBox = document.createElement("input");
    checkBox.type = "checkbox";
    tr.appendChild(checkBox);

    var fileNameTd = document.createElement("td");
    fileNameTd.textContent = currentLoadedFile.name;
    tr.appendChild(fileNameTd);

    var dateTd = document.createElement("td");
    var today = new Date();
    dateTd.textContent = today.getDate() + "-" + (today.getMonth() + 1) + "-" + today.getFullYear();
    tr.appendChild(dateTd);

    var commentTd = document.createElement("td");
    commentTd.textContent = commentText;
    commentTd.className = "comment-att";

    tr.appendChild(commentTd);

    attachmentBody.appendChild(tr);

    return tr.id;

}


// --- Update attachment row in the table ---

function updateRowAttachment(commentText, id) {

    var tr = document.getElementById(id);

    tr.children[0].checked = false;
    tr.children[1].textContent = currentLoadedFile.name;
    var today = new Date();
    tr.children[2].textContent = today.getDate() + "-" + (today.getMonth() + 1) + "-" + today.getFullYear();
    tr.children[3].textContent = commentText;

    return id;

}


// -- Fill inputs value for update ---

function fillAttachmentInputs(attachment) {

    var comment = document.getElementById("comment-att");
    var loadBtn = document.getElementById("load-att-btn");

    currentLoadedFile = attachment["file"];
    comment.textContent = attachment["comment"];
    loadBtn.textContent = currentLoadedFile.name;

}
"use strict";

var attachmentFilename = null;

// --- Attachments for request ---

function getAttachmentsList() {

    var items = document.querySelectorAll("#attachment-body tr");

    var attachmentsList = [];

    for (var i = 0; i < items.length; i++) {

        var date = items[i].children[2].textContent.split("-");

        var attachment = {
            "id": items[i].id.replace(/a/g, ""),
            "contactId": -1,
            "fileName": items[i].children[1].textContent,
            "dateOfLoad": {
                "day": date[0],
                "month": date [1],
                "year": date [2]
            },
            "comment": items[i].children[3].textContent
        };

        attachmentsList.push(attachment);
    }

    return attachmentsList;

}

// -- Delete attachments from the table and delete file from map of attachments ---

function deleteAttachmentsRow() {

    var items = validateAttachmentsCheckboxes();

    if (items !== false) {
        for (var i = 0; i < items.length; i++) {
            delete attachmentsForRequest[items[i].parentNode.id];
            items[i].parentNode.parentNode.removeChild(items[i].parentNode);
        }
    }

}


// --- Get attachment form the table ---

function getDataForUpdateAttachment() {

    var item = validateAttachmentsCheckboxes(true);

    if (item !== false) {

        var attachment = {};
        var attachmentRow = item.parentNode;

        attachment["id"] = attachmentRow.id;
        attachment["file"] = attachmentsForRequest[attachment.id];
        attachment["fileName"] = attachmentRow.children[1].textContent;
        attachment["comment"] = attachmentRow.children[3].textContent;

        return attachment;
    }

    return null;

}


function downloadAttachment() {

    var item = validateAttachmentsCheckboxes(true);

    if (item !== false) {

        var attachmentRow = item.parentNode;

        if (attachmentRow.classList.contains("updatedFile")) {
            alert("Attachment hasn't been download to the server.");

        } else {

            var attId = attachmentRow.id.replace(/a/g, "");
            attachmentFilename = attachmentRow.children[1].textContent;
            sendRequest(downloadAttachmentUrl + "?contactId=" + updateContactId + "&attId=" + attId, "GET", callbackDownload);
        }

    }

}


function callbackDownload(response) {

    var bs = atob(response);
    var buffer = new ArrayBuffer(bs.length);
    var ba = new Uint8Array(buffer);
    for (var i = 0; i < bs.length; i++) {
        ba[i] = bs.charCodeAt(i);
    }
    var blob = new Blob([ba], {type: "octet/stream"});

    var a = document.createElement("a");
    document.body.appendChild(a);
    a.style.display = "none";
    var url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = attachmentFilename;
    a.click();
    window.URL.revokeObjectURL(url);

}


function validateAttachmentsCheckboxes(isUpdated) {

    var checkboxes = document.querySelectorAll("#attachment-body input[type=checkbox]");

    if (checkboxes.length === 0) {
        alert("You should add an attachment !");
        return false;
    }

    var checked = document.querySelectorAll("#attachment-body input[type=checkbox]:checked");

    if (checked.length === 0) {
        alert("You should choose attachments.");
        return false;
    }

    if (isUpdated && checked.length !== 1) {
        alert("You should choose one attachment.");
        return false;
    }

    return isUpdated === undefined ? checked : checked[0];

}
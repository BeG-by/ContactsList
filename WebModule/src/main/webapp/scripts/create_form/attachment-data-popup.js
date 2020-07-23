"use strict";

// --- Comments for request ---

function getCommentsAttachment() {

    var comments = document.querySelectorAll(".comment-att");

    var arrComments = [];

    for (var i = 0; i < comments.length; i++) {
        arrComments.push(comments[i].textContent);
    }

    return arrComments;

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
        attachment["comment"] = attachmentRow.children[3].textContent;

        return attachment;
    }

    return null;

}

function validateAttachmentsCheckboxes(isUpdated) {

    var checkboxes = document.querySelectorAll("#attachment-body input[type=checkbox]");

    if (checkboxes.length === 0) {
        alert("You should add an attachment !");
        return false;
    }

    var checked = document.querySelectorAll("#attachment-body input[type=checkbox]:checked");

    if (checked.length === 0) {
        alert("You should choose attachments !");
        return false;
    }

    if (isUpdated && checked.length !== 1) {
        alert("You should choose one attachment !");
        return false;
    }

    return isUpdated === undefined ? checked : checked[0];

}
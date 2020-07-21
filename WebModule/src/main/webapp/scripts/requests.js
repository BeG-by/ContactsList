"use strict";

function sendRequest(url, type, callback, message) {

    var xhr = new XMLHttpRequest();

    xhr.open(type, url);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Accept", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            callback(xhr.response);

            if (message !== undefined) {
                alert(message);
            }

        } else if (xhr.readyState === 4 && xhr.status >= 400) {
            alert("Error: " + xhr.status);
        }
    };

    xhr.send();

}

function sendRequestWithBody(url, type, body, async, message) {

    var xhr = new XMLHttpRequest();

    if (async === undefined) {
        async = true;
    }

    xhr.open(type, url, async);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Accept", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            if (message !== undefined) {
                alert(message);
            }

        } else if (xhr.readyState === 4 && xhr.status >= 400) {
            alert("Error: " + xhr.status);
        }

    };

    xhr.send(JSON.stringify(body));

}


function sendMultipartRequest(type, url, json, avatar, attachments, comments) {

    var xhr = new XMLHttpRequest();
    var formData = new FormData();

    xhr.open(type, url);
    xhr.responseType = "json";

    formData.append("jsonContact", JSON.stringify(json));

    if (avatar !== null) {
        formData.append("avatar", avatar);
    }

    for (var i = 0; i < attachments.length; i++) {
        formData.append("attachment" + i, attachments[i]);
    }

    console.log(comments);
    formData.append("comments", JSON.stringify(comments));


    xhr.send(formData);

}



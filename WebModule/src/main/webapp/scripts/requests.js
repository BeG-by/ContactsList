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

    xhr.send(JSON.stringify(["Тест", "Второй" , "Third"]));

}


function sendMultipartRequest(type, url, json, avatar, attachments) {

    var xhr = new XMLHttpRequest();
    var formData = new FormData();

    xhr.open(type, url);
    xhr.responseType = "json";

    // xhr.setRequestHeader("Content-type" , "multipart/form-data; charset=utf-8");

    formData.append("jsonContact", JSON.stringify(json));

    if (avatar !== null) {
        formData.append("avatar", avatar);
    }

    for (var i = 0; i < attachments.length; i++) {
        formData.append("attachment" + i, attachments[i]);
    }

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
                alert("Contact has been saved !");
        } else if (xhr.readyState === 4 && xhr.status >= 400) {
            alert("Error: " + xhr.status);
        }

    };

    xhr.send(formData);

}

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
            alert("Error: " + xhr.status + " " + xhr.response);
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
            alert("Error: " + xhr.status + " " + xhr.response);
        }

    };

    xhr.send(JSON.stringify(body));

}


function sendMultipartRequest(type, url, json, avatar, attachments, message) {

    var xhr = new XMLHttpRequest();
    var formData = new FormData();

    xhr.open(type, url);
    xhr.responseType = "json";

    formData.append("jsonContact", JSON.stringify(json));

    if (avatar !== null) {
        formData.append("avatar", avatar);
    }

    for (var property in attachments) {
        formData.append("attachment" + property.replace(/a/g, ""), attachments[property]);
    }


    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            alert(message);
        } else if (xhr.readyState === 4 && xhr.status >= 400) {
            alert("Error: " + xhr.status + " " + xhr.response);
        }

    };

    xhr.send(formData);

}

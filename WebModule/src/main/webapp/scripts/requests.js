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

        }
        //else if (xhr.readyState === 4 && xhr.status >= 400) {
        //     alert(xhr.status);
        // }
    };

    xhr.send();

}

function sendRequestWithBody(url, type, body, message) {

    var xhr = new XMLHttpRequest();

    xhr.open(type, url);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Accept", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {

            if (message !== undefined) {
                alert(message);
            }

        }
        //else if (xhr.readyState === 4 && xhr.status >= 400) {
        //     alert(xhr.status);
        // }
    };

    xhr.send(JSON.stringify(body));

}


function sendMultipartRequest(type, url, json, avatar) {

    var xhr = new XMLHttpRequest();
    var formData = new FormData();

    xhr.open(type, url);
    xhr.responseType = "json";

    formData.append("jsonContact", JSON.stringify(json));
    formData.append("avatar", avatar);

    xhr.send(formData);

}



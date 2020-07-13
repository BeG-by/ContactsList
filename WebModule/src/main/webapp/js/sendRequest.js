"use strict";

var xhr = new XMLHttpRequest();

function sendGetRequest(url, callback) {

    xhr.open("GET", url);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            if (callback !== null) {
                callback(xhr.response);
            }
        }
    };

    xhr.send();

}

function sendRequest(url, type, body, message) {

    xhr.open(type, url);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            alert(message);
        }
    };

    xhr.send(JSON.stringify(body));
}



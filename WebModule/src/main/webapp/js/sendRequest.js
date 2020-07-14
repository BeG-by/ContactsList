"use strict";

function sendRequest(url, type, body, callback, message) {

    var xhr = new XMLHttpRequest();

    xhr.open(type, url);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {

            if (callback !== null) {
                callback(xhr.response);
            } else {
                alert(message);
            }

        }
    };

    if (body !== null) {
        xhr.send(JSON.stringify(body));
    } else {
        xhr.send();
    }
}

// function sendGetRequest(url, callback) {
//
//     xhr.open("GET", url);
//     xhr.responseType = "json";
//     xhr.setRequestHeader("Content-type", "application/json");
//
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
//             if (callback !== null) {
//                 callback(xhr.response);
//             }
//         }
//     };
//
//     xhr.send();
//
// }



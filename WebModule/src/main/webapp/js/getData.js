//test
const findAllUrl = "http://localhost:8080/contacts/findAll";

function getData(url, type, callback) {
    const xhr = new XMLHttpRequest();

    xhr.open(type, url);
    xhr.responseType = "json";
    xhr.setRequestHeader("Content-type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            callback(xhr.response);
        }
    };

    xhr.send();

}

getData(findAllUrl, "GET", getAllContacts);

function getAllContacts(response) {

    const table = document.querySelectorAll("#contacts-table")[0];
    const jsonResponse = response;

    for (const jsonKey in jsonResponse) {
        const tr = document.createElement("tr");
        const contact = jsonResponse[jsonKey];
        const address = contact["address"];
        const birthday = contact["birthday"];
        var day = birthday["day"];
        var month = birthday["month"];

        if (day < 10) {
            day = "0" + day;
        }

        if (month < 10) {
            month = "0" + month;
        }

        const birthdayToString = day + "." + month + "." + birthday["year"];

        const tableContact = {
            firstName: document.createElement("td"),
            lastName: document.createElement("td"),
            middleName: document.createElement("td"),
            birthday: document.createElement("td"),
            country: document.createElement("td"),
            city: document.createElement("td"),
            street: document.createElement("td"),
            index: document.createElement("td"),
            currentJob: document.createElement("td")
        };


        tableContact.firstName.textContent = contact["firstName"];
        tableContact.lastName.textContent = contact["lastName"];
        tableContact.middleName.textContent = contact["middleName"];
        tableContact.birthday.textContent = birthdayToString;
        tableContact.country.textContent = address["country"];
        tableContact.city.textContent = address["city"];
        tableContact.street.textContent = address["street"];
        tableContact.index.textContent = address["index"];
        tableContact.currentJob.textContent = contact["currentJob"];

        for (const propertyTdKey in tableContact) {
            tr.append(tableContact[propertyTdKey]);
        }

        table.append(tr);

    }
}



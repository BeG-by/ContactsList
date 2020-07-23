"use strict";

function createInput(type, name, id, textLabel) {

    var div = document.createElement("div");
    div.className = "form-group";
    var label = document.createElement("label");
    label.for = id;
    label.innerText = textLabel;

    div.appendChild(label);

    var p = document.createElement("p");
    var input = document.createElement("input");
    input.type = type;
    input.name = name;
    input.id = id;
    input.addEventListener("input", replaceLetters);

    p.appendChild(input);
    div.appendChild(p);

    return div;
}

function createRadioInput(name, value, labelText, isChecked) {

    var div = document.createElement("div");
    div.id = value + "-div";

    var input = document.createElement("input");
    input.type = "radio";
    input.name = name;
    input.id = value;
    input.value = value;
    input.className = "form-check-input";
    input.checked = isChecked;

    var label = createLabel(value, labelText);
    label.className = "form-check-label";

    div.appendChild(input);
    div.appendChild(label);

    return div;
}

function createTextArea(name, textLabel) {

    var div = document.createElement("div");
    var textArea = document.createElement("textarea");
    textArea.name = name;
    textArea.id = name;
    textArea.cols = 30;
    textArea.rows = 2;

    var p = document.createElement("p");
    var label = createLabel(name, textLabel);

    p.appendChild(textArea);
    div.appendChild(label);
    div.appendChild(p);
    return div;
}

function createLabel(id, text) {
    var label = document.createElement("label");
    label.innerText = text;
    label.setAttribute("for", id);
    return label;
}
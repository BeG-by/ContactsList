"use strict";

var requestAvatar = null;

var avatarImg = document.getElementById("avatar");
var uploadInput = document.getElementById("upload-img");

avatarImg.addEventListener("click", function () {
    uploadInput.click();
});

uploadInput.addEventListener("change", function () {
    var file = this.files[0];
    var reader = new FileReader();

    if (file !== undefined && file.type.match("image.*")) {
        reader.readAsDataURL(file);
    }

    reader.onload = function () {
        if (reader.result !== undefined) {
            requestAvatar = file;
            avatarImg.src = reader.result;
        }
    }

});


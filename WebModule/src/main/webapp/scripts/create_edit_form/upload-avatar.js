"use strict";

var maxSizeOfImg = 12000000;

var avatarImg = document.getElementById("avatar");
var uploadInput = document.getElementById("upload-img");

avatarImg.addEventListener("click", function () {
    uploadInput.click();
});

uploadInput.addEventListener("change", function () {
    var file = this.files[0];
    var reader = new FileReader();

    if (file !== undefined && file.type.match("image.*")) {

        if (file.size > maxSizeOfImg) {
            alert("File is to large ! Max size is :" + maxSizeOfImg / 1000000 + "MB");
            return;
        }

        if (file.name.length > 100) {
            alert("Image name length should be less than 100");
            return;
        }

        reader.readAsDataURL(file);
    }

    reader.onload = function () {
        if (reader.result !== undefined) {
            requestAvatar = file;
            avatarImg.src = reader.result;
        }
    }

});

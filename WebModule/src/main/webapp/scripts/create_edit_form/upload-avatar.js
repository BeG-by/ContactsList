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

    if (file !== undefined) {
        if (file.type.match("image.*")) {

            if (file.size > maxSizeOfImg) {
                alert("File is to large. Max size is :" + maxSizeOfImg / 1000000 + "MB");
                return;
            }

            if (file.name.length > 100) {
                alert("File name is too long (maximum is 100 characters).");
                return;
            }

            reader.readAsDataURL(file);

        } else {
            alert("Incorrect file type.")
        }
    }

    reader.onload = function () {
        if (reader.result !== undefined) {
            requestAvatar = file;
            avatarImg.src = reader.result;
        }
    }

});

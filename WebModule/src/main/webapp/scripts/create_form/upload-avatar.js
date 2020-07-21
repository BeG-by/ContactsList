"use strict";

var requestAvatar = null;
var maxSizeOfImg = 500000;

var avatarImg = document.getElementById("avatar");
var uploadInput = document.getElementById("upload-img");

avatarImg.addEventListener("click", function () {
    uploadInput.click();
});

uploadInput.addEventListener("change", function () {
    var file = this.files[0];
    var reader = new FileReader();

    if (file !== undefined && file.type.match("image.*")) {

        if(file.size > maxSizeOfImg){
            alert("File is to large ! Max size is :" + maxSizeOfImg/1000 + "Kb");
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


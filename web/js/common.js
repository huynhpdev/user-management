var validPassword = true;
var interactToPasswordField = false;
function checkUserInput(userName, email, phone, photo) {
    var txtUsername = document.getElementById(userName).value;
    var txtEmail = document.getElementById(email).value;
    var txtPhone = document.getElementById(phone).value;
    var userPhoto = document.getElementById(photo).value;
    
    if (txtUsername.length < 2) {
        alert("Username length must be greater than 2");
        return false;
    }

    if (!validPassword) {
        alert("Password length must be greater than 5");
        return false;
    } 

    var emailRgEx = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;

    if (!emailRgEx.test(txtEmail)) {
        alert("Please fill out a valid email address");
        return false;
    }

    var phoneRgEx = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;

    if (!phoneRgEx.test(txtPhone)) {
        alert("Please fill out a valid phone");
        return false;
    }
    var _validFileExtensions = [".jpg", ".jpeg", ".png"];
    if (userPhoto.length > 0) {
        var blnValid = false;
        for (var j = 0; j < _validFileExtensions.length; j++) {
            var sCurExtension = _validFileExtensions[j];
            if (userPhoto.substr(userPhoto.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                blnValid = true;
                break;
            }
        }

        if (!blnValid) {
            alert("Sorry, " + userPhoto + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
            return false;
        }
    }
}

function popupConfirmWindow(id, userID) {
    document.getElementById(id).style.height = "100%";
    document.getElementById("warningMessage").style.height = "100%";
    document.getElementById('selectedUserID').value = userID;
}

function closeConfirmWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningMessage").style.height = "0%";
}

function popupDeleteWindow(id, userID, promotionID) {
    document.getElementById('userIDPromotion').value = userID;
    document.getElementById('promotionID').value = promotionID;

    document.getElementById(id).style.height = "100%";
    document.getElementById("warningDeleteMessage").style.height = "100%";

}

function closeDeleteWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningDeleteMessage").style.height = "0%";
}

function popupUpdateWindow(id, userID, promotionID, rank) {

    document.getElementById('userIDOfPromotion').value = userID;
    document.getElementById('promotionIDOfUser').value = promotionID;
    document.getElementById('fillInRank').value = rank;

    document.getElementById(id).style.height = "100%";
    document.getElementById("warningUpdateMessage").style.height = "100%";
}

function closeUpdateWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningUpdateMessage").style.height = "0%";
}

function changePassword(inputPassword) {
    var txtPassword = document.getElementById(inputPassword).value;
    document.getElementById('changedPasswordAlready').value = txtPassword;   
    
    if (txtPassword.length < 6) {
        alert("Password length must be greater than 5");
        return false;
    } 
}

function changePasswordByAdmin(inputPassword) {
    interactToPasswordField = true;
    
    var txtPassword = document.getElementById(inputPassword).value;
    
    console.log(txtPassword);  
    
    if (txtPassword.length < 6) {
        validPassword = false;
        alert("Password length must be greater than 5");
        return false;
    } else {
        validPassword = true;
    }
}

function cancelToChangePassword(inputPassword) {
    var txtPassword = document.getElementById(inputPassword).value;
    if (txtPassword.length === 0) {
        validPassword = true;
    }
}


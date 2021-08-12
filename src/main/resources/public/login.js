function userSignup() {
	closeAllForms();
	document.getElementById("userSignup").style.display = "block";
}

function empSignup() {
	closeAllForms();
	document.getElementById("empSignup").style.display = "block";
}

function closeUSForm() {
	document.getElementById("userSignup").style.display = "none";
}

function closeESForm() {
	document.getElementById("empSignup").style.display = "none";
}

function closeAllForms() {
	closeUSForm();
	closeESForm();
}
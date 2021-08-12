window.onload = function() {
	displayAccounts();
}

function closeAllForms() {
	closeMTForm();
	closeGTForm();
	closeAcForm();
}

function depositButton() {
	closeAllForms();
	document.getElementById("bankAction").setAttribute("value", "deposit");
	openGTForm();
}

function withdrawButton() {
	closeAllForms();
	document.getElementById("bankAction").setAttribute("value", "withdraw");
	openGTForm();
}

function mtButton() {
	closeAllForms();
	document.getElementById("moneyTransfer").style.display = "block";
}

function acButton() {
	closeAllForms();
	document.getElementById("newAccount").style.display = "block";
}

function closeAcForm() {
	document.getElementById("newAccount").style.display = "none";
}

function closeMTForm() {
	document.getElementById("moneyTransfer").style.display = "none";
}

function openGTForm() {
  document.getElementById("giveandtake").style.display = "block";
}

function closeGTForm() {
  document.getElementById("giveandtake").style.display = "none";
}

function logout() {
	
}

function displayAccounts() {
	let xhr   = new XMLHttpRequest();
	const url = "bankAccounts"
	
	xhr.onreadystatechange = function() {
		switch(xhr.readyState) {
			case 4:
				if (xhr.status == 200) {
					let acctList = JSON.parse(xhr.responseText);
					acctList.forEach( element => { addAcctToTable(element); })
				}
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}

function addAcctToTable(acct) {
	let table      = document.getElementById("accountsGoHere");
	let tableRow   = document.createElement("tr");
	let iDCol      = document.createElement("td");
	let balCol     = document.createElement("td");
	//let buttonCol  = document.createElement("td");
	//let depositB   = document.createElement("td");
	//let withdrawB  = document.createElement("td");
	//let transferB  = document.createElement("td");
	
	tableRow.appendChild(iDCol);
	tableRow.appendChild(balCol);
	//tableRow.appendChild(buttonCol);
	
	//buttonCol.appendChild(depositB);
	//buttonCol.appendChild(withdrawB);
	//buttonCol.appendChild(transferB);
	
	table.appendChild(tableRow);
	
	iDCol.innerHTML  = acct.accountID;
	balCol.innerHTML = acct.balance;
	
	
	//withdrawB.innerHTML = `<button id = "${acct.accountID}w" type="submit" class="btn btn-primary">Withdraw Funds</button>`;
	//transferB.innerHTML = `<button id = "${acct.accountID}t" type="submit" class="btn btn-primary">Money Transfer</button>`;
	
	tableRow.setAttribute("id", acct.accountID + "id");
}
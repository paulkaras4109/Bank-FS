window.onload = function() {
	getApplications();
	getClients();
	getLogs();
}

function getApplications() {
	let xhr   = new XMLHttpRequest();
	const url = "getApplications"
	
	xhr.onreadystatechange = function() {
		switch(xhr.readyState) {
			case 4:
				if (xhr.status == 200) {
					let acctList = JSON.parse(xhr.responseText);
					acctList.forEach( element => { addAppToTable(element); })
				}
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
}

function getClients() {
	let xhr   = new XMLHttpRequest();
	const url = "getClients"
	
	xhr.onreadystatechange = function() {
		switch(xhr.readyState) {
			case 4:
				if (xhr.status == 200) {
					let acctList = JSON.parse(xhr.responseText);
					acctList.forEach( element => { addClientToTable(element); })
				}
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
}

function getLogs() {
	let xhr   = new XMLHttpRequest();
	const url = "getLogs"
	
	xhr.onreadystatechange = function() {
		switch(xhr.readyState) {
			case 4:
				if (xhr.status == 200) {
					let log = JSON.parse(xhr.responseText);
					console.log(log);
					let textBox = document.getElementById("logs");
					textBox.textContent = log;
				}
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
}

function addAppToTable(acct) {
	let table         = document.getElementById("pendingAccounts");
	let tableRow      = document.createElement("tr");
	let iDCol         = document.createElement("td");
	let balCol        = document.createElement("td");
	let approveButton = document.createElement("td");
	
	tableRow.appendChild(iDCol);
	tableRow.appendChild(balCol);
	tableRow.appendChild(approveButton);
	
	table.appendChild(tableRow);
	
	iDCol.innerHTML  = acct.senderID;
	balCol.innerHTML = acct.balance;
	
	approveButton.innerHTML = `
	<form action="approveAccount" method="POST" id=approve${acct.messageID}>
    		<button id = "approveBut" type="submit" class="btn btn-primary">Approve</button>
    		<input type = "hidden" name="messageID" value=${acct.messageID}>
    </form>`;
	
	tableRow.setAttribute("id", acct.accountID + "id");
}

function parseAcct(acct) {
	let rs = "";
	for (let x of acct.accounts) {
		rs += x.accountID + "; $" + x.balance + "\n";
	}
	return rs;
}

function addClientToTable(acct) {
	let table    = document.getElementById("viewUsers");
	let tableRow = document.createElement("tr");
	let nameCol  = document.createElement("td");
	let acctCol  = document.createElement("td");
	
	tableRow.appendChild(nameCol);
	tableRow.appendChild(acctCol);
	
	table.appendChild(tableRow);
	
	nameCol.innerHTML = acct.username;
	acctCol.innerHTML = parseAcct(acct);
	
	tableRow.setAttribute("id", acct.accountID + "id");
}
package com.paul.controller;

import java.util.ArrayList;

import com.paul.model.Account;
import com.paul.model.Client;
import com.paul.model.Message;
import com.paul.service.AuthService;
import com.paul.service.AuthServiceImpl;
import com.paul.service.BankService;
import com.paul.service.BankServiceImpl;

import io.javalin.http.Context;

public class BankControllerImpl implements BankController {
	
	private BankService bSer = new BankServiceImpl();
	private AuthController aCon = new AuthControllerImpl();
	private AuthService aSer = new AuthServiceImpl();
	
	@Override
	public void getUserAccts(Context ctx) {
		if (aCon.checkUser(ctx)) {
			ctx.status(200);
			String token = ctx.cookieStore("user");
			ArrayList<Account> accts = bSer.getUserAccounts(aSer.getUnameFromToken(token));
			ctx.json(accts);
		} else {
			ctx.status(403);
			ctx.redirect("login.html");
		}
		
	}

	@Override
	public void giveAndTake(Context ctx) {
		String accountId  = ctx.formParam("aID");
		String moneyValue = ctx.formParam("moneyValue");
		String bankAction = ctx.formParam("bankAction");
		double amount;
		boolean result;
		int aID;
		
		if (!aCon.checkUser(ctx)) {
			ctx.status(403);
			ctx.redirect("login.html");
			return;
		}
		
		try { 
			amount = Double.parseDouble(moneyValue);
			aID = Integer.parseInt(accountId);
		}
		catch (NumberFormatException e) { 
			ctx.html("<h3> Bad input! </h3>");
			ctx.status(400);
			return;
		}
		
		//Prevent a user from updating a bank account that isn't their own
		if (!checkAcctOwnership(aID)) {
			ctx.status(403);
			ctx.redirect("bank.html");
			return;
		}
		
		if (bankAction.equals("deposit")) {
			result = bSer.deposit(aID, amount);
		} else {
			result = bSer.withdraw(aID, amount);
		}
		
		ctx.redirect("bank.html");
		if (result == true) {
			ctx.html("<h3>" + bankAction + " successful!</h3>");
		} else {
			ctx.html("<h3>Something went wrong with your" + bankAction + "</h3>");
		}
	}

	@Override
	public void moneyTransfer(Context ctx) {
		String sendAccID  = ctx.formParam("sID");
		String recvAccID  = ctx.formParam("rID");
		String moneyValue = ctx.formParam("moneyValue");
		double amount;
		boolean result;
		int sID;
		int rID;
		
		if (!aCon.checkUser(ctx)) {
			ctx.status(403);
			ctx.redirect("login.html");
			return;
		}
		
		try { 
			amount = Double.parseDouble(moneyValue);
			sID    = Integer.parseInt(sendAccID);
			rID    = Integer.parseInt(recvAccID);
		}
		catch (NumberFormatException e) { 
			ctx.html("<h3> Bad input! </h3>");
			ctx.status(400);
			return;
		}
		
		//Prevent a user from updating a bank account that isn't their own
		if (!checkAcctOwnership(sID) || !checkAcctOwnership(rID)) {
			ctx.status(403);
			return;
		}
		result = bSer.transfer(sID, rID, amount);
		ctx.redirect("bank.html");
		if (result == true) {
			ctx.html("<h3> Transfer successful!</h3>");
		} else {
			ctx.html("<h3>Something went wrong with your transfer </h3>");
		}
	}
	
	boolean checkAcctOwnership(int accountID) {
		ArrayList<Account> accts = bSer.getUserAccounts(aSer.getCurrentUser().getUsername());
		for (Account aElem : accts) {
			if (aElem.getAccountID() == accountID) { return true; }
		}
		return false;
	}

	@Override
	public void newAccount(Context ctx) {
		String moneyValue = ctx.formParam("moneyValue");
		double amount;
		int uID = aSer.getCurrentUser().getUserId();
		
		try {
			amount = Double.parseDouble(moneyValue);
		} catch (NumberFormatException e) {
			ctx.html("<h3> Bad Input! </h3>");
			ctx.status(400);
			return;
		}
		
		bSer.applyForBankAccount(uID, amount);
		ctx.status(201);
		ctx.redirect("bank.html");
	}

	@Override
	public void getApplications(Context ctx) {
		if (aCon.checkUser(ctx)) {
			ctx.status(200);
			//Map<Message, Client> muMap = new HashMap<>();
			ArrayList<Message> applications = bSer.getApplications();
			ctx.json(applications);
		} else {
			ctx.status(403);
			ctx.redirect("login.html");
		}
		
	}

	@Override
	public void approveAccount(Context ctx) {
		int messageId  = Integer.parseInt(ctx.formParam("messageID"));
		boolean result = bSer.approveBankAccount(messageId);
		
		if (result) {
			ctx.status(201);
		} else {
			ctx.status(500);
		}
		ctx.redirect("admin.html");
	}

	@Override
	public void createUserAccount(Context ctx) {
		String uname   = ctx.formParam("uname");
		String passwd  = ctx.formParam("passwd");
		String passwdc = ctx.formParam("passwdc");
		int acctType   = Integer.parseInt(ctx.formParam("acctType"));
		boolean result;
		
		if (passwd.equals(passwdc) == false) {
			ctx.status(400);
			ctx.redirect("login.html");
		}
		
		result = aSer.createUserAccount(uname, passwd, acctType);
		if (result) {
			ctx.status(201);
		} else {
			ctx.status(500);
		}
		ctx.redirect("login.html");
	}

	@Override
	public void getClients(Context ctx) {
		if (aCon.checkUser(ctx)) {
			ctx.status(200);
			ArrayList<Client> clients = bSer.getAllClients(); 
			ctx.json(clients);
		} else {
			ctx.status(403);
			ctx.redirect("login.html");
		}
		
	}

	@Override
	public void getLogs(Context ctx) {
		// TODO Auto-generated method stub
		ctx.status(200);
		ctx.json(bSer.getLogs());
	}
}

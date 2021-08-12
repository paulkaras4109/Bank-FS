package com.paul;

import com.paul.controller.*;

import io.javalin.Javalin;

public class MainDriver {
	
	private static AuthController authC = new AuthControllerImpl();
	private static BankController bankC = new BankControllerImpl();
	
	
	public static void main(String[] args) {
		Javalin bank = Javalin.create(
				config -> { config.addStaticFiles("/public"); }).start(9000);
		
		bank.post("login", ctx -> authC.login(ctx));
		bank.post("giveAndTake", ctx -> bankC.giveAndTake(ctx));
		bank.post("moneyTransfer", ctx-> bankC.moneyTransfer(ctx));
		bank.post("newAccount", ctx -> bankC.newAccount(ctx));
		bank.post("approveAccount", ctx -> bankC.approveAccount(ctx));
		bank.post("createNewUserAccount", ctx -> bankC.createUserAccount(ctx));
		
		bank.get("/", ctx -> ctx.redirect("login.html"));
		bank.get("logout", ctx -> authC.logout(ctx));
		bank.get("checkUser", ctx -> authC.checkUser(ctx));
		bank.get("bankAccounts", ctx -> bankC.getUserAccts(ctx));
		bank.get("getApplications", ctx -> bankC.getApplications(ctx));
		bank.get("getClients", ctx -> bankC.getClients(ctx));
		bank.get("getLogs", ctx -> bankC.getLogs(ctx));
	}
}
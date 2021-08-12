package com.paul.controller;

import io.javalin.http.Context;

public interface BankController {

	public void getUserAccts(Context ctx);
	public void giveAndTake(Context ctx);
	public void moneyTransfer(Context ctx);
	public void newAccount(Context ctx);
	public void approveAccount(Context ctx);
	public void getApplications(Context ctx);
	public void createUserAccount(Context ctx);
	public void getClients(Context ctx);
	public void getLogs(Context ctx);
}

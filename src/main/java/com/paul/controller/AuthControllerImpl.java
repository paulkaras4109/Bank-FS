package com.paul.controller;
import com.paul.model.Client;
import com.paul.model.User;
import com.paul.service.*;

import io.javalin.http.Context;

public class AuthControllerImpl implements AuthController {
	
	private AuthService as = new AuthServiceImpl();
	
	@Override
	public void login(Context ctx) {
		String uname    = ctx.formParam("username");
		String passwd   = ctx.formParam("password");
		User loggedUser = null;
		int accountType = Integer.parseInt(ctx.formParam("acctType"));
		
		loggedUser = as.authenticateUser(uname, passwd, accountType);
		if (loggedUser != null) {
			ctx.status(200);
			ctx.cookieStore("user", as.createToken(uname));
			
			if (accountType == Client.CLIENT_TYPE) {
				ctx.redirect("bank.html");
			} else {
				ctx.redirect("admin.html");
			}
			
		} else {
			ctx.status(403);
			ctx.redirect("login.html");
		}
	}

	@Override
	public void logout(Context ctx) {
		ctx.clearCookieStore();
		ctx.redirect("login.html");
		
	}

	@Override
	public boolean checkUser(Context ctx) {
		return as.validateToken(ctx.cookieStore("user"));
	}

}

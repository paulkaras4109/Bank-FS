package com.paul.model;

import java.util.ArrayList;

public class Client extends User {
	private ArrayList<Account> accounts;
	
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	public Client(int uId, String uname, String passwd) { 
		this.setId(uId);
		this.setUsername(uname);
		this.setPassword(passwd);
		this.accounts = null;
	}
	public Client(String uname, String passwd) { 
		this.setUsername(uname);
		this.setPassword(passwd);
	}
	public Client(String uname) { 
		this.setUsername(uname);
	}
	public Client(int uId, String uname, String passwd, ArrayList<Account> accounts) {
		this.setId(uId);
		this.setUsername(uname);
		this.setPassword(passwd);
		this.accounts = accounts;
	}
}

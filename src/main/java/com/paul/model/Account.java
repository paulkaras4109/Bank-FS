package com.paul.model;

public class Account {
	private int accountID;
	private int ownerID;
	private double balance;
	
	public Account(int aId, int oId, double bal) {
		this.accountID = aId;
		this.ownerID = oId;
		this.balance = bal;
	}
	public double getBalance()   { return balance;            }
	public void setBalance(double b)   { this.balance = b;    }
	public int getAccountID() { return accountID;             }
	public void setAccountID(int aID) { this.accountID = aID; }
	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
}

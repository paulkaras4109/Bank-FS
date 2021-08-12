package com.paul.service;

import java.util.ArrayList;

import com.paul.model.*;

public interface BankService {
	public ArrayList<Account> getUserAccounts(String uname);
	public ArrayList<Message> getApplications();
	public ArrayList<Client> getAllClients();
	public Client getClient(int userID);
	public boolean deposit(int accountID, double depositAmt);
	public boolean withdraw(int accountID, double withdrawAmt);
	public boolean transfer(int accountIDFrom, int accountIDTo, double transferAmt);
	public boolean applyForBankAccount(int accountID, double appAmount);
	public boolean approveBankAccount(int messageId);
	public String getLogs();
}

package com.paul.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paul.dao.*;
import com.paul.model.Account;
import com.paul.model.Client;
import com.paul.model.Message;

public class BankServiceImpl implements BankService {
	private static final Logger LOG = LogManager.getLogger(Client.class);
	private static BankDAO bDao = new BankDAOImpl();
	//private static AuthService aSer = new AuthServiceImpl();
	private ArrayList<Account> getUserAccounts(Client u) {
		return bDao.getAccounts(u.getUserId());
	}
	
	@Override
	public ArrayList<Account> getUserAccounts(String uname) {
		Client u = bDao.getClient(uname);
		if (u == null) { return null; }
		return this.getUserAccounts(u);
	}

	@Override
	public boolean deposit(int accountID, double depositAmt) {
		Account acct = bDao.getAccount(accountID);
		if (acct == null) { return false; }
		if (depositAmt < 0.0) { 
			LOG.info("User " + Integer.toString(accountID) + " tried to deposit a negative balance");
			return false; 
		}
		acct.setBalance(acct.getBalance() + depositAmt);
		bDao.commitAccount(acct);
		LOG.info("User " + Integer.toString(accountID) + " deposited " + Double.toString(depositAmt));
		return true;
	}

	@Override
	public boolean withdraw(int accountID, double withdrawAmt) {
		Account acct = bDao.getAccount(accountID);
		if (acct == null) { return false; }
		if (withdrawAmt < 0.0) { 
			LOG.info("User " + Integer.toString(accountID) + " tried to withdraw a negative balance");
			return false; 
		}
		acct.setBalance(acct.getBalance() - withdrawAmt);
		
		if (acct.getBalance() < 0.0) { 
			LOG.info("User " + Integer.toString(accountID) + " tried to withdraw too much money from an account");
			return false;
		}
		
		LOG.info("User " + Integer.toString(accountID) + " withdrew " + Double.toString(withdrawAmt));
		bDao.commitAccount(acct);
		return true;
	}

	@Override
	public boolean transfer(int accountIDFrom, int accountIDTo, double transferAmt) {
		Account acctFrom = bDao.getAccount(accountIDFrom);
		Account acctTo   = bDao.getAccount(accountIDTo);
		
		if (acctFrom == null || acctTo == null) { return false; }
		if (transferAmt < 0) { 
			LOG.info("User " + Integer.toString(accountIDFrom) + " tried to withdraw a negative balance");
			return false; 
		}
		
		acctFrom.setBalance(acctFrom.getBalance() - transferAmt);
		if (acctFrom.getBalance() < 0.0) { 
			LOG.info("User " + Integer.toString(accountIDFrom) + " tried to withdraw too much money from an account");
			return false; 
		}
		acctTo.setBalance(acctTo.getBalance() + transferAmt);
		
		bDao.commitAccount(acctFrom);
		bDao.commitAccount(acctTo);
		return true;
	}

	@Override
	public boolean applyForBankAccount(int accountID, double appAmount) {
		Message message = new Message(accountID, Message.EMPLOYEE_RID, appAmount);
		bDao.sendMessage(message);
		LOG.trace("User " + accountID + " applied for a bank account");
		return true;
	}

	@Override
	public ArrayList<Message> getApplications() {
		return bDao.getMessageInbox(Message.EMPLOYEE_RID);
	}

	@Override
	public Client getClient(int userID) {
		return bDao.getClient(userID);
	}

	@Override
	public boolean approveBankAccount(int messageId) {
		Message acctMessage = bDao.getMessage(messageId);
		bDao.createBankAccount(acctMessage.getSenderID(), acctMessage.getBalance());
		bDao.deleteMessage(messageId);
		return true;
	}

	@Override
	public ArrayList<Client> getAllClients() {
		return bDao.getAllClients();
	}

	@Override
	public String getLogs() {
		StringBuilder sb = new StringBuilder();
		String filepath;
		
		if (System.getProperty("os.name").equals("Linux")) {
			filepath = "/home/b055/Source/work/Bank-FS/logs/logfile.log";
		} else {
			filepath = "C:\\Users\\paulk\\Source\\Bank-FS\\logs\\logfile.log";
		}
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filepath));
			String l = rd.readLine();
			while (l != null) {
				if (l.contains("INFO")) {
					sb.append(l);
					sb.append("\n");
				}
				l = rd.readLine();
			}
			rd.close();
		} catch (IOException e) { e.printStackTrace(); }
		return sb.toString();
	}
}

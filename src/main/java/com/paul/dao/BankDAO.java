package com.paul.dao;

import com.paul.model.*;
import java.util.ArrayList;

public interface BankDAO {
	ArrayList<Message> getMessageInbox(int recipID);
	ArrayList<Account> getAccounts(int ownerID);
	Client getClient(int userID);
	Client getClient(String uname);
	ArrayList<Client> getAllClients();
	Account getAccount(int accountID);
	Message getMessage(int messageID);
	User verifyUser(String uname, String passwd, int accountType);
	void sendMessage(Message msg);
	void deleteMessage(int msgID);
	void createBankAccount(int ownerID, double balance);
	void commitAccount(Account acct);
	void commitUser(String uname, String passwd, int accountType);
}

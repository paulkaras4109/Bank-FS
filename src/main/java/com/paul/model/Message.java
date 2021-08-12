package com.paul.model;

public class Message {
	private int messageID;
	private int senderID;
	private int recipientID;
	private double balance;
	
	public static final int EMPLOYEE_RID = -1;
	
	public Message(int sId, int rId, double bal) {
		this.messageID = -1;
		this.senderID = sId;
		this.recipientID = rId;
		this.balance = bal;
	}
	public Message(int mId, int sId, int rId, double bal) {
		this.messageID = mId;
		this.senderID = sId;
		this.recipientID = rId;
		this.balance = bal;
	}
	
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public int getSenderID() {
		return senderID;
	}
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	public int getRecipientID() {
		return recipientID;
	}
	public void setRecipientID(int recipientID) {
		this.recipientID = recipientID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}

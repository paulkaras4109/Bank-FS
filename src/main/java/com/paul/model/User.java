package com.paul.model;

public abstract class User {
	private int userId;
	private String username;
	private String password;
	
	public static final int CLIENT_TYPE = 1;
	public static final int EMPLOYEE_TYPE = 2;
	
	//Depreciated
	//public abstract void printOptions();
	//public abstract int processChoice(int choice);
	
	public int getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String uname) { this.username = uname; }
	public void setId(int id) { this.userId = id; }
	protected void setPassword(String passwd) { this.password = passwd; }
	
	public boolean equals(Object o) {
		if (o == this)
	        return true;
	    if (!(o instanceof User))
	        return false;
	    User other = (User) o;
	    return (this.userId == other.userId) && (this.username.equals(other.username)) && (this.password.equals(password));
	}
}

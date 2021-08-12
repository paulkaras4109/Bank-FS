package com.paul.model;


public class Employee extends User {
	public Employee(int uId, String uname, String passwd) { 
		this.setUsername(uname);
		this.setId(uId);
		this.setPassword(passwd);
	}
}

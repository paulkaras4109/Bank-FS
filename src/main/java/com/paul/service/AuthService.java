package com.paul.service;

import com.paul.model.User;

public interface AuthService {
	public User authenticateUser(String uname, String passwd, int accountType);
	public String createToken(String uname);
	public boolean validateToken(String token);
	public String getUnameFromToken(String token);
	public User getCurrentUser();
	public boolean createUserAccount(String uname, String passwd, int accountType);
}

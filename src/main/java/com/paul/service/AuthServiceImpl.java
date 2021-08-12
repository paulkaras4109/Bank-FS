package com.paul.service;

import java.util.HashMap;
import java.util.Map;

import com.paul.dao.*;
import com.paul.model.User;
import com.paul.util.PasswdHasher;

public class AuthServiceImpl implements AuthService {
	private static User currentUser = null;
	BankDAO bDao = new BankDAOImpl();
	private static Map<String, String> tokenRepo = new HashMap<>();
	
	@Override
	public User authenticateUser(String uname, String passwd, int accountType) {
		String hashedPasswd = PasswdHasher.hashPasswd(passwd);
		User foundUser = null;
		
		foundUser = bDao.verifyUser(uname, hashedPasswd, accountType);
		AuthServiceImpl.currentUser = foundUser;
		return foundUser;
	}

	@Override
	public String createToken(String uname) {
		String token = PasswdHasher.hashPasswd(uname);
		tokenRepo.put(token, uname);
		return token;
	}

	@Override
	public boolean validateToken(String token) {
		return tokenRepo.containsKey(token);
	}

	@Override
	public String getUnameFromToken(String token) {
		return tokenRepo.get(token);
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}
	
	@Override
	public boolean createUserAccount(String uname, String passwd, int accountType) {
		bDao.commitUser(uname, PasswdHasher.hashPasswd(passwd), accountType);
		return true;
	}
}

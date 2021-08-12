package com.paul.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswdHasher {
	public static String hashPasswd(String passwd) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
		md.update(passwd.getBytes());
		byte[] digest = md.digest();
		String hash = new String(digest, StandardCharsets.UTF_8);
		return hash;
	}
}

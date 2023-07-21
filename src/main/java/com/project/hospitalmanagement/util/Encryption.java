package com.project.hospitalmanagement.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class Encryption {
	
	private Encryption() {}
	
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

	public static String encode(String password) {
		return Strings.isNullOrEmpty(password) ? "" : ENCODER.encode(password);
	}

	public static boolean verifyHash(String plainText, String encodedText) {
		return BCrypt.checkpw(plainText, encodedText);
	}

}

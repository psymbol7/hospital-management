package com.project.hospitalmanagement.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	
	private CommonUtil() {}
	
	/**
	 * Password should -:
	 * Be at least 8 characters in length
	 * Contain both upper and lowercase alphabetic characters (e.g. A-Z, a-z)
	 * Have at least one numerical character (e.g. 0-9)
	 * Have at least one special character (e.g. ~!@#$%^&*()_-+=) 
	 */
	public static boolean isPasswordFormatValid(String password) {
		Matcher matcher = Pattern.compile(Constant.PASSWORD_PATTERN).matcher(password);
		return matcher.matches();
	}

}

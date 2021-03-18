package com.telkom.training.utils;

public class SecurityUtils {
	public static final String SECRET = "693ee065865a51c647d6f0948963c231";
	public static final long EXPIRATION_TIME = 900_000; // 15 mins
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_IN_URL = "/employee/login";
	public static final String SIGN_UP_URL = "/employee/register";
}

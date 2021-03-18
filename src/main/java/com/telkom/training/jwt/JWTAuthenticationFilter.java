package com.telkom.training.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telkom.training.utils.ResponseBuilder;
import com.telkom.training.utils.ResponseMessage;
import com.telkom.training.utils.SecurityUtils;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager aunAuthenticationManager) {
		this.authenticationManager = aunAuthenticationManager;
		
		// path untuk login
		setFilterProcessesUrl(SecurityUtils.SIGN_IN_URL);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>()));
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = JWT.create()
				.withSubject(((User) authResult.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SecurityUtils.SECRET.getBytes()));
		
		ResponseBuilder.setResponse(response, HttpServletResponse.SC_OK, ResponseMessage.SUCCESS, token);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		ResponseBuilder.setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseMessage.FAILED, "");
	}
}



















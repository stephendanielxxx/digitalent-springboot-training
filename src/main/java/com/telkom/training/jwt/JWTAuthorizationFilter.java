package com.telkom.training.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telkom.training.utils.ResponseBuilder;
import com.telkom.training.utils.ResponseMessage;
import com.telkom.training.utils.SecurityUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(SecurityUtils.HEADER_STRING);
		
		// check header request
		if(header == null || !header.startsWith(SecurityUtils.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(SecurityUtils.HEADER_STRING);
		System.out.println("Token "+token);
		try {
			if(token != null) {
				String user = JWT.require(Algorithm.HMAC512(SecurityUtils.SECRET.getBytes()))
						.build()
						.verify(token.replace(SecurityUtils.TOKEN_PREFIX, ""))
						.getSubject();
				
				if(user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseBuilder.setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseMessage.FAILED, "aaaa");
		}
		
		return null;
	}

}



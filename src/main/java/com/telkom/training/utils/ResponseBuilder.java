package com.telkom.training.utils;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkom.training.responses.TelkomResponse;

public class ResponseBuilder {

	public static void setResponse(HttpServletResponse response, int code, String message, String data) {
		try {
			TelkomResponse<String> telkomResponse = new TelkomResponse<>();
			telkomResponse.setCode(code);
			telkomResponse.setMessage(message);
			telkomResponse.setData(data);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String finalResponse = objectMapper.writeValueAsString(telkomResponse);
			
			response.setStatus(code);
			response.setContentType("application/json");
			response.getWriter().write(finalResponse);
			response.getWriter().flush();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}

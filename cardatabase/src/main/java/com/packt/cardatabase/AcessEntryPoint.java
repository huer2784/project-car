package com.packt.cardatabase;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AcessEntryPoint implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		 response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		 response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		 PrintWriter writer = response.getWriter();
		
		 writer.println("Error : " + accessDeniedException.getMessage());

	}

}

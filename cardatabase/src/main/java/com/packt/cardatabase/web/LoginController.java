package com.packt.cardatabase.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.AccountCredentials;
import com.packt.cardatabase.service.JwtService;

@RestController
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
		// TODO Auto-generated constructor stub
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials){
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(accountCredentials.username(), accountCredentials.password()); // 검증받을 객체생성
		
		Authentication auth = authenticationManager.authenticate(creds); // 검증해줘 : UserDetailsService 구현체 찾아서 loadUserByUsername 실행
		
		//토큰생성
		logger.info("auth name : {}",auth.getName());
		
		String jwts = jwtService.createToken(auth.getName());
		
		logger.info("jwt token : {}", jwts);
		
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer "+jwts)
								  .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
								  .build();
	}
}

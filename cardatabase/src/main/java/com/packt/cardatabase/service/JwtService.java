package com.packt.cardatabase.service;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtService {
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	
	static final long EXPIRETIME = 24*3600*1000;
	static final String PREFIX = "Bearer";
	
	//해당소스는 이렇게 하드코딩하지말고 따로 빼서 properties 에서 가져오거나 해야됨
	static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	//토큰생성
	public String createToken(String username) {
		String token = Jwts.builder().setSubject(username)
									 .setExpiration(new Date(System.currentTimeMillis() + EXPIRETIME))
									 .signWith(KEY)
									 .compact();
		
		logger.info("create jwt token : {}", token);
		
		return token;
	}
	
	// 요청의 authorizition 헤더에서 토큰 가져온 뒤
	// 토큰을 확인하고 사용자 이름 가져옴
	public String getAuthUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(token != null) {
			logger.info("get jwt token : {}", token);
			String user = Jwts.parserBuilder()
							  .setSigningKey(KEY)
							  .build()
							  .parseClaimsJws(token.replace(PREFIX, ""))
							  .getBody()
							  .getSubject();
			logger.info("get jwt token body : {}", Jwts.parserBuilder()
					  .setSigningKey(KEY)
					  .build()
					  .parseClaimsJws(token.replace(PREFIX, ""))
					  .getBody()
				);
			
			logger.info("get jwt token subject : {}", Jwts.parserBuilder()
					.setSigningKey(KEY)
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody()
					.getSubject()
				);
			
			if(user != null) {
				return user;
			}
		}
		
		return null;
	}

}

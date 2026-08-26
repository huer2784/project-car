package com.packt.cardatabase;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.builder().username("user")
//										 .password(passwordEncoder().encode("password"))
//										 .roles("USER")
//										 .build()
//										 ;
//		return new InMemoryUserDetailsManager(user);
//	}
//	
//	private final UserDetailsServiceImpl userDetailsServiceImpl;
//	
//	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
//		// TODO Auto-generated constructor stub
//		this.userDetailsServiceImpl = userDetailsServiceImpl;
//	}
//	
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
//	}
//	
	/*
	 * 교차 출처
	 * http://localhost:9080 -> http://localhost:8080
	 * 
	 * 같은 출처
	 * http://localhost:9080 -> http://localhost:9080/api/cars
	 * 
	 * 다른 출처
	 * http://localhost:9080 -> http://localhost:8080/api/cars
	 * http://localhost:9080 -> https://localhost:9080
	 * */
	private final AuthenticationFilter authenticationFilter;
	private final AuthEntryPoint authEntryPoint;
	private final AcessEntryPoint accessEntryPoint;
	
	public SecurityConfig(AuthenticationFilter authenticationFilter, AuthEntryPoint authEntryPoint, AcessEntryPoint accessEntryPoint) {
		// TODO Auto-generated constructor stub
		this.authenticationFilter = authenticationFilter;
		this.authEntryPoint = authEntryPoint;
		this.accessEntryPoint = accessEntryPoint;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http.csrf((csrf) -> csrf.disable())
//			.cors(cors -> cors.configurationSource(configurationSource()))
//			.authorizeHttpRequests((req) -> req.anyRequest().permitAll())
//			;
		
		http.csrf((csrf) -> csrf.disable())
			.cors(cors -> cors.configurationSource(configurationSource()))
			.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/admin/**").hasRole("ADMIN")
																				   .requestMatchers("/user/**").hasRole("USER")
																				   .requestMatchers(HttpMethod.POST, "/login").permitAll()
																				   .anyRequest().authenticated()
					)
			.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling((exept) -> exept.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessEntryPoint))
			;
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource configurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(Arrays.asList("*")); // http://localhost:3000 에서 Boot 로의 호출을 허용한다
		config.setAllowedMethods(Arrays.asList("*")); // GET POST PUT DELETE PATCH
		config.setAllowedHeaders(Arrays.asList("*")); // 헤더에 어떤것을 허용할지 ex)Authorization, content-type
		config.setAllowCredentials(false);
		//config.applyPermitDefaultValues();
		
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
}

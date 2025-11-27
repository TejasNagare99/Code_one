package com.excel.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.excel.service.UserMasterAuthenticationImpl;
import com.excel.utility.CodifyErrors;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired private UserMasterAuthenticationImpl userMasterAuthentication;
	@Autowired private JwtProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			System.err.println("doFilterInternal");
			String jwt = this.getJwt(request);
			if (jwt != null && this.tokenProvider.validateJwtToken(jwt)) {
				String username = this.tokenProvider.getUsernameFromJwtToken(jwt);
				UserDetails userDetails = this.userMasterAuthentication.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
				return;
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		//	System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	
		
	}
	
	private String getJwt(HttpServletRequest request) {
		String authenticationHeader = request.getHeader("Authorization");
		
		if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
			return authenticationHeader.replace("Bearer ", "");
		}
		
		return null;
	}

}

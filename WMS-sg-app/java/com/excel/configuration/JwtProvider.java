package com.excel.configuration;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.excel.utility.CodifyErrors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	
	private String jwtSecret = "$&#22+@!&E";
	private int jwtExpiration = 86400;
	
//	public static void main(String args[]) {
//		JwtProvider p = new JwtProvider();
//		p.generateJwtToken(null);
//	}
	
	public String generateJwtToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		System.err.println("generateJwtToken::"+user.getUsername());
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + this.jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, this.jwtSecret)
				.compact();
	}
	
	public String generateJwtToken(String username) {
		//User user = (User) authentication.getPrincipal();
		System.err.println("generateJwtToken:::"+username);
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + this.jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, this.jwtSecret)
				.compact();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return false;
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(this.jwtSecret)
				.parseClaimsJws(token)
				.getBody().getSubject();
	}

	/*
	 * public String generateMobileJwtToken(Authentication authentication) { User
	 * user = (User) authentication.getPrincipal(); return Jwts.builder()
	 * .setSubject(user.getUsername()) .setIssuedAt(new Date()) .setExpiration(new
	 * Date((new Date()).getTime() + this.jwtExpiration * 10000))
	 * .signWith(SignatureAlgorithm.HS512, this.jwtSecret) .compact(); }
	 */

	public String getUsernameFromRequest(HttpServletRequest request) {
		String authenticationHeader = request.getHeader("Authorization");
		String token = null;
		if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
			 token =  authenticationHeader.replace("Bearer ", "");
			 return Jwts.parser()
						.setSigningKey(this.jwtSecret)
						.parseClaimsJws(token)
						.getBody().getSubject();
		}
		return null;
		
	}
}

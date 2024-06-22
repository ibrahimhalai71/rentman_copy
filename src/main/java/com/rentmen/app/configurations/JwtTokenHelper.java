package com.rentmen.app.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.JobRepo;
import com.rentmen.app.repositories.UserRepo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
	 @Autowired
	    private UserRepo userRepo;
	 @Autowired
	 private JobRepo jobRepo;
    public static final long JWT_TOKEN_VALIDITY = 18000L;
    private String secret = "jwtTokenKey";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return (Claims) Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }
    private Boolean isReviewFormToken(String token) {
    	Claims claims = getAllClaimsFromToken(token);
    	return (Boolean) claims.get("review_form");
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return Boolean.valueOf(expiration.before(new Date()));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        long expTime = System.currentTimeMillis() + 18000000L;
        return doGenerateToken(claims, getEmailFromUserDetails(userDetails), expTime);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, long expTime) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expTime))
                .signWith(SignatureAlgorithm.HS512, this.secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return Boolean.valueOf((username.equals(getEmailFromUserDetails(userDetails)) && !isTokenExpired(token).booleanValue()));
    }
    
    private String getEmailFromUserDetails(UserDetails userDetails) {
    	return this.userRepo.findByFirstName(userDetails.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "name", userDetails.getUsername())).getEmail();
    }
    
    public Long getJobIdFromToken(String token) {
    	return Long.parseLong(getUsernameFromToken(token));
    }

	public String generateTokenForReviewForm(String jobId) {
		Map<String, Object> claims = new HashMap<>();
		long currentTimeMillis = System.currentTimeMillis();
		long expirationTimeMillis = currentTimeMillis + 5L * 24 * 60 * 60 * 1000;
		claims.put("review_form", true);
		return doGenerateToken(claims, jobId, expirationTimeMillis);
	}
    
	public Boolean validateTokenForReviewForm(String token) {
		return Boolean.valueOf(this.jobRepo.existsById(getJobIdFromToken(token))
				&& !isTokenExpired(token).booleanValue() && isReviewFormToken(token).booleanValue());
	}
}

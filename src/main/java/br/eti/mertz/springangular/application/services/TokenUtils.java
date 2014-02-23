package br.eti.mertz.springangular.application.services;

import org.springframework.security.core.userdetails.UserDetails;


public interface TokenUtils {

	public String createToken(UserDetails userDetails);
	public String computeSignature(UserDetails userDetails, long expires);
	public String getUserNameFromToken(String authToken);
	public boolean validateToken(String authToken, UserDetails userDetails);
}

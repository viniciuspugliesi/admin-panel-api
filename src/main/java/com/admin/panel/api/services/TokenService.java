package com.admin.panel.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.panel.api.domain.Token;
import com.admin.panel.api.domain.User;
import com.admin.panel.api.domain.enuns.TokenType;
import com.admin.panel.api.repositories.TokenRepository;
import com.admin.panel.api.security.UserSecurity;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	
	public Token create(UserSecurity userSecurity, User user) {
		Token token = new Token(userSecurity.getToken(), TokenType.AUTHENTICATION, user);
		
		return tokenRepository.save(token);
	}

	public boolean existsAuthentication(UserSecurity userSecurity) {
		return tokenRepository.countByTokenAndTypeAndUserId(userSecurity.getToken(), TokenType.AUTHENTICATION.value(), userSecurity.getId()) > 0;
	}

	public void expiresAuthentication(UserSecurity userSecurity) {
		Token token = tokenRepository.findByTokenAndTypeAndUserId(userSecurity.getToken(), TokenType.AUTHENTICATION.value(), userSecurity.getId());
		tokenRepository.delete(token);
	}
}

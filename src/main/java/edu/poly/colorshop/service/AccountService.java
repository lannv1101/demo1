package edu.poly.colorshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import edu.poly.colorshop.entity.Account;

public interface AccountService {

	Optional<Account> findById(String username);

	List<Account> findAll();

	<S extends Account> S save(S entity);

	List<Account> getAdministrations();

	void deleteById(String username);

	Long getSize();

	void updatePassword(Account account, String password);

	void updateResetToken(String token, String email) throws ClassNotFoundException;

	Account getByResetToken(String token);

	void formLoginOAuth2(OAuth2AuthenticationToken oauth2);

	Account findByVerificationCode(String code);

	boolean verify(String verificationCode);

	void changePassword(Account account, String newPassword);
}

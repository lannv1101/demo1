package edu.poly.colorshop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.repository.AccountRepo;
import edu.poly.colorshop.service.AccountService;

@Service
@Repository
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRepo accountRepo;

	@Autowired
	private BCryptPasswordEncoder be;

	public Account findByVerificationCode(String code) {
		return accountRepo.findByVerificationCode(code);
	}

	@Override
	public Optional<Account> findById(String username) {
		return accountRepo.findById(username);
	}

	@Override
	public <S extends Account> S save(S entity) {
		Optional<Account> optExist = findById(entity.getUsername());
		if (optExist.isPresent()) {
			// k nhap moi pass word khi cap nhat
			if (StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(optExist.get().getPassword());
			} else {
				// cap nhat thong tin
				// entity.setPassword(be.encode(entity.getPassword()));
				entity.setPassword(entity.getPassword());

			}
		} else {
			// nhạp moi
			entity.setPassword(be.encode(entity.getPassword()));
		}
		return accountRepo.save(entity);
	}

	public Account findByEmail(String email) {
		return accountRepo.findByEmail(email);
	}

	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	@Override
	public List<Account> getAdministrations() {
		return accountRepo.getAdministrations();
	}

	@Override
	public void deleteById(String username) {
		accountRepo.deleteById(username);
	}

	@Override
	public Long getSize() {
		return accountRepo.getSize();
	}

	// đặt giá trị cho trường resetPasswordToken của người dùng được tìm thấy bởi
	// emai
	@Override
	public void updateResetToken(String token, String email) throws ClassNotFoundException {
		Account account = accountRepo.findByEmail(email);
		if (account != null) {
			account.setResetToken(token);
			accountRepo.save(account);
		} else {
			throw new ClassNotFoundException("Could not find any customer with the email" + email);

		}
	}

	// đặt mật khẩu mới cho người dùng
	@Override
	public void updatePassword(Account account, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(password);
		account.setPassword(encodePassword);
		account.setResetToken(null);
		accountRepo.save(account);
	}

	public Account findByResetToken(String resetToken) {
		return accountRepo.findByResetToken(resetToken);
	}

	// tìm người dùng bằng mã thông báo mật khẩu đặt lại đã cho
	@Override
	public Account getByResetToken(String token) {
		return accountRepo.findByResetToken(token);
	}

	// loginOauth2
	public void formLoginOAuth2(OAuth2AuthenticationToken oauth2) {
		// String username=
		String email = oauth2.getPrincipal().getAttribute("email");
		String password = Long.toHexString(System.currentTimeMillis());
		UserDetails user = User.withUsername(email).password(be.encode(password)).roles("CUST").build();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	// Triển khai chức năng xác minh tài khoản người dùng
	@Override
	public boolean verify(String verificationCode) {
		Account account = accountRepo.findByVerificationCode(verificationCode);
		if (account == null || account.getEnabled().equals(true)) {
			return false;
		} else {
			account.setVerificationCode(null);
			account.setEnabled(true);
			accountRepo.save(account);
			return true;
		}

	}

	// triển khai phương pháp cập nhật mật khẩu của khách hàng
	@Override
	public void changePassword(Account account, String newPassword) {
		String encodedPassword = be.encode(newPassword);
		account.setPassword(encodedPassword);
		account.setPasswordChangedTime(new Date());
		accountRepo.save(account);
	}
}

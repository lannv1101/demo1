package edu.poly.colorshop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")

public class Account implements Serializable {
	@Id
	@NotBlank
	String username;
	@NotBlank
	String password;
	@NotBlank
	String fullname;
	@NotBlank
	@Email
	String email;
	String photo;
	// tạo mã thông báo đặt lại mật khẩu ngẫu nhiên
	String resetToken;
	// mã xác nhận đăng nhập
	String verificationCode;
	Boolean enabled;
	// thời gian hết hạn mật khẩu
	Date passwordChangedTime;
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Authority> authorities;

	@Override
	public String toString() {
		return "Account: username-" + username + "; fullname-" + fullname;
	}

	private static final long PASSWORD_EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L; // 30 days
	// sử dụng để kiểm tra xem mật khẩu của người dùng có hết hạn hay không.

	public boolean isPasswordExpired() {
		if (this.passwordChangedTime == null)
			return false;

		long currentTime = System.currentTimeMillis();
		long lastChangedTime = this.passwordChangedTime.getTime();

		return currentTime > lastChangedTime + PASSWORD_EXPIRATION_TIME;
	}
}

package edu.poly.colorshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.colorshop.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
    @Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.role.id IN ('DIRE','STAF')")
    List<Account> getAdministrations();

    @Query("select COUNT(a.username) from Account a ")
    Long getSize();

    Account findByEmail(String email);

    // sử dụng để xác thực mã thông báo khi người dùng nhấp vào liên kết thay đổi
    // mật khẩu trong email
    Account findByResetToken(String resetToken);

    @Query("SELECT a FROM Account a WHERE a.verificationCode = ?1")
    public Account findByVerificationCode(String code);
}

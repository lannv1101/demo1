package edu.poly.colorshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.entity.Authority;

public interface AuthorityRepo extends JpaRepository<Authority, Integer> {
    @Query("SELECT DISTINCT a FROM Authority a WHERE a.account IN ?1")
    List<Authority> authoritiesOf(List<Account> accounts);

    @Query("SELECT a FROM Authority a WHERE a.role.id=?1")
    List<Authority> findByRole(String role);
}

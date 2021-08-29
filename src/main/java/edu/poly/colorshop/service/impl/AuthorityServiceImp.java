package edu.poly.colorshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.entity.Authority;
import edu.poly.colorshop.repository.AccountRepo;
import edu.poly.colorshop.repository.AuthorityRepo;
import edu.poly.colorshop.service.AuthorityService;

@Service
@Repository
public class AuthorityServiceImp implements AuthorityService {
	@Autowired
	AuthorityRepo authorityRepo;
	@Autowired
	AccountRepo accountRepo;

	@Override
	public List<Authority> findByRole(String role) {
		return authorityRepo.findByRole(role);
	}

	@Override
	public List<Authority> findAll() {
		return authorityRepo.findAll();
	}

	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = accountRepo.getAdministrations();
		return authorityRepo.authoritiesOf(accounts);
	}

	@Override
	public <S extends Authority> S save(S entity) {
		return authorityRepo.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		authorityRepo.deleteById(id);
	}

}

package edu.poly.colorshop.service;

import java.util.List;
import edu.poly.colorshop.entity.Authority;

public interface AuthorityService {

	List<Authority> findAuthoritiesOfAdministrators();

	List<Authority> findAll();

	void deleteById(Integer id);

	<S extends Authority> S save(S entity);

	List<Authority> findByRole(String role);

}

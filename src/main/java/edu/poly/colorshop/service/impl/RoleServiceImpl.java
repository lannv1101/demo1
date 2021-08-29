package edu.poly.colorshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.poly.colorshop.entity.Role;
import edu.poly.colorshop.repository.RoleRepo;
import edu.poly.colorshop.service.RoleService;

@Service
@Repository
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepo roleRepo;

	@Override
	public List<Role> findAll() {
		return roleRepo.findAll();
	}

}

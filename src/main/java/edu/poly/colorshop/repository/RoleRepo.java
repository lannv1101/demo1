package edu.poly.colorshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.colorshop.entity.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, String> {

}

package com.mfi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	@Query("select r from Role r where r.roleId=?1") 
	  Role findbyRoleId(int id);
}

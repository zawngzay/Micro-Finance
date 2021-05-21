package com.mfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.Customer;
import com.mfi.model.Role;
import com.mfi.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository repo;
	public void save(Role role) {
		repo.save(role);
	}
	public void update(Role role) {
		repo.save(role);
	}
	
	public List<Role> selectAll(){
		return repo.findAll();
	}
	public Role selectId(int id) {
		return repo.findbyRoleId(id);
	}
}

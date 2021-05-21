package com.mfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.Permission;
import com.mfi.repository.PermissionRepository;

@Service
public class PermissionService {
	@Autowired
	PermissionRepository repo;

	public List<Permission> selectAll() {
		return repo.findAll();
	}

	public void save(Permission permission) {
		repo.save(permission);
	}

	public Permission selectById(int id) {
		return repo.findbyId(id);
	}

	public void createPermission(Permission permission) {
		repo.save(permission);
	}

	public Permission findByName(String name) {
		return repo.findByName(name);
	}

}

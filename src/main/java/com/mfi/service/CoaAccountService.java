package com.mfi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.COA;
import com.mfi.model.CurrentAccount;
import com.mfi.repository.CoaAccountRepository;

@Service
@Transactional
public class CoaAccountService {
	@Autowired
	CoaAccountRepository repo;
	public void update(COA coa) {
		repo.save(coa);
	}
	public COA getCOA(String code) {
		return repo.findbyCode(code);
	}
	
	public List<COA> selectAll(){
		return repo.findAll();
		
	}
	public void save(COA coa) {
		repo.save(coa);
	}
	 
	public void updateSave(double saving) {
		repo.updateSaving(saving);
	}
	
	public void updateCurrent(double current) {
		repo.updateCurrent(current);
	}
	
	public void updateLoan(double loan) {
		repo.updateLoan(loan);
	}
	public Double selectAmount(String glType) {
		return repo.findAmount(glType);
	}
	
	
}

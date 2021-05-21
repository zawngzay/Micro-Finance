package com.mfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.CurrentAccount;
import com.mfi.model.SavingAccount;
import com.mfi.repository.CurrentAccountRepository;

@Service
public class CurrentAccountService {
	@Autowired
	CurrentAccountRepository repo;
	
	public void save(CurrentAccount currentAccount) {
		repo.save(currentAccount);
	}
	public void update(CurrentAccount currentAccount) {
		repo.save(currentAccount);
	}
	
	public List<CurrentAccount> selectAll(){
		return repo.findAll();
	}
	
	public CurrentAccount getCurrentAccount(String code) {
		return repo.findbyCode(code);
	}
	
	
	public CurrentAccount getAccountNumber(String code) {
		return repo.getAccountNumber(code);
	}
	
	public double selectTotalCurrent() {
		return repo.totalCurrent();
		
	}
}

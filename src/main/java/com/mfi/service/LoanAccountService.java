package com.mfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mfi.model.LoanAccount;
import com.mfi.repository.LoanAccountRepository;

@Service
public class LoanAccountService {
	@Autowired
	LoanAccountRepository repo;
	
	public void save(LoanAccount loanAccount) {
		repo.save(loanAccount);
	}
	
	public List<LoanAccount> selectAll(){
		return repo.findAll();
	}
	public double selectTotalLoan() {
		return repo.totalLoan();
		
	}
	
	public LoanAccount getAccountNumber(String code) {
		return repo.getAccountNumber(code);
	}
	public void update(LoanAccount loanAccount) {
		repo.save(loanAccount);
	}
	
	public LoanAccount getLoanAccount(String code) {
		return repo.findbyCode(code);
	}
}
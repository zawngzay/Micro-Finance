package com.mfi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.LoanSchedule;
import com.mfi.repository.RepaymentRepository;

@Service
public class RepaymentService {
	@Autowired
	RepaymentRepository repo;
	
	public List<LoanSchedule> selectAll(){
		return repo.findAll();
	}
	
	
	public List<LoanSchedule> selectByDueDate(LocalDate date, String status){
		return repo.findByDueDate(date,status);
	}
	
	public List<LoanSchedule> completeLoan(LocalDate date, String status){
		return repo.completeLoan(date,status);
	}
	
	public List<LoanSchedule> incompleteLoan(LocalDate date, String status){
		return repo.incompleteLoan(date,status);
	}
	
	
	public void loanSchduleUpdate(LoanSchedule loanSchedule) {
		 repo.save(loanSchedule);
	}
}

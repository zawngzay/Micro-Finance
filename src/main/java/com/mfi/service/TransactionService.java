package com.mfi.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.Transaction;
import com.mfi.model.User;
import com.mfi.repository.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	TransactionRepository repo;
	public void deposit(Transaction transaction) {
		repo.save(transaction);
	}
	public void bankTran(Transaction transaction) {
		repo.save(transaction);
	}
	public void disbursement(Transaction transaction) {
		repo.save(transaction);
	}
	public void repayment(Transaction transaction) {
		repo.save(transaction);
	}
	
	public List<Transaction> accountTransaction(String number){
		return repo.accountTransaction(number);
	}
	
	public List<Transaction> recordByAccount(Date start, Date end, String number){
		return repo.recordByAccount(start,end,number);
	}
}

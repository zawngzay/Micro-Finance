package com.mfi.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.CurrentAccount;
import com.mfi.model.Customer;
import com.mfi.model.LoanAccount;
import com.mfi.model.LoanSchedule;
import com.mfi.model.SavingAccount;
import com.mfi.model.Transaction;
import com.mfi.repository.CurrentAccountRepository;
import com.mfi.repository.CustomerRepository;
import com.mfi.repository.LoanAccountRepository;
import com.mfi.repository.LoanScheduleRepository;
import com.mfi.repository.RepaymentRepository;
import com.mfi.repository.SavingAccountRepository;
import com.mfi.repository.TransactionRepository;


@Service

public class ReportService {
	@Autowired
	TransactionRepository tranRepo;
	@Autowired
	CustomerRepository cusRepo;
	@Autowired
	RepaymentRepository repaymentRepo;
	@Autowired
	LoanScheduleRepository loanRepo;
	@Autowired
	CurrentAccountRepository currentRepo;
	@Autowired
	SavingAccountRepository savingRepo;
	@Autowired
	LoanAccountRepository loanAccRepo;
	
	public List<Transaction> selectAllTransaction(Date date){
		return tranRepo.recordDateAscending(date);
	}
	public List<Transaction> selectByStartDate(Date start,Date end){
		return tranRepo.recordByStartDate(start,end);
	}
	public List<Customer> findCustomerByTodayDate(Date date){
		return cusRepo.findByTodayDate(date);
	}
	
	public List<Customer> customerFilter(Date start, Date end){
		return cusRepo.recordByStartDate(start, end);
	}
	public List<LoanSchedule> overDueFilter(LocalDate start, LocalDate end,String status){
		return repaymentRepo.overDueFilter(start, end, status);
	}
	public LoanSchedule completeSchdule(String status, int loanInfo){
		return loanRepo.findbyStatus(status,loanInfo);
	}
	public Double getSum(String status, int loanInfo){
		return loanRepo.getSum(status,loanInfo);
	}
	
	public List<LoanSchedule> findAllLoan(int loanInfo){
		return loanRepo.findAllLoan(loanInfo);
	}
	
	public List<CurrentAccount> currentAccountFilter(Date start, Date end){
		return currentRepo.accountFilter(start, end);
	}
	public List<SavingAccount> savingAccountFilter(Date start, Date end){
		return savingRepo.accountFilter(start, end);
	}
	public List<LoanAccount> loanAccountFilter(Date start, Date end){
		return loanAccRepo.accountFilter(start, end);
	}
	

	
}

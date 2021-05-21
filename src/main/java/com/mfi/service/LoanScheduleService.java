package com.mfi.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.LoanInfo;
import com.mfi.model.LoanSchedule;
import com.mfi.repository.LoanScheduleRepository;

@Service
@Transactional
public class LoanScheduleService {
	
	@Autowired
	
	

	LoanScheduleRepository loanScheduleRep;

	public  void addLoanScheduleForm(int paymentTermNo, LocalDate repaymentDate, Double beginningBalance, Date dueDate,
			Double totalRepaymentAmount, Double interestRate, Double lateFees, Double endingBalance, String status,
			int createdUser, LocalDate createdDate, int updateUser, LocalDate updateDate, LoanInfo loanInfo_id,Double principal) {
		
		LoanSchedule loanSchedule=new LoanSchedule();
		loanSchedule.setPaymentTermNo(paymentTermNo);
		loanSchedule.setRepaymentDate(repaymentDate);
		loanSchedule.setBeginningBalance(beginningBalance);
		loanSchedule.setDueDate(dueDate);
		loanSchedule.setTotalRepaymentAmount(totalRepaymentAmount);
		loanSchedule.setInterestRate(interestRate);
		loanSchedule.setLateFees(lateFees);
		loanSchedule.setEndingBalance(endingBalance);
		loanSchedule.setStatus(status);
		loanSchedule.setCreatedUser(createdUser);
		loanSchedule.setCreatedDate(createdDate);
		loanSchedule.setUpdateUser(updateUser);
		loanSchedule.setUpdateDate(updateDate);
		loanSchedule.setLoanInfo(loanInfo_id);
		loanSchedule.setPrincipal(principal);
		loanScheduleRep.save(loanSchedule);
		
	}
	
	public  double calMonthlyPayment(Double amount,double monthlyIR,Integer months) {
		return (monthlyIR * amount)/(1-Math.pow(1+monthlyIR,-months));
	}
	
//	
//	public  void displayAmortizationTable(Double amount,double monthlyIR,Integer months,LoanInfo loanInfo_id,int createdUser,
//			int updateUser,Date updateDate,LocalDate createdDate ) {
//		Double balance=amount;
//		double payment=calMonthlyPayment(amount, monthlyIR, months);
//		double irPaid;
//		double amountPaid;
//		double newBalance;
//		for(int payment_tern_no=1;payment_tern_no<=months;payment_tern_no++) {
//			irPaid=balance*monthlyIR;
//			amountPaid=payment-irPaid;
//			newBalance=balance-amountPaid;
//			addLoanScheduleForm(payment_tern_no, null, balance, null, payment, irPaid, null, 
//					newBalance,null, createdUser, createdDate, updateUser, updateDate, loanInfo_id);
//			balance=newBalance;
//		}
//	}
	
	
	public List<LoanSchedule> getLoanSchByLoanInfoId(LoanInfo loaninfo){
		return loanScheduleRep.findByLoanInfo(loaninfo);
	}
	
	public void removeLoanSchByLoanInfoId(LoanInfo loaninfo) {
		loanScheduleRep.deleteAll();
	}
	
	public void deleteLoanschByLoanInfoId(Integer loaninfo) {
		loanScheduleRep.deleteLoanSchedule(loaninfo);
	}
	
	public List<LoanSchedule> findbyLoanId(Integer loaninfoid) {
		return loanScheduleRep.getByLoanScheduleByloanId(loaninfoid);
	}
	
	public void save(LoanSchedule loanSchedule) {
		 loanScheduleRep.save(loanSchedule);
	}
}












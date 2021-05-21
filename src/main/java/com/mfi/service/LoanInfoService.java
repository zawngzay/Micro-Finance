package com.mfi.service;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ReportAsSingleViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mfi.model.Customer;
import com.mfi.model.LoanInfo;
import com.mfi.repository.LoanInfoRepository;

@Service
public class LoanInfoService {
	
	@Autowired
	LoanInfoRepository loanRep;
	
	public List<Customer> searchCustomer(String crmSearch){
		
		return loanRep.findCustomer(crmSearch);
		
		
	}
	
	public Customer searchCrmByCode(String crmCode) {
		return loanRep.findCrmCode(crmCode);
	}
	
	public List<LoanInfo> getLoanInfobyCrmCode(String crmCode) {
		return loanRep.getLoanInfoByCustomerCode(crmCode);
	}
	
//	public LoanInfo searchLoanInfobyTs(Timestamp date) {
//		return loanRep.findByTs(date);
//	}
	
	public LoanInfo getLoanInfobyId(Integer loanId) {
		return loanRep.getByLoanInfoId(loanId);
	}
	
	public LoanInfo addLoanInfo( Customer customer, Double loanAmount, Date registerDate, Integer loanTermYear,
			double interestRate, String description, Integer creditScore, String status,
			int createdUser, LocalDate createdDate, int updateUser, LocalDate updateDate) {
		
		LoanInfo loanInfo=new LoanInfo();
		loanInfo.setCustomer(customer);
		loanInfo.setLoanAmount(loanAmount);
		loanInfo.setRegisterDate(registerDate);
		loanInfo.setLoanTermYear(loanTermYear);
		loanInfo.setInterestRate(interestRate);
		loanInfo.setDescription(description);
		loanInfo.setCreditScore(creditScore);
		loanInfo.setStatus(status);
		loanInfo.setCreatedUser(createdUser);
		loanInfo.setCreatedDate(createdDate);
		loanInfo.setUpdateUser(updateUser);
		loanInfo.setUpdateDate(updateDate);
	
		
		LoanInfo savedLoanInfo =  loanRep.save(loanInfo);
		
		return savedLoanInfo;
		
	}
	
	
	public LoanInfo updateLoanInfo(LoanInfo loaninfo) {
		return loanRep.save(loaninfo);
	}

	
	public List<LoanInfo> getLoanInfo(String loansearch) {
		List<Customer> customers = loanRep.findCustomer(loansearch);
		List<LoanInfo> loanInfos = new ArrayList<>();
		if (customers != null) {
			for (Customer customer : customers) {
				List<LoanInfo> loanInfoList = loanRep.findByCustomer(customer);
				for (LoanInfo loanInfo : loanInfoList) {
					loanInfos.add(loanInfo);
				}
			}
			return loanInfos;
		}
	return null;
		
	}
	
	public List<LoanInfo> selectSatus(String status){
		return loanRep.findbyStatus(status);
	}
	public void save(LoanInfo loanInfo) {
		loanRep.save(loanInfo);
	}
	
	public LoanInfo selectOne(int loanId) {
		return loanRep.getByLoanInfoId(loanId);
	}

}

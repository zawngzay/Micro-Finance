package com.mfi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.Customer;
import com.mfi.model.LoanInfo;

public interface LoanInfoRepository extends JpaRepository<LoanInfo, Integer> {
	
	 @Query("select c from Customer c where c.customerCode = ?1 or c.nrc= ?1 or c.phone = ?1 or c.name = ?1 ")
		public List<Customer> findCustomer(String crmSearch);
	 
	 
	 @Query("select c from Customer c where c.customerCode = ?1")
	 public Customer findCrmCode(String crmCode);
	 

	 @Query("select l from LoanInfo l where l.customer.customerCode=?1") 
	 public List<LoanInfo> getLoanInfoByCustomerCode(String code);
	 
	 @Query("select l from LoanInfo l where l.LoanInfoId = ?1")
	 public LoanInfo getByLoanInfoId(Integer loanId);

	 public List<LoanInfo> findByCustomer(Customer customer);
	 
	 @Query("select l from LoanInfo l where l.status = ?1")
	 List<LoanInfo> findbyStatus(String status);
}

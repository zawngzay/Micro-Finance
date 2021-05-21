package com.mfi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
	@Query("SELECT t FROM Transaction t where t.createdDate=?1  ORDER BY t.createdDate DESC")
	List<Transaction> recordDateAscending(Date date); 
	
	@Query("select t from Transaction t where t.createdDate between ?1 and ?2")
	List<Transaction> recordByStartDate(Date start,Date end);
	
	@Query("select t from Transaction t where t.accountNumber=?1 ORDER BY t.createdDate DESC")
	List<Transaction> accountTransaction(String number);
	
	
	
	@Query("select t from Transaction t where t.createdDate between ?1 and ?2 and t.accountNumber=?3")
	List<Transaction> recordByAccount(Date start,Date end,String number);
}

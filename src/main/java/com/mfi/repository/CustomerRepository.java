package com.mfi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfi.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	
	
	  @Query("select c from Customer c where c.customerCode=?1") 
	  Customer findbyCode(String code);
	  
	  @Query("select c from Customer c where c.name=?1 or c.nrc=?1")
	  List<Customer> findbyNameOrNrc(String namenrc);
	  
	  @Query("select c from Customer c where c.createdDate=?1")
	  List<Customer> findByTodayDate (Date date);
	  
	  @Query("select c from Customer c where c.createdDate between ?1 and ?2")
	  List<Customer> recordByStartDate(Date start,Date end);
	  
	  @Query("select c from Customer c ORDER BY createdDate DESC")
	  List<Customer> customerLedger();
	  
	  Customer findByNrc(String nrc);
}

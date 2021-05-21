package com.mfi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfi.model.CurrentAccount;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, String> {
	@Query("select c from CurrentAccount c where c.customer.customerCode=?1") 
	CurrentAccount findbyCode(String code);
	
	@Query("select c from CurrentAccount c where c.currentAccountNumber=?1") 
	CurrentAccount getAccountNumber(String code);
	
	@Query("select c from CurrentAccount c where c.createdDate between ?1 and ?2")
	List<CurrentAccount> accountFilter(Date start, Date end);
	
	@Query(value = "SELECT SUM(balance) FROM current_account ", nativeQuery = true)
	public double totalCurrent();
	
	
}

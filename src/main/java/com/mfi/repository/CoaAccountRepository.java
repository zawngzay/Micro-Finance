package com.mfi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.COA;

public interface CoaAccountRepository extends JpaRepository<COA, String> {
	@Query("select c from COA c where c.glType=?1") 
	COA findbyCode(String code);
	
	@Modifying
	@Query(value = "UPDATE `mfi`.`coa` SET `amount` = ? WHERE (`gl_type` = 'Saving Account');", nativeQuery = true)
	public void updateSaving(double saving);
	
	@Modifying
	@Query(value = "UPDATE `mfi`.`coa` SET `amount` = ? WHERE (`gl_type` = 'Current Account');", nativeQuery = true)
	public void updateCurrent(double current);
	
	@Modifying
	@Query(value = "UPDATE `mfi`.`coa` SET `amount` = ? WHERE (`gl_type` = 'Loan Account');", nativeQuery = true)
	public void updateLoan(double loan);
	
	@Query(value = "SELECT amount FROM coa WHERE gl_type=?1", nativeQuery = true)
	public Double findAmount(String glType);
	
	

}

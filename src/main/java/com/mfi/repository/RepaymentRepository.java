package com.mfi.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.LoanSchedule;

public interface RepaymentRepository extends JpaRepository<LoanSchedule, String> {
	
	@Query("select ls from LoanSchedule ls where repaymentDate<=?1 and status=?2")
	List<LoanSchedule> findByDueDate(LocalDate date, String status);
	
	@Query("select ls from LoanSchedule ls where updateDate<=?1 and status=?2")
	List<LoanSchedule> completeLoan(LocalDate date, String status);
	
	@Query("select ls from LoanSchedule ls where updateDate<=?1 and status=?2")
	List<LoanSchedule> incompleteLoan(LocalDate date, String status);
	
	@Query("select ls from LoanSchedule ls where repaymentDate between ?1 and ?2 and status=?3")
	List<LoanSchedule> overDueFilter(LocalDate start,LocalDate end,String status);
}

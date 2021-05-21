package com.mfi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.LoanInfo;

public interface DisbursementRepository extends JpaRepository<LoanInfo, String> {
	 @Query("select l from LoanInfo l where l.status=?1")
	 List<LoanInfo> findbyStatus(String status);
}

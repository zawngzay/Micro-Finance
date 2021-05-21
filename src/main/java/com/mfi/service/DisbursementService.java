package com.mfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.LoanInfo;
import com.mfi.repository.DisbursementRepository;
@Service
public class DisbursementService {
@Autowired
DisbursementRepository repo;
public List<LoanInfo> selectDisbursement(String status){
	return repo.findbyStatus(status);
}
}

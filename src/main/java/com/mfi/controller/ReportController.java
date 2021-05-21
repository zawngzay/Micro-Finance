package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.formmodel.CurrentAccountForm;
import com.mfi.formmodel.LoanAccountForm;
import com.mfi.formmodel.SavingAccountForm;
import com.mfi.model.CurrentAccount;
import com.mfi.model.Customer;
import com.mfi.model.LoanAccount;
import com.mfi.model.LoanInfo;
import com.mfi.model.LoanSchedule;
import com.mfi.model.SavingAccount;
import com.mfi.model.Transaction;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.LoanInfoService;
import com.mfi.service.RepaymentService;
import com.mfi.service.ReportService;
import com.mfi.service.SavingAccountService;

@Controller
public class ReportController {
	@Autowired
	ReportService reportService;
	@Autowired
	LoanInfoService loanInfoService;
	@Autowired
	CurrentAccountService currentService;
	@Autowired
	SavingAccountService savingService;
	@Autowired
	LoanAccountService loanAccountService;
	@Autowired
	RepaymentService repaymentService;
	
	List<CurrentAccount> current = new ArrayList<CurrentAccount>();
	List<SavingAccount> saving = new ArrayList<SavingAccount>();
	List<LoanAccount> loan = new ArrayList<LoanAccount>();
	List<Transaction> trans=new ArrayList<Transaction>();
	
	
	
//	@RequestMapping("/accountListing")
//	public String accountListing(Model model) {
//		List<CurrentAccount> currentAccount = currentService.selectAll();
//		List<SavingAccount> savingAccount = savingService.selectAll();
//		List<LoanAccount> loanAccount = loanAccountService.selectAll();
//		model.addAttribute("currentAccount",currentAccount);
//		model.addAttribute("savingAccount",savingAccount);
//		model.addAttribute("loanAccount",loanAccount);
//		return "mfi/reports/MFI_RPT_ACC";
//	}
//
//	
//	
//	
//	
//	@PostMapping("/accountFilter")
//	public String accountFilter(@Param("start")String start, @Param("end")String end, Model model) {
//		
//		Date startdate = Date.valueOf(start);
//		Date enddate = Date.valueOf(end);
//		
//		List<CurrentAccount> currentAccount = reportService.currentAccountFilter(startdate, enddate);
//		List<SavingAccount> savingAccount = reportService.savingAccountFilter(startdate,enddate);
//		List<LoanAccount> loanAccount = reportService.loanAccountFilter(startdate,enddate);
//		model.addAttribute("currentAccount",currentAccount);
//		model.addAttribute("savingAccount",savingAccount);
//		model.addAttribute("loanAccount",loanAccount);
//		return "mfi/reports/MFI_RPT_CLB";
//	}
//	
//	@RequestMapping("/loanOutstanding")
//	public String loanOutstandingBalance(Model model) {
//		List<LoanInfo> loanInfo = loanInfoService.selectSatus("Progress");
//		List<LoanSchedule> loan = new ArrayList<LoanSchedule>();
//		int allLoan = 0;
//		
//		for(int i=0; i<loanInfo.size(); i++) {
//			Integer loanInfoID =  loanInfo.get(i).getLoanInfoId();
//			LoanSchedule completeSchdule = reportService.completeSchdule("Complete",loanInfoID);
//			
//			loan.add(completeSchdule);
//			
//			List<LoanSchedule> findAllLoan = reportService.findAllLoan(i+1);
//			allLoan=findAllLoan.size();
//			
//			model.addAttribute("allLoan",allLoan);
//			Double completeAmount = reportService.getSum("Complete", loanInfoID);
//			System.out.println(completeAmount);
//			
//			model.addAttribute("completeAmount",completeAmount);
//		}
//		
//		model.addAttribute("completeSchdule",loan);
//	
//			return "mfi/reports/MFI_RPT_LOB";
//	}
	
	
//	@RequestMapping("customerLedger")
//	public String customerLedgerBalance(Model model) {
//		List<CurrentAccount> currentAccount = currentService.selectAll();
//		List<SavingAccount> savingAccount = savingService.selectAll();
//		List<LoanAccount> loanAccount = loanAccountService.selectAll();
//		model.addAttribute("currentAccount",currentAccount);
//		model.addAttribute("savingAccount",savingAccount);
//		model.addAttribute("loanAccount",loanAccount);
//		
//		current.addAll(currentAccount);
//		saving.addAll(savingAccount);
//		loan.addAll(loanAccount);
//		
//		return "mfi/reports/MFI_RPT_CLB";
//	}
}

package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfi.model.COA;
import com.mfi.model.LoanAccount;
import com.mfi.model.Transaction;
import com.mfi.service.CoaAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.TransactionService;
@Controller
public class LoanAccountController {
	@Autowired
	LoanAccountService loanAccountService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	CoaAccountService coaService;
	
	
	@PostMapping("/loanAccountSearch")
	public String loanAccountSearch(@Param("loanAccountNumber") String loanAccountNumber, Model model) {
		
		LoanAccount account = loanAccountService.getAccountNumber(loanAccountNumber);
		if(account==null) {
			model.addAttribute("notfound",true);
			return "mfi/account/MFI_LAC_02";
		}
		model.addAttribute("account", account);
		return "mfi/account/MFI_LAC_02";
	}

	@GetMapping("/loanDetail/{number}")
	public String loanDetail(@PathVariable("number") String loanAccountNumber, Model model) {

		LoanAccount account = loanAccountService.getAccountNumber(loanAccountNumber);
		List<Transaction> trasactionList = transactionService.accountTransaction(loanAccountNumber);
		model.addAttribute("account", account);
		model.addAttribute("trasactionList", trasactionList);
		// System.out.println(account.getCustomer().getName());
		return "mfi/account/MFI_LAC_04";
	}

	@GetMapping("/closeLoanAccount/{number}")
	public String closeLoanAccount(@PathVariable("number") String loanAccountNumber, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();

		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);

		LoanAccount account = loanAccountService.getAccountNumber(loanAccountNumber);
		Double amount = account.getLoanAmount();

		account.setLoanAmount(0.0);
		account.setAccountStatus(false);
		account.setUpdateDate(date);
		account.setUpdateUser(userId);
		account.setCloseDate(date);
		loanAccountService.update(account);

		COA coa = coaService.getCOA("Cash");
		coa.setAmount(coa.getAmount());
		coa.setUpdateDate(date);
		coa.setUpdateUser(userId);
		coaService.update(coa);
		


		// current account transaction
		Transaction loanTransaction = new Transaction();
		loanTransaction.setTransactionType("Credit");
		loanTransaction.setAccountName(account.getCustomer().getName());
		loanTransaction.setCoaId(6);
		loanTransaction.setAccountNumber(account.getLoanAccountNumber());
		loanTransaction.setAmount(amount);
		loanTransaction.setNarration("Account Closing");
		loanTransaction.setCreatedDate(date);
		loanTransaction.setCreatedUser(userId);
		transactionService.deposit(loanTransaction);

//		coa account transaction
		Transaction bankTran = new Transaction();
		bankTran.setTransactionType("Credit");
		bankTran.setAccountName("Bank Cash Account");
		bankTran.setCoaId(coa.getCoaId());
		bankTran.setAccountNumber(coa.getAccountNumber());
		bankTran.setAmount(coa.getAmount());
		bankTran.setNarration("Account Closing");
		bankTran.setCreatedDate(date);
		bankTran.setCreatedUser(userId);
		transactionService.bankTran(bankTran);
		model.addAttribute("close",true);
		return "mfi/account/MFI_LAC_02";
	}

	@GetMapping("/reopenLoanAccount/{number}")
	public String reopenLoanAccount(@PathVariable("number") String loanAccountNumber, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();

		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);

		LoanAccount account = loanAccountService.getAccountNumber(loanAccountNumber);

		account.setLoanAmount(0.0);
		account.setAccountStatus(true);
		account.setUpdateDate(date);
		account.setUpdateUser(userId);
		loanAccountService.update(account);
		model.addAttribute("reactive",true);
		return "mfi/account/MFI_LAC_02";
	}

	@PostMapping("/loanAccountTransactionSearch/{number}")
	public String transactionSearch(@Param("start") String start, @Param("end") String end,
			@PathVariable("number") String loanAccountNumber, Model model) {

		Date startdate = Date.valueOf(start);
		Date enddate = Date.valueOf(end);
		List<Transaction> trasactionList = transactionService.recordByAccount(startdate, enddate, loanAccountNumber);
		model.addAttribute("trasactionList", trasactionList);

		LoanAccount account = loanAccountService.getAccountNumber(loanAccountNumber);
		model.addAttribute("account", account);

		return "account/MFI_CAC_04";
	}

}
package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mfi.model.COA;
import com.mfi.model.CurrentAccount;
import com.mfi.model.Transaction;
import com.mfi.service.CoaAccountService;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.TransactionService;

@Controller
public class DepositeController {
	
	@Autowired
	TransactionService transactionService;
	@Autowired
	CurrentAccountService currentAccountService;
	@Autowired
	CoaAccountService coaService;
	
	
	
	@GetMapping(path = "/getCurrentAccountInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CurrentAccount> getCurrentAccountByCurrentAccountNo(@RequestParam(name = "accountNo", required = true) String accountNo) {
		System.out.println("accNO" + accountNo);
		//CurrentAccount account = currentService.getAccountNumber(currentAccountNumber);
		CurrentAccount currentAccount=currentAccountService.getAccountNumber(accountNo);
		System.out.println("cur" + currentAccount);
		if(currentAccount.equals(null)) {

			return null;
		}

		return new ResponseEntity<CurrentAccount>(currentAccount, HttpStatus.OK);
	}
	

	@PostMapping("/deposit")
	public String deposit(@ModelAttribute("depositBean")@Valid Transaction transaction , BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "mfi/transaction/MFI_DSP_01";
		}
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		
		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);
//		get amount
		Double amount = transaction.getAmount();
		String accountNo=transaction.getAccountNumber();
		CurrentAccount currentAcc=currentAccountService.getAccountNumber(accountNo);
		
		if(currentAcc == null ) {
			model.addAttribute("transaction", transaction);
			model.addAttribute("accountnotexist",true);
			return "mfi/transaction/MFI_DSP_01";
			
		}else if(currentAcc.getAccountStatus().equals(false)) {
			model.addAttribute("transaction", transaction);
			model.addAttribute("deactive",true);
			return "mfi/transaction/MFI_DSP_01";
		}
		CurrentAccount currentAccount = currentAccountService.getAccountNumber(transaction.getAccountNumber());
		Double balance = currentAccount.getBalance()+amount;
		currentAccount.setBalance(balance);
		currentAccount.setUpdateDate(date);
		currentAccount.setUpdateUser(userId);
		currentAccountService.update(currentAccount);
		
//		coa cash update
		COA coa = coaService.getCOA("Cash");
		Double coaBalance= coa.getAmount()+amount;
		coa.setAmount(coaBalance);
		coa.setUpdateDate(date);
		coa.setUpdateUser(userId);
		coaService.update(coa);

//		coa current update
		COA coaCurrent = coaService.getCOA("Current Account");
		Double coaCuBalance =currentAccountService.selectTotalCurrent();
		coaCurrent.setAmount(coaCuBalance);
		coaCurrent.setUpdateDate(date);
		coaCurrent.setUpdateUser(userId);
		coaService.update(coaCurrent);
		
		
//		transaction.setDate(date);
		transaction.setTransactionType("Credit");
		transaction.setCoaId(5);
		transaction.setCreatedUser(userId);
		transaction.setCreatedDate(date);
		transactionService.deposit(transaction);
		
		Transaction bankTran = new Transaction();
		bankTran.setTransactionType("Debit");
		bankTran.setAccountName("Bank Cash Account");
		bankTran.setCoaId(coa.getCoaId());
		bankTran.setAccountNumber(coa.getAccountNumber());
		bankTran.setAmount(amount);
		bankTran.setNarration("Customer Deposit");
		bankTran.setCreatedUser(userId);
		bankTran.setCreatedDate(date);
		transactionService.bankTran(bankTran);
		model.addAttribute("depositBean", new Transaction());
		model.addAttribute("reg",true);
		return "mfi/transaction/MFI_DSP_01";
	}
	
}
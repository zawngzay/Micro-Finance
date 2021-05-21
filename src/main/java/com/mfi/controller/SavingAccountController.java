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

import com.mfi.model.COA;
import com.mfi.model.CurrentAccount;
import com.mfi.model.SavingAccount;
import com.mfi.model.Transaction;
import com.mfi.service.CoaAccountService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.SavingAccountService;
import com.mfi.service.TransactionService;

@Controller
public class SavingAccountController {

	@Autowired
	SavingAccountService savingAccountService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	CoaAccountService coaService;

	@PostMapping("/savingAccountSearch")
	public String currentSearch(@Param("savingAccountNumber") String savingAccountNumber, Model model) {

		SavingAccount account = savingAccountService.getAccountNumber(savingAccountNumber);

		if (account != null) {

			model.addAttribute("account", account);
			return "mfi/account/MFI_SAC_02";
		} else {
			model.addAttribute("notfound", true);
			return "mfi/account/MFI_SAC_02";
		}

	}

	@GetMapping("/savingDetail/{number}")
	public String savingDetail(@PathVariable("number") String savingAccountNumber, Model model) {

		SavingAccount account = savingAccountService.getAccountNumber(savingAccountNumber);
		List<Transaction> trasactionList = transactionService.accountTransaction(savingAccountNumber);
		model.addAttribute("account", account);
		model.addAttribute("trasactionList", trasactionList);
		// System.out.println(account.getCustomer().getName());
		return "mfi/account/MFI_SAC_04";

	}

	@GetMapping("/closeSavingAccount/{number}")
	public String closeSavingAccount(@PathVariable("number") String savingAccountNumber, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();

		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);

		SavingAccount account = savingAccountService.getAccountNumber(savingAccountNumber);
		Double amount = account.getBalance();
		System.out.println("Close saving account" + account);
		account.setBalance(0.0);
		account.setAccountStatus(false);
		account.setUpdateDate(date);
		account.setUpdateUser(userId);

		account.setCloseDate(date);
		savingAccountService.update(account);

//		coa cash update
		COA coa = coaService.getCOA("Cash");
		coa.setAmount(coa.getAmount() - amount);
		coa.setUpdateDate(date);
		coa.setUpdateUser(userId);
		coaService.update(coa);

//		coa saving update
		COA coaSaving = coaService.getCOA("Saving Account");
		Double coaBalance = savingAccountService.selectTotalSaving();
		coaSaving.setAmount(coaBalance);
		coaSaving.setUpdateDate(date);
		coaSaving.setUpdateUser(userId);
		coaService.update(coaSaving);

		// saving account transaction
		Transaction savingTransaction = new Transaction();
		savingTransaction.setTransactionType("Credit");
		savingTransaction.setAccountName(account.getCustomer().getName());
		savingTransaction.setCoaId(5);
		savingTransaction.setAccountNumber(account.getSavingAccountNumber());
		savingTransaction.setAmount(amount);
		savingTransaction.setNarration("Account Closing");
		savingTransaction.setCreatedDate(date);
		savingTransaction.setCreatedUser(userId);
		transactionService.deposit(savingTransaction);

//		coa account transaction
		Transaction bankTran = new Transaction();
		bankTran.setTransactionType("Credit");
		bankTran.setAccountName("Bank Cash Account");
		bankTran.setCoaId(coa.getCoaId());
		bankTran.setAccountNumber(coa.getAccountNumber());
		bankTran.setAmount(amount);
		bankTran.setNarration("Account Closing");
		bankTran.setCreatedDate(date);
		bankTran.setCreatedUser(userId);
		transactionService.bankTran(bankTran);
		model.addAttribute("close", true);
		return "mfi/account/MFI_SAC_02";
	}

	@GetMapping("/reopenSavingAccount/{number}")
	public String reopenSavingAccount(@PathVariable("number") String savingAccountNumber, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();

		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);

		SavingAccount account = savingAccountService.getAccountNumber(savingAccountNumber);
		System.out.println("Reopen saving account" + account);
		Double amount = 1000.00;

		// account.setBalance(amount);
		account.setAccountStatus(true);
		account.setUpdateDate(date);
		account.setUpdateUser(userId);
		savingAccountService.update(account);

		COA coa = coaService.getCOA("Cash");
		coa.setAmount(coa.getAmount() + amount);
		coa.setUpdateDate(date);
		coa.setUpdateUser(userId);
		coaService.update(coa);

//		coa saving update
		COA coaSaving = coaService.getCOA("Saving Account");
		//Double coaBalance = savingAccountService.selectTotalSaving();
		coaSaving.setAmount(coaSaving.getAmount()+amount);
		coaSaving.setUpdateDate(date);
		coaSaving.setUpdateUser(userId);
		coaService.update(coaSaving);

		// saving account transaction
		Transaction savingTransaction = new Transaction();
		savingTransaction.setTransactionType("Debit");
		savingTransaction.setAccountName(account.getCustomer().getName());
		savingTransaction.setCoaId(5);
		savingTransaction.setAccountNumber(account.getSavingAccountNumber());
		savingTransaction.setAmount(amount);
		savingTransaction.setNarration("Account Closing");
		savingTransaction.setCreatedDate(date);
		savingTransaction.setCreatedUser(userId);
		transactionService.deposit(savingTransaction);

//		coa account transaction
		Transaction bankTran = new Transaction();
		bankTran.setTransactionType("Debit");
		bankTran.setAccountName("Bank Cash Account");
		bankTran.setCoaId(coa.getCoaId());
		bankTran.setAccountNumber(coa.getAccountNumber());
		bankTran.setAmount(amount);
		bankTran.setNarration("Account Closing");
		bankTran.setCreatedDate(date);
		bankTran.setCreatedUser(userId);
		transactionService.bankTran(bankTran);
		model.addAttribute("reactive", true);
		return "mfi/account/MFI_SAC_02";

	}

	@PostMapping("/savingAccountTransactionSearch/{number}")
	public String transactionSearch(@Param("start") String start, @Param("end") String end,
			@PathVariable("number") String savingAccountNumber, Model model) {

		Date startdate = Date.valueOf(start);
		Date enddate = Date.valueOf(end);
		List<Transaction> trasactionList = transactionService.recordByAccount(startdate, enddate, savingAccountNumber);
		model.addAttribute("trasactionList", trasactionList);

		SavingAccount account = savingAccountService.getAccountNumber(savingAccountNumber);
		model.addAttribute("account", account);

		return "mfi/account/MFI_SAC_04";
	}

}
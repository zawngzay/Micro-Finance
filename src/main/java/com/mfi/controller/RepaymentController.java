package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.model.COA;
import com.mfi.model.CurrentAccount;
import com.mfi.model.LoanAccount;
import com.mfi.model.LoanSchedule;
import com.mfi.model.Transaction;
import com.mfi.service.CoaAccountService;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.RepaymentService;
import com.mfi.service.TransactionService;

@Controller
public class RepaymentController {

	@Autowired
	RepaymentService repaymentService;
	@Autowired
	LoanAccountService loanAccountService;
	@Autowired
	CurrentAccountService currentAccountService;
	@Autowired
	CoaAccountService coaService;
	@Autowired
	TransactionService transactionService;

	@RequestMapping("/repaymentPreview")
	public String repaymentPreview(Model model) {
		LocalDate now = LocalDate.now();

		List<LoanSchedule> LoanSchdule = repaymentService.selectByDueDate(now, "active");
		model.addAttribute("loanSchdule", LoanSchdule);
		model.addAttribute("localDate", LocalDate.now());
		return "mfi/transaction/MFI_RPM_02";
	}

	@RequestMapping("/payment")
	public String payment(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();

		COA cash = coaService.getCOA("Cash");
		COA income = coaService.getCOA("Income");
		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);

		List<LoanSchedule> list = repaymentService.selectByDueDate(now, "active");
		int i = 0;
		while (i < list.size()) {

			LoanSchedule loanSchdule = list.get(i);
			LocalDate repaymentDate = loanSchdule.getRepaymentDate();
			Double repaymentAmount = loanSchdule.getTotalRepaymentAmount();
			Double principal = loanSchdule.getPrincipal();
			Double interest = loanSchdule.getInterestRate();
			String customerCode = loanSchdule.getLoanInfo().getCustomer().getCustomerCode(); // zz
			CurrentAccount account = currentAccountService.getCurrentAccount(customerCode); // zz
			// System.out.println(account);
			// CurrentAccount account =
			// loanSchdule.getLoanInfo().getCustomer().getCurrentAccount(); //yinmat

			if (repaymentAmount < account.getBalance() && repaymentDate.equals(now)) {
				// current account minus
				account.setBalance(account.getBalance() - repaymentAmount);
				account.setUpdateDate(date);
				account.setUpdateUser(userId);
				currentAccountService.save(account);

				// coa current update
				COA coaCurrent = coaService.getCOA("Current Account");
				Double coaCuBalance = currentAccountService.selectTotalCurrent();
				coaCurrent.setAmount(coaCuBalance);
				coaCurrent.setUpdateDate(date);
				coaCurrent.setUpdateUser(userId);
				coaService.update(coaCurrent);

//				// coa plus
//				cash.setAmount(cash.getAmount() + principal);
//				cash.setUpdateDate(date);
//				cash.setUpdateUser(userId);
//				coaService.update(cash);

				income.setAmount(income.getAmount() + interest);
				income.setUpdateDate(date);
				income.setUpdateUser(userId);
				coaService.update(income);

				// loan schdule update
				loanSchdule.setStatus("Complete");
				loanSchdule.setUpdateDate(now);
				loanSchdule.setUpdateUser(userId);
				repaymentService.loanSchduleUpdate(loanSchdule);

				// loan acc & loan info update
				LoanAccount loanAccount = loanAccountService.getLoanAccount(customerCode); // zz
				// LoanAccount loanAccount =
				// loanSchdule.getLoanInfo().getCustomer().getLoanAccount(); // yin myat
				loanAccount.setLoanAmount(loanAccount.getLoanAmount() - principal);
				loanAccount.setUpdateDate(date);
				loanAccount.setUpdateUser(userId);
				loanAccountService.save(loanAccount);

				COA coaLoan = coaService.getCOA("Loan Account");
				Double coaLoanBalance = loanAccountService.selectTotalLoan();
				coaLoan.setAmount(coaLoanBalance);
				coaLoan.setUpdateDate(date);
				coaLoan.setUpdateUser(userId);
				coaService.update(coaLoan);

				if (loanAccount.getLoanAmount() == 0) {
					loanAccount.setAccountStatus(false);
					loanAccount.setUpdateDate(date);
					loanAccount.setUpdateUser(userId);
					loanAccountService.save(loanAccount);

					loanSchdule.getLoanInfo().setStatus("Loan Complete");
					loanSchdule.getLoanInfo().setUpdateDate(now);
					loanSchdule.getLoanInfo().setUpdateUser(userId);
					repaymentService.loanSchduleUpdate(loanSchdule);
				}
				// current transaction
				Transaction currentTransaction = new Transaction();
				currentTransaction.setTransactionType("Debit");
				currentTransaction.setAccountName(loanSchdule.getLoanInfo().getCustomer().getName());
				currentTransaction.setCoaId(5);
				currentTransaction.setAccountNumber(account.getCurrentAccountNumber());
				currentTransaction.setAmount(repaymentAmount);
				currentTransaction.setNarration("Loan Repayment");
				currentTransaction.setCreatedDate(date);
				currentTransaction.setUpdateUser(userId);
				transactionService.repayment(currentTransaction);

				// loan transaction
				Transaction loanTransaction = new Transaction();
				loanTransaction.setTransactionType("Debit");
				loanTransaction.setAccountName(loanSchdule.getLoanInfo().getCustomer().getName());
				loanTransaction.setCoaId(6);
				loanTransaction.setAccountNumber(loanAccount.getLoanAccountNumber());
				loanTransaction.setAmount(repaymentAmount);
				loanTransaction.setNarration("Loan Repayment");
				loanTransaction.setCreatedDate(date);
				loanTransaction.setUpdateUser(userId);
				transactionService.repayment(currentTransaction);

//				// cash account transaction
//				Transaction cashTran = new Transaction();
//				cashTran.setTransactionType("Credit");
//				cashTran.setAccountName("Bank Cash Account");
//				cashTran.setCoaId(cash.getCoaId());
//				cashTran.setAccountNumber(cash.getAccountNumber());
//				cashTran.setAmount(principal);
//				cashTran.setNarration("Loan Repayment");
//				cashTran.setCreatedDate(date);
//				cashTran.setUpdateUser(userId);
//				transactionService.bankTran(cashTran);

				// income account transaction
				Transaction incomeTran = new Transaction();
				incomeTran.setTransactionType("Credit");
				incomeTran.setAccountName("Bank Cash Account");
				incomeTran.setCoaId(income.getCoaId());
				incomeTran.setAccountNumber(income.getAccountNumber());
				incomeTran.setAmount(interest);
				incomeTran.setNarration("Loan Income");
				incomeTran.setCreatedDate(date);
				incomeTran.setUpdateUser(userId);
				transactionService.bankTran(incomeTran);

			}

			else if (repaymentAmount < account.getBalance() && now.isAfter(repaymentDate)) {
				// late fee calculation
				long noOfDaysBetween = ChronoUnit.DAYS.between(repaymentDate, now);
				Double latefee;
				if (noOfDaysBetween <= 30) {
					latefee = (double) (noOfDaysBetween * 50);
				} else {
					Double firstLateFee = (double) (30 * 50);
					Double secondLateFee = (double) ((noOfDaysBetween - 30) * 100);
					latefee = firstLateFee + secondLateFee;
				}

				// current minus
				account.setBalance(account.getBalance() - (repaymentAmount + latefee));
				account.setUpdateDate(date);
				account.setUpdateUser(userId);
				currentAccountService.save(account);

				// coa plus
//				cash.setAmount(cash.getAmount() + principal);
//				cash.setUpdateDate(date);
//				cash.setUpdateUser(userId);
//				coaService.update(cash);

				// coa current update
				COA coaCurrent = coaService.getCOA("Current Account");
				Double coaCuBalance = currentAccountService.selectTotalCurrent();
				coaCurrent.setAmount(coaCuBalance);
				coaCurrent.setUpdateDate(date);
				coaCurrent.setUpdateUser(userId);
				coaService.update(coaCurrent);

				income.setAmount(income.getAmount() + (interest + latefee));
				income.setUpdateDate(date);
				income.setUpdateUser(userId);
				coaService.update(income);

				// loan schdule update
				loanSchdule.setStatus("Complete");
				loanSchdule.setLateFees(latefee);
				loanSchdule.setUpdateDate(now);
				loanSchdule.setUpdateUser(userId);
				repaymentService.loanSchduleUpdate(loanSchdule);

				// loan acc & loan info update
				LoanAccount loanAccount = loanAccountService.getLoanAccount(customerCode); // zz
				// LoanAccount loanAccount =
				// loanSchdule.getLoanInfo().getCustomer().getLoanAccount(); //yin myat
				loanAccount.setLoanAmount(loanAccount.getLoanAmount() - principal);
				loanAccountService.save(loanAccount);
				if (loanAccount.getLoanAmount() == 0) {
					loanAccount.setAccountStatus(false);
					loanAccount.setUpdateDate(date);
					loanAccount.setUpdateUser(userId);
					loanAccountService.save(loanAccount);

					loanSchdule.getLoanInfo().setStatus("Loan Complete");
					loanSchdule.getLoanInfo().setUpdateDate(now);
					loanSchdule.setUpdateUser(userId);
					repaymentService.loanSchduleUpdate(loanSchdule);
				}
				COA coaLoan = coaService.getCOA("Loan Account");
				Double coaLoanBalance = loanAccountService.selectTotalLoan();
				System.out.println(coaLoanBalance);
				coaLoan.setAmount(coaLoanBalance);
				coaLoan.setUpdateDate(date);
				coaLoan.setUpdateUser(userId);
				coaService.update(coaLoan);
				// current transaction
				Transaction currentTransaction = new Transaction();
				currentTransaction.setTransactionType("Debit");
				currentTransaction.setAccountName(loanSchdule.getLoanInfo().getCustomer().getName());
				currentTransaction.setCoaId(5);
				currentTransaction.setAccountNumber(account.getCurrentAccountNumber());
				currentTransaction.setAmount(repaymentAmount);
				currentTransaction.setNarration("Loan Repayment");
				currentTransaction.setCreatedDate(date);
				currentTransaction.setUpdateUser(userId);
				transactionService.repayment(currentTransaction);

				// loan transaction
				Transaction loanTransaction = new Transaction();
				loanTransaction.setTransactionType("Debit");
				loanTransaction.setAccountName(loanSchdule.getLoanInfo().getCustomer().getName());
				loanTransaction.setCoaId(6);
				loanTransaction.setAccountNumber(loanAccount.getLoanAccountNumber());
				loanTransaction.setAmount(repaymentAmount);
				loanTransaction.setNarration("Loan Repayment");
				loanTransaction.setCreatedDate(date);
				loanTransaction.setUpdateUser(userId);
				transactionService.repayment(currentTransaction);

				// cash account transaction
//				Transaction cashTran = new Transaction();
//				cashTran.setTransactionType("Credit");
//				cashTran.setAccountName("Bank Cash Account");
//				cashTran.setCoaId(cash.getCoaId());
//				cashTran.setAccountNumber(cash.getAccountNumber());
//				cashTran.setAmount(principal);
//				cashTran.setNarration("Loan Repayment");
//				cashTran.setCreatedDate(date);
//				cashTran.setUpdateUser(userId);
//				transactionService.bankTran(cashTran);

				// income account transaction
				Transaction incomeTran = new Transaction();
				incomeTran.setTransactionType("Credit");
				incomeTran.setAccountName("Bank Cash Account");
				incomeTran.setCoaId(income.getCoaId());
				incomeTran.setAccountNumber(income.getAccountNumber());
				incomeTran.setAmount(interest);
				incomeTran.setNarration("Loan Income");
				incomeTran.setCreatedDate(date);
				incomeTran.setUpdateUser(userId);
				transactionService.bankTran(incomeTran);

			} else {
				loanSchdule.setUpdateDate(now);
				loanSchdule.setUpdateUser(userId);
				repaymentService.loanSchduleUpdate(loanSchdule);
			}

//			System.out.println(loan.getStatus());
			i++;
		}
		List<LoanSchedule> completeLoan = repaymentService.completeLoan(now, "Complete");
		model.addAttribute("loanSchdule", completeLoan);
		model.addAttribute("localDate", LocalDate.now());
		return "mfi/transaction/MFI_RPM_02";
	}
}
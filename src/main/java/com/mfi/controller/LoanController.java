package com.mfi.controller;

import java.sql.Date;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfi.formmodel.LoanInfoForm;
import com.mfi.model.CurrentAccount;
import com.mfi.model.Customer;
import com.mfi.model.LoanAccount;
import com.mfi.model.LoanInfo;
import com.mfi.model.LoanSchedule;
import com.mfi.model.SavingAccount;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.LoanInfoService;
import com.mfi.service.LoanScheduleService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.SavingAccountService;

@Controller
public class LoanController {
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	@Autowired
	LoanInfoService loanservice;

	@Autowired
	CurrentAccountService currentService;
	@Autowired
	SavingAccountService savingService;
	@Autowired
	LoanAccountService loanAccountService;

	@Autowired
	LoanScheduleService loanScheduleservice;

	@RequestMapping("/searchCRM")
	public String searchCustomer(Model model, @RequestParam(name = "crmSearch", required = false) String crmSearch) {
		List<Customer> customers = new ArrayList<>();
		customers = loanservice.searchCustomer(crmSearch);
		model.addAttribute("customerList", customers);
		model.addAttribute("crmSearch", crmSearch);
		if (customers.isEmpty()) {
			model.addAttribute("notfound", true);
			return "mfi/loan/MFI_LON_01";
		}
		return "mfi/loan/MFI_LON_01";
	}

	// LoanInfoSearching
	@RequestMapping("/searchloanInfo")
	public String searchloanInfo(Model model, @RequestParam(name = "loanSearch", required = false) String loanSearch) {

		if (loanSearch != null) {
			List<LoanInfo> loanInfo = new ArrayList<>();

			loanInfo = loanservice.getLoanInfo(loanSearch);
			if (loanInfo.size() == 0) {
				model.addAttribute("notfound", true);

			}
			model.addAttribute("loanInfolist", loanInfo);
			model.addAttribute("loanSearch", loanSearch);
			return "mfi/loan/MFI_LON_02";
		} else {
			return "mfi/loan/MFI_LON_02";
		}

	}

	@RequestMapping("/caculateCreditScore/{id}")
	public String calculateCreditScore(@PathVariable("id") String crmCode, Model model) {
		Customer customer = loanservice.searchCrmByCode(crmCode);
		if(customer.isDelStatus()) {
			model.addAttribute("delstatus", true);
			return "mfi/loan/MFI_LON_01";
		}
		int creditTotal = 0;
		int creditIncome = 0;
		int creditAge = 0;
		int creditGender = 0;
		// Customer MonthlyIncome
		double crmIncome = customer.getMonthlyIncome();
		if (crmIncome >= 200000 && crmIncome < 500000) {
			creditIncome = 20;
		} else if (crmIncome >= 500000 && crmIncome < 1000000) {
			creditIncome = 25;
		} else if (crmIncome >= 1000000 && crmIncome < 1500000) {
			creditIncome = 30;
		} else {
			creditIncome = 35;
		}
		// Customer Age
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date crmDob = customer.getDob();
		String crmAge = dateFormat.format(crmDob);
		LocalDate now = LocalDate.now();
		String currentDate = now.toString();
		int crmYear = Integer.parseInt(crmAge.substring(0, 4));
		int currentYear = Integer.parseInt(currentDate.substring(0, 4));
		int cumRealAge = currentYear - crmYear;
		// Customer Gender
		String crmGender = customer.getGender();
		if (cumRealAge >= 18 && cumRealAge < 25) {
			creditAge = 20;
		} else if (cumRealAge >= 25 && cumRealAge < 35) {
			creditAge = 35;
		} else if (cumRealAge >= 35 && cumRealAge < 45) {
			creditAge = 30;
		} else {
			creditAge = 25;
		}
		if (crmGender == "Male") {
			creditGender = 10;
		} else {
			creditGender = 15;
		}
		creditTotal = creditIncome + creditAge + creditGender;
		LoanInfoForm loan = new LoanInfoForm();
		loan.setCreditScore(creditTotal);
		loan.setCustomerCode(customer.getCustomerCode());
		model.addAttribute("loanInfoForm", loan);
		return "mfi/loan/MFI_LON_01_01";

	}

	@PostMapping("/addLoanInfo")
	public String addLoanInfo(@Valid @ModelAttribute("loanInfoForm") LoanInfoForm loanInfoForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("loanInfoForm", loanInfoForm);
			return "mfi/loan/MFI_LON_01_01";
		}
		String customercode = loanInfoForm.getCustomerCode();
		
		List<LoanInfo> loaninfo = loanservice.getLoanInfobyCrmCode(customercode);
		System.out.println("loanINfo is " + loaninfo);
		if (loaninfo.size() == 0) {
			/*
			 * int createdUser = 1; int updatedUser = 0;
			 */
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
			int userId = currentPrincipalName.getUserId();
			LocalDate now = LocalDate.now();
			
			LocalDate createdDate = LocalDate.now();
			LocalDate updatedDate = null;
			Date Rdate = Date.valueOf(loanInfoForm.getRegisterDate());
			double interstRate = loanInfoForm.getInterestRate() / 100;
			Customer customer = loanservice.searchCrmByCode(loanInfoForm.getCustomerCode());
			LoanInfo loanInfo = loanservice.addLoanInfo(customer, loanInfoForm.getLoanAmount(), Rdate,
					loanInfoForm.getLoanTermYear(), interstRate, loanInfoForm.getDescription(),
					loanInfoForm.getCreditScore(), "Pending", userId, createdDate, 0, updatedDate);
			Integer loanId = loanInfo.getLoanInfoId();
			LoanInfo loan = loanservice.getLoanInfobyId(loanId);
			int months = loan.getLoanTermYear() * 12;
			double monthlyIR = loan.getInterestRate() / 12;
			Double amount = loan.getLoanAmount();
			LocalDate futureRegisterDate;
			LocalDate registerDate = loan.getRegisterDate().toLocalDate().plusDays(5).plusMonths(1);
			Double balance = amount;
			double monthlyPayment = Double
					.parseDouble(df2.format(loanScheduleservice.calMonthlyPayment(amount, monthlyIR, months)));
			double irPaid;
			double amountPaid;
			double newBalance;

			for (int payment_tern_no = 1; payment_tern_no <= months; payment_tern_no++) {
				futureRegisterDate = registerDate;
				irPaid = Double.parseDouble(df2.format(balance * monthlyIR));
				amountPaid = Double.parseDouble(df2.format(monthlyPayment - irPaid));
				newBalance = Double.parseDouble(df2.format(balance - amountPaid));

				loanScheduleservice.addLoanScheduleForm(payment_tern_no, futureRegisterDate, balance, null,
						monthlyPayment, irPaid, null, newBalance, "Pending", userId, createdDate, 0,
						updatedDate, loan, amountPaid);
				balance = newBalance;
				registerDate = futureRegisterDate.plusMonths(1);
			}

			redirectAttrs.addFlashAttribute("reg", true);
			return "redirect:/searchloanInfo";
		} else {

			for (LoanInfo loanInfos : loaninfo) {

				if (loanInfos.getStatus().matches("Progress") || loanInfos.getStatus().matches("Pending")
						|| loanInfos.getStatus().matches("Approve")) {
					model.addAttribute("progress", true);
					return "mfi/loan/MFI_LON_01_01";
				}
			}

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
			int userId = currentPrincipalName.getUserId();
			LocalDate now = LocalDate.now();
			
			LocalDate createdDate = LocalDate.now();
			LocalDate updatedDate = null;
			Date Rdate = Date.valueOf(loanInfoForm.getRegisterDate());
			double interstRate = loanInfoForm.getInterestRate() / 100;
			Customer customer = loanservice.searchCrmByCode(loanInfoForm.getCustomerCode());
			LoanInfo loanInfo = loanservice.addLoanInfo(customer, loanInfoForm.getLoanAmount(), Rdate,
					loanInfoForm.getLoanTermYear(), interstRate, loanInfoForm.getDescription(),
					loanInfoForm.getCreditScore(), "Pending", userId, createdDate, 0, updatedDate);
			Integer loanId = loanInfo.getLoanInfoId();
			LoanInfo loan = loanservice.getLoanInfobyId(loanId);
			int months = loan.getLoanTermYear() * 12;
			double monthlyIR = loan.getInterestRate() / 12;
			Double amount = loan.getLoanAmount();
			LocalDate futureRegisterDate;
			LocalDate registerDate = loan.getRegisterDate().toLocalDate().plusDays(5).plusMonths(1);
			Double balance = amount;
			double monthlyPayment = Double
					.parseDouble(df2.format(loanScheduleservice.calMonthlyPayment(amount, monthlyIR, months)));
			double irPaid;
			double amountPaid;
			double newBalance;

			for (int payment_tern_no = 1; payment_tern_no <= months; payment_tern_no++) {
				futureRegisterDate = registerDate;
				irPaid = Double.parseDouble(df2.format(balance * monthlyIR));
				amountPaid = Double.parseDouble(df2.format(monthlyPayment - irPaid));
				newBalance = Double.parseDouble(df2.format(balance - amountPaid));

				loanScheduleservice.addLoanScheduleForm(payment_tern_no, futureRegisterDate, balance, null,
						monthlyPayment, irPaid, null, newBalance, "Pending", userId, createdDate, 0,
						updatedDate, loan, amountPaid);
				balance = newBalance;
				registerDate = futureRegisterDate.plusMonths(1);
			}
			redirectAttrs.addFlashAttribute("reg", true);
			return "redirect:/searchloanInfo";
		}

	}

	@GetMapping("/loanViewDetail/{id}")
	public String loanViewDetail(@PathVariable("id") Integer loaninfo_id, Model model) {
		LoanInfo loaninfo = loanservice.getLoanInfobyId(loaninfo_id);
//	    	   System.out.println("loaninfo"+loaninfo);
		model.addAttribute("loaninfo", loaninfo);
		List<LoanSchedule> loanschedule = loanScheduleservice.getLoanSchByLoanInfoId(loaninfo);
		model.addAttribute("loanschedules", loanschedule);
		return "mfi/loan/MFI_LON_04";
	}

	@GetMapping("/loanEditView/{id}")
	public String loanEditView(@PathVariable("id") Integer loaninfo_id, Model model) {
		LoanInfo loaninfo = loanservice.getLoanInfobyId(loaninfo_id);
		LoanInfoForm loaninfoForm = new LoanInfoForm();
		loaninfoForm.setLoanInfoId(loaninfo.getLoanInfoId());
		loaninfoForm.setCustomerCode(loaninfo.getCustomer().getCustomerCode());
		loaninfoForm.setLoanAmount(loaninfo.getLoanAmount());
		loaninfoForm.setRegisterDate(loaninfo.getRegisterDate().toString());
		loaninfoForm.setLoanTermYear(loaninfo.getLoanTermYear());
		loaninfoForm.setInterestRate(loaninfo.getInterestRate() * 100);
		loaninfoForm.setDescription(loaninfo.getDescription());
		loaninfoForm.setCreditScore(loaninfo.getCreditScore());
		loaninfoForm.setStatus(loaninfo.getStatus());
		loaninfoForm.setCreatedUser(loaninfo.getCreatedUser());
//		 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//		 String createdDate = dateFormat.format(loaninfo.getCreatedDate());
//		 Date date = java.sql.Date.valueOf(createdDate);
		loaninfoForm.setCreatedDate(loaninfo.getCreatedDate());
		loaninfoForm.setUpdateUser(loaninfo.getUpdateUser());
		loaninfoForm.setUpdateDate(loaninfo.getUpdateDate());
		model.addAttribute("loaninfo", loaninfoForm);
		return "mfi/loan/MFI_LON_03";
	}

	@PostMapping("/editLoanInfo/{id}")
	public String editLoanInfoe(@Valid @ModelAttribute("loaninfo") LoanInfoForm loanInfoForm,
			BindingResult bindingResult, @PathVariable("id") Integer loaninfo_id, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("loanInfoForm", loanInfoForm);
			return "mfi/loan/MFI_LON_03";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		LocalDate now = LocalDate.now();
		
		double interstRate = loanInfoForm.getInterestRate() / 100;
		LoanInfo updateLoanInfo = loanservice.getLoanInfobyId(loaninfo_id);
		Customer customer = loanservice.searchCrmByCode(loanInfoForm.getCustomerCode());
		updateLoanInfo.setCustomer(customer);
		updateLoanInfo.setLoanAmount(loanInfoForm.getLoanAmount());
		updateLoanInfo.setRegisterDate(Date.valueOf(loanInfoForm.getRegisterDate()));
		updateLoanInfo.setLoanTermYear(loanInfoForm.getLoanTermYear());
		updateLoanInfo.setInterestRate(interstRate);
		updateLoanInfo.setDescription(loanInfoForm.getDescription());
		updateLoanInfo.setCreditScore(loanInfoForm.getCreditScore());
		updateLoanInfo.setStatus(loanInfoForm.getStatus());
		
		
		/*
		 * updateLoanInfo.setCreatedUser(loanInfoForm.getCreatedUser());
		 * updateLoanInfo.setCreatedDate(loanInfoForm.getCreatedDate());
		 */
		 
		updateLoanInfo.setUpdateUser(userId);
		updateLoanInfo.setUpdateDate(now);
		LoanInfo loanInfo = loanservice.updateLoanInfo(updateLoanInfo);
		Integer loanId = loanInfo.getLoanInfoId();
		LoanInfo loan = loanservice.getLoanInfobyId(loanId);
		List<LoanSchedule> loanSdule=loanScheduleservice.findbyLoanId(loanId);
		LocalDate date =loanSdule.get(0).getCreatedDate();
		int id =loanSdule.get(0).getCreatedUser();
		loanScheduleservice.deleteLoanschByLoanInfoId(loanId);
		int months = loan.getLoanTermYear() * 12;
		double monthlyIR = loan.getInterestRate() / 12;
		Double amount = loan.getLoanAmount();
		LocalDate futureRegisterDate;
		LocalDate registerDate = loan.getRegisterDate().toLocalDate().plusDays(5);
		Double balance = amount;
		double monthlyPayment = Double
				.parseDouble(df2.format(loanScheduleservice.calMonthlyPayment(amount, monthlyIR, months)));
		double irPaid;
		double amountPaid;
		double newBalance;

		for (int payment_tern_no = 1; payment_tern_no <= months; payment_tern_no++) {
			futureRegisterDate = registerDate;
			irPaid = Double.parseDouble(df2.format(balance * monthlyIR));
			amountPaid = Double.parseDouble(df2.format(monthlyPayment - irPaid));
			newBalance = Double.parseDouble(df2.format(balance - amountPaid));

			loanScheduleservice.addLoanScheduleForm(payment_tern_no, futureRegisterDate, balance, null, monthlyPayment,
					irPaid, null, newBalance, "Pending", id, date, userId,
					now, loan, amountPaid);
			balance = newBalance;
			registerDate = futureRegisterDate.plusMonths(1);
		}

		redirectAttributes.addFlashAttribute("edit", true);
		return "redirect:/searchloanInfo";
	}

	@GetMapping("/checkerLoanList")
	public String checkerLoanList(Model model) {
		String status = "Pending";
		model.addAttribute("loanList", loanservice.selectSatus(status));
		return "mfi/loan/MFI_LON_03_01";
	}

	@GetMapping("/checkerLoanDetail/{id}")
	public String checkerLoanDetail(@PathVariable("id") Integer loaninfo_id, Model model) {
		LoanInfo loaninfo = loanservice.getLoanInfobyId(loaninfo_id);
//	    	   System.out.println("loaninfo"+loaninfo);
		model.addAttribute("loaninfo", loaninfo);
		List<LoanSchedule> loanschedule = loanScheduleservice.getLoanSchByLoanInfoId(loaninfo);
		model.addAttribute("loanschedules", loanschedule);
		return "mfi/loan/MFI_LON_04_01";
	}

	@RequestMapping("approveLoan/{id}")
	public String approveLoan(@PathVariable("id") int id, Model model) {
		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);
		LoanInfo loanList = loanservice.selectOne(id);

		loanList.setStatus("Approve");
		loanList.setUpdateDate(now);
		loanservice.save(loanList);
		
		List<LoanSchedule> schedule = loanScheduleservice.findbyLoanId(id);
		for(int i = 0; i < schedule.size(); i++ ) {
			LoanSchedule loanSchdule = schedule.get(i);
			loanSchdule.setStatus("Approve");
			loanScheduleservice.save(loanSchdule);
		}
		return "redirect:/checkerLoanList";
	}

	@RequestMapping("rejectLoan/{id}")
	public String rejectLoan(@PathVariable("id") int id, Model model) {
		LocalDate now = LocalDate.now();
		Date date = Date.valueOf(now);
		LoanInfo loanList = loanservice.selectOne(id);
		loanList.setStatus("Reject");
		loanList.setUpdateDate(now);
		loanservice.save(loanList);
		return "redirect:/checkerLoanList";
	}

}
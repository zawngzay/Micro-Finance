package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.model.COA;
import com.mfi.model.Role;
import com.mfi.service.CoaAccountService;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.SavingAccountService;



@Controller
public class CoaAccountController {
	
	@Autowired
	private CurrentAccountService cuService;
	
	@Autowired
	private SavingAccountService saService;
	
	@Autowired
	private LoanAccountService laService;
	
	@Autowired
	private CoaAccountService coaService;
	
	@RequestMapping("/coa")
	public String coa(Model model) {
		
//		double saving =saService.selectTotalSaving();
//		coaService.updateSave(saving);
//		
//		double current=cuService.selectTotalCurrent();
//		coaService.updateCurrent(current);
//		
//		double loan=laService.selectTotalLoan();
//		coaService.updateLoan(loan);
		
		model.addAttribute("coa",coaService.selectAll());
		
		return "/mfi/coa/MFI_COA_02";
	}
	
	@RequestMapping("/addCoa")
	public String addCao(Model model) {
		model.addAttribute("coaBean", new COA());
		return "mfi/coa/MFI_COA_01";
	}
	
	@RequestMapping("/coaRegister")
	public String addCrm(@ModelAttribute("coaBean") @Valid COA coa, BindingResult result,
			 Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roleBean", coa);
			model.addAttribute("Register Faield", "mesg");
			return "mfi/user/MFI_ROL_01";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		LocalDate now = LocalDate.now();
		Date createdDate = Date.valueOf(now);
		COA addCoa = new COA();
		addCoa.setCreatedUser(userId);
		addCoa.setCreatedDate(createdDate);
		addCoa.setAmount(coa.getAmount());
		addCoa.setBankId(coa.getBankId());
		addCoa.setAccountNumber(coa.getAccountNumber());
		addCoa.setGlType(coa.getGlType());
		coaService.save(coa);
		return "redirect:/coa";
	}

}
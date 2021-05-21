package com.mfi.controller;

import java.sql.Date;
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

import com.mfi.model.BlackList;
import com.mfi.model.Customer;
import com.mfi.service.BlackListService;
import com.mfi.service.CustomerService;
import com.mfi.service.MyUserDetails;

@Controller
public class BlackListController {

	@Autowired
	private BlackListService bkService;
	
	@Autowired
	private CustomerService crmService;

	@RequestMapping("/addBlackList")
	public String setupBlacklist(@Valid @ModelAttribute("blackList") BlackList blacklist, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("blackList", blacklist);
			return "mfi/blacklist/MFI_BLT_01";
		}
		String nrc = blacklist.getNrc();
		List<BlackList> blList = bkService.selectAllBk();
		for (BlackList bl : blList) {
			if (bl.getNrc().equals(nrc)) {
				model.addAttribute("mesg", "BlackList already exist !!!");
				model.addAttribute("blacklist", blacklist);
				return "mfi/blacklist/MFI_BLT_01";
			}
		}
		Customer customer=crmService.findByNrc(blacklist.getNrc());
		if(customer != null) {
			customer.setDelStatus(true);
			crmService.update(customer);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		 LocalDate now = LocalDate.now();
			Date createDate = Date.valueOf(now);
		bkService.saveBk(blacklist.getName(), blacklist.getNrc(), blacklist.getAddress(), blacklist.getFatherName(),
				userId, createDate, 0, null, blacklist.getRemark());
		model.addAttribute("reg", true);
		return "mfi/blacklist/MFI_BLT_02";

	}

	@RequestMapping("/searchingBlackList")
	public ModelAndView searchBlacklist(Model model,
			@RequestParam(name = "bkNameorNRC", required = false) String bkNameorNRC) {
		List<BlackList> blacklists = new ArrayList<>();

//		if(bkNameorNRC !=null) {
//		if(bkNameorNRC.equals("")) {
//			List<BlackList> blacklists1 = bkService.selectAllBk();
//			model.addAttribute("blacklists", blacklists1);
//			//model.addAttribute("mesg", message);
//			return new ModelAndView("mfi/blacklist/MFI_BLT_02");
//		}else {
			blacklists = bkService.getbkName(bkNameorNRC);
			if (blacklists.size() == 0) {
				model.addAttribute("notfound", true);
				model.addAttribute("bkNameorNRC", bkNameorNRC);
				return new ModelAndView("mfi/blacklist/MFI_BLT_02");
			} else {
				model.addAttribute("bkNameorNRC", bkNameorNRC);
				model.addAttribute("blacklists", blacklists);
				return new ModelAndView("mfi/blacklist/MFI_BLT_02");
			}
		}
		

		// }
//		}else {
//			model.addAttribute("error","Data not found");
//			return new ModelAndView("blacklist/MFI_BLT_02");
//		}

	

	@GetMapping("/updateBlacklist/{id}")
	public String setupUpdateBlacklist(@PathVariable("id") int id, Model model) {

		BlackList blacklist = bkService.selectOneBk(id);
		model.addAttribute("blackLists", blacklist);
		return "mfi/blacklist/MFI_BLT_03";
	}

	@GetMapping("/deleteBlackList/{id}")
	public String deleteBlackList(@PathVariable("id") int id, Model model) {
		BlackList blacklist=bkService.selectOneBk(id);
		Customer customer=crmService.findByNrc(blacklist.getNrc());
		if(customer != null) {
			customer.setDelStatus(false);
			crmService.update(customer);
		}
		bkService.deleteBk(id);
		model.addAttribute("delete", true);
		return "mfi/blacklist/MFI_BLT_02";
	}

	@PostMapping("/updateBlacklist/{id}")
	public String updateBlacklist(@ModelAttribute("blackLists") @Valid BlackList bl, BindingResult bindingResult,
			@PathVariable("id") int id, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("blackList", bl);
			return "mfi/blacklist/MFI_BLT_01";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		 LocalDate now = LocalDate.now();
			Date updateDate = Date.valueOf(now);
		BlackList blOne=bkService.selectOneBk(id);
		blOne.setName(bl.getName());
		blOne.setNrc(bl.getNrc());
		blOne.setAddress(bl.getAddress());
		blOne.setFatherName(bl.getFatherName());
		blOne.setCreatedDate(bl.getCreatedDate());
		blOne.setCreatedDate(bl.getCreatedDate());
		blOne.setUpdateUser(userId);
		blOne.setUpdateDate(updateDate);
		bkService.updateBk(blOne);
		/*
		 * bkService.saveBk(bl.getName(), bl.getNrc(), bl.getAddress(),
		 * bl.getFatherName(), createdUser, createdDate, updatedUser, updatedDate,
		 * bl.getRemark());
		 */
		model.addAttribute("edit", true);
		return "mfi/blacklist/MFI_BLT_02";

	}

	@GetMapping("/detailBlacklist/{id}")
	public String viewDetail(@PathVariable("id") int id, Model model) {
		
		model.addAttribute("viewDetail", bkService.selectOneBk(id));
		
		return "mfi/blacklist/MFI_BLT_04";

	}
	/*
	 * @GetMapping("/detailBlacklist2/{id}") public String
	 * viewDetail2(@PathVariable("id") int id, Model model) {
	 * 
	 * model.addAttribute("viewDetail", bkService.selectOneBk(id));
	 * model.addAttribute("mesg", "Delete Successfully!!!"); return
	 * "blacklist/MFI_BLT_04";
	 * 
	 * }
	 */

}

package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfi.model.Customer;
import com.mfi.model.Role;
import com.mfi.model.User;
import com.mfi.service.MyUserDetails;
import com.mfi.service.RoleService;

@Controller
public class RoleController {
	@Autowired
	RoleService roleService;

	@GetMapping("/roleAdd")
	public String addRole(Model model) {
		model.addAttribute("roleBean", new Role());
		return "mfi/user/MFI_ROL_01";
	}

	@PostMapping("/roleregister")
	public String addCrm(@ModelAttribute("roleBean") @Valid Role role, BindingResult result,
			 RedirectAttributes model) {
		if (result.hasErrors()) {
			model.addFlashAttribute("roleBean", role);
			model.addFlashAttribute("Register Faield", "mesg");
			return "mfi/user/MFI_ROL_01";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		 LocalDate now = LocalDate.now();
			Date createdDate = Date.valueOf(now);
		
        	Role roleRegister = new Role();
        	roleRegister.setRoleName(role.getRoleName());
        	roleRegister.setRolePosition(role.getRolePosition());
        	roleRegister.setCreatedUser(userId);
        	roleRegister.setCreatedDate(createdDate);
			roleService.save(roleRegister);
			//boolean mesg = true;
			model.addFlashAttribute("reg", true);
			
			return "redirect:/role";
		}

	@GetMapping("/roleEdit/{id}")
	public String roleEdit(@PathVariable("id") int id, Model model) {
		model.addAttribute("roleEdit", roleService.selectId(id));
		return "mfi/user/MFI_ROL_03";
	}

	@PostMapping("/roleEdit/{id}")
	public String customerUpdate(@ModelAttribute("roleEdit") @Valid Role role, BindingResult result,
			@PathVariable("id") int id, RedirectAttributes  model) {
		if (result.hasErrors()) {
			model.addFlashAttribute("roleEdit", role);
			return "mfi/user/MFI_ROL_03";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		 LocalDate now = LocalDate.now();
			Date updatedDate = Date.valueOf(now);
			Role roleRegister = new Role();
			roleRegister.setRoleId(id);
        	roleRegister.setRoleName(role.getRoleName());
        	roleRegister.setRolePosition(role.getRolePosition());
        	roleRegister.setCreatedUser(role.getCreatedUser());
        	roleRegister.setCreatedDate(role.getCreatedDate());
        	roleRegister.setUpdatedUser(userId);
        	roleRegister.setUpdatedDate(updatedDate);
        	
		model.addFlashAttribute("edit",true );
		roleService.update(roleRegister);
		return "redirect:/role";
	}
}

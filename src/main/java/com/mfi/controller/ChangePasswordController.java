package com.mfi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfi.formmodel.UserChangePassword;
import com.mfi.model.User;
import com.mfi.service.MyUserDetails;
import com.mfi.service.UserService;

@Controller
public class ChangePasswordController {

	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/setup")
	public String setup(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		String username = currentPrincipalName.getUsername();
		System.out.println("name" + username);
		// User user = userService.selectOne(userId);
		model.addAttribute("changePassuname", username);
		model.addAttribute("changePasswordBean", new UserChangePassword());
		return "mfi/user/MFI_USR_03";
	}

	@RequestMapping("/userChangePassword")
	public String changePass(@ModelAttribute("changePasswordBean") @Valid UserChangePassword userChnagePass,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
			
			String username = currentPrincipalName.getUsername();
			redirectAttributes.addFlashAttribute("changePasswordBean", userChnagePass);

			model.addAttribute("changePassuname", username);
			return "mfi/user/MFI_USR_03";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		User user = userService.selectOne(userId);

		if (userChnagePass.getNewPassword().equals(userChnagePass.getConfirmPassword())) {
			user.setPassword(passwordEncoder.encode(userChnagePass.getNewPassword()));
			userService.update(user);
			boolean mesg = true;
			redirectAttributes.addFlashAttribute("mesg", mesg);
			return "redirect:/setup";

		} else {
			boolean err = true;
			redirectAttributes.addFlashAttribute("err", err);
			return "redirect:/setup";

		}

	}
}

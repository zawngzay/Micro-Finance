package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfi.formmodel.CustomerForm;
import com.mfi.formmodel.Userform;
import com.mfi.model.Permission;
import com.mfi.model.Role;
import com.mfi.model.User;
import com.mfi.service.MyUserDetails;
import com.mfi.service.PermissionService;
import com.mfi.service.RoleService;
import com.mfi.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/setupuserRegister")
	public String setupuserRegister(Model model,HttpSession session) {
		
		
		model.addAttribute("userBean", new Userform());
		session.setAttribute("role", roleService.selectAll());
		model.addAttribute("permission", permissionService.selectAll());
		
		return "mfi/user/MFI_CUR_01";
	}

	@PostMapping("/userRegister")
	public String userRegister(@ModelAttribute("userBean") @Valid Userform user,BindingResult bindingResult, Model model,RedirectAttributes redirect) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userBean", user);
			
			return "mfi/user/MFI_CUR_01";
		}
		User checkUser = userService.findByEmail(user.getEmail());
		User nrcUser=userService.findByNRC(user.getNrc());
		if (!user.getConPass().equals(user.getPassword())) {
			model.addAttribute("user", user);
			model.addAttribute("role", roleService.selectAll());
			model.addAttribute("mesg", "Password and Confirm Password has to be the same");
			return "mfi/user/MFI_CUR_01";
		}else if(checkUser != null) {
			model.addAttribute("user", user);
			model.addAttribute("role", roleService.selectAll());
			model.addAttribute("email", true);
			return "mfi/user/MFI_CUR_01";
		}else if(nrcUser != null) {
			model.addAttribute("user", user);
			model.addAttribute("role", roleService.selectAll());
			model.addAttribute("nrc", true);
			return "mfi/user/MFI_CUR_01";
		}
		

//		if (!user.getConPass().equals(user.getPassword())) {
//			model.addAttribute("user", user);
//			model.addAttribute("error", "error");
//			model.addAttribute("conpass", true);
//			return "mfi/user/MFI_CUR_03";
//		}
		Role role = roleService.selectId(user.getRole());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		LocalDate now = LocalDate.now();
		Date createdDate = Date.valueOf(now);

		User userBean = new User();
		userBean.setName(user.getName());
		userBean.setEmail(user.getEmail());
		userBean.setNrc(user.getNrc());
		userBean.setPassword(passwordEncoder.encode(user.getPassword()));
		userBean.setPhone(user.getPhone());
		userBean.setRole(role);
		userBean.setPosition(user.getPosition());
		userBean.setCreatedUser(userId);
		userBean.setCreatedDate(createdDate);
		String permission = "";

		if (role.getRolePosition().equals("Maker")) {
			permission = "MAKER";
			user.getPermission().add(user.getPermission().size() - 1, permission);
			;
		} else {
			permission = "CHECKER";
			user.getPermission().add(user.getPermission().size() - 1, permission);
		}

		for (int i = 0; i < user.getPermission().size(); i++) {

			Permission p = permissionService.findByName(user.getPermission().get(i));
			userBean.getPermission().add(p);
			userService.save(userBean);
		}

		System.out.println(user.getRole());
//		

		System.out.println("perName" + user.getPermission());
		redirect.addFlashAttribute("reg", true);
		
		return "redirect:/user";
	}

	@RequestMapping("/userSearch")
	public String searchAll(@ModelAttribute("userSearch") User user, Model model) {
		if(user.getName()!=null) {
			List<User> userList = userService.findbyName(user.getName());
			if(userList.size()==0) {
				model.addAttribute("search",true);
			}
			model.addAttribute("userList", userList);
			return "mfi/user/MFI_CUR_02";
		}else {
			return "mfi/user/MFI_CUR_02";
		}
		
		

	}

	@GetMapping("/userEdit/{id}")
	public String userEdit(@PathVariable("id") int id, Model model,HttpSession session) {
		List<Role> role = roleService.selectAll();
		User user = userService.selectOne(id);
		
		Userform userForm = new Userform();
		userForm.setUser_id(id);
		userForm.setName(user.getName());
		userForm.setEmail(user.getEmail());
		userForm.setPosition(user.getPosition());
		userForm.setNrc(user.getNrc());
		userForm.setPhone(user.getPhone());
		userForm.setPassword(user.getPassword());
		userForm.setConPass(user.getPassword());
		userForm.setRole((user.getRole().getRoleId()));
		List<String> perList = new ArrayList<String>();
		for(int i = 0;i<user.getPermission().size();i++) {
			perList.add(user.getPermission().get(i).getPerName());
		}
		userForm.setPermission(perList);
		
		model.addAttribute("perList", user.getPermission());
		model.addAttribute("userEdit", userForm);
		session.setAttribute("role", roleService.selectAll());
		return "mfi/user/MFI_CUR_03";
	}

	@PostMapping("/userEdit/{id}")
	public String usreUpdate(@ModelAttribute("userEdit") @Valid Userform user, BindingResult result,
			@PathVariable("id") int id, RedirectAttributes redirect,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "mfi/user/MFI_CUR_03";
		}
		if (!user.getConPass().equals(user.getPassword())) {
			model.addAttribute("user", user);
//			model.addAttribute("error", "error");
			model.addAttribute("conpass", true);
			return "mfi/user/MFI_CUR_03";
		}

		Role role = roleService.selectId(user.getRole());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int userId = currentPrincipalName.getUserId();
		LocalDate now = LocalDate.now();
		Date updateDate = Date.valueOf(now);
		
		User userBean = userService.selectOne(id);
		userBean.setName(user.getName());
		userBean.setEmail(user.getEmail());
		userBean.setNrc(user.getNrc());
		userBean.setPassword(passwordEncoder.encode(user.getPassword()));
		userBean.setPhone(user.getPhone());
		userBean.setRole(role);
		userBean.setPosition(user.getPosition());
		userBean.setCreatedUser(user.getCreatedUser());
		userBean.setCreatedDate(user.getCreatedDate());
		userBean.setUpdateUser(userId);
		userBean.setUpdateDate(updateDate);
		String permission = "";
		List<Permission> per = new ArrayList<>();
		if (role.getRolePosition().equals("Maker")) {
			per.add(permissionService.findByName("MAKER"));
		
//			userBean.getPermission().add(user.getPermission().size() - 1, perName);
			
		} else {
			per.add(permissionService.findByName("CHECKER"));
//			permission = "CHECKER";
//			user.getPermission().add(user.getPermission().size() - 1, permission);
		}
		
		

		for (int i = 0; i < user.getPermission().size(); i++) {
			Permission p = permissionService.findByName(user.getPermission().get(i));
			per.add(p);
			userBean.setPermission(per);
			
			
		}

		userService.update(userBean);

	redirect.addFlashAttribute("edit", true);
		
		return "redirect:/userSearch";
	}

}
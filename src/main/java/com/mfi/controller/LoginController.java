package com.mfi.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.model.COA;
import com.mfi.model.Permission;
import com.mfi.model.Role;
import com.mfi.model.User;
import com.mfi.service.CoaAccountService;
import com.mfi.service.MyUserDetails;
import com.mfi.service.PermissionService;
import com.mfi.service.RoleService;
import com.mfi.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private PermissionService perService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CoaAccountService coaService;

	@RequestMapping("/")
	public String home(HttpSession session) {
		//session.setAttribute("role", roleService.selectAll());
		Permission p = perService.findByName("MASTER");
		if (p == null) {
			createPermission();
		}
		return "redirect:/login";
	}

	@RequestMapping("/login")
	public String login() {

		User user = userService.findByEmail("master@gmail.com");

		System.out.println("perService" + perService.findByName("MASTER"));
		if (user == null) {

			List<Permission> permission = new ArrayList<Permission>();
			permission.add(perService.findByName("MASTER"));

			User master = new User();
			master.setEmail("master@gmail.com");
			master.setName("Master");
			master.setPassword(passwordEncoder.encode("master"));
			
			Role role = new Role();
			role.setRoleName("Master");
			role.setRolePosition("Master");
			roleService.save(role);	
			master.setRole(role);
			master.setPermission(permission);
			userService.save(master);
			
			
			/*
			 * Authentication authentication =
			 * SecurityContextHolder.getContext().getAuthentication(); MyUserDetails
			 * currentPrincipalName = (MyUserDetails) authentication.getPrincipal(); int
			 * userId = currentPrincipalName.getUserId();
			 */	
			

			
			LocalDate now = LocalDate.now();
			Date date = Date.valueOf(now); // Magic happens here!
			COA capital = new COA();
			capital.setCreatedUser(master.getUser_id());
			capital.setBankId(1);
			capital.setGlType("Capital");
			capital.setAccountNumber("1111000011110001");
			capital.setAmount(10000000.0);
			capital.setCreatedDate(date);
			coaService.save(capital);
			

			COA cash = new COA();
			cash.setCreatedUser(master.getUser_id());
			cash.setBankId(1);
			cash.setGlType("Cash");
			cash.setAccountNumber("1111000011110002");
			cash.setAmount(10000000.0);
			cash.setCreatedDate(date);
			coaService.save(cash);
			

			COA income = new COA();
			income.setCreatedUser(master.getUser_id());
			income.setBankId(1);
			income.setGlType("Income");
			income.setAccountNumber("1111000011110003");
			income.setAmount(0.0);
			income.setCreatedDate(date);
			coaService.save(income);
			
			COA current = new COA();
			current.setCreatedUser(master.getUser_id());
			current.setBankId(1);
			current.setGlType("Current Account");
			current.setAmount(0.0);
			current.setCreatedDate(date);
			coaService.save(current);
			

			COA saving = new COA();
			saving.setCreatedUser(master.getUser_id());
			saving.setBankId(1);
			saving.setGlType("Saving Account");
			saving.setAmount(0.0);
			saving.setCreatedDate(date);
			coaService.save(saving);
			
			COA loan = new COA();
			loan.setCreatedUser(master.getUser_id());
			loan.setBankId(1);
			loan.setGlType("Loan Account");
			loan.setAmount(0.0);
			loan.setCreatedDate(date);
			coaService.save(loan);

		}
		return "MFI_LGN_01";
	}

	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute("err", "Error");
		return "MFI_LGN_01";
	}

	@RequestMapping("/index")
	public String index() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails currentPrincipalName = (MyUserDetails) authentication.getPrincipal();
		int id = currentPrincipalName.getUserId();
		System.out.println("id:" + currentPrincipalName.getUserId());

		User user = userService.selectOne(id);
		for (Permission p : user.getPermission()) {
			System.out.println("p" + p.getPerName());
		}

		if (user.getRole().getRolePosition().matches("Checker")) {

			return "redirect:/checker";
			// return "mfi/dashboard/checker_dashboard" ;
		} else if (user.getRole().getRolePosition().matches("Maker")) {

			return "redirect:/maker";

		} else{
			return "redirect:/checker";
		}

	}

	public void createPermission() {

		Permission permission11 = new Permission();
		permission11.setPerName("MAKER");

		Permission permission10 = new Permission();
		permission10.setPerName("CHECKER");

		Permission permission8 = new Permission();
		permission8.setPerName("MASTER");

		Permission permission1 = new Permission();
		permission1.setPerName("CRM");
		Permission permission2 = new Permission();
		permission2.setPerName("Account");
		Permission permission3 = new Permission();
		permission3.setPerName("Reports");
		Permission permission4 = new Permission();
		permission4.setPerName("Loan");
		Permission permission5 = new Permission();
		permission5.setPerName("Transaction");
		Permission permission6 = new Permission();
		permission6.setPerName("COA");
		Permission permission7 = new Permission();
		permission7.setPerName("Blacklist");
		Permission permission12 = new Permission();
		permission12.setPerName("Admin");

		perService.createPermission(permission8);
		perService.createPermission(permission11);
		perService.createPermission(permission10);
		perService.createPermission(permission12);

		perService.createPermission(permission1);

		perService.createPermission(permission2);
		perService.createPermission(permission3);
		perService.createPermission(permission4);
		perService.createPermission(permission5);
		perService.createPermission(permission6);
		perService.createPermission(permission7);

	}

}

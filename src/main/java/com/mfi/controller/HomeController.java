package com.mfi.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {

	@RequestMapping("/index")
	public String home() {
		return "checker_dashboard.html";
	}
}

package com.example.springboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class CustomErrorController implements ErrorController {
	@RequestMapping("/error")
	public String handelError(Authentication authentication) {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (roles.contains("ROLE_ADMIN")) {
			return "redirect:/admin";
		}
		return "redirect:/user";

	}
}

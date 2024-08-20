package com.anhtu.ex1.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.anhtu.ex1.model.Customer;
import com.anhtu.ex1.repository.CustomerRepository;
import com.anhtu.ex1.service.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("customer")
public class LoginController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		if (customerService.authenticate(username, password)) {
			model.addAttribute("customer", customerService.findByUsername(username));
			return "welcome";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}
	}

	@GetMapping("/change-password")
	public String showChangePasswordPage() {
		return "change-password";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String username, @RequestParam String oldPassword,
			@RequestParam String newPassword, Model model) {
		if (customerService.validatePassword(newPassword)) {
			model.addAttribute("error", "Invalid new password. New password need >=8 symbols, at least one numeric (0,1,2…9) "
					+ "one capital (A, B, C,…) and one like #, @, $, &, *");
			return "change-password";
		}
		if (customerService.changePassword(username, oldPassword, newPassword)) {
			model.addAttribute("customer", customerService.findByUsername(username));
			return "welcome";
		} else {
			model.addAttribute("error", "Invalid username or old password");
			return "change-password";
		}
	}

	@GetMapping("/forgot-password")
	public String showForgotPasswordPage() {
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String emailOrPhone, @RequestParam boolean isEmail, Model model) {
		if (customerService.forgotPassword(emailOrPhone, isEmail)) {
			model.addAttribute("message", "Password reset successfully. Check your email or SMS.");
		} else {
			model.addAttribute("error", "Email or phone number not found");
		}
		return "forgot-password";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}
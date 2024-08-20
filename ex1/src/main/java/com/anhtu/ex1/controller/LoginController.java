package com.anhtu.ex1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anhtu.ex1.service.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (customerService.authenticate(username, password)) {
            return "home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    
    @GetMapping("/change-password")
    public String changePassword() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String username, 
                                 @RequestParam String oldPassword, 
                                 @RequestParam String newPassword, 
                                 Model model) {
        if (customerService.changePassword(username, oldPassword, newPassword)) {
            model.addAttribute("message", "Password changed successfully");
            return "home";
        } else {
            model.addAttribute("error", "Invalid username or old password");
            return "change-password";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String resetPassword(@RequestParam String username, 
                                @RequestParam String newPassword, 
                                Model model) {
        if (customerService.resetPassword(username, newPassword)) {
            model.addAttribute("message", "Password reset successfully");
            return "login";
        } else {
            model.addAttribute("error", "Invalid username");
            return "forgot-password";
        }
    }
    
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}

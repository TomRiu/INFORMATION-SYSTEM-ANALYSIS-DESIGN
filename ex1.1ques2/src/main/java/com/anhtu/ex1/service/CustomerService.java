package com.anhtu.ex1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.anhtu.ex1.model.Customer;
import com.anhtu.ex1.repository.CustomerRepository;

import java.util.regex.Pattern;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$&*]).{8,}$"
    );

    public boolean validatePassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    
    public Customer findByUsername(String username) {
    	return customerRepository.findByUsername(username);
    }
    
    public Customer findByEmail(String email) {
    	return customerRepository.findByEmail(email);
    }
    
    public Customer findByPhone(String phone) {
    	return customerRepository.findByPhone(phone);
    }

	public boolean authenticate(String username, String password) {
		Customer customer = findByUsername(username);
		System.out.println(customer);
		return customer != null && customer.getPassword().equals(password);
	}

	public boolean changePassword(String username, String oldPassword, String newPassword) {
		Customer customer = findByUsername(username);
		if (customer != null && customer.getPassword().equals(oldPassword)) {
			customer.setPassword(newPassword);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

    public boolean forgotPassword(String emailOrPhone, boolean isEmail) {
    	
    	Customer customer = isEmail ? findByEmail(emailOrPhone) : findByPhone(emailOrPhone);
    	if (customer != null) {
    		String newPassword = generateRandomPassword();
    		customer.setPassword(newPassword);
    		customerRepository.save(customer);
    		if (isEmail) {
    			sendPasswordResetEmail(emailOrPhone, newPassword);
    		} else {
    			sendPasswordResetSms(emailOrPhone, newPassword);
    		}		
    		return true;
    	} 
    	
		return false;
	}
    
    public String generateRandomPassword() {
    	return "NewPassword123#";
    }
    
    public void sendPasswordResetEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Your new password is: " + newPassword);
        mailSender.send(message);
    }

    public void sendPasswordResetSms(String phoneNumber, String newPassword) {
        // Implement SMS sending logic here
    }
	
}
package com.anhtu.ex1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhtu.ex1.model.Customer;
import com.anhtu.ex1.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public boolean authenticate(String username, String password) {
		Customer customer = customerRepository.findByUsername(username);
		return customer != null && customer.getPassword().equals(password);
	}

	public boolean changePassword(String username, String oldPassword, String newPassword) {
		Customer customer = customerRepository.findByUsername(username);
		if (customer != null && customer.getPassword().equals(oldPassword)) {
			customer.setPassword(newPassword);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	public boolean resetPassword(String username, String newPassword) {
		Customer customer = customerRepository.findByUsername(username);
		if (customer != null) {
			customer.setPassword(newPassword);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}
}

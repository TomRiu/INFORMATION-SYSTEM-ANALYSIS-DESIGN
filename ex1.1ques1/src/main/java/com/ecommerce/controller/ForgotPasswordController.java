package com.ecommerce.controller;

import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Random;

@WebServlet("/forgotPassword")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailOrPhone = request.getParameter("emailOrPhone");
        Customer customer = customerDAO.findCustomerByEmailOrPhone(emailOrPhone, emailOrPhone);

        if (customer == null) {
            request.setAttribute("errorMessage", "No account found with that email or phone number.");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }

        String tempPassword = "NewPassword123#";
        customerDAO.updatePassword(customer.getId(), tempPassword);

        // Here, you'd implement sending the temp password via email or SMS. 
        // For simplicity, we'll just display it on the screen.

        request.setAttribute("successMessage", "A temporary password has been sent to your email/phone.");
        request.setAttribute("tempPassword", tempPassword);
        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private String generateTempPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&*";
        StringBuilder tempPassword = new StringBuilder(10);
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            tempPassword.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }
}

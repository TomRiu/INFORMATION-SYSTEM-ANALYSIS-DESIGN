package com.ecommerce.controller;

import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String newPassword = request.getParameter("newPassword");

        if (!isValidPassword(newPassword)) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters long, contain at least one digit, one uppercase letter, and one special character.");
            request.getRequestDispatcher("change_password.jsp").forward(request, response);
            return;
        }

        boolean isUpdated = customerDAO.updatePassword(customer.getId(), newPassword);
        if (isUpdated) {
            request.setAttribute("successMessage", "Password changed successfully.");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to change password.");
            request.getRequestDispatcher("change_password.jsp").forward(request, response);
        }
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$&*]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
}

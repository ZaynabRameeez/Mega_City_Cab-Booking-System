/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Service.UserService;
import Utils.PasswordUtils;

public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public ResetPasswordServlet() {
        super();
        this.userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate passwords
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match!");
            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
            return;
        }

        // Hash new password
        String hashedPassword = PasswordUtils.hashPassword(newPassword);

        // Reset password in DB
        boolean resetSuccess = userService.resetPassword(email, hashedPassword);

        if (resetSuccess) {
            response.sendRedirect("Login.jsp?message=Password reset successful. Please login.");
        } else {
            request.setAttribute("message", "Invalid email or error updating password.");
            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
        }
    }
}

//package Controller;
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import Service.UserService;
//import Utils.PasswordUtils;
//
//public class ResetPasswordServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private UserService userService;
//
//    public ResetPasswordServlet() {
//        super();
//        this.userService = new UserService();
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String email = request.getParameter("email");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        // Validate passwords
//        if (!newPassword.equals(confirmPassword)) {
//            request.setAttribute("message", "Passwords do not match!");
//            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
//            return;
//        }
//
//        // Hash new password
//        String hashedPassword = PasswordUtils.hashPassword(newPassword);
//
//        // Reset password in DB
//        boolean resetSuccess = userService.resetPassword(email, hashedPassword);
//
//        if (resetSuccess) {
//            response.sendRedirect("Login.jsp?message=Password reset successful. Please login.");
//        } else {
//            request.setAttribute("message", "Invalid email or error updating password.");
//            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
//        }
//    }
//}

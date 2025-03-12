/////*
//// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template


package Controller;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.User;
import Service.UserService;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

      public void init() {
       userService = new UserService();
    }
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//    response.sendRedirect("Registration.jsp"); // Redirect to the registration page
//}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();
        String birthday = request.getParameter("birthday").trim();
        String email = request.getParameter("email").trim();
        String mobile = request.getParameter("mobile").trim();
        String password = request.getParameter("password").trim();
        String address = request.getParameter("address");

   
      
        boolean success = userService.registerUser(firstName, lastName, birthday, email, mobile, password,address);
        
       
     
        if (success) {
            response.sendRedirect("Login.jsp?success=Registration Successful");
        } else {
            response.sendRedirect("Registration.jsp?error=Registration Failed or User Already Exists");
        }
    }
}


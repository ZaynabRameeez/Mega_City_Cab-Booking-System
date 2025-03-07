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

    public RegisterServlet() {
        super();
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.sendRedirect("Registration.jsp"); // Redirect to the registration page
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String nic = request.getParameter("nic");
        String role = request.getParameter("role"); // admin, user, or driver
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

   
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Password and Confirm Password must match.");
            request.getRequestDispatcher("Registration.jsp").forward(request, response);
            return;
        }

        
        // Create user object
  
        User newUser = new User(username, email, phone, password, nic, gender, address);
        
        // Register user
        String resultMessage = userService.registerUser(newUser);
        request.setAttribute("message", resultMessage);
        
        if ("Registration successful!".equals(resultMessage)) { 
   
    response.sendRedirect("Login.jsp");
} 
        else {
    
    request.getRequestDispatcher("Registration.jsp").forward(request, response);
        }
    }
}





//import Model.User;
//import DAO.UserDAO;
//import Service.UserService;
//import Utils.DBConfig;
//import java.sql.Connection;
//import Utils.PasswordUtils;
//import java.io.IOException;
//import java.sql.SQLException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.catalina.Lifecycle;
//
//
//   public class RegisterServlet extends HttpServlet {
//        private UserService userService;
//        
//
//     @Override
//public void init() throws ServletException {
//    try {
//        userService = new UserService(); // Uses default constructor
//    } catch (Exception e) {
//        throw new ServletException("Error initializing RegisterServlet", e);
//    }
//}
//
//   
//
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//       
//
//        // Retrieve form data
//        String username = request.getParameter("username");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String nic = request.getParameter("NIC");
//        String password = request.getParameter("password");
//        String confirmPassword = request.getParameter("confirmPassword");
//        String role = request.getParameter("role");
//        String gender = request.getParameter("gender");
//        String address = request.getParameter("address");
//
//        // Retain form data if validation fails
//        request.setAttribute("username", username);
//        request.setAttribute("email", email);
//        request.setAttribute("phone", phone);
//        request.setAttribute("nic", nic);
//        request.setAttribute("role", role);
//        request.setAttribute("gender", gender);
//        request.setAttribute("address", address);
//
//        // Perform validation checks
//        if (!validateInputs(username, email, phone, nic, password, confirmPassword, role, request, response)) {
//            return; // Stop execution if validation fails
//        }
//
//        // Hash password before storing
//        String hashedPassword = PasswordUtils.hashPassword(password);
//
//        // Restrict Drivers Until Admin Approval
//        boolean isActive = !"Driver".equalsIgnoreCase(role); // Drivers are inactive until approved by Admin
//      
//
//        // Create User Object
//        User user = new User(username, email, phone, nic, hashedPassword, role, gender, address);
//        user.setActive(isActive);
//
//        // Attempt registration
//        if (userService.registerUser(user)) {
//            if (role.equalsIgnoreCase("Driver")) {
//                response.sendRedirect("Registration.jsp?success=pendingapproval");
//            } else {
//                response.sendRedirect("Login.jsp?success=registered");
//            }
//        } else {
//            request.setAttribute("error", "Registration failed. Please try again.");
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//        }
//    }
//
//    /**
//     * Validates input fields and handles error messages.
//     */
//    private boolean validateInputs(String username, String email, String phone, String nic, 
//                                   String password, String confirmPassword, String role, 
//                                   HttpServletRequest request, HttpServletResponse response) 
//                                   throws ServletException, IOException {
//        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
//            setErrorAndForward("Missing required fields.", request, response);
//            return false;
//        }
//
//        if (!PasswordUtils.isValidPassword(password)) {
//            setErrorAndForward("Password must be at least 8 characters long, include 1 letter, 1 number, and 1 special character.", request, response);
//            return false;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            setErrorAndForward("Passwords do not match.", request, response);
//            return false;
//        }
//
//        if (!phone.matches("\\d{10}")) {
//            setErrorAndForward("Phone number must be exactly 10 digits.", request, response);
//            return false;
//        }
//
//        if (!nic.matches("\\d{9}[Vv]") && !nic.matches("\\d{12}")) {
//            setErrorAndForward("NIC must be in the format 123456789V or 123456789012.", request, response);
//            return false;
//        }
//
//        if ("Admin".equalsIgnoreCase(role) && !email.endsWith("@megacitycab.com")) {
//            setErrorAndForward("Only company administrators can register as Admin.", request, response);
//            return false;
//        }
//
//        if (userService.isEmailTaken(email)) {
//            setErrorAndForward("Email already exists. Please use a different one.", request, response);
//            return false;
//        }
//
//        if (userService.isUsernameTaken(username)) {
//            setErrorAndForward("Username already exists.", request, response);
//            return false;
//        }
//
//        return true; // Validation passed
//    }
//
//    /**
//     * Sets an error message and forwards the request.
//     */
//    private void setErrorAndForward(String errorMessage, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.setAttribute("error", errorMessage);
//        request.getRequestDispatcher("Registration.jsp").forward(request, response);
//    }
//}
//



















////package Controller;
////
////
////import Service.UserService;
////import Utils.DBConfig;
////import Model.User;
////import java.sql.Connection;
////import Utils.PasswordUtils;
////import java.io.IOException;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServlet;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////
////public class RegisterServlet extends HttpServlet {
////    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        String username = request.getParameter("username");
////        String email = request.getParameter("email");
////        String phone = request.getParameter("phone");
////        String nic = request.getParameter("nic");
////        String password = request.getParameter("password");
////        String confirmPassword = request.getParameter("confirmPassword");
////        String role = request.getParameter("role");
////        String gender = request.getParameter("gender");
////        String address = request.getParameter("address");
////
////        // ✅ Retain form data for validation failure
////        request.setAttribute("username", username);
////        request.setAttribute("email", email);
////        request.setAttribute("phone", phone);
////        request.setAttribute("nic", nic);
////        request.setAttribute("role", role);
////        request.setAttribute("gender", gender);
////        request.setAttribute("address", address);
////        request.removeAttribute("error"); // Clear previous errors
////
////        // ✅ Validate Required Fields
////        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
////            request.setAttribute("error", "Missing required fields");
////            request.getRequestDispatcher("Registration.jsp").forward(request, response);
////            return;
////        }
////
////        // ✅ Password Strength Validation
////        if (password.length() < 6 || !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*")) {
////            request.setAttribute("error", "Password must be at least 6 characters long, contain an uppercase letter and a number");
////            request.getRequestDispatcher("Registration.jsp").forward(request, response);
////            return;
////        }
////
////        // ✅ Confirm Password Validation
////        if (!password.equals(confirmPassword)) {
////            request.setAttribute("error", "Passwords do not match.");
////            request.getRequestDispatcher("Registration.jsp").forward(request, response);
////            return;
////        }
////          // ✅ Hash Password Before Creating User Object
////           String hashedPassword = PasswordUtils.hashPassword(password);
////           
////        // ✅ Phone Number Validation (10-digit, numeric)
////        if (!phone.matches("\\d{10}")) {
////            request.setAttribute("error", "Phone number must be exactly 10 digits");
////            request.getRequestDispatcher("Registration.jsp").forward(request, response);
////            return;
////        }
////
////        // ✅ Role-Based Validations
////        if ("driver".equalsIgnoreCase(role)) {
////            if (nic.isEmpty() || (!nic.matches("\\d{9}[Vv]") && !nic.matches("\\d{12}"))) {
////                request.setAttribute("error", "Drivers must provide a valid NIC (e.g., 123456789V or 123456789012)");
////                request.getRequestDispatcher("Registration.jsp").forward(request, response);
////                return;
////            }
////        }
////
////        if ("admin".equalsIgnoreCase(role) && !email.endsWith("@megacitycab.com")) {
////            request.setAttribute("error", "Only company administrators can register as Admin");
////            request.getRequestDispatcher("Registration.jsp").forward(request, response);
////            return;
////        }
////
////        // ✅ Database Connection
////        try (Connection connection = DBConfig.getConnection()) {
////            if (connection == null) {
////                request.setAttribute("error", "Database connection failed. Please try again later.");
////                request.getRequestDispatcher("Registration.jsp").forward(request, response);
////                return;
////            }
////
////            // ✅ Initialize userservice
////            UserService userService = new UserService(connection);
////
////          
////            // ✅ Check for Existing Username
////            if (userService.isUsernameTaken(username)) {
////              request.setAttribute("error", "Username already exists");
////              request.getRequestDispatcher("Registration.jsp").forward(request, response);
////                  return;
////                 }
////
////
////
////            // ✅ Create User Object & Register
////            User user = new User(username, email, phone, nic, hashedPassword, role, gender, address);
////
////             if (userService.registerUser(user)) {
////                response.sendRedirect("Login.jsp?success=registered");
////            } else {
////                request.setAttribute("error", "Registration failed. Please try again.");
////                request.getRequestDispatcher("Registration.jsp").forward(request, response);
////            }
////        } catch (Exception e) {
////            request.setAttribute("error", "Internal error: " + e.getMessage());
////            request.getRequestDispatcher("Registration.jsp").forward(request, response);
////        }
////    }
////}
//
//
//
//
//package Controller;
//
//import Model.User;
//import Service.UserService;
//import Utils.DBConfig;
//import Utils.PasswordUtils;
//import java.io.IOException;
//import java.sql.Connection;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
//public class RegisterServlet extends HttpServlet {
//    private UserService userService;
//
//   @Override
//      public void init() throws ServletException {
//    try {
//        Connection connection = DBConfig.getConnection(); // ✅ Keep the connection open
//        userService = new UserService(connection);
//    } catch (Exception e) {
//        throw new ServletException("Database connection failed", e);
//    }
//}
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Retrieve form parameters
//        String username = request.getParameter("username");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String nic = request.getParameter("NIC");
//        String password = request.getParameter("password");
//        String confirmPassword = request.getParameter("confirmPassword");
//        String role = request.getParameter("role");
//        String gender = request.getParameter("gender");
//        String address = request.getParameter("address");
//
//        // ✅ Retain form data for validation failure
//        request.setAttribute("username", username);
//        request.setAttribute("email", email);
//        request.setAttribute("phone", phone);
//        request.setAttribute("nic", nic);
//        request.setAttribute("role", role);
//        request.setAttribute("gender", gender);
//        request.setAttribute("address", address);
//        request.removeAttribute("error"); // Clear previous errors
//
//        // ✅ Validate Required Fields
//        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
//            request.setAttribute("error", "Missing required fields");
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            return;
//        }
//
//        // ✅ Password Strength Validation
//        if (!PasswordUtils.isValidPassword(password)) {
//            request.setAttribute("error", "Password must be at least 6 characters long, contain an uppercase letter and a number");
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            return;
//        }
//
//        // ✅ Confirm Password Validation
//        if (!password.equals(confirmPassword)) {
//            request.setAttribute("error", "Passwords do not match.");
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            return;
//        }
//
//        //  Hash Password Before Storing
//        String hashedPassword = PasswordUtils.hashPassword(password);
//
//         // Phone Number Validation (10-digit, numeric)
//        if (!phone.matches("\\d{10}")) {
//            request.setAttribute("error", "Phone number must be exactly 10 digits");
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            return;
//        }
//
//      // ✅ General NIC Validation for ALL Users
//if (nic.isEmpty() || (!nic.matches("\\d{9}[Vv]") && !nic.matches("\\d{12}"))) {
//    request.setAttribute("error", "NIC must be in the format 123456789V or 123456789012.");
//    request.getRequestDispatcher("Registration.jsp").forward(request, response);
//    return;
//}
//
//
//        if ("Admin".equalsIgnoreCase(role) && !email.endsWith("@megacitycab.com")) {
//            request.setAttribute("error", "Only company administrators can register as Admin");
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            // Check for existing email
//            if (userService.isEmailTaken(email)) {
//                request.setAttribute("error", "Email already exists. Please use a different one.");
//                request.getRequestDispatcher("Registration.jsp").forward(request, response);
//                return;
//            }
//            
//            // Check for existing username
//            if (userService.isUsernameTaken(username)) {
//                request.setAttribute("error", "Username already exists");
//                request.getRequestDispatcher("Registration.jsp").forward(request, response);
//                return;
//            }
//            
//            // Restrict Drivers Until Admin Approval
//            boolean isActive = !role.equalsIgnoreCase("Driver");
//            
//            // Create User Object
//            User user = new User(username, email, phone, nic, hashedPassword, role, gender, address);
//            user.setActive(isActive);
//            
//            // Register User
//            if (userService.registerUser(user)) {
//                if (role.equalsIgnoreCase("Driver")) {
//                    response.sendRedirect("Registration.jsp?success=pendingapproval");
//                } else {
//                    response.sendRedirect("Login.jsp?success=registered");
//                }
//            } else {
//                request.setAttribute("error", "Registration failed. Please try again.");
//                request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            }
//        } catch (Exception e) {
//            request.setAttribute("error", "Internal error: " + e.getMessage());
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//      }
//    }
//}
//     
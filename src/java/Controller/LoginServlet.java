///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */



package Controller;



import Model.User;
import DAO.UserDAO;
import Utils.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email and password are required.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user != null && PasswordUtils.verifyPassword(password, user.getPassword())) {
            if (!user.isIs_active()&& user.getRole().equalsIgnoreCase("driver")) {
                request.setAttribute("errorMessage", "Your account is not yet approved by the admin.");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }

            // Store user session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.getRole().equalsIgnoreCase("admin")) {
                response.sendRedirect("AdminDashboard.jsp");
            } else if (user.getRole().equalsIgnoreCase("driver")) {
                response.sendRedirect("DriverDashboard.jsp");
            } else {
                response.sendRedirect("UsersDashboard.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}
















////package Controller;
////
////import Service.UserService;
////import Model.User;
////import Utils.DBConfig;
////import java.io.IOException;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServlet;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import jakarta.servlet.http.HttpSession;
////
////
////public class LoginServlet extends HttpServlet {
////    private UserService userService;
////
////    @Override
////    public void init() throws ServletException {
////        this.userService = new UserService(DBConfig.getConnection());  // Initialize UserService
////    }
////
////    @Override
////    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
////            throws ServletException, IOException {
////        String username = request.getParameter("username");
////        String password = request.getParameter("password");
////
////  
////
////        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
////            request.setAttribute("error", "Username and password are required.");
////            request.getRequestDispatcher("Login.jsp").forward(request, response);
////            return;
////        }
////
////         try {
////            User user = userService.authenticateUser(username, password);
////
////            if (user != null) {
////                HttpSession session = request.getSession();
////                session.setAttribute("loggedUser", user);
////                session.setAttribute("loggedUserId", user.getId()); // Add this line to be accessible to other pages
////                session.setAttribute("role", user.getRole());
////                
////                System.out.println("User role: " + user.getRole());
////                
////                // Redirect based on role
////                if("admin".equals(user.getRole().trim())) {
////                    response.sendRedirect("AdminDashboard.jsp");
////                } else if ("driver".equals(user.getRole())) {
////         
////                    response.sendRedirect("DriverDashboard.jsp");
////                } else {
////                    response.sendRedirect("UsersDashboard.jsp");
////                }
////            } else {
////                request.setAttribute("error", "Invalid username or password.");
////                request.getRequestDispatcher("Login.jsp").forward(request, response);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            request.setAttribute("error", "Internal error occurred.");
////            request.getRequestDispatcher("Login.jsp").forward(request, response);
////        }
////    }
////}
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
//import jakarta.servlet.http.HttpSession;
//
//public class LoginServlet extends HttpServlet {
//    private UserService userService;
//    
//    @Override
//    public void init() throws ServletException {
//        try {
//            Connection connection = DBConfig.getConnection();
//            userService = new UserService(connection);
//        } catch (Exception e) {
//            throw new ServletException("Database connection failed", e);
//        }
//    }
//
//    
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Retrieve form parameters
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        // ✅ Retain form data for validation failure
//        request.setAttribute("email", email);
//        request.removeAttribute("error"); // Clear previous errors
//
//        if (email.isEmpty() || password.isEmpty()) {
//            request.setAttribute("error", "Email and password are required.");
//            request.getRequestDispatcher("Login.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            // ✅ Fetch user by email
//            User user = userService.getUserByEmail(email);
//           if (user != null) {
//               
//               
//          if (user == null) {
//               request.setAttribute("error", "Invalid email or password.");
//              request.getRequestDispatcher("Login.jsp").forward(request, response);
//                  return;
//            }
//                // ✅ Validate hashed password
//                if (!PasswordUtils.verifyPassword(password, user.getPassword())) {
//                    request.setAttribute("error", "Invalid email or password.");
//                    request.getRequestDispatcher("Login.jsp").forward(request, response);
//                    return;
//                }
//
//                // 🚨 Prevent inactive drivers from logging in
//                if ("Driver".equalsIgnoreCase(user.getRole()) && !user.isActive()) {
//                    request.setAttribute("error", "Your account is pending approval. Please wait for admin approval.");
//                    request.getRequestDispatcher("Login.jsp").forward(request, response);
//                    return;
//                }
//
//                // ✅ Start session and store user info
//                HttpSession session = request.getSession();
//                session.setAttribute("user", user);
//
//                // ✅ Redirect based on role
//                switch (user.getRole().toLowerCase()) {
//                    case "admin":
//                        response.sendRedirect("AdminDashboard.jsp");
//                        break;
//                    case "driver":
//                        response.sendRedirect("driver_dashboard.jsp");
//                        break;
//                    default:
//                        response.sendRedirect("UsersDashboard.jsp");
//                        break;
//                }
//            } else {
//                request.setAttribute("error", "Invalid email or password.");
//                request.getRequestDispatcher("Login.jsp").forward(request, response);
//            }
//        } catch (Exception e) {
//            request.setAttribute("error", "Internal error: " + e.getMessage());
//            request.getRequestDispatcher("Login.jsp").forward(request, response);
//        }
//    }
//}
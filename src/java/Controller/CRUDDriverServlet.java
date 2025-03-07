/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DAO.AdminDAO;

/**
 *
 * @author zainr
 */


public class CRUDDriverServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        int driverId = Integer.parseInt(request.getParameter("driverId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String action = request.getParameter("action");

        try {
            boolean success = false;

            if ("approve".equals(action)) {
                success = AdminDAO.approveDriver(driverId, userId);
                if (success) {
                    session.setAttribute("message", "Driver approved successfully!");
                } else {
                    session.setAttribute("error", "Failed to approve driver.");
                }
            } else if ("reject".equals(action)) {
                success = AdminDAO.rejectDriver(driverId, userId);
                if (success) {
                    session.setAttribute("message", "Driver rejected successfully!");
                } else {
                    session.setAttribute("error", "Failed to reject driver.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Database error occurred.");
        }

        response.sendRedirect("ManageDrivers.jsp"); // Redirect back to Manage Drivers page
    }
}














//public class CRUDDriverServlet extends HttpServlet {
//    private UserService userService;
//
//    public void init() {
//        userService = new UserService();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        int userId = Integer.parseInt(request.getParameter("id"));
//
//        if ("approve".equals(action)) {
//            if (userService.approveDriver(userId)) {
//                response.sendRedirect("ManageDrivers.jsp?msg=Driver approved successfully!");
//            }
//        } else if ("reject".equals(action)) {
//            if (userService.rejectDriver(userId)) {
//                response.sendRedirect("ManageDrivers.jsp?msg=Driver rejected successfully!");
//            }
//        }
//    }
//}
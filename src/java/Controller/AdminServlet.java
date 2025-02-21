/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles admin actions like approving drivers, updating roles, and deleting users.
 */

public class AdminServlet extends HttpServlet {
    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int adminId = Integer.parseInt(request.getParameter("adminId")); // Admin ID from session or request
        int userId = Integer.parseInt(request.getParameter("userId"));   // User ID for action

        boolean result = false;
        
        switch (action) {
            case "approveDriver":
                result = adminService.approveDriver(adminId, userId);
                break;
            case "updateUserRole":
                String newRole = request.getParameter("newRole");
                result = adminService.updateUserRole(adminId, userId, newRole);
                break;
            case "deleteUser":
                result = adminService.deleteUser(adminId, userId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
        }

        if (result) {
            response.getWriter().write("Success");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Operation failed");
        }
    }
}

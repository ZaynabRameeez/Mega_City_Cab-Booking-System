/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Utils.DBConfig;

/**
 *
 * @author zainr
 */




public class ApproveDriverRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int requestId = Integer.parseInt(request.getParameter("requestId"));

        try (Connection conn = DBConfig.getConnection()) {
            // Update request status to "Approved"
            String updateRequestQuery = "UPDATE driver_requests SET status = 'Approved' WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateRequestQuery);
            stmt.setInt(1, requestId);
            stmt.executeUpdate();

            // Update user role to "Driver"
            String updateUserRoleQuery = "UPDATE users SET role = 'Driver' WHERE id = (SELECT user_id FROM driver_requests WHERE id = ?)";
            PreparedStatement stmt2 = conn.prepareStatement(updateUserRoleQuery);
            stmt2.setInt(1, requestId);
            stmt2.executeUpdate();

            response.sendRedirect("manageDriverRequests.jsp?success=Request Approved");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageDriverRequests.jsp?error=Database Error");
        }
    }
}


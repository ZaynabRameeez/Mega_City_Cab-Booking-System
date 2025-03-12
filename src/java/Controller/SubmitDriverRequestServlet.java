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
import java.sql.SQLException;
import jakarta.servlet.http.HttpSession;
import Utils.DBConfig;

/**
 *
 * @author zainr
 */



public class SubmitDriverRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("Login.jsp?error=Session Expired");
            return;
        }

        String email = (String) session.getAttribute("email");
        String fullName = request.getParameter("full_name");
        String mobile = request.getParameter("mobile");
        String vehicleType = request.getParameter("vehicle_type");
        String vehicleModel = request.getParameter("vehicle_model");
        String vehiclePlate = request.getParameter("vehicle_plate");

        try (Connection conn = DBConfig.getConnection()) {
            String insertQuery = "INSERT INTO driver_requests (user_id, full_name, mobile, vehicle_type, vehicle_model, vehicle_plate) " +
                                 "VALUES ((SELECT id FROM users WHERE email = ?), ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.setString(3, mobile);
            stmt.setString(4, vehicleType);
            stmt.setString(5, vehicleModel);
            stmt.setString(6, vehiclePlate);

            stmt.executeUpdate();
            response.sendRedirect("requestDriver.jsp?success=Request Submitted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("requestDriver.jsp?error=Database Error");
        }
    }
}

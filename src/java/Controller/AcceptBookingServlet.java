package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Utils.DBConfig;

/**
 *
 * @author zainr
 */


public class AcceptBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null || !session.getAttribute("role").equals("Driver")) {
            response.sendRedirect("Login.jsp?error=Unauthorized Access");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String driverEmail = (String) session.getAttribute("email");

        try (Connection conn = DBConfig.getConnection()) {
            String updateQuery = "UPDATE bookings SET status = 'Confirmed', assigned_driver = (SELECT id FROM users WHERE email = ?) WHERE booking_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, driverEmail);
            stmt.setInt(2, bookingId);

            stmt.executeUpdate();
            response.sendRedirect("DriverDashboard.jsp?success=Ride Accepted");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("DriverDashboard.jsp?error=Database Error");
        }
    }
}

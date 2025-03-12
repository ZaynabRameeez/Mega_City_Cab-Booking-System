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
import Utils.DBConfig;

/**
 *
 * @author zainr
 */



public class UpdateBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String newStatus = request.getParameter("status");

        try (Connection conn = DBConfig.getConnection()) {
            String updateQuery = "UPDATE bookings SET status = ? WHERE booking_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, newStatus);
            stmt.setInt(2, bookingId);

            stmt.executeUpdate();
            response.sendRedirect("AdminDashboard.jsp?success=Booking Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("AdminDashboard.jsp?error=Database Error");
        }
    }
}

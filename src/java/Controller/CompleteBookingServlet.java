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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.http.HttpSession;
import Utils.DBConfig;

/**
 *
 * @author zainr
 */





public class CompleteBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null || !session.getAttribute("role").equals("Driver")) {
            response.sendRedirect("Login.jsp?error=Unauthorized Access");
            return;
        }

        int bookingId = -1;
        try {
            bookingId = Integer.parseInt(request.getParameter("bookingId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("DriverDashboard.jsp?error=Invalid Booking ID");
            return;
        }

        System.out.println("Received Booking ID: " + bookingId); // Debugging log

        try (Connection conn = DBConfig.getConnection()) {
            String updateQuery = "UPDATE bookings SET status = 'Completed', completed_at = NOW() WHERE booking_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setInt(1, bookingId);
            
            
            String notifyUserQuery = "INSERT INTO notifications (user_id, message) " +
                         "VALUES ((SELECT user_id FROM bookings WHERE booking_id = ?), 'Your ride has been successfully completed.')";
            PreparedStatement notifyStmt = conn.prepareStatement(notifyUserQuery);
            notifyStmt.setInt(1, bookingId);
            notifyStmt.executeUpdate();


            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Ride marked as completed for Booking ID: " + bookingId);
            } else {
                System.out.println("No booking found with ID: " + bookingId);
            }

            // delay to ensure database updates before redirecting
            Thread.sleep(1000);

            response.sendRedirect("DriverDashboard.jsp?success=Ride Completed Successfully");
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            response.sendRedirect("DriverDashboard.jsp?error=Database Error");
        }
    }
}

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
public class SubmitRatingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("Login.jsp?error=Session Expired");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String feedback = request.getParameter("feedback");

        try (Connection conn = DBConfig.getConnection()) {
            String insertQuery = "INSERT INTO ratings (booking_id, driver_id, user_id, rating, feedback) " +
                                 "VALUES (?, ?, (SELECT user_id FROM bookings WHERE booking_id = ?), ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setInt(1, bookingId);
            stmt.setInt(2, driverId);
            stmt.setInt(3, bookingId);
            stmt.setInt(4, rating);
            stmt.setString(5, feedback);

            stmt.executeUpdate();
            response.sendRedirect("myBookings.jsp?success=Rating Submitted");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("myBookings.jsp?error=Database Error");
        }
    }
}

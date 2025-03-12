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






public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("Login.jsp?error=Session Expired");
            return;
        }

        String email = (String) session.getAttribute("email");
        String pickup = request.getParameter("pickup");
        String dropoff = request.getParameter("dropoff");
        String vehicleType = request.getParameter("vehicleType");
        String rideDate = request.getParameter("rideDate");
        String rideTime = request.getParameter("rideTime");
        double calculatedPrice = Double.parseDouble(request.getParameter("calculatedPrice"));

        try (Connection conn = DBConfig.getConnection()) {
            String insertQuery = "INSERT INTO bookings (user_id, pickup_location, dropoff_location, vehicle_type, ride_date, ride_time, status, price) "
                               + "VALUES ((SELECT id FROM users WHERE email = ?), ?, ?, ?, ?, ?, 'Pending', ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, email);
            insertStmt.setString(2, pickup);
            insertStmt.setString(3, dropoff);
            insertStmt.setString(4, vehicleType);
            insertStmt.setString(5, rideDate);
            insertStmt.setString(6, rideTime);
            insertStmt.setDouble(7, calculatedPrice);

            insertStmt.executeUpdate();
            response.sendRedirect("myBookings.jsp?success=Ride Booked Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("BookRide.jsp?error=Database Error");
        }
    }
}

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




import com.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.http.HttpSession;

@WebServlet("/IgnoreBookingServlet")
public class IgnoreBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null || !session.getAttribute("role").equals("Driver")) {
            response.sendRedirect("login.jsp?error=Unauthorized Access");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            String updateQuery = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setInt(1, bookingId);

            stmt.executeUpdate();
            response.sendRedirect("driverDashboard.jsp?success=Ride Ignored");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("driverDashboard.jsp?error=Database Error");
        }
    }
}

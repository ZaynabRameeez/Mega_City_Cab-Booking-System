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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import Utils.DBConfig;

/**
 *
 * @author zainr
 */



public class NotificationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("Login.jsp?error=Session Expired");
            return;
        }

        String email = (String) session.getAttribute("email");
        List<String> notifications = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT message FROM notifications WHERE user_id = (SELECT id FROM users WHERE email = ?) AND is_read = FALSE ORDER BY created_at DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notifications.add(rs.getString("message"));
            }
            request.setAttribute("notifications", notifications);
            request.getRequestDispatcher("notifications.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String role = (String) session.getAttribute("role");
            if (role.equals("Admin")) {
                response.sendRedirect("AdminDashboard.jsp");
            } else if (role.equals("Driver")) {
                response.sendRedirect("DriverDashboard.jsp");
            } else {
                response.sendRedirect("UsersDashboard.jsp");
            }

        }
    }
}

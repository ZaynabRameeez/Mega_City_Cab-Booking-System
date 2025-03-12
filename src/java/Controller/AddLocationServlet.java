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


public class AddLocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fromCity = request.getParameter("from_city");
        String toCity = request.getParameter("to_city");
        double distanceKm = Double.parseDouble(request.getParameter("distance_km"));

        try (Connection conn = DBConfig.getConnection()) {
            String insertQuery = "INSERT INTO locations (from_city, to_city, distance_km) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, fromCity);
            stmt.setString(2, toCity);
            stmt.setDouble(3, distanceKm);

            stmt.executeUpdate();
            response.sendRedirect("manageLocations.jsp?success=Location Added");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageLocations.jsp?error=Database Error");
        }
    }
}

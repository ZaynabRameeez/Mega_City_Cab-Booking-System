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



public class AddCabPricingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vehicleType = request.getParameter("vehicle_type");
        double baseFare = Double.parseDouble(request.getParameter("base_fare"));
        double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
        double after2kmRate = Double.parseDouble(request.getParameter("after_2km_rate"));

        try (Connection conn = DBConfig.getConnection()) {
            String insertQuery = "INSERT INTO cab_pricing (vehicle_type, base_fare, per_km_rate, after_2km_rate) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, vehicleType);
            stmt.setDouble(2, baseFare);
            stmt.setDouble(3, perKmRate);
            stmt.setDouble(4, after2kmRate);

            stmt.executeUpdate();
            response.sendRedirect("manageCabPricing.jsp?success=Pricing Added");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageCabPricing.jsp?error=Database Error");
        }
    }
}

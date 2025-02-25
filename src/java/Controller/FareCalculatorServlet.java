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

import Utils.DistanceCalculator;
import Utils.FareCalculator;

public class FareCalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Parse and validate parameters
            Double pickupLat = parseDouble(request.getParameter("pickupLat"));
            Double pickupLon = parseDouble(request.getParameter("pickupLon"));
            Double dropLat = parseDouble(request.getParameter("dropLat"));
            Double dropLon = parseDouble(request.getParameter("dropLon"));
            Integer vehicleTypeId = parseInteger(request.getParameter("vehicleTypeId")); // Expecting an integer

            if (pickupLat == null || pickupLon == null || dropLat == null || dropLon == null || vehicleTypeId == null) {
                out.write("{\"error\": \"Invalid or missing parameters\"}");
                return;
            }

            // Calculate distance
            double distance = DistanceCalculator.calculateDistance(pickupLat, pickupLon, dropLat, dropLon);

            // Calculate fare using FareCalculator
            double totalFare = FareCalculator.calculateFare(vehicleTypeId, distance);

            // JSON response
            String jsonResponse = String.format("{\"distance\": %.2f, \"totalFare\": %.2f}", distance, totalFare);
            out.write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"An internal error occurred.\"}");
        }
    }

    // Utility method to safely parse double values
    private Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Utility method to safely parse integer values
    private Integer parseInteger(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}





















//package Controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import Utils.DBConfig;
//import Utils.DistanceCalculator;
//
//public class FareCalculatorServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    private void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        try (PrintWriter out = response.getWriter()) {
//            // Validate and parse parameters from both GET & POST requests
//            Double pickupLat = parseDouble(request.getParameter("pickupLat"));
//            Double pickupLon = parseDouble(request.getParameter("pickupLon"));
//            Double dropLat = parseDouble(request.getParameter("dropLat"));
//            Double dropLon = parseDouble(request.getParameter("dropLon"));
//            String vehicleType = request.getParameter("vehicleType");
//
//            if (pickupLat == null || pickupLon == null || dropLat == null || dropLon == null || vehicleType == null) {
//                out.write("{\"error\": \"Invalid or missing parameters\"}");
//                return;
//            }
//
//            // Calculate distance
//            double distance = DistanceCalculator.calculateDistance(pickupLat, pickupLon, dropLat, dropLon);
//
//            // Fetch base fare & per km rate from database
//            double baseFare = 0, perKmRate = 0;
//            try (Connection con = DBConfig.getConnection();
//                 PreparedStatement ps = con.prepareStatement("SELECT base_fare, per_km_rate FROM fares WHERE vehicle_type = ?")) {
//                ps.setString(1, vehicleType);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        baseFare = rs.getDouble("base_fare");
//                        perKmRate = rs.getDouble("per_km_rate");
//                    } else {
//                        out.write("{\"error\": \"Vehicle type not found\"}");
//                        return;
//                    }
//                }
//            }
//
//            // Calculate total fare
//            double totalFare = baseFare + (distance * perKmRate);
//
//            // JSON response as a string (without using a third-party library)
//            String jsonResponse = String.format("{\"distance\": %.2f, \"totalFare\": %.2f}", distance, totalFare);
//            out.write(jsonResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Utility method to safely parse double values
//    private Double parseDouble(String value) {
//        try {
//            return value != null ? Double.parseDouble(value) : null;
//        } catch (Exception e) {
//            return null; // Return null for invalid values
//        }
//    }
//}
//






//package Controller;
//
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import Utils.DBConfig;
//import Utils.DistanceCalculator;
//
//public class FareCalculatorServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        try (PrintWriter out = response.getWriter()) {
//            // Get parameters from request
//            double pickupLat = Double.parseDouble(request.getParameter("pickupLat"));
//            double pickupLon = Double.parseDouble(request.getParameter("pickupLon"));
//            double dropLat = Double.parseDouble(request.getParameter("dropLat"));
//            double dropLon = Double.parseDouble(request.getParameter("dropLon"));
//            String vehicleType = request.getParameter("vehicleType");
//
//            // Calculate distance
//            double distance = DistanceCalculator.calculateDistance(pickupLat, pickupLon, dropLat, dropLon);
//
//            // Fetch base fare & per km rate from database
//            double baseFare = 0, perKmRate = 0;
//            try (Connection con = DBConfig.getConnection();
//                 PreparedStatement ps = con.prepareStatement("SELECT base_fare, per_km_rate FROM fares WHERE vehicle_type = ?")) {
//                ps.setString(1, vehicleType);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        baseFare = rs.getDouble("base_fare");
//                        perKmRate = rs.getDouble("per_km_rate");
//                    }
//                }
//            }
//
//            // Calculate total fare
//            double totalFare = baseFare + (distance * perKmRate);
//
//            // Return JSON response
//            out.write("{\"distance\":" + distance + ", \"totalFare\":" + totalFare + "}");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

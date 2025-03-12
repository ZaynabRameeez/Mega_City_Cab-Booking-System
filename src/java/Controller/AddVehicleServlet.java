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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Utils.DBConfig;

/**
 *
 * @author zainr
 */







@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10, // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp?error=Session Expired");
            return;
        }

        String email = (String) session.getAttribute("email");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String acType = request.getParameter("ac_type");
        String plateNumber = request.getParameter("plate_number");
        String color = request.getParameter("color");

        Part filePart = request.getPart("vehicle_image");
        String fileName = new File(filePart.getSubmittedFileName()).getName();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        filePart.write(uploadPath + File.separator + fileName);

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "INSERT INTO vehicles (driver_id, brand, model, ac_type, plate_number, color, vehicle_image) " +
                         "VALUES ((SELECT id FROM users WHERE email = ?), ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setString(4, acType);
            stmt.setString(5, plateNumber);
            stmt.setString(6, color);
            stmt.setString(7, fileName);

            stmt.executeUpdate();
            response.sendRedirect("manageVehicle.jsp?success=Vehicle Added");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("manageVehicle.jsp?error=Database Error");
        }
    }
}

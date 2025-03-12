

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */




package Controller;


import Utils.DBConfig;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10, // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("Login.jsp?error=Session Expired");
            return;
        }

        String email = (String) session.getAttribute("email");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String acType = request.getParameter("ac_type");
        String plateNumber = request.getParameter("plate_number");
        String color = request.getParameter("color");

        Part filePart = request.getPart("vehicle_image");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        if (!new File(uploadPath).exists()) new File(uploadPath).mkdir();
        if (!fileName.isEmpty()) {
            filePart.write(uploadPath + File.separator + fileName);
        }

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "UPDATE vehicles SET brand = ?, model = ?, ac_type = ?, plate_number = ?, color = ?" +
                         (fileName.isEmpty() ? "" : ", vehicle_image = ?") +
                         " WHERE driver_id = (SELECT id FROM users WHERE email = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setString(3, acType);
            stmt.setString(4, plateNumber);
            stmt.setString(5, color);

            if (!fileName.isEmpty()) {
                stmt.setString(6, fileName);
                stmt.setString(7, email);
            } else {
                stmt.setString(6, email);
            }

            stmt.executeUpdate();
            response.sendRedirect("manageVehicle.jsp?success=Vehicle Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("manageVehicle.jsp?error=Database Error");
        }
    }
}

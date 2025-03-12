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
public class UpdateUserProfileServlet extends HttpServlet {
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
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");

        Part filePart = request.getPart("profile_image");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        if (!new File(uploadPath).exists()) new File(uploadPath).mkdir();
        if (!fileName.isEmpty()) {
            filePart.write(uploadPath + File.separator + fileName);
        }

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, mobile = ?, address = ?" +
                         (fileName.isEmpty() ? "" : ", profile_image = ?") + 
                         " WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, mobile);
            stmt.setString(4, address);
            if (!fileName.isEmpty()) {
                stmt.setString(5, fileName);
                stmt.setString(6, email);
            } else {
                stmt.setString(5, email);
            }

            stmt.executeUpdate();
            response.sendRedirect("UserProfile.jsp?success=Profile Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("UserProfile.jsp?error=Database Error");
        }
    }
}

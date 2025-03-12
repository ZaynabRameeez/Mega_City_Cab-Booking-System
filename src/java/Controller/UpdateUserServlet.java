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




public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String mobile = request.getParameter("mobile");
        String role = request.getParameter("role");

        try (Connection conn = DBConfig.getConnection()) {
            String updateQuery = "UPDATE users SET first_name = ?, last_name = ?, mobile = ?, role = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, mobile);
            stmt.setString(4, role);
            stmt.setInt(5, userId);

            stmt.executeUpdate();
            response.sendRedirect("ManageUsers.jsp?success=User Updated Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("ManageUsers.jsp?error=Database Error");
        }
    }
}

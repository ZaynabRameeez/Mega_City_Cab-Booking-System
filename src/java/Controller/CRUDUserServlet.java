/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;


import DAO.UserDAO;
import java.io.IOException;
import Service.UserService;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author zainr
 */
public class CRUDUserServlet extends HttpServlet {

  
    private static final long serialVersionUID = 1L;
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("id");

    if (userId != null) {
        try {
            int id = Integer.parseInt(userId);
            UserService userService = new UserService();
            
            boolean deleted = userService.deleteUser(id);

            if (deleted) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User deletion failed.");
            }

            response.sendRedirect("ManageUsers.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("User ID is null!");
    }
}
}



package Controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.User;
import DAO.UserDAO;
import DAO.AdminDAO;

public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    AdminDAO adminDAO = new AdminDAO();
    List<User> userList = userDAO.getAllUsers();
   request.setAttribute("userList", userList);


    int totalUsers = adminDAO.getTotalUsers();
    int totalDrivers = adminDAO.getTotalDrivers();
    int pendingApprovals = adminDAO.getPendingApprovals();

    System.out.println("Total Users: " + totalUsers);
    System.out.println("Total Drivers: " + totalDrivers);
    System.out.println("Pending Approvals: " + pendingApprovals);

    request.setAttribute("totalUsers", totalUsers);
    request.setAttribute("totalDrivers", totalDrivers);
    request.setAttribute("pendingApprovals", pendingApprovals);

    request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);
}
}

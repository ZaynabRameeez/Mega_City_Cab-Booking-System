/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Model.User;
import DAO.AdminDAO;
import java.util.List;
/**
 *
 * @author zainr
 */



public class AdminService {
    private AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAO();
    }



    // ✅ Approve Driver (Admin Only)
    public boolean approveDriver(int adminId, int driverId) {
        if (!adminDAO.isUserAdmin(adminId)) {
            System.out.println("Access Denied: Only admins can approve drivers!");
            return false;
        }
        return adminDAO.approveDriver(driverId);
    }


    // ✅ Delete User (Admin Only)
    public boolean deleteUser(int adminId, int userId) {
        if (!adminDAO.isUserAdmin(adminId)) {
            System.out.println("Access Denied: Only admins can delete users!");
            return false;
        }
        return adminDAO.deleteUser(userId);
    }

    // ✅ Get an Admin user
    public int getAdminUserId() {
        return adminDAO.getAdminUserId();
    }
}

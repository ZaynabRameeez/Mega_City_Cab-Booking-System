/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author zainr
 */



import Utils.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.User;
import java.util.ArrayList;
import java.util.List;


public class AdminDAO {
    private Connection connection;

    // ✅ Constructor to initialize connection
    public AdminDAO() {
        try {
            this.connection = DBConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize AdminDAO: " + e.getMessage(), e);
        }
    }

    // ✅ Check if a user is an Admin
    public boolean isUserAdmin(int userId) {
        String sql = "SELECT 1 FROM users WHERE id = ? AND role = 'Admin'";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // ✅ True if user is admin
            }
        } catch (SQLException e) {
            System.err.println("Error checking admin status: " + e.getMessage());
        }
        return false;
    }

  // Approve a driver (Only Admins can do this)
public boolean approveDriver(int driverId) {
    String sql = "UPDATE users SET is_active = TRUE WHERE id = ? AND LOWER(role) = 'driver'";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, driverId);
        
        return stmt.executeUpdate() > 0; // Returns true if at least one row is updated
        
    } catch (SQLException e) {
        System.err.println("Error approving driver: " + e.getMessage());
    }
    
    return false;
}

    // ✅ Fetch all users
    public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT id, username, email, role, is_active FROM users";

    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setActive(rs.getBoolean("is_active"));
            users.add(user);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching users: " + e.getMessage());
    }
    return users;
}





    // ✅ Update user role (Admins cannot be changed)
    public boolean updateUserRole(int userId, String newRole) {
        String sql = "UPDATE users SET role = ? WHERE id = ? AND role != 'Admin'";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, newRole);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user role: " + e.getMessage());
        }
        return false;
    }

    // ✅ Delete a user (Admins cannot be deleted)
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ? AND role != 'Admin'";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    
    // ✅ Get an Admin user
    public int getAdminUserId() {
        String sql = "SELECT id FROM users WHERE role = 'Admin' LIMIT 1";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin user: " + e.getMessage());
        }
        return -1; // No admin found
    }
    public int getTotalUsers() {
    int count = 0;
    try (Connection con = DBConfig.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE role = 'user'");
         ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log error
    }
    return count;
}
public int getTotalDrivers() {
    int count = 0;
    try (Connection con = DBConfig.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE role = 'driver' AND is_approved = 1");
         ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log error
    }
    return count;
}
public int getPendingApprovals() {
    int count = 0;
    try (Connection con = DBConfig.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE role = 'driver' AND is_approved = 0");
         ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log error
    }
    return count;
}

}







































//          try (Connection conn = DBConfig.getConnection(); 
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        stmt.setInt(1, userId);
//        try (ResultSet rs = stmt.executeQuery()) {
//            return rs.next(); // ✅ True if user is admin
//        }
//    } catch (SQLException e) {
//        System.err.println("Error checking admin status: " + e.getMessage());
//    }
//    return false;
//}
   
  
    

   



//    public boolean approveDriver(int adminId, int driverId) {
//        if (!isUserAdmin(adminId)) {
//            System.out.println("Only admins can approve drivers!");
//            return false;
//        }
//
//        String sql = "UPDATE users SET is_active = TRUE WHERE id = ? AND role = 'Driver'";
//        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
//            stmt.setInt(1, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error approving driver: " + e.getMessage());
//        }
//        return false;
//    }

    // ✅ Update user role (Admins cannot be changed)
//    public boolean updateUserRole(int adminId, int userId, String newRole) {
//        if (!isUserAdmin(adminId)) {
//            System.out.println("Only admins can update roles!");
//            return false;
//        }
//
//        String sql = "UPDATE users SET role = ? WHERE id = ? AND role != 'Admin'";
//        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
//            stmt.setString(1, newRole);
//            stmt.setInt(2, userId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error updating user role: " + e.getMessage());
//        }
//        return false;
//    }
//
//    // ✅ Delete a user (Admins cannot be deleted)
//    public boolean deleteUser(int adminId, int userId) {
//        if (!isUserAdmin(adminId)) {
//            System.out.println("Only admins can delete users!");
//            return false;
//        }
//
//        String sql = "DELETE FROM users WHERE id = ? AND role != 'Admin'";
//        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error deleting user: " + e.getMessage());
//        }
//        return false;
//    }
//
//    // ✅ Get an Admin user
//    public int getAdminUserId() {
//        String sql = "SELECT id FROM users WHERE role = 'Admin' LIMIT 1";
//
//        try (PreparedStatement stmt = this.connection.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt("id");
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching admin user: " + e.getMessage());
//        }
//        return -1; // No admin found
//    }


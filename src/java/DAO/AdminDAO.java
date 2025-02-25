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
import Model.Driver;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    
    // ✅ Check if a user is an Admin
    public boolean isUserAdmin(int userId) {
        String sql = "SELECT 1 FROM users WHERE id = ? AND LOWER(role) = 'admin'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking admin status: " + e.getMessage());
        }
        return false;
    }

    // ✅ Approve a driver using driverId
    public boolean approveDriver(int driverId) {
        String sql = "UPDATE users SET is_active = TRUE WHERE id = ? AND LOWER(role) = 'driver'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            return stmt.executeUpdate() > 0;
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
        String sql = "UPDATE users SET role = ? WHERE id = ? AND LOWER(role) != 'admin'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        String sql = "DELETE FROM users WHERE id = ? AND LOWER(role) != 'admin'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    // ✅ Get Admin User ID
    public int getAdminUserId() {
        String sql = "SELECT id FROM users WHERE LOWER(role) = 'admin' LIMIT 1";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin user: " + e.getMessage());
        }
        return -1;
    }

    // ✅ Get total users
    public int getTotalUsers() {
        return getCount("SELECT COUNT(*) FROM users WHERE LOWER(role) = 'user'");
    }

    // ✅ Get total active drivers
    public int getTotalDrivers() {
        return getCount("SELECT COUNT(*) FROM users WHERE LOWER(role) = 'driver' AND is_active = TRUE");
    }

    // ✅ Get pending approvals
    public int getPendingApprovals() {
        return getCount("SELECT COUNT(*) FROM users WHERE LOWER(role) = 'driver' AND is_approved = FALSE");
    }

    // ✅ Reusable count method
    private int getCount(String sql) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.err.println("Error fetching count: " + e.getMessage());
        }
        return 0;
    }

    // ✅ Get pending driver requests
    public List<User> getPendingDriverRequests() {
        List<User> pendingDrivers = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.email, d.license_number, d.vehicle_type " +
                     "FROM users u JOIN driver_requests d ON u.id = d.user_id WHERE d.status = 'Pending'";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setLicenseNumber(rs.getString("license_number"));
                user.setVehicleType(rs.getString("vehicle_type"));
                pendingDrivers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching pending drivers: " + e.getMessage());
        }
        return pendingDrivers;
    }

    // ✅ Approve Driver Request by Email
    public boolean approveDriverByEmail(String email) {
        String sql = "UPDATE users SET role = 'Driver', is_active = TRUE WHERE email = ?";
        String deleteRequest = "DELETE FROM driver_requests WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteRequest)) {

            stmt.setString(1, email);
            deleteStmt.setString(1, email);

            if (stmt.executeUpdate() > 0) {
                deleteStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error approving driver: " + e.getMessage());
        }
        return false;
    }

    // ✅ Reject Driver Request
    public boolean rejectDriverRequest(String email) {
        return executeUpdate("DELETE FROM driver_requests WHERE email = ?", email);
    }

    // ✅ Reusable update method
    private boolean executeUpdate(String sql, String param) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, param);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
        }
        return false;
    }
}








//import Utils.DBConfig;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import Model.User;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class AdminDAO {
//    private Connection connection;
//
//    // ✅ Constructor to initialize connection
//    public AdminDAO() {
//        try {
//            this.connection = DBConfig.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to initialize AdminDAO: " + e.getMessage(), e);
//        }
//    }
//
//    // ✅ Check if a user is an Admin
//    public boolean isUserAdmin(int userId) {
//        String sql = "SELECT 1 FROM users WHERE id = ? AND role = 'Admin'";
//
//        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return rs.next(); // ✅ True if user is admin
//            }
//        } catch (SQLException e) {
//            System.err.println("Error checking admin status: " + e.getMessage());
//        }
//        return false;
//    }
//
//  // Approve a driver (Only Admins can do this)
//public boolean approveDriver(int driverId) {
//    String sql = "UPDATE users SET is_active = TRUE WHERE id = ? AND LOWER(role) = 'driver'";
//    
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        
//        stmt.setInt(1, driverId);
//        
//        return stmt.executeUpdate() > 0; // Returns true if at least one row is updated
//        
//    } catch (SQLException e) {
//        System.err.println("Error approving driver: " + e.getMessage());
//    }
//    
//    return false;
//}
//
//    // ✅ Fetch all users
//    public List<User> getAllUsers() {
//    List<User> users = new ArrayList<>();
//    String sql = "SELECT id, username, email, role, is_active FROM users";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         ResultSet rs = stmt.executeQuery()) {
//
//        while (rs.next()) {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setUsername(rs.getString("username"));
//            user.setEmail(rs.getString("email"));
//            user.setRole(rs.getString("role"));
//            user.setActive(rs.getBoolean("is_active"));
//            users.add(user);
//        }
//    } catch (SQLException e) {
//        System.err.println("Error fetching users: " + e.getMessage());
//    }
//    return users;
//    
//    
//}
//
//    // ✅ Update user role (Admins cannot be changed)
//    public boolean updateUserRole(int userId, String newRole) {
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
//    public boolean deleteUser(int userId) {
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
//    
//    // ✅ Get an Admin user
//    public int getAdminUserId() {
//        String sql = "SELECT id FROM users WHERE role = 'Admin' LIMIT 1";
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
//    public int getTotalUsers() {
//    int count = 0;
//    try (Connection con = DBConfig.getConnection();
//         PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE role = 'user'");
//         ResultSet rs = pst.executeQuery()) {
//        if (rs.next()) {
//            count = rs.getInt(1);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace(); // Log error
//    }
//    return count;
//}
//public int getTotalDrivers() {
//    int count = 0;
//    try (Connection con = DBConfig.getConnection();
//         PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE role = 'driver' AND is_approved = 1");
//         ResultSet rs = pst.executeQuery()) {
//        if (rs.next()) {
//            count = rs.getInt(1);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace(); // Log error
//    }
//    return count;
//}
//public int getPendingApprovals() {
//    int count = 0;
//    try (Connection con = DBConfig.getConnection();
//         PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE role = 'driver' AND is_approved = 0");
//         ResultSet rs = pst.executeQuery()) {
//        if (rs.next()) {
//            count = rs.getInt(1);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace(); // Log error
//    }
//    return count;
//}
//// Get all pending driver requests
//public List<User> getPendingDriverRequests() {
//    List<User> pendingDrivers = new ArrayList<>();
//    String sql = "SELECT u.id, u.username, u.email, d.license_number, d.vehicle_type " +
//                 "FROM users u JOIN driver_requests d ON u.email = d.email WHERE d.status = 'Pending'";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         ResultSet rs = stmt.executeQuery()) {
//
//        while (rs.next()) {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setUsername(rs.getString("username"));
//            user.setEmail(rs.getString("email"));
//            user.setLicenseNumber(rs.getString("license_number")); 
//            user.setVehicleType(rs.getString("vehicle_type"));
//            pendingDrivers.add(user);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return pendingDrivers;
//}
//
//// Approve Driver Request
//public boolean approveDriver(String email) {
//    String sql = "UPDATE users SET role = 'Driver' WHERE email = ?";
//    String deleteRequest = "DELETE FROM driver_requests WHERE email = ?";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         PreparedStatement deleteStmt = conn.prepareStatement(deleteRequest)) {
//
//        stmt.setString(1, email);
//        deleteStmt.setString(1, email);
//
//        int updatedRows = stmt.executeUpdate();
//        if (updatedRows > 0) {
//            deleteStmt.executeUpdate(); // Remove request after approval
//            return true;
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return false;
//}
//
//// Reject Driver Request
//public boolean rejectDriverRequest(String email) {
//    String sql = "DELETE FROM driver_requests WHERE email = ?";
//    
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        
//        stmt.setString(1, email);
//        return stmt.executeUpdate() > 0; 
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return false;
//}







































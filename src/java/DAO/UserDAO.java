package DAO;

import Model.User;
import Utils.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.PasswordUtils;
import java.sql.SQLException;
import jakarta.servlet.http.Part;
import java.io.IOException;


public class UserDAO {

    // Get all users (For Admin Dashboard)
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users"; 

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("nic"),
                    rs.getString("role"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("photo"),
                    rs.getBoolean("is_active"),
                    rs.getBoolean("is_approved"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
//    
//public List<User> getAllDrivers() {
//    List<User> drivers = new ArrayList<>();
//    String query = "SELECT * FROM users WHERE role = 'driver'";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query);
//         ResultSet rs = stmt.executeQuery()) {
//
//        while (rs.next()) {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setUsername(rs.getString("username"));
//            user.setEmail(rs.getString("email"));
//            user.setPhone(rs.getString("phone"));
//            user.setStatus(rs.getString("status"));
//            drivers.add(user);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return drivers;
//}
//public boolean sendDriverRequest(int userId, String license, String vehicleType, String vehicleNumber) {
//    String query = "UPDATE users SET status = 'pending', license_number = ?, vehicleType = ?, vehicleNumber = ? WHERE id = ?";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//        stmt.setString(1, license);
//        stmt.setString(2, vehicleType);
//        stmt.setString(3, vehicleNumber);
//        stmt.setInt(4, userId);
//
//        int rowsUpdated = stmt.executeUpdate();
//        return rowsUpdated > 0;
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return false;
//}

    // Check if Email Already Exists
    public boolean isEmailExists(String email) {
        String query = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try(ResultSet rs = stmt.executeQuery()){
            return rs.next();
        } 
        }   catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if Username Already Exists
    public boolean isUsernameExists(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register a New User (Default role is 'User')
   public boolean registerUser(User user) {
    String query = "INSERT INTO users (username, email, phone, password, nic, role, gender, address, photo, is_active, is_approved, created_at, updated_at) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPhone());
        stmt.setString(4, PasswordUtils.hashPassword(user.getPassword())); // Ensure password is hashed
        stmt.setString(5, user.getNic());
        stmt.setString(6, user.getRole() == null ? "User" : user.getRole());
        stmt.setString(7, user.getGender());
        stmt.setString(8, user.getAddress());
        // Set default profile photo if user hasn't uploaded one
        stmt.setString(9, user.getPhoto() == null || user.getPhoto().isEmpty() ? "assets/no-profile.jpg" : user.getPhoto());
        stmt.setBoolean(10, user.isActive());
        stmt.setBoolean(11, user.isApproved());
        stmt.setTimestamp(12, user.getCreatedAt());
        stmt.setTimestamp(13, user.getUpdatedAt());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error registering user: " + e.getMessage());
    }
    return false;
}


//    // Request Driver Role
//    public boolean requestDriverRole(String email, String licenseNumber, String vehicleType, String vehicleNumber) {
//        String query = "INSERT INTO driver_requests (email, license_number, vehicle_type,Vehicle_number, status) VALUES (?, ?, ?,?, 'Pending')";
//        
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            
//            stmt.setString(1, email);
//            stmt.setString(2, licenseNumber);
//            stmt.setString(3, vehicleType);
//            stmt.setString(4, vehicleNumber);
//            
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    
// Authenticate User
public User authenticateUser(String email, String password) {
    String query = "SELECT * FROM users WHERE email = ?";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, email);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (PasswordUtils.verifyPassword(password, storedPassword)) { // Compare hashed passwords
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("nic"),
                        rs.getString("role"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("photo"),
                        rs.getBoolean("is_active"),
                        rs.getBoolean("is_approved"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                    );
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    // Get User by Email
    public User getUserByEmail(String email) {
       
        String sql = "SELECT * FROM users WHERE email = ? ";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
              stmt.setString(1, email);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("nic"),
                    rs.getString("role"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("photo"),
                    rs.getBoolean("is_active"),
                    rs.getBoolean("is_approved"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
      
    public boolean resetPassword(String email, String hashedPassword) {
        try (Connection conn = DBConfig.getConnection()) {
            // Check if email exists
            String checkQuery = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                
                if (!rs.next()) {
                    return false; // Email not found
                }
            }

            // Update the password
            String updateQuery = "UPDATE users SET password = ? WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, hashedPassword);
                stmt.setString(2, email);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0; // Return true if password was updated
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

// 
//// Check if the user has already submitted a driver request
//public boolean isDriverRequestPending(String email) {
//    String sql = "SELECT 1 FROM driver_requests WHERE email = ? AND status = 'Pending'";
//    
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        
//        stmt.setString(1, email); // Fix: Set parameter
//
//        try (ResultSet rs = stmt.executeQuery()) {
//            return rs.next(); // If there's a pending request, return true
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return false;
//}
//
//
//public boolean approveDriverRequest(String email) {
//    String updateUserQuery = "UPDATE users SET role = 'Driver', is_approved = true WHERE email = ?";
//    String deleteRequestQuery = "DELETE FROM driver_requests WHERE email = ?"; // Remove from requests table
//    
//    try (Connection conn = DBConfig.getConnection()) {
//        conn.setAutoCommit(false); // Start transaction
//
//        try (PreparedStatement updateStmt = conn.prepareStatement(updateUserQuery);
//             PreparedStatement deleteStmt = conn.prepareStatement(deleteRequestQuery)) {
//
//            updateStmt.setString(1, email);
//            int userUpdated = updateStmt.executeUpdate();
//
//            deleteStmt.setString(1, email);
//            int requestDeleted = deleteStmt.executeUpdate();
//
//            if (userUpdated > 0 && requestDeleted > 0) {
//                conn.commit(); // Commit transaction if both queries succeed
//                return true;
//            } else {
//                conn.rollback(); // Rollback if something goes wrong
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//        try (Connection conn = DBConfig.getConnection()) {
//            conn.rollback(); // Ensure rollback on exception
//        } catch (SQLException rollbackEx) {
//            rollbackEx.printStackTrace();
//        }
//    }
//    return false;
//}
public User getUserById(int userId) {
    User user = null;
    try (Connection con = DBConfig.getConnection();
         PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?")) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            
           user.setPhoto(rs.getString("photo") != null ? rs.getString("photo") : "assets/no-profile.jpg");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}


public boolean updateUserProfile(User user) {
    String sql = "UPDATE users SET username = ?, email = ?, phone = ?, address = ?, updatedAt = NOW() WHERE id = ?";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPhone());
        stmt.setString(4, user.getAddress());
        stmt.setInt(5, user.getId());
        
        int rowsAffected = stmt.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);  // Debug log
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean verifyOldPassword(int userId, String oldPassword) {
    String sql = "SELECT password FROM users WHERE id = ?";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            String hashedPassword = rs.getString("password");
            return PasswordUtils.verifyPassword(oldPassword, hashedPassword);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean updatePassword(int userId, String newPassword) {
    String sql = "UPDATE users SET password = ?, updatedAt = NOW() WHERE id = ?";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, PasswordUtils.hashPassword(newPassword)); 
        stmt.setInt(2, userId);
        
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean updateProfilePhoto(int userId, String photoPath) {
    String sql = "UPDATE users SET photo = ? WHERE id = ?";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, photoPath);
        stmt.setInt(2, userId);
        
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean removeProfilePhoto(int userId) {
    String sql = "UPDATE users SET photo =NULL  WHERE id = ?";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, userId);
        
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    


public boolean deleteUser(int userId) {
    String query = "DELETE FROM users WHERE id = ?";

    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



}

















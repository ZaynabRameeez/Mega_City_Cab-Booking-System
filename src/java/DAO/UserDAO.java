package DAO;

import Model.User;
import Utils.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String query = "INSERT INTO users (username, email, phone, password, nic, role, gender, address, is_active) " +
                       "VALUES (?, ?, ?, ?, ?, 'User', ?, ?, true)";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword()); // Ensure password is hashed
            stmt.setString(5, user.getNic());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getAddress());
            
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
        return false;
    }

    // Request Driver Role
    public boolean requestDriverRole(String email, String licenseNumber, String vehicleType) {
        String query = "INSERT INTO driver_requests (email, license_number, vehicle_type, status) VALUES (?, ?, ?, 'Pending')";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            stmt.setString(2, licenseNumber);
            stmt.setString(3, vehicleType);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Authenticate User
    public User authenticateUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password); // Ensure password is hashed before checking
            
            try (ResultSet rs = stmt.executeQuery()){
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get User by Email
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User(
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
        
    }
    
    //Update User Password
    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0; // Returns true if update is successful
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
        return false;
    }
      
// Check if the user has already submitted a driver request
public boolean isDriverRequestPending(String email) {
    String sql = "SELECT 1 FROM driver_requests WHERE email = ? AND status = 'Pending'";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}




//package DAO;
//
//import Model.User;
//import Utils.DBConfig;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserDAO {
//
//    // Get all users (For Admin Dashboard)
//    public List<User> getAllUsers() {
//        List<User> userList = new ArrayList<>();
//        String query = "SELECT * FROM users"; 
//
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                User user = new User(
//                    rs.getInt("id"),
//                    rs.getString("username"),
//                    rs.getString("email"),
//                    rs.getString("phone"),
//                    rs.getString("password"),
//                    rs.getString("nic"),
//                    rs.getString("role"),
//                    rs.getString("gender"),
//                    rs.getString("address"),
//                    rs.getString("photo"),
//                    rs.getBoolean("is_active")
//                );
//                userList.add(user);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return userList;
//    }
//
//    // Check if Email Already Exists
//    public boolean isEmailExists(String email) {
//        String query = "SELECT id FROM users WHERE email = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next(); // Returns true if email exists
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Check if Username Already Exists
//    public boolean isUsernameExists(String username) {
//        String query = "SELECT id FROM users WHERE username = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, username);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next(); // Returns true if username exists
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Register a New User
//    public boolean registerUser(User user) {
//        String query = "INSERT INTO users (username, email, phone, password, nic, role, gender, address, is_active) " +
//                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getEmail());
//            stmt.setString(3, user.getPhone());
//            stmt.setString(4, user.getPassword()); // Password should be hashed before passing
//            stmt.setString(5, user.getNic());
//            stmt.setString(6, user.getRole());
//            stmt.setString(7, user.getGender());
//            stmt.setString(8, user.getAddress());
//            stmt.setBoolean(9, user.getRole().equalsIgnoreCase("driver") ? false : true);
//                         // By default, users are active except drivers
//
//            return stmt.executeUpdate() > 0; // Returns true if insertion is successful
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Authenticate User
//    public User authenticateUser(String email, String password) {
//        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
//
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, email);
//            stmt.setString(2, password); // Ensure password is hashed before checking
//
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return new User(
//                    rs.getInt("id"),
//                    rs.getString("username"),
//                    rs.getString("email"),
//                    rs.getString("phone"),
//                    rs.getString("password"),
//                    rs.getString("nic"),
//                    rs.getString("role"),
//                    rs.getString("gender"),
//                    rs.getString("address"),
//                    rs.getString("photo"),
//                    rs.getBoolean("is_active")
//                );
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null; // Return null if authentication fails
//    }
//
//    // Get User by Email
//    public User getUserByEmail(String email) {
//        User user = null;
//        String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
//
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                user = new User(
//                    rs.getInt("id"),
//                    rs.getString("username"),
//                    rs.getString("email"),
//                    rs.getString("phone"),
//                    rs.getString("password"),
//                    rs.getString("nic"),
//                    rs.getString("role"),
//                    rs.getString("gender"),
//                    rs.getString("address"),
//                    rs.getString("photo"),
//                    rs.getBoolean("is_active")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }
//
//    // Update User Password
//    public boolean updatePassword(String email, String hashedPassword) {
//        String sql = "UPDATE users SET password = ? WHERE email = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, hashedPassword);
//            stmt.setString(2, email);
//            return stmt.executeUpdate() > 0; // Returns true if update is successful
//        } catch (SQLException e) {
//            System.err.println("Error updating password: " + e.getMessage());
//        }
//        return false;
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

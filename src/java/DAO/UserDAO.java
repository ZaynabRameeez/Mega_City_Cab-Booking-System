package DAO;

import Model.User;
import Utils.DBConfig;
import java.sql.*;
import Utils.PasswordUtils;
import java.sql.SQLException;



public class UserDAO {
    
    // ✅ Check if a User Already Exists
    public static boolean isUserExists(String email, String mobile,String nic) {
       try (Connection conn = DBConfig.getConnection();
     PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE email = ? OR mobile OR nic = ?")) {
    stmt.setString(1, email);
    stmt.setString(2, mobile);
    stmt.setString(3, nic);
    try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
    }
} catch (Exception e) {
    e.printStackTrace();
}
return false;
    }

  // ✅ Insert New User into Database
    public static boolean insertUser(User user) {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, birthday, email, mobile, role, password,address,nic) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getBirthday());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getMobile());
            stmt.setString(6, user.getRole());
            stmt.setString(7, PasswordUtils.hashPassword(user.getPassword())); 
            stmt.setString(8, user.getAddress());
            stmt.setString(9, user.getNic());

            return stmt.executeUpdate() > 0; // Returns true if insert is successful
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    
    // ✅ Make this method static
    public static boolean resetPassword(String email, String hashedPassword) {
        try (Connection conn = DBConfig.getConnection()) {
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
}















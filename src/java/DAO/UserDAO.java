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
                    rs.getBoolean("is_active")
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
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if email exists
        } catch (SQLException e) {
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
            return rs.next(); // Returns true if username exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register a New User
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, email, phone, password, nic, role, gender, address, is_active) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword()); // Password should be hashed before passing
            stmt.setString(5, user.getNic());
            stmt.setString(6, user.getRole());
            stmt.setString(7, user.getGender());
            stmt.setString(8, user.getAddress());
            stmt.setBoolean(9, user.getRole().equalsIgnoreCase("driver") ? false : true);
                         // By default, users are active except drivers

            return stmt.executeUpdate() > 0; // Returns true if insertion is successful

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

            ResultSet rs = stmt.executeQuery();
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
                    rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
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
                    rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Update User Password
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
}























//package DAO;
//
//
//import Model.User;
//import Utils.DBConfig;
//import java.sql.*;
//
//public class UserDAO {
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
//  public User authenticateUser(String email, String password) {
//    String query = "SELECT * FROM users WHERE email = ? AND password = ?";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//        stmt.setString(1, email);
//        stmt.setString(2, password); // Ensure password is hashed before checking
//
//        ResultSet rs = stmt.executeQuery();
//        if (rs.next()) {
//            String role = rs.getString("role");
//            
//            return new User(
//                rs.getInt("id"),
//                rs.getString("username"),
//                rs.getString("email"),
//                rs.getString("phone"),
//                rs.getString("password"),
//                rs.getString("nic"),
//                role,
//                rs.getString("gender"),
//                rs.getString("address"),
//                rs.getString("photo"),
//                rs.getBoolean("is_active")
//            );
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return null; // Return null if authentication fails
//}
//
////    // Approve Driver (Admin Only)
////    public boolean approveDriver(int driverId) {
////        String query = "UPDATE users SET is_active = 1 WHERE id = ? AND role = 'driver'";
////
////        try (Connection conn = DBConfig.getConnection();
////             PreparedStatement stmt = conn.prepareStatement(query)) {
////            stmt.setInt(1, driverId);
////            return stmt.executeUpdate() > 0; // Returns true if update is successful
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return false;
////    }
//    
//    public User getUserByEmail(String email) {
//    User user = null;
//    String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
//
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        
//        stmt.setString(1, email);
//        ResultSet rs = stmt.executeQuery();
//
//        if (rs.next()) {
//            user = new User(
//                rs.getInt("id"),
//                rs.getString("username"),
//                rs.getString("email"),
//                rs.getString("phone"),
//                rs.getString("password"),
//                rs.getString("nic"),
//                rs.getString("role"),
//                rs.getString("gender"),
//                rs.getString("address"),
//                rs.getString("photo"),
//                rs.getBoolean("is_active")
//            );
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return user;
//}
//    // Add updatePassword method here
//public boolean updatePassword(String email, String hashedPassword) {
//    String sql = "UPDATE users SET password = ? WHERE email = ?";
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        
//        stmt.setString(1, hashedPassword);
//        stmt.setString(2, email);
//        return stmt.executeUpdate() > 0; // Returns true if update is successful
//    } catch (SQLException e) {
//        System.err.println("Error updating password: " + e.getMessage());
//    }
//    return false;
//}
//
//
//}
//
//
//





























//import Model.User;
//import Utils.PasswordUtils;
//import Utils.DBConfig;
//import java.sql.*;
//
//public class UserDAO {
//    private final Connection connection;
//
//    // Constructor accepting Connection
//    public UserDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    // Register a new user
//    public boolean registerUser(User user) {
//        // Prevent duplicate registration
//        if (isEmailTaken(user.getEmail()) || isUsernameTaken(user.getUsername())) {
//            return false;
//        }
//
//        String sql = "INSERT INTO users (username, email, phone, nic, password, role, gender, address, is_active, created_at, updated_at) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
//
//        boolean isActive = !user.getRole().equalsIgnoreCase("Driver"); // Drivers inactive by default
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getEmail());
//            stmt.setString(3, user.getPhone());
//            stmt.setString(4, user.getNic());
//            stmt.setString(5, PasswordUtils.hashPassword(user.getPassword())); // Secure password hashing
//            stmt.setString(6, user.getRole());
//            stmt.setString(7, user.getGender());
//            stmt.setString(8, user.getAddress());
//            stmt.setBoolean(9, isActive);
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error registering user: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Check if email already exists
//    public boolean isEmailTaken(String email) {
//        String sql = "SELECT 1 FROM users WHERE email = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next(); // Returns true if email exists
//        } catch (SQLException e) {
//            System.err.println("Error checking email existence: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Check if username already exists
//    public boolean isUsernameTaken(String username) {
//        String sql = "SELECT 1 FROM users WHERE username = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next(); // Returns true if username exists
//        } catch (SQLException e) {
//            System.err.println("Error checking username existence: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Authenticate user (login)
//    public User authenticateUser(String email, String password) {
//        String sql = "SELECT * FROM users WHERE email = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                String hashedPassword = rs.getString("password");
//
//                // Verify password
//                if (PasswordUtils.verifyPassword(password, hashedPassword)) {
//                    return createUserFromResultSet(rs);
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error during authentication: " + e.getMessage());
//        }
//        return null; // Return null if authentication fails
//    }
//
//    // Fetch user by email
//    public User getUserByEmail(String email) {
//        String sql = "SELECT * FROM users WHERE email = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return createUserFromResultSet(rs);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching user by email: " + e.getMessage());
//        }
//        return null;
//    }
//
//    // Helper method to create User object from ResultSet
//    private User createUserFromResultSet(ResultSet rs) throws SQLException {
//        return new User(
//            rs.getInt("id"),
//            rs.getString("username"),
//            rs.getString("email"),
//            rs.getString("phone"),
//            rs.getString("nic"),
//            rs.getString("password"),
//            rs.getString("role"),
//            rs.getString("gender"),
//            rs.getString("address"),
//            rs.getBoolean("is_active")
//        );
//    }
//}
//
//










//package DAO;
//
//import Model.User;
//import Utils.PasswordUtils;
//import java.sql.Connection;
//import Utils.DBConfig;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserDAO {
//    private final Connection connection;
//
//    // Constructor accepting Connection
//    public UserDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    // Register a new user
//    public boolean registerUser(User user) {
//        String sql = "INSERT INTO users (username, email, phone, NIC, password, role, gender, address, is_active, created_at, updated_at) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
//
//        boolean isActive = !user.getRole().equalsIgnoreCase("Driver"); // Drivers inactive by default
//
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getEmail());
//            stmt.setString(3, user.getPhone());
//            stmt.setString(4, user.getNic());
//            stmt.setString(5, PasswordUtils.hashPassword(user.getPassword())); // Hash password before saving
//            stmt.setString(6, user.getRole());
//            stmt.setString(7, user.getGender());
//            stmt.setString(8, user.getAddress());
//            stmt.setBoolean(9, isActive);
//
//            int rowsInserted = stmt.executeUpdate();
//            return rowsInserted > 0;
//        } catch (SQLException e) {
//            System.err.println("Error registering user: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Fetch user by email
//    public User getUserByEmail(String email) {
//        String sql = "SELECT * FROM users WHERE email = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return createUserFromResultSet(rs);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching user by email: " + e.getMessage());
//        }
//        return null;
//    }
//
//    // Check if username exists
//    public boolean isUsernameTaken(String username) {
//        String sql = "SELECT 1 FROM users WHERE username = ?";
//        return exists(sql, username);
//    }
//
//    // Check if email exists
//    public boolean isEmailTaken(String email) {
//        String sql = "SELECT 1 FROM users WHERE email = ?";
//        return exists(sql, email);
//    }
//
//    // General method to check existence
//    private boolean exists(String sql, String value) {
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, value);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            System.err.println("Error checking existence: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Helper method to create User object from ResultSet
//    private User createUserFromResultSet(ResultSet rs) throws SQLException {
//        return new User(
//            rs.getInt("id"),
//            rs.getString("username"),
//            rs.getString("email"),
//            rs.getString("phone"),
//            rs.getString("nic"),
//            rs.getString("password"),
//            rs.getString("role"),
//            rs.getString("gender"),
//            rs.getString("address"),
//            rs.getBoolean("is_active")
//        );
//    }
//    // Authenticate user by email and password
//public User authenticateUser(String email, String password) {
//    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
//
//     try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//        stmt.setString(1, email);
//        ResultSet rs = stmt.executeQuery();
//
//        if (rs.next()) {
//            String hashedPassword = rs.getString("password");
//
//            // Verify the entered password against the hashed password
//            if (PasswordUtils.verifyPassword(password, hashedPassword)) {
//                return createUserFromResultSet(rs);
//            }
//        }
//    } catch (SQLException e) {
//        System.err.println("Error during authentication: " + e.getMessage());
//    }
//    return null; // Return null if authentication fails
//}
//
//}

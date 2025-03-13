/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class DBConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/mega_city_cab"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "Ashen@2227"; 

    // Method to establish a connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database Driver not found!", e);
        }
    }

    // Method to close the connection
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

   public static int getUserIdByEmail(String email) {
    int userId = -1; // Default value in case the email is not found
    String query = "SELECT id FROM users WHERE email = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery(); // Ensure ResultSet is imported
        
        if (rs.next()) {
            userId = rs.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return userId;
}

}

    







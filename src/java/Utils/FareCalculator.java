/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author zainr
 */
public class FareCalculator {
    



 
    public static double calculateFare(int vehicleTypeId, double distance) {
        double baseFare = 0;
        double perKmRate = 0;
        
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT base_fare, per_km_rate FROM vehicle_types WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vehicleTypeId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                baseFare = rs.getDouble("base_fare");
                perKmRate = rs.getDouble("per_km_rate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return baseFare + (perKmRate * distance);
    }
}

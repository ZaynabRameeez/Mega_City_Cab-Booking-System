/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author zainr
 */


import Model.Booking;
import Utils.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public boolean addBooking(Booking booking) {
        boolean success = false;
        try (Connection conn = DBConfig.getConnection()) {
            String query = "INSERT INTO bookings (user_id, pickup_location, dropoff_location, vehicle_type, ride_date, ride_time, status, price) "
                    + "VALUES (?, ?, ?, ?, ?, ?, 'Pending', ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, booking.getUserId());
            stmt.setString(2, booking.getPickupLocation());
            stmt.setString(3, booking.getDropoffLocation());
            stmt.setString(4, booking.getVehicleType());
            stmt.setString(5, booking.getRideDate());
            stmt.setString(6, booking.getRideTime());
            stmt.setDouble(7, booking.getPrice());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection()) {
            String query = "SELECT * FROM bookings WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(userId, rs.getString("pickup_location"), rs.getString("dropoff_location"),
                        rs.getString("vehicle_type"), rs.getString("ride_date"), rs.getString("ride_time"), rs.getDouble("price"));
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setStatus(rs.getString("status"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}

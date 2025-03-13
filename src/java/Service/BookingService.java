/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author zainr
 */


import DAO.BookingDAO;
import Model.Booking;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO = new BookingDAO();

    public boolean bookRide(Booking booking) {
        return bookingDAO.addBooking(booking);
    }

    public List<Booking> getUserBookings(int userId) {
        return bookingDAO.getBookingsByUser(userId);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Driver;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.User; 
import Service.DriverService;



public class DriverServlet extends HttpServlet {
    private DriverService driverService;

    public void init() {
        driverService = new DriverService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "registerDriver":
                registerDriver(request, response);
                break;
            case "updateStatus":
                updateDriverStatus(request, response);
                break;
            case "confirmRide":
                confirmRide(request, response);
                break;
            case "updateEarnings":
                updateDriverEarnings(request, response);
                break;
            case "requestInfoUpdate":
                requestDriverInfoUpdate(request, response);
                break;
        }
    }

    // ✅ Register a new driver
    private void registerDriver(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String license = request.getParameter("licenseNumber");
        String vehicleType = request.getParameter("vehicleType");
        String vehicleNumber = request.getParameter("vehicleNumber");

        Driver driver = new Driver(id, license, vehicleType, vehicleNumber, false, "Pending", 0, 0.0, 0.0);
        boolean success = driverService.registerDriver(driver);

        if (success) {
            response.sendRedirect("DriverDashboard.jsp?message=Registration Successful");
        } else {
            response.sendRedirect("DriverDashboard.jsp?error=Registration Failed");
        }
    }

    // ✅ Update Driver Status
    private void updateDriverStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        String status = request.getParameter("status");

        boolean success = driverService.updateDriverStatus(driverId, status);
        response.sendRedirect("DriverDashboard.jsp?message=" + (success ? "Status Updated" : "Update Failed"));
    }

    // ✅ Confirm Ride
    private void confirmRide(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int rideId = Integer.parseInt(request.getParameter("rideId"));
        int driverId = Integer.parseInt(request.getParameter("driverId"));

        boolean success = driverService.confirmRide(rideId, driverId);
        response.sendRedirect("DriverDashboard.jsp?message=" + (success ? "Ride Confirmed" : "Failed to Confirm Ride"));
    }

    // ✅ Update Driver Earnings
    private void updateDriverEarnings(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        double amount = Double.parseDouble(request.getParameter("amount"));

        boolean success = driverService.updateDriverEarnings(driverId, amount);
        response.sendRedirect("DriverDashboard.jsp?message=" + (success ? "Earnings Updated" : "Failed to Update Earnings"));
    }

    // ✅ Request Driver Info Update (Requires Admin Approval)
    private void requestDriverInfoUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        String license = request.getParameter("licenseNumber");
        String vehicleType = request.getParameter("vehicleType");
        String vehicleNumber = request.getParameter("vehicleNumber");

        boolean success = driverService.updateDriverInfo(driverId, license, vehicleType, vehicleNumber);
        response.sendRedirect("DriverDashboard.jsp?message=" + (success ? "Update Requested" : "Update Failed"));
    }
}


















//
//public class DriverServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private final DriverService driverService = new DriverService();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//
//        if ("viewProfile".equals(action)) {
//            int driverId = Integer.parseInt(request.getParameter("driverId"));
//            User driver = driverService.getDriverById(driverId);
//            request.setAttribute("driver", driver);
//            request.getRequestDispatcher("Driverprofile.jsp").forward(request, response);
//
//        } else if ("viewDrivers".equals(action)) {
//            List<User> drivers = driverService.getActiveDrivers();
//            request.setAttribute("drivers", drivers);
//            request.getRequestDispatcher("driver_list.jsp").forward(request, response);
//
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
//        }
//    }
//
//    @Override
//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    String action = request.getParameter("action");
//    HttpSession session = request.getSession();
//    User driverUser = (User) session.getAttribute("user");
//
//    if (driverUser == null || !"driver".equalsIgnoreCase(driverUser.getRole())) {
//        response.sendRedirect("Login.jsp");
//        return;
//    }
//
//    if ("updateProfile".equals(action)) {
//        String name = request.getParameter("name");
//        String phone = request.getParameter("phone");
//        String vehicleType = request.getParameter("vehicleType");
//        String vehicleNumber = request.getParameter("vehicleNumber");
//
//        // Fetch the driver entity using the user ID
//        Driver driver = driverService.getDriverByUserId(driverUser.getId());
//
//        if (driver != null) {
//            boolean success = driverService.updateDriverProfile(driver.getDriverId(), name, phone, vehicleType, vehicleNumber);
//
//            if (success) {
//                driverUser.setName(name);
//                driverUser.setPhone(phone);
//
//                // Update driver details
//                driver.setVehicleType(vehicleType);
//                driver.setLicenseNumber(vehicleNumber);
//                
//                session.setAttribute("user", driverUser);
//                session.setAttribute("driver", driver); // Store driver details in session
//            }
//
//            response.sendRedirect("DriverProfile.jsp?updateSuccess=" + success);
//        } else {
//            response.sendRedirect("DriverProfile.jsp?error=Driver not found");
//        }
//
//    } else if ("updateVehicle".equals(action)) {
//        String vehicleType = request.getParameter("vehicleType");
//        String vehicleNumber = request.getParameter("vehicleNumber");
//
//        // Fetch the driver entity using user ID
//        Driver driver = driverService.getDriverByUserId(driverUser.getId());
//
//        if (driver != null) {
//            boolean success = driverService.updateDriverVehicle(driver.getDriverId(), vehicleType, vehicleNumber);
//
//            if (success) {
//                driver.setVehicleType(vehicleType);
//                driver.setLicenseNumber(vehicleNumber);
//                session.setAttribute("driver", driver);
//            }
//
//            response.getWriter().write(success ? "Vehicle updated" : "Failed to update vehicle");
//        } else {
//            response.getWriter().write("Driver not found");
//        }
//    } else {
//        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
//    }
//}
//}

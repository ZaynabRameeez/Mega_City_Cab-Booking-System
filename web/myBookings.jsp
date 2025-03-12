<%-- 
    Document   : myBookings
    Created on : Mar 10, 2025, 9:10:57 PM
    Author     : zainr
--%>

<%@page import="Utils.DBConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.sql.*, Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("Login.jsp?error=Session Expired");
        return;
    }

    Connection conn = DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement(
        "SELECT b.booking_id, b.pickup_location, b.dropoff_location, b.vehicle_type, b.ride_date, b.ride_time, b.status, b.price, " +
        "u.first_name AS driver_name, u.mobile AS driver_mobile, u.profile_image, v.brand, v.model, v.plate_number, v.vehicle_image " +
        "FROM bookings b LEFT JOIN users u ON b.assigned_driver = u.id " +
        "LEFT JOIN vehicles v ON u.id = v.driver_id " +
        "WHERE b.user_id = (SELECT id FROM users WHERE email = ?) " +
        "ORDER BY b.ride_date DESC, b.ride_time DESC"
    );
    stmt.setString(1, email);
    ResultSet rs = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings - Mega City Cab</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        /* Navbar styling */
        .navbar-custom {
            background-color: #343a40;
        }
        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: #ffffff;
        }
        .navbar-custom .nav-link:hover {
            color: #adb5bd;
        }
        /* Card styling */
        .card-header {
            background-color: #007bff;
            color: #fff;
            border-top-left-radius: 0.5rem;
            border-top-right-radius: 0.5rem;
            text-align: center;
            padding: 1rem;
        }
        .card {
            border: none;
            border-radius: 0.5rem;
        }
        /* Table styling */
        .table thead th {
            border-bottom: 2px solid #dee2e6;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f3f5;
        }
        /* Status badges */
        .badge-status {
            font-size: 0.9rem;
            padding: 0.5em 0.75em;
            border-radius: 0.5rem;
        }
        .status-pending { background-color: #ffc107; color: #212529; }
        .status-confirmed { background-color: #28a745; color: #fff; }
        .status-completed { background-color: #007bff; color: #fff; }
        .status-cancelled { background-color: #dc3545; color: #fff; }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom shadow">
        <div class="container">
            <a class="navbar-brand" href="#">Mega City Cab</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" 
                aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarContent">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <span class="nav-link"><i class="bi bi-person-circle"></i> <strong><%= email %></strong></span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="UsersDashboard.jsp"><i class="bi bi-house-door"></i> Dashboard</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Container -->
    <div class="container my-5">
        <div class="card shadow-lg">
            <div class="card-header">
                <h3><i class="bi bi-bookmark-check"></i> My Bookings</h3>
                <p class="mb-0">View your past and upcoming rides</p>
            </div>
            <div class="card-body">
                <% if (!rs.isBeforeFirst()) { %>
                    <div class="alert alert-warning text-center">
                        <i class="bi bi-exclamation-triangle-fill"></i> No bookings found. <a href="BookRide.jsp" class="alert-link">Book a Ride</a>
                    </div>
                <% } else { %>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th>#</th>
                                    <th>Pickup</th>
                                    <th>Drop-off</th>
                                    <th>Vehicle</th>
                                    <th>Date</th>
                                    <th>Time</th>
                                    <th>Status</th>
                                    <th>Fare (Rs.)</th>
                                    <th>Driver</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% while (rs.next()) { 
                                    boolean hasDriver = rs.getString("driver_name") != null;
                                %>
                                    <tr>
                                        <td><%= rs.getInt("booking_id") %></td>
                                        <td><%= rs.getString("pickup_location") %></td>
                                        <td><%= rs.getString("dropoff_location") %></td>
                                        <td><%= rs.getString("vehicle_type") %></td>
                                        <td><%= rs.getDate("ride_date") %></td>
                                        <td><%= rs.getTime("ride_time") %></td>
                                        <td>
                                            <% String status = rs.getString("status"); %>
                                            <span class="badge badge-status 
                                                <%= status.equals("Pending") ? "status-pending" :
                                                    status.equals("Confirmed") ? "status-confirmed" :
                                                    status.equals("Completed") ? "status-completed" :
                                                    "status-cancelled" %>">
                                                <%= status %>
                                            </span>
                                        </td>
                                        <td>Rs. <%= rs.getDouble("price") %></td>
                                        <td>
                                            <% if (hasDriver) { %>
                                                <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#driverModal_<%= rs.getInt("booking_id") %>">
                                                    <i class="bi bi-eye"></i> View
                                                </button>
                                            <% } else { %>
                                                <span class="text-muted">No Driver Assigned</span>
                                            <% } %>
                                        </td>
                                    </tr>

                                    <% if (hasDriver) { %>
                                        <!-- Driver Details Modal -->
                                        <div class="modal fade" id="driverModal_<%= rs.getInt("booking_id") %>" tabindex="-1" aria-labelledby="driverModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="driverModalLabel">Driver & Vehicle Details</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body text-center">
                                                        <img src="uploads/<%= rs.getString("profile_image") %>" alt="Driver Profile" class="img-fluid rounded-circle mb-3" style="max-width: 150px;">
                                                        <p><strong>Driver:</strong> <%= rs.getString("driver_name") %></p>
                                                        <p><strong>Mobile:</strong> <%= rs.getString("driver_mobile") %></p>
                                                        <hr>
                                                        <p><strong>Vehicle:</strong> <%= rs.getString("brand") %> <%= rs.getString("model") %></p>
                                                        <p><strong>Plate Number:</strong> <%= rs.getString("plate_number") %></p>
                                                        <div class="mt-3">
                                                            <img src="uploads/<%= rs.getString("vehicle_image") %>" alt="Vehicle Image" class="img-fluid rounded" style="max-width: 250px;">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    <% } %>
                            
                                <% } %>
                            
                            </tbody>
                        </table>
                    </div>
                <% } %>
                <div class="text-center mt-4">
                    <a href="UsersDashboard.jsp" class="btn btn-secondary">
                        <i class="bi bi-arrow-left-circle"></i> Back to Dashboard
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

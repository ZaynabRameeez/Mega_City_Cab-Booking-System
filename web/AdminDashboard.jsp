

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="Model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .sidebar {
            height: 100vh;
            width: 250px;
            position: fixed;
            background: #343a40;
            padding-top: 20px;
        }
        .sidebar a {
            padding: 10px 20px;
            display: block;
            color: white;
            text-decoration: none;
        }
        .sidebar a:hover {
            background-color: #007bff;
        }
        .content {
            margin-left: 260px;
            padding: 20px;
        }
        .card {
            border-radius: 10px;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

    <!-- Sidebar Navigation -->
    <div class="sidebar">
        <h4 class="text-center text-white">Admin Panel</h4>
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="ManageUsers.jsp">Manage Users</a>
        <a href="ManageDrivers.jsp">Manage Drivers</a>
        <a href="ManageVehicle.jsp">Manage Vehicles</a>
        <a href="manageBookings.jsp">Manage Bookings</a>
        <a href="paymentManagement.jsp">Payment Management</a>
        <a href="updateSystemSettings.jsp">System Settings</a>
        <a href="systemReports.jsp">System Reports</a>
        <a href="LogoutServlet">Logout</a>
    </div>

    <!-- Main Content -->
    <div class="content">
        <h2>Welcome, Admin</h2>

        <!-- Stats Cards -->
        <div class="row">
            <div class="col-md-3">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-body">
                        <h5>Total Users</h5>
                        <h2>150</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-success mb-3">
                    <div class="card-body">
                        <h5>Total Drivers</h5>
                        <h2>50</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-warning mb-3">
                    <div class="card-body">
                        <h5>Total Bookings</h5>
                        <h2>320</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-danger mb-3">
                    <div class="card-body">
                        <h5>Pending Approvals</h5>
                        <h2>5</h2>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="row mt-4">
            <div class="col-md-4">
                <a href="ManageUsers.jsp" class="btn btn-primary btn-lg btn-block">Manage Users</a>
            </div>
            <div class="col-md-4">
                <a href="ManageDrivers.jsp" class="btn btn-success btn-lg btn-block">Manage Drivers</a>
            </div>
            <div class="col-md-4">
                <a href="manageBookings.jsp" class="btn btn-warning btn-lg btn-block">Manage Bookings</a>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%-- 
    Document   : DriverProfile
    Created on : Feb 21, 2025, 6:01:23 PM
    Author     : zainr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="Model.User" %>
<%
    HttpSession sessionObj = request.getSession(false);
    User driver = (User) sessionObj.getAttribute("user");

    if (driver == null || !"driver".equalsIgnoreCase(driver.getRole())) {
        response.sendRedirect("Login.jsp");
        return;
    }

    String updateSuccess = request.getParameter("updateSuccess");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Driver Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .card { border-radius: 10px; }
    </style>
</head>
<body>

<!-- ✅ Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Mega City Cab</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="DriverDaashboard.jsp">Dashboard</a></li>
                <li class="nav-item"><a class="nav-link active" href="Driverprofile.jsp">Profile</a></li>
                <li class="nav-item"><a class="nav-link text-danger" href="Logout.jsp">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="text-center">Driver Profile</h2>

    <% if ("true".equals(updateSuccess)) { %>
        <div class="alert alert-success">Profile updated successfully!</div>
    <% } %>

    <div class="card p-4">
        <form action="DriverServlet" method="post">
            <input type="hidden" name="action" value="updateProfile">
            
            <div class="mb-3">
                <label class="form-label">Full Name</label>
                <input type="text" class="form-control" name="name" value="<%= driver.getName() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" value="<%= driver.getEmail() %>" disabled>
            </div>

            <div class="mb-3">
                <label class="form-label">Phone</label>
                <input type="text" class="form-control" name="phone" value="<%= driver.getPhone() %>" required>
            </div>

            <h4 class="mt-4">Vehicle Details</h4>

            <div class="mb-3">
                <label class="form-label">Vehicle Type</label>
                <select class="form-control" name="vehicleType" required>
                    <option value="Car" <%= "Car".equals(driver.getVehicleType()) ? "selected" : "" %>>Car</option>
                    <option value="Van" <%= "Van".equals(driver.getVehicleType()) ? "selected" : "" %>>Van</option>
                    <option value="Bike" <%= "Bike".equals(driver.getVehicleType()) ? "selected" : "" %>>Bike</option>
                    <option value="Auto" <%= "Auto".equals(driver.getVehicleType()) ? "selected" : "" %>>Auto</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Vehicle Number</label>
                <input type="text" class="form-control" name="vehicleNumber" value="<%= driver.getVehicleNumber() %>" required>
            </div>

            <button type="submit" class="btn btn-primary">Update Profile</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

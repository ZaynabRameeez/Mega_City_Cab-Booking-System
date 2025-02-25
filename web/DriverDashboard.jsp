<%-- 
    Document   : DriverDashboard
    Created on : Feb 17, 2025, 1:56:48 PM
    Author     : zainr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Model.RideRequest" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="Model.User" %>
<%
    HttpSession sessionObj = request.getSession(false);
    User driver = (User) sessionObj.getAttribute("user");

    if (driver == null || !"driver".equalsIgnoreCase(driver.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<RideRequest> rideRequests = (List<RideRequest>) request.getAttribute("rideRequests");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Driver Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .card { border-radius: 10px; }
        .ride-request { margin-bottom: 15px; }
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
                <li class="nav-item"><a class="nav-link" href="DriverDashboard.jsp">Dashboard</a></li>
                <li class="nav-item"><a class="nav-link" href="Driverprofile.jsp">Profile</a></li>
                <li class="nav-item"><a class="nav-link text-danger" href="Logout.jsp">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="text-center">Welcome, <%= driver.getUsername() %>!</h2>

    <div class="row mt-4">
        <!-- ✅ Earnings Summary -->
        <div class="col-md-4">
            <div class="card text-white bg-success mb-3">
                <div class="card-body">
                    <h5 class="card-title">Total Earnings</h5>
                    <p class="card-text">$1500</p>
                </div>
            </div>
        </div>

        <!-- ✅ Completed Rides -->
        <div class="col-md-4">
            <div class="card text-white bg-primary mb-3">
                <div class="card-body">
                    <h5 class="card-title">Completed Rides</h5>
                    <p class="card-text">45</p>
                </div>
            </div>
        </div>

        <!-- ✅ Pending Ride Requests -->
        <div class="col-md-4">
            <div class="card text-white bg-warning mb-3">
                <div class="card-body">
                    <h5 class="card-title">New Ride Requests</h5>
                    <p class="card-text"><%= rideRequests != null ? rideRequests.size() : 0 %></p>
                </div>
            </div>
        </div>
    </div>

    <!-- ✅ Ride Requests Section -->
    <h3 class="mt-4">New Ride Requests</h3>
    <div class="row">
        <%
            if (rideRequests != null && !rideRequests.isEmpty()) {
                for (RideRequest requestObj : rideRequests) {
        %>
        <div class="col-md-6">
            <div class="card ride-request">
                <div class="card-body">
                    <h5 class="card-title">Pickup: <%= requestObj.getPickupLocation() %></h5>
                    <p class="card-text">Dropoff: <%= requestObj.getDropoffLocation() %></p>
                    <p class="card-text">Fare: $<%= requestObj.getFare() %></p>
                    <form action="DriverServlet" method="post">
                        <input type="hidden" name="action" value="acceptRide">
                        <input type="hidden" name="rideId" value="<%= requestObj.getId() %>">
                        <button type="submit" class="btn btn-success">Accept Ride</button>
                    </form>
                </div>
            </div>
        </div>
        <%
                }
            } else {
        %>
        <p class="text-muted">No new ride requests at the moment.</p>
        <%
            }
        %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

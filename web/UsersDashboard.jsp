<%-- 
    Document   : UserDashboard
    Created on : Feb 13, 2025, 10:18:33 AM
    Author     : zainr
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Model.User" %>


<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        /* Main Content Styling */
      .main-content {
    margin-left: 250px; /* Sidebar space */
    padding: 120px 20px 20px; /* Increased top padding */
    transition: margin-left 0.3s;
}
/* Ensure space below navbar */
body {
    padding-top: 70px; /* Adjust this based on your navbar height */
}

        /* Taxi Animation */
        .taxi-container {
            width: 100%;
            overflow: hidden;
            position: relative;
            height: 120px;
            margin-top: 20px;
        }

        .taxi-animation {
            position: absolute;
            width: 120px;
            left: -150px; /* Start fully hidden on the left */
            animation: moveTaxi 6s linear infinite; /* Continuous movement */
        }

        @keyframes moveTaxi {
            0% { left: -150px; } /* Start from sidebar (hidden) */
            100% { left: 100vw; } /* Move across screen */
        }

        /* Responsive Design */
    /* Responsive Fix */
@media (max-width: 992px) {
    .main-content {
        margin-left: 0;
        padding-top: 130px; /* More space for smaller screens */
    }
}


            .taxi-animation {
                width: 100px; /* Adjust taxi size for smaller screens */
            }
        
    </style>
</head>
<body>
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp"/>

    <!-- Main Content -->
    <div class="container main-content">
        <h1>Quick Ride Booking</h1>
        <div class="row ">
            <div class="col-md-6 col-sm-10">
                <input type="text" class="form-control mb-3" placeholder="Search for a ride..." id="searchRide">
                <button class="btn btn-primary w-100" onclick="redirectToBooking()">Search</button>
            </div>
        </div>

        <!-- Animated Taxi -->
        <div class="taxi-container">
            <img src="Img/Taxi.png" class="taxi-animation" alt="Moving Taxi">
        </div>   
        
        
    
        
        <div class="modal fade" id="driverRequestModal" tabindex="-1" aria-labelledby="driverRequestModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="driverRequestModalLabel">Become a Driver</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="DriverServlet" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="userId" value="<%= user.getId() %>">
                        <div class="mb-3">
                            <label for="license" class="form-label">Driver License Number</label>
                            <input type="text" class="form-control" id="license" name="license" required>
                        </div>
                        <div class="mb-3">
                            <label for="vehicleType" class="form-label">Vehicle Type</label>
                            <select class="form-control" id="vehicleType" name="vehicleType" required>
                                <option value="Car">Car</option>
                                <option value="Van">Van</option>
                                <option value="Bike">Bike</option>
                                <option value="Auto">Auto</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="vehicleNumber" class="form-label">Vehicle Number</label>
                            <input type="text" class="form-control" id="vehicleNumber" name="vehicleNumber" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Submit Request</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Redirect to Booking Page
        function redirectToBooking() {
            window.location.href = "BookRide.jsp";
        }
        
        document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("form[action='DriverServlet']").addEventListener("submit", function (event) {
        let license = document.getElementById("license").value.trim();
        let vehicleNumber = document.getElementById("vehicleNumber").value.trim();
        
        if (!/^[A-Za-z0-9-]+$/.test(license)) {
            alert("Invalid license format!");
            event.preventDefault();
        }

        if (!/^[A-Za-z0-9-]+$/.test(vehicleNumber)) {
            alert("Invalid vehicle number format!");
            event.preventDefault();
        }
    });
});
        
    </script>
</body>
</html>




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
        body {
            transition: background 0.5s, color 0.5s;
        }
        .dark-mode {
            background-color: #121212;
            color: white;
        }

        /* Sidebar */
        .sidebar {
            background-color: #343a40;
            color: white;
            width: 250px;
            height: 100vh;
            padding: 15px;
            position: fixed;
            transition: 0.3s;
        }
        .sidebar a {
            color: white;
            display: block;
            margin: 10px 0;
            text-decoration: none;
        }
        .sidebar a:hover {
            background-color: #495057;
            padding: 5px;
            border-radius: 5px;
        }

        /* Main content */
        .main-content {
            margin-left: 250px;
            transition: margin-left 0.3s;
            padding: 20px;
        }

        /* Taxi Animation */
        .taxi-container {
            width: 100%;
            overflow: hidden;
            position: relative;
            height: 100px;
            margin-top: 20px;
        }
        .taxi-animation {
            position: absolute;
            width: 120px;
            animation: moveTaxi 6s linear infinite;
        }
        @keyframes moveTaxi {
            0% { left: -120px; }
            100% { left: 100vw; }
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <span class="navbar-brand">Welcome, <%= user.getUsername() %>!</span>
        <button id="themeToggle" class="btn btn-secondary">☀️🌙</button>
    </nav>

    <!-- Sidebar -->
    <div id="sidebar" class="sidebar">
        <h2>Mega Cab</h2>
        <a href="UserDashboard.jsp">🏠 Dashboard</a>
        <a href="BookRide.jsp">🚖 Book a Ride</a>
        <a href="MyRides.jsp">📅 My Rides</a>
        <a href="Payments.jsp">💳 Payments</a>
        <a href="Support.jsp">🆘 Help Center</a>
        <a href="LogoutServlet">🚪 Logout</a>
        <hr>
        <a href="#" data-bs-toggle="modal" data-bs-target="#driverRequestModal">🚖 Want to be a Driver?</a>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <h1>Quick Ride Booking</h1>
        <input type="text" class="form-control mb-3" placeholder="Search for a ride..." id="searchRide">
        <button class="btn btn-primary" onclick="redirectToBooking()">Search</button>

        <!-- Animated Taxi -->
        <div class="mt-4 taxi-container">
            <img src="Img/Taxi.png" class="taxi-animation" alt="Moving Taxi">
        </div>
    </div>

    <!-- Driver Request Modal -->
    <div class="modal fade" id="driverRequestModal" tabindex="-1" aria-labelledby="driverRequestModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="driverRequestModalLabel">Request to be a Driver</h5>
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
        // Theme Toggle
        document.getElementById("themeToggle").addEventListener("click", function() {
            document.body.classList.toggle("dark-mode");
        });

        // Redirect to Booking
        function redirectToBooking() {
            window.location.href = "BookRide.jsp";
        }
    </script>
</body>
</html>


















//<!DOCTYPE html>
<!--<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <style>
        body {
            transition: background 0.5s, color 0.5s;
        }
        .dark-mode {
            background-color: #121212;
            color: white;
        }

        /* Navbar */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
        }

        /* Sidebar */
        .sidebar {
            background-color: #343a40;
            color: white;
            width: 250px;
            height: 100vh;
            padding: 15px;
            position: fixed;
            transition: 0.3s;
        }
        .sidebar a {
            color: white;
            display: block;
            margin: 10px 0;
            text-decoration: none;
        }
        .sidebar a:hover {
            background-color: #495057;
            padding: 5px;
            border-radius: 5px;
        }

        /* Hide sidebar in mobile */
        .sidebar.collapsed {
            width: 80px;
        }
        .sidebar.collapsed h2 {
            display: none;
        }
        .sidebar.collapsed a {
            text-align: center;
            font-size: 14px;
        }

        /* Main content */
        .main-content {
            margin-left: 250px;
            transition: margin-left 0.3s;
            padding: 20px;
        }
        .collapsed + .main-content {
            margin-left: 80px;
        }

        /* Taxi Animation */
        .taxi-container {
            width: 100%;
            overflow: hidden;
            position: relative;
            height: 100px;
            margin-top: 20px;
        }
        .taxi-animation {
            position: absolute;
            width: 120px;
            left: -150px;
            animation: moveTaxi 5s linear infinite;
        }

        @keyframes moveTaxi {
            0% { left: 80px; }  
            50% { left: 80%; }  
            100% { left: 80px; }  
        }

        /* Responsive Design */
        /* Responsive Sidebar */
@media (max-width: 768px) {
    .sidebar {
        width: 80px; /* Smaller sidebar on small screens */
        text-align: center;
        padding: 10px 5px;
    }
    .sidebar h2 {
        display: none; /* Hide text */
    }
    .sidebar a {
        font-size: 12px; /* Adjust text size */
    }
    .main-content {
        margin-left: 80px; /* Adjust content margin */
    }
}

/* Taxi Animation - Forward Only */
@keyframes moveTaxi {
    0% { transform: translateX(80px); } /* Start from sidebar */
    100% { transform: translateX(100vw); } /* Move off-screen */
}
.taxi-animation {
    position: absolute;
    width: 120px;
    animation: moveTaxi 6s linear infinite; /* Keep moving forward */
}

    </style>
</head>
<body>
     Navbar 
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
       
        <span class="navbar-brand">Welcome, <%= user.getUsername() %>!</span>
        <button id="themeToggle" class="btn btn-secondary">☀️🌙</button>
    </nav>

     Sidebar 
    <div id="sidebar" class="sidebar">
        <h2>Mega Cab</h2>
        <a href="UserDashboard.jsp">🏠 Dashboard</a>
        <a href="BookRide.jsp">🚖 Book a Ride</a>
        <a href="MyRides.jsp">📅 My Rides</a>
        <a href="Payments.jsp">💳 Payments</a>
        <a href="Support.jsp">🆘 Help Center</a>
        <a href="LogoutServlet">🚪 Logout</a>
    </div>

     Main Content 
    <div class="main-content">
        <h1>Quick Ride Booking</h1>
        <input type="text" class="form-control mb-3" placeholder="Search for a ride..." id="searchRide">
        <button class="btn btn-primary" onclick="redirectToBooking()">Search</button>

         Animated Taxi 
        <div class="mt-4 taxi-container">
            <img src="Img/Taxi.png" class="taxi-animation" alt="Moving Taxi">
        </div>
    </div>

    <script>
      

        // Theme Toggle
        document.getElementById("themeToggle").addEventListener("click", function() {
            document.body.classList.toggle("dark-mode");
        });

        // Redirect to Booking
        function redirectToBooking() {
            window.location.href = "BookRide.jsp";
        }
    </script>
</body>
</html>-->



















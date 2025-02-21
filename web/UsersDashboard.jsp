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






















<!--<!DOCTYPE html>
<html lang="en">
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
    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px;
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
        left: -150px;  /* Start from the sidebar */
        animation: moveTaxi 5s linear infinite;
    }
    
    @keyframes moveTaxi {
        0% { left: 250px; }  
        100% { left: 100%; }  
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
    <div class="d-flex">
        <div class="bg-dark text-white p-3" style="width: 250px; height: 100vh;">
            <h2>Mega Cab</h2>
            <a href="UserDashboard.jsp" >🏠 Dashboard</a>
            <a href="BookRide.jsp" class="text-white d-block">🚖 Book a Ride</a>
            <a href="MyRides.jsp" class="text-white d-block">📅 My Rides</a>
            <a href="Payments.jsp" class="text-white d-block">💳 Payments</a>
            <a href="Support.jsp" class="text-white d-block">🆘 Help Center</a>
            <a href="LogoutServlet" class="text-white d-block">🚪 Logout</a>
        </div>
        
         Main Content 
        <div class="p-4 w-100">
            <h1>Quick Ride Booking</h1>
            <input type="text" class="form-control mb-3" placeholder="Search for a ride..." id="searchRide">
            <button class="btn btn-primary" onclick="redirectToBooking()">Search</button>
            
             Animated Taxi 
            <div class="mt-4">
                <img src="Img/Taxi.png" class="taxi-animation" alt="Moving Taxi">
            </div>
        </div>
    </div>

    <script>
        document.getElementById("themeToggle").addEventListener("click", function() {
            document.body.classList.toggle("dark-mode");
        });
        
        function redirectToBooking() {
            window.location.href = "BookRide.jsp";
        }
    </script>
</body>
</html>-->










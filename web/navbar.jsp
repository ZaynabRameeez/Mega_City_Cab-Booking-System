<%-- 
    Document   : navbar
    Created on : Mar 1, 2025, 12:17:31 AM
    Author     : zainr
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Model.User" %>
<%@ page session="true" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    if (email == null || !role.equalsIgnoreCase("Customer")) {
        response.sendRedirect("Login.jsp?error=Unauthorized Access");
        return;
    }
%>

<!-- Bootstrap & FontAwesome -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<style>
    body { transition: background 0.5s, color 0.5s; }
    .dark-mode { background-color: #121212; color: white; }

    /* Sidebar */
    .sidebar {
        background-color: #343a40;
        color: white;
        width: 250px;
        height: 100vh;
        padding: 15px;
        position: fixed;
        left: 0;
        top: 0;
        transition: transform 0.3s ease-in-out;
        transform: translateX(0);
        z-index: 1000;
    }

    /* Hide sidebar on small screens */
    @media (max-width: 992px) {
        .sidebar {
            transform: translateX(-100%);
        }
        .sidebar.show {
            transform: translateX(0);
        }
    }

    /* Sidebar links */
    .sidebar a {
        color: white;
        display: block;
        margin: 10px 0;
        text-decoration: none;
        padding: 10px;
    }

    .sidebar a:hover {
        background-color: #495057;
        border-radius: 5px;
    }

    /* Main content */
    .main-content {
        margin-left: 250px;
        padding: 20px;
        transition: margin-left 0.3s;
    }

    @media (max-width: 992px) {
        .main-content {
            margin-left: 0;
        }
    }

    /* Navbar */
    .navbar {
        position: fixed;
        width: 100%;
        top: 0;
        left: 0;
        z-index: 999;
        padding-left: 260px; /* Push navbar content to the right */
        transition: padding-left 0.3s;
    }

    /* Adjust when sidebar is hidden on small screens */
    @media (max-width: 992px) {
        .navbar {
            padding-left: 10px;
        }
    }

    /* Sidebar toggle button styling */
    .sidebar-toggle {
        position: fixed;
        top: 15px;
        left: 10px;
        z-index: 1100;
        background-color: #343a40;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 5px;
        cursor: pointer;
    }

    .sidebar-toggle:hover {
        background-color: #495057;
    }
</style>

<!-- Sidebar Toggle Button (only for small screens) -->
<button class="sidebar-toggle d-lg-none" onclick="toggleSidebar()">☰</button>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <span class="navbar-brand">Welcome,  <%= email %>!!</span>
        <button id="themeToggle" class="btn btn-secondary">☀️🌙</button>
    </div>
</nav>

<!-- Sidebar -->
<div id="sidebar" class="sidebar">
    <h2>Mega Cab</h2>
    <a href="UsersDashboard.jsp">🏠 Dashboard</a>
    <a href="BookRide.jsp">🚖 Book a Ride</a>
    <a href="MyRides.jsp">📅 My Rides</a>
    <a href="Payments.jsp">💳 Payments</a>
    <a href="Support.jsp">🆘 Help Center</a>
    <a href="LogoutServlet">🚪 Logout</a>
    <a href="UserProfile.jsp">👤 Manage Profile</a>
    <hr>
    <a href="#" data-bs-toggle="modal" data-bs-target="#driverRequestModal">🚖 Want to be a Driver?</a>
</div>

<!-- JavaScript -->
<script>
    function toggleSidebar() {
        var sidebar = document.getElementById("sidebar");
        sidebar.classList.toggle("show");
        
        var navbar = document.querySelector(".navbar");
        if (sidebar.classList.contains("show")) {
            navbar.style.paddingLeft = "260px"; // Adjust navbar when sidebar is open
        } else {
            navbar.style.paddingLeft = "10px"; // Reset navbar when sidebar is closed
        }
    }

    document.getElementById("themeToggle").addEventListener("click", function() {
        document.body.classList.toggle("dark-mode");
    });
</script>

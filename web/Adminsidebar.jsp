<%-- 
    Document   : Adminsidebar
    Created on : Mar 6, 2025, 11:40:02?AM
    Author     : zainr
--%>
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

<!-- Sidebar styles -->
<style>
    .sidebar {
        height: 100vh;
        width: 250px;
        position: fixed;
        background: #343a40;
        padding-top: 20px;
        top: 0;
        left: 0;
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

    /* Responsive Sidebar for Smaller Screens */
    @media (max-width: 768px) {
        .sidebar {
            width: 100%;
            height: auto;
            position: relative;
        }
        .sidebar a {
            text-align: center;
        }
    }
</style>

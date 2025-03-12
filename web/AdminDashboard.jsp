

<%@ page session="true" import="java.sql.*, Utils.DBConfig" %>
<%
    // Verify if the user is logged in and has Admin privileges
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    if (email == null || !role.equalsIgnoreCase("Admin")) {
        response.sendRedirect("Login.jsp?error=Unauthorized Access");
        return;
    }

    Connection conn =DBConfig.getConnection();

    // Fetch Total Users Count
    PreparedStatement totalUsersStmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM users");
    ResultSet totalUsersRs = totalUsersStmt.executeQuery();
    int totalUsers = totalUsersRs.next() ? totalUsersRs.getInt("total") : 0;

    // Fetch Total Bookings Count
    PreparedStatement totalBookingsStmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM bookings");
    ResultSet totalBookingsRs = totalBookingsStmt.executeQuery();
    int totalBookings = totalBookingsRs.next() ? totalBookingsRs.getInt("total") : 0;

    // Fetch Total Completed Rides Count
    PreparedStatement completedRidesStmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM bookings WHERE status = 'Completed'");
    ResultSet completedRidesRs = completedRidesStmt.executeQuery();
    int completedRides = completedRidesRs.next() ? completedRidesRs.getInt("total") : 0;

    // Fetch Total Earnings
    PreparedStatement earningsStmt = conn.prepareStatement("SELECT SUM(price) AS earnings FROM bookings WHERE status = 'Completed'");
    ResultSet earningsRs = earningsStmt.executeQuery();
    double totalEarnings = earningsRs.next() ? earningsRs.getDouble("earnings") : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin Dashboard - Mega City Cab</title>
  <!-- Bootstrap CSS and Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body {
      background: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      color: #333;
    }
    /* Navbar */
    .navbar {
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    /* Sidebar */
    .sidebar {
      background: #343a40;
      min-height: 100vh;
      padding-top: 20px;
    }
    .sidebar a {
      color: #adb5bd;
      padding: 12px 20px;
      display: block;
      text-decoration: none;
      font-size: 16px;
      transition: background 0.3s, color 0.3s;
    }
    .sidebar a:hover,
    .sidebar a.active {
      background: #495057;
      color: #fff;
      border-radius: 5px;
    }
    /* Dashboard Cards */
    .dashboard-card {
      border: none;
      border-radius: 15px;
      transition: transform 0.3s, box-shadow 0.3s;
    }
    .dashboard-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 15px rgba(0,0,0,0.2);
    }
    .card-title {
      font-size: 18px;
      font-weight: 600;
    }
    .card-value {
      font-size: 32px;
      font-weight: bold;
    }
    /* Main Content */
    .main-content {
      padding: 20px;
    }
    /* Footer */
    footer {
      background: #343a40;
      color: #adb5bd;
      padding: 15px;
      text-align: center;
    }
    @media (max-width: 768px) {
      .sidebar { min-height: auto; }
    }
  </style>
  <script>
    function confirmLogout() {
      return confirm("Are you sure you want to logout?");
    }
  </script>
</head>
<body>
  <!-- Navigation Bar -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container">
      <a class="navbar-brand" href="#">Mega City Cab </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav align-items-center">
          <li class="nav-item me-3">
            <span class="nav-link">Admin: <b><%= email %></b> </span>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="NotificationServlet">
              <i class="bi bi-bell-fill"></i> Notifications
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="manageDriverRequests.jsp">
              <i class="bi bi-truck"></i> Driver Requests
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="manageLocations.jsp">
              <i class="bi bi-geo-alt"></i> Manage Locations
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="manageCabPricing.jsp">
              <i class="bi bi-currency-dollar"></i> Manage Pricing
            </a>
          </li>
          <li class="nav-item">
            <a class="btn btn-outline-light" href="LogoutServlet" onclick="return confirmLogout();">
              <i class="bi bi-box-arrow-right"></i> Logout
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Main Layout -->
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <div class="col-md-3 sidebar d-none d-md-block">
        <nav class="nav flex-column">
          <a class="nav-link active" href="AdminDashboard.jsp">
            <i class="bi bi-speedometer2"></i> Dashboard
          </a>
          <a class="nav-link" href="ManageUsers.jsp">
            <i class="bi bi-person-lines-fill"></i> Manage Users
          </a>
          <a class="nav-link" href="manageBookings.jsp">
            <i class="bi bi-calendar-check"></i> Manage Bookings
          </a>
        </nav>
      </div>

      <!-- Main Content -->
      <div class="col-md-9 main-content">
        <div class="container">
          <div class="card mb-4 shadow-sm border-0" style="border-radius: 15px;">
            <div class="card-body text-center">
              <h2 class="card-title">Welcome to the Admin Dashboard</h2>
              <p class="card-text">Monitor bookings, users, and earnings at a glance.</p>
            </div>
          </div>

          <div class="row g-4">
            <!-- Total Users -->
            <div class="col-sm-6 col-lg-3">
              <div class="card text-white bg-primary dashboard-card">
                <div class="card-body text-center">
                  <div class="card-title">Total Users</div>
                  <div class="card-value"><%= totalUsers %></div>
                </div>
              </div>
            </div>
            <!-- Total Bookings -->
            <div class="col-sm-6 col-lg-3">
              <div class="card text-white bg-success dashboard-card">
                <div class="card-body text-center">
                  <div class="card-title">Total Bookings</div>
                  <div class="card-value"><%= totalBookings %></div>
                </div>
              </div>
            </div>
            <!-- Completed Rides -->
            <div class="col-sm-6 col-lg-3">
              <div class="card text-white bg-warning dashboard-card">
                <div class="card-body text-center">
                  <div class="card-title">Completed Rides</div>
                  <div class="card-value"><%= completedRides %></div>
                </div>
              </div>
            </div>
            <!-- Total Earnings -->
            <div class="col-sm-6 col-lg-3">
              <div class="card text-white bg-danger dashboard-card">
                <div class="card-body text-center">
                  <div class="card-title">Total Earnings</div>
                  <div class="card-value">Rs. <%= totalEarnings %></div>
                </div>
              </div>
            </div>
          </div>

          <div class="mt-4 text-center">
            <a href="ManageUsers.jsp" class="btn btn-outline-secondary me-2">Manage Users</a>
            <a href="manageBookings.jsp" class="btn btn-outline-secondary">Manage Bookings</a>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer>
    <div class="container">
      <p class="mb-0">&copy; 2025 Mega City Cab. All Rights Reserved.</p>
    </div>
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

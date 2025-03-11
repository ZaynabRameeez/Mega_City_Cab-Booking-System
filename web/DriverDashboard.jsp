<%-- 
    Document   : DriverDashboard
    Created on : Feb 17, 2025, 1:56:48?PM
    Author     : zainr
--%>
<%@ page session="true" import="java.sql.*, Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    if (email == null || !role.equalsIgnoreCase("Driver")) {
        response.sendRedirect("Login.jsp?error=Unauthorized Access");
        return;
    }

    Connection conn = DBConfig.getConnection();

    // Fetch driver ID
    PreparedStatement driverIdStmt = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
    driverIdStmt.setString(1, email);
    ResultSet driverIdRs = driverIdStmt.executeQuery();
    int driverId = -1;
    if (driverIdRs.next()) {
        driverId = driverIdRs.getInt("id");
    }

    // Fetch total accepted bookings count
    PreparedStatement totalAcceptedStmt = conn.prepareStatement(
        "SELECT COUNT(*) AS total FROM bookings WHERE assigned_driver = ? AND status = 'Confirmed'"
    );
    totalAcceptedStmt.setInt(1, driverId);
    ResultSet totalAcceptedRs = totalAcceptedStmt.executeQuery();
    int totalAccepted = 0;
    if (totalAcceptedRs.next()) {
        totalAccepted = totalAcceptedRs.getInt("total");
    }

    // Fetch total earnings
    PreparedStatement earningsStmt = conn.prepareStatement(
        "SELECT SUM(price) AS earnings FROM bookings WHERE assigned_driver = ? AND status = 'Completed'"
    );
    earningsStmt.setInt(1, driverId);
    ResultSet earningsRs = earningsStmt.executeQuery();
    double totalEarnings = 0;
    if (earningsRs.next()) {
        totalEarnings = earningsRs.getDouble("earnings");
    }

    // Filters
    String filterPickup = request.getParameter("filterPickup");
    String filterDate = request.getParameter("filterDate");
    
    // Fetch available rides with filters
    String query = "SELECT b.booking_id, u.first_name, u.last_name, u.mobile, b.pickup_location, b.dropoff_location, " +
                   "b.vehicle_type, b.ride_date, b.ride_time, b.price " +
                   "FROM bookings b JOIN users u ON b.user_id = u.id " +
                   "WHERE b.status = 'Pending'";
    if (filterPickup != null && !filterPickup.isEmpty()) {
        query += " AND b.pickup_location = '" + filterPickup + "'";
    }
    if (filterDate != null && !filterDate.isEmpty()) {
        query += " AND b.ride_date = '" + filterDate + "'";
    }
    query += " ORDER BY b.ride_date DESC, b.ride_time DESC";
    
    PreparedStatement availableRidesStmt = conn.prepareStatement(query);
    ResultSet availableRidesRs = availableRidesStmt.executeQuery();

    // Fetch assigned rides
    PreparedStatement assignedStmt = conn.prepareStatement(
        "SELECT booking_id, pickup_location, dropoff_location, vehicle_type, ride_date, ride_time, status, price " +
        "FROM bookings WHERE assigned_driver = ? ORDER BY ride_date DESC, ride_time DESC"
    );
    assignedStmt.setInt(1, driverId);
    ResultSet assignedRs = assignedStmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Driver Dashboard - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <style>
    body {
      background: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    /* Navbar Styling */
    .navbar-custom {
      background-color: #343a40;
    }
    .navbar-custom .navbar-brand,
    .navbar-custom .nav-link {
      color: #fff;
    }
    .navbar-custom .nav-link:hover {
      color: #ffc107;
    }
    /* Sidebar Styling */
    .sidebar {
      background-color: #fff;
      min-height: 100vh;
      padding: 20px;
      border-right: 1px solid #dee2e6;
    }
    .sidebar .nav-link {
      color: #495057;
      margin-bottom: 10px;
      transition: background 0.3s, color 0.3s;
    }
    .sidebar .nav-link.active, .sidebar .nav-link:hover {
      background-color: #e9ecef;
      color: #212529;
      border-radius: 5px;
    }
    /* Stats Cards */
    .stats-card {
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    .stats-card .card-body {
      padding: 1.5rem;
    }
    /* Table Styling */
    .table thead th {
      vertical-align: middle;
    }
    .table-hover tbody tr:hover {
      background-color: #f1f3f5;
    }
    /* Button Customization */
    .btn-custom {
      border-radius: 5px;
    }
  </style>
  <script>
    function disableButtonAndSubmit(bookingId) {
      document.getElementById("completeBtn_" + bookingId).disabled = true;
      setTimeout(() => {
        document.getElementById("completeForm_" + bookingId).submit();
      }, 300);
    }
    function confirmLogout() {
      return confirm("Are you sure you want to logout?");
    }
  </script>
</head>
<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-custom shadow-sm">
    <div class="container">
      <a class="navbar-brand" href="#">Mega City Cab</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
              aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"><i class="bi bi-list" style="color:#fff;"></i></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav align-items-center">
          <li class="nav-item me-3">
            <span class="nav-link"><i class="bi bi-person-circle"></i> <%= email %></span>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="NotificationServlet">
              <i class="bi bi-bell"></i> Notifications
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="logout.jsp"><i class="bi bi-box-arrow-right"></i> Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Main Container -->
  <div class="container-fluid my-4">
    <div class="row">
      <!-- Sidebar -->
      <aside class="col-md-3 col-lg-2 sidebar">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link active" href="driverDashboard.jsp">
              <i class="bi bi-speedometer2"></i> Dashboard
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="manageVehicle.jsp">
              <i class="bi bi-truck"></i> Manage Vehicle
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="driverProfile.jsp">
              <i class="bi bi-person-lines-fill"></i> Manage Profile
            </a>
          </li>
        </ul>
      </aside>

      <!-- Dashboard Main Content -->
      <main class="col-md-9 col-lg-10 px-md-4 py-4">
        <div class="card shadow-sm p-4 mb-4">
          <h3 class="text-center mb-3">Driver Dashboard</h3>
          <p class="text-center text-muted">Manage your rides and track your earnings effectively.</p>
          <!-- Stats Section -->
          <div class="row mb-4">
            <div class="col-md-6 mb-3">
              <div class="card text-white bg-success stats-card">
                <div class="card-body text-center">
                  <h4>Total Earnings</h4>
                  <h2>Rs. <%= totalEarnings %></h2>
                </div>
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <div class="card text-white bg-primary stats-card">
                <div class="card-body text-center">
                  <h4>Total Rides Accepted</h4>
                  <h2><%= totalAccepted %></h2>
                </div>
              </div>
            </div>
          </div>
          <!-- Navigation Buttons -->
          <div class="d-flex justify-content-between mb-4">
            <a href="manageVehicle.jsp" class="btn btn-primary btn-custom">
              <i class="bi bi-truck"></i> Manage My Vehicle
            </a>
            <a href="driverProfile.jsp" class="btn btn-secondary btn-custom">
              <i class="bi bi-person-lines-fill"></i> Manage Profile
            </a>
          </div>
          
          <!-- Filter Section -->
          <h4>Filter Rides <i class="bi bi-funnel"></i></h4>
          <form method="GET" action="driverDashboard.jsp" class="row g-3 mb-4">
            <div class="col-md-5">
              <label class="form-label">Pickup Location:</label>
              <select name="filterPickup" class="form-select">
                <option value="">All</option>
                <option value="Colombo" <%= "Colombo".equals(filterPickup) ? "selected" : "" %>>Colombo</option>
                <option value="Kandy" <%= "Kandy".equals(filterPickup) ? "selected" : "" %>>Kandy</option>
                <option value="Galle" <%= "Galle".equals(filterPickup) ? "selected" : "" %>>Galle</option>
                <option value="Jaffna" <%= "Jaffna".equals(filterPickup) ? "selected" : "" %>>Jaffna</option>
              </select>
            </div>
            <div class="col-md-4">
              <label class="form-label">Ride Date:</label>
              <input type="date" name="filterDate" class="form-control" value="<%= filterDate != null ? filterDate : "" %>">
            </div>
            <div class="col-md-3 d-flex align-items-end">
              <button type="submit" class="btn btn-dark btn-custom w-100">
                <i class="bi bi-search"></i> Apply Filters
              </button>
            </div>
          </form>
          
          <!-- Available Rides Section -->
          <h4>Available Rides <i class="bi bi-calendar2-check"></i></h4>
          <div class="table-responsive mb-4">
            <table class="table table-hover align-middle">
              <thead class="table-dark">
                <tr>
                  <th>#</th>
                  <th>Customer</th>
                  <th>Mobile</th>
                  <th>Pickup</th>
                  <th>Drop-off</th>
                  <th>Vehicle</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Fare (Rs.)</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <% while (availableRidesRs.next()) { %>
                  <tr>
                    <td><%= availableRidesRs.getInt("booking_id") %></td>
                    <td><%= availableRidesRs.getString("first_name") %> <%= availableRidesRs.getString("last_name") %></td>
                    <td><%= availableRidesRs.getString("mobile") %></td>
                    <td><%= availableRidesRs.getString("pickup_location") %></td>
                    <td><%= availableRidesRs.getString("dropoff_location") %></td>
                    <td><%= availableRidesRs.getString("vehicle_type") %></td>
                    <td><%= availableRidesRs.getDate("ride_date") %></td>
                    <td><%= availableRidesRs.getTime("ride_time") %></td>
                    <td>Rs. <%= availableRidesRs.getDouble("price") %></td>
                    <td>
                      <form action="AcceptBookingServlet" method="post">
                        <input type="hidden" name="bookingId" value="<%= availableRidesRs.getInt("booking_id") %>">
                        <button type="submit" class="btn btn-sm btn-success btn-custom">
                          <i class="bi bi-check-circle"></i> Accept
                        </button>
                      </form>
                    </td>
                  </tr>
                <% } %>
              </tbody>
            </table>
          </div>
          
          <!-- Assigned Rides Section -->
          <h4>My Assigned Rides <i class="bi bi-clipboard-check"></i></h4>
          <div class="table-responsive">
            <table class="table table-hover align-middle">
              <thead class="table-secondary">
                <tr>
                  <th>#</th>
                  <th>Pickup</th>
                  <th>Drop-off</th>
                  <th>Vehicle</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Fare (Rs.)</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <% while (assignedRs.next()) { 
                     String rideStatus = assignedRs.getString("status");
                     boolean isCompleted = rideStatus.equals("Completed");
                %>
                  <tr>
                    <td><%= assignedRs.getInt("booking_id") %></td>
                    <td><%= assignedRs.getString("pickup_location") %></td>
                    <td><%= assignedRs.getString("dropoff_location") %></td>
                    <td><%= assignedRs.getString("vehicle_type") %></td>
                    <td><%= assignedRs.getDate("ride_date") %></td>
                    <td><%= assignedRs.getTime("ride_time") %></td>
                    <td>Rs. <%= assignedRs.getDouble("price") %></td>
                    <td>
                      <span class="badge <%= isCompleted ? "bg-primary" : "bg-success" %>">
                        <%= isCompleted ? "Completed" : "Confirmed" %>
                      </span>
                    </td>
                    <td>
                      <% if (!isCompleted) { %>
                        <form id="completeForm_<%= assignedRs.getInt("booking_id") %>" action="CompleteBookingServlet" method="post">
                          <input type="hidden" name="bookingId" value="<%= assignedRs.getInt("booking_id") %>">
                          <button type="button" class="btn btn-sm btn-primary btn-custom"
                                  id="completeBtn_<%= assignedRs.getInt("booking_id") %>"
                                  onclick="disableButtonAndSubmit(<%= assignedRs.getInt("booking_id") %>)">
                            <i class="bi bi-check"></i> Complete
                          </button>
                        </form>
                      <% } else { %>
                        <button class="btn btn-sm btn-secondary btn-custom" disabled>
                          <i class="bi bi-check-circle-fill"></i> Completed
                        </button>
                      <% } %>
                    </td>
                  </tr>
                <% } %>
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </div>
  </div>
  
  <!-- Footer -->
  <footer class="bg-dark text-white text-center py-3">
    <div class="container">
      <small>&copy; 2025 Mega City Cab. All Rights Reserved.</small>
    </div>
  </footer>

  <!-- Bootstrap Bundle JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%-- 
    Document   : manageBookings
    Created on : Mar 10, 2025, 9:08:30?PM
    Author     : zainr
--%>

<%@ page session="true" import="java.sql.*, Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    if (email == null || !role.equalsIgnoreCase("Admin")) {
        response.sendRedirect("Login.jsp?error=Unauthorized Access");
        return;
    }

    Connection conn = DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement(
        "SELECT b.booking_id, u.first_name, u.last_name, u.mobile, b.pickup_location, b.dropoff_location, " +
        "b.vehicle_type, b.ride_date, b.ride_time, b.status, b.price, d.first_name AS driver_name " +
        "FROM bookings b JOIN users u ON b.user_id = u.id " +
        "LEFT JOIN users d ON b.assigned_driver = d.id ORDER BY b.ride_date DESC, b.ride_time DESC"
    );
    ResultSet rs = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Bookings - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  <script>
    function searchBookings() {
      let input = document.getElementById("searchInput").value.toLowerCase();
      let rows = document.querySelectorAll("#bookingTable tbody tr");
      rows.forEach(row => {
          let customer = row.cells[1].textContent.toLowerCase();
          let pickup = row.cells[3].textContent.toLowerCase();
          let dropoff = row.cells[4].textContent.toLowerCase();
          row.style.display = (customer.includes(input) || pickup.includes(input) || dropoff.includes(input)) ? "" : "none";
      });
    }
    function confirmDelete(bookingId) {
      return confirm("Are you sure you want to delete Booking ID " + bookingId + "?");
    }
  </script>
  <style>
    body {
      background: linear-gradient(135deg, #222, #444);
      color: white;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .container {
      background: rgba(255,255,255,0.2);
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    }
    .btn-custom {
      transition: 0.3s;
    }
    .btn-custom:hover {
      transform: translateY(-3px);
      box-shadow: 0 4px 10px rgba(255,255,255,0.3);
    }
    .search-bar {
      background: #fff;
      color: black;
      border-radius: 5px;
      padding: 8px;
      margin-bottom: 15px;
      width: 100%;
    }
  </style>
</head>
<body>
  <div class="container mt-5">
    <h2 class="text-center"><i class="bi bi-card-checklist"></i> Manage Bookings</h2>
    <p class="text-center">Update, assign drivers, or delete bookings.</p>
    <!-- Search Bar -->
    <input type="text" id="searchInput" class="search-bar" onkeyup="searchBookings()" placeholder="Search by Customer Name, Pickup or Drop-off...">
    <!-- Bookings Table -->
    <div class="table-responsive">
      <table class="table table-hover table-dark" id="bookingTable">
        <thead>
          <tr>
            <th>ID</th>
            <th>Customer</th>
            <th>Mobile</th>
            <th>Pickup</th>
            <th>Drop-off</th>
            <th>Vehicle</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
            <th>Fare (Rs.)</th>
            <th>Driver</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <% while (rs.next()) { %>
            <tr>
              <td><%= rs.getInt("booking_id") %></td>
              <td><%= rs.getString("first_name") %> <%= rs.getString("last_name") %></td>
              <td><%= rs.getString("mobile") %></td>
              <td><%= rs.getString("pickup_location") %></td>
              <td><%= rs.getString("dropoff_location") %></td>
              <td><%= rs.getString("vehicle_type") %></td>
              <td><%= rs.getDate("ride_date") %></td>
              <td><%= rs.getTime("ride_time") %></td>
              <td>
                <form action="UpdateBookingStatusServlet" method="post">
                  <input type="hidden" name="bookingId" value="<%= rs.getInt("booking_id") %>">
                  <select name="status" class="form-select form-select-sm" onchange="this.form.submit()">
                    <option value="Pending" <%= rs.getString("status").equals("Pending") ? "selected" : "" %>>Pending</option>
                    <option value="Confirmed" <%= rs.getString("status").equals("Confirmed") ? "selected" : "" %>>Confirmed</option>
                    <option value="Completed" <%= rs.getString("status").equals("Completed") ? "selected" : "" %>>Completed</option>
                    <option value="Cancelled" <%= rs.getString("status").equals("Cancelled") ? "selected" : "" %>>Cancelled</option>
                  </select>
                </form>
              </td>
              <td>Rs. <%= rs.getDouble("price") %></td>
              <td>
                <form action="AssignDriverServlet" method="post">
                  <input type="hidden" name="bookingId" value="<%= rs.getInt("booking_id") %>">
                  <select name="assigned_driver" class="form-select form-select-sm" onchange="this.form.submit()">
                    <option value="">Assign Driver</option>
                    <%
                      PreparedStatement driversStmt = conn.prepareStatement("SELECT id, first_name FROM users WHERE role = 'Driver'");
                      ResultSet driversRs = driversStmt.executeQuery();
                      while (driversRs.next()) {
                    %>
                      <option value="<%= driversRs.getInt("id") %>" <%= rs.getString("driver_name") != null && rs.getString("driver_name").equals(driversRs.getString("first_name")) ? "selected" : "" %>>
                        <%= driversRs.getString("first_name") %>
                      </option>
                    <% } %>
                  </select>
                </form>
              </td>
              <td>
                <a href="DeleteBookingServlet?id=<%= rs.getInt("booking_id") %>" class="btn btn-danger btn-sm btn-custom" onclick="return confirmDelete(<%= rs.getInt("booking_id") %>);">
                  <i class="bi bi-trash"></i> Delete
                </a>
              </td>
            </tr>
          <% } %>
        </tbody>
      </table>
    </div>
    <div class="text-center mt-4">
      <a href="AdminDashboard.jsp" class="btn btn-secondary btn-custom">
        <i class="bi bi-arrow-left-circle"></i> Back to Dashboard
      </a>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

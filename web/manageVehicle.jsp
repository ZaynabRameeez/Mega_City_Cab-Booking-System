<%-- 
    Document   : manageVehicle
    Created on : Mar 11, 2025, 12:03:38?PM
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
    PreparedStatement stmt = conn.prepareStatement(
        "SELECT brand, model, ac_type, plate_number, color, vehicle_image FROM vehicles WHERE driver_id = (SELECT id FROM users WHERE email = ?)"
    );
    stmt.setString(1, email);
    ResultSet rs = stmt.executeQuery();
    boolean hasVehicle = rs.next();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Vehicle - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <style>
    body {
      background: #f8f9fa;
    }
    /* Navbar styling */
    .navbar-custom {
      background-color: #343a40;
    }
    .navbar-custom .navbar-brand,
    .navbar-custom .nav-link {
      color: #ffffff;
    }
    .navbar-custom .nav-link:hover {
      color: #adb5bd;
    }
    /* Card styling */
    .card {
      border: none;
      border-radius: 1rem;
    }
    .card-header {
      background-color: #007bff;
      color: #fff;
      border-top-left-radius: 1rem;
      border-top-right-radius: 1rem;
      text-align: center;
      padding: 1.5rem;
    }
    .vehicle-img {
      max-width: 300px;
      border-radius: 0.5rem;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }
    .form-label {
      font-weight: 500;
    }
    .btn-custom {
      border-radius: 0.5rem;
    }
  </style>
</head>
<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-custom shadow-sm">
    <div class="container">
      <a class="navbar-brand" href="#">Mega City Cab</a>
      <div class="collapse navbar-collapse justify-content-end">
        <ul class="navbar-nav">
          <li class="nav-item">
            <span class="nav-link"><i class="bi bi-person-circle"></i> <%= email %></span>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="DriverDashboard.jsp"><i class="bi bi-house-door"></i> Dashboard</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Main Container -->
  <div class="container mt-5">
    <div class="card shadow-lg p-4">
      <div class="card-header">
        <h3>Manage My Vehicle <i class="bi bi-truck"></i></h3>
        <p class="mb-0">Add or update your vehicle details</p>
      </div>
      <div class="card-body">
        <% if (hasVehicle) { %>
          <div class="text-center mb-4">
            <img src="uploads/<%= rs.getString("vehicle_image") %>" alt="Vehicle Image" class="img-fluid vehicle-img mb-3">
            <h5><%= rs.getString("brand") %> - <%= rs.getString("model") %> (<%= rs.getString("ac_type") %>)</h5>
            <p>Plate Number: <%= rs.getString("plate_number") %> | Color: <%= rs.getString("color") %></p>
          </div>
          <form action="UpdateVehicleServlet" method="post" enctype="multipart/form-data">
        <% } else { %>
          <form action="AddVehicleServlet" method="post" enctype="multipart/form-data">
        <% } %>
          <div class="mb-3">
            <label class="form-label">Brand</label>
            <input type="text" name="brand" class="form-control" required value="<%= hasVehicle ? rs.getString("brand") : "" %>">
          </div>
          <div class="mb-3">
            <label class="form-label">Model</label>
            <input type="text" name="model" class="form-control" required value="<%= hasVehicle ? rs.getString("model") : "" %>">
          </div>
          <div class="mb-3">
            <label class="form-label">A/C Type</label>
            <select name="ac_type" class="form-control" required>
              <option value="A/C" <%= hasVehicle && "A/C".equals(rs.getString("ac_type")) ? "selected" : "" %>>A/C</option>
              <option value="Non A/C" <%= hasVehicle && "Non A/C".equals(rs.getString("ac_type")) ? "selected" : "" %>>Non A/C</option>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Plate Number</label>
            <input type="text" name="plate_number" class="form-control" required value="<%= hasVehicle ? rs.getString("plate_number") : "" %>">
          </div>
          <div class="mb-3">
            <label class="form-label">Color</label>
            <input type="text" name="color" class="form-control" required value="<%= hasVehicle ? rs.getString("color") : "" %>">
          </div>
          <div class="mb-3">
            <label class="form-label">Upload Vehicle Image</label>
            <input type="file" name="vehicle_image" class="form-control">
          </div>
          <button type="submit" class="btn btn-primary w-100 btn-custom"><%= hasVehicle ? "Update Vehicle" : "Add Vehicle" %></button>
        </form>
        <div class="text-center mt-3">
          <a href="DriverDashboard.jsp" class="btn btn-secondary btn-custom">Back to Dashboard</a>
        </div>
      </div>
    </div>
  </div>
  
  <!-- Bootstrap Bundle JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

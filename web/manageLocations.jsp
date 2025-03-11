<%-- 
    Document   : manageLocations
    Created on : Mar 10, 2025, 9:10:10?PM
    Author     : zainr
--%>

<%@ page session="true" import="java.sql.*,Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("Login.jsp?error=Session Expired");
        return;
    }
    
    Connection conn = DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM locations ORDER BY from_city, to_city");
    ResultSet rs = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manage Locations - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body {
      background: #f7f7f7;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .card-container {
      background: #fff;
      border-radius: 15px;
      padding: 30px;
      box-shadow: 0 4px 20px rgba(0,0,0,0.1);
    }
    .header-title {
      font-size: 2rem;
      font-weight: 600;
      margin-bottom: 10px;
    }
    .header-subtitle {
      font-size: 1rem;
      color: #6c757d;
      margin-bottom: 30px;
    }
    .form-label {
      font-weight: 500;
    }
    .btn-custom {
      border-radius: 0.375rem;
      transition: transform 0.3s;
    }
    .btn-custom:hover {
      transform: translateY(-2px);
    }
    .table thead {
      background: #343a40;
      color: #fff;
    }
    .table-striped tbody tr:nth-of-type(odd) {
      background-color: #f8f9fa;
    }
    .table-hover tbody tr:hover {
      background-color: #e9ecef;
    }
  </style>
</head>
<body>
  <div class="container my-5">
    <div class="card card-container">
      <div class="text-center mb-4">
        <h2 class="header-title"><i class="bi bi-geo-alt-fill"></i> Manage Locations</h2>
        <p class="header-subtitle">Add, update, or delete locations & distances.</p>
      </div>
      
      <!-- Add New Location Form -->
      <form action="AddLocationServlet" method="post" class="mb-4">
        <div class="row g-3">
          <div class="col-md-4">
            <label class="form-label">From City:</label>
            <input type="text" name="from_city" class="form-control" placeholder="Enter departure city" required>
          </div>
          <div class="col-md-4">
            <label class="form-label">To City:</label>
            <input type="text" name="to_city" class="form-control" placeholder="Enter destination city" required>
          </div>
          <div class="col-md-4">
            <label class="form-label">Distance (km):</label>
            <input type="number" step="0.01" name="distance_km" class="form-control" placeholder="Enter distance" required>
          </div>
        </div>
        <button type="submit" class="btn btn-primary btn-custom mt-4 w-100">
          <i class="bi bi-plus-circle"></i> Add Location
        </button>
      </form>
      
      <!-- Locations Table -->
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>From City</th>
              <th>To City</th>
              <th>Distance (km)</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <% while (rs.next()) { %>
              <tr>
                <td><%= rs.getString("from_city") %></td>
                <td><%= rs.getString("to_city") %></td>
                <td><%= rs.getDouble("distance_km") %> km</td>
                <td>
                  <form action="DeleteLocationServlet" method="post" class="d-inline">
                    <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                    <button type="submit" class="btn btn-sm btn-danger btn-custom">
                      <i class="bi bi-trash"></i> Delete
                    </button>
                  </form>
                </td>
              </tr>
            <% } %>
          </tbody>
        </table>
      </div>
      
      <div class="text-center mt-4">
        <a href="AdminDashboard.jsp" class="btn btn-secondary btn-custom">
          <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
      </div>
    </div>
  </div>
  
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

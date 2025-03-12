<%-- 
    Document   : manageCabPricing
    Created on : Mar 10, 2025, 9:09:09?PM
    Author     : zainr
--%>

<%@ page session="true" import="java.sql.*, Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("Login.jsp?error=Session Expired");
        return;
    }

    Connection conn = DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cab_pricing ORDER BY vehicle_type");
    ResultSet rs = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manage Cab Pricing - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body {
      background: #f4f6f8;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .card-container {
      border-radius: 15px;
      box-shadow: 0 4px 20px rgba(0,0,0,0.1);
      background: #fff;
      padding: 30px;
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
    /* Form Styling */
    .pricing-form label {
      font-weight: 500;
    }
    .pricing-form input {
      border-radius: 0.375rem;
    }
    /* Table Styling */
    .table thead th {
      background: #343a40;
      color: #fff;
    }
    .table-hover tbody tr:hover {
      background-color: #f1f3f5;
    }
    .btn-custom {
      border-radius: 0.375rem;
      transition: transform 0.3s ease;
    }
    .btn-custom:hover {
      transform: translateY(-2px);
    }
  </style>
</head>
<body>
  <div class="container my-5">
    <div class="card card-container">
      <div class="text-center mb-4">
        <h2 class="header-title"><i class="bi bi-currency-dollar"></i> Manage Cab Pricing</h2>
        <p class="header-subtitle">Add, update, or delete pricing details for your cabs.</p>
      </div>
      <!-- Add New Pricing Form -->
      <form action="AddCabPricingServlet" method="post" class="pricing-form mb-5">
        <div class="row g-3">
          <div class="col-md-3">
            <label class="form-label">Vehicle Type</label>
            <input type="text" name="vehicle_type" class="form-control" placeholder="e.g., Sedan" required>
          </div>
          <div class="col-md-3">
            <label class="form-label">Base Fare (Rs.)</label>
            <input type="number" step="0.01" name="base_fare" class="form-control" placeholder="e.g., 50.00" required>
          </div>
          <div class="col-md-3">
            <label class="form-label">Per Km Rate (Rs.)</label>
            <input type="number" step="0.01" name="per_km_rate" class="form-control" placeholder="e.g., 10.00" required>
          </div>
          <div class="col-md-3">
            <label class="form-label">After 2km Rate (Rs.)</label>
            <input type="number" step="0.01" name="after_2km_rate" class="form-control" placeholder="e.g., 8.00" required>
          </div>
        </div>
        <button type="submit" class="btn btn-primary btn-custom mt-4 w-100">
          <i class="bi bi-plus-circle"></i> Add Pricing
        </button>
      </form>

      <!-- Pricing Table -->
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>Vehicle Type</th>
              <th>Base Fare (Rs.)</th>
              <th>Per Km Rate (Rs.)</th>
              <th>After 2km Rate (Rs.)</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <% while (rs.next()) { %>
              <tr>
                <td><%= rs.getString("vehicle_type") %></td>
                <td>Rs. <%= rs.getDouble("base_fare") %></td>
                <td>Rs. <%= rs.getDouble("per_km_rate") %></td>
                <td>Rs. <%= rs.getDouble("after_2km_rate") %></td>
                <td>
                  <form action="DeleteCabPricingServlet" method="post" class="d-inline">
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

  <!-- Bootstrap Bundle JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

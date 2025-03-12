<%-- 
    Document   : DriverProfile
    Created on : Feb 21, 2025, 6:01:23?PM
    Author     : zainr
--%>

<%@ page session="true" import="java.sql.*,Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    if (email == null || !role.equalsIgnoreCase("Driver")) {
        response.sendRedirect("Login.jsp?error=Unauthorized Access");
        return;
    }

    Connection conn = DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement(
        "SELECT first_name, last_name, mobile, address, profile_image FROM users WHERE email = ?"
    );
    stmt.setString(1, email);
    ResultSet rs = stmt.executeQuery();
    boolean hasProfile = rs.next();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Driver Profile - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <style>
    body {
      background-color: #f8f9fa;
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
    /* Card and Profile Styling */
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
    .profile-img {
      width: 150px;
      height: 150px;
      object-fit: cover;
      border: 5px solid #fff;
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
    <div class="card shadow-lg">
      <div class="card-header">
        <h3>Manage My Profile <i class="bi bi-pencil-square"></i></h3>
        <p class="mb-0">Update your personal details</p>
      </div>
      <div class="card-body">
        <% if (hasProfile) { %>
          <div class="text-center mb-4">
            <img src="uploads/<%= rs.getString("profile_image") %>" alt="Profile Picture" class="rounded-circle profile-img mb-3">
            <h5><%= rs.getString("first_name") %> <%= rs.getString("last_name") %></h5>
            <p><strong>Email:</strong> <%= email %></p>
            <p><strong>Mobile:</strong> <%= rs.getString("mobile") %></p>
            <p><strong>Address:</strong> <%= rs.getString("address") != null ? rs.getString("address") : "Not Provided" %></p>
          </div>
        <% } %>
        <form action="UpdateProfileServlet" method="post" enctype="multipart/form-data">
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">First Name</label>
              <input type="text" name="first_name" class="form-control" required value="<%= hasProfile ? rs.getString("first_name") : "" %>">
            </div>
            <div class="col-md-6">
              <label class="form-label">Last Name</label>
              <input type="text" name="last_name" class="form-control" required value="<%= hasProfile ? rs.getString("last_name") : "" %>">
            </div>
          </div>
          <div class="mb-3 mt-3">
            <label class="form-label">Mobile</label>
            <input type="text" name="mobile" class="form-control" required value="<%= hasProfile ? rs.getString("mobile") : "" %>">
          </div>
          <div class="mb-3">
            <label class="form-label">Address</label>
            <input type="text" name="address" class="form-control" value="<%= hasProfile ? rs.getString("address") : "" %>">
          </div>
          <div class="mb-3">
            <label class="form-label">Upload Profile Picture</label>
            <input type="file" name="profile_image" class="form-control">
          </div>
          <button type="submit" class="btn btn-primary w-100 btn-custom">Update Profile</button>
        </form>
        <div class="text-center mt-3">
          <a href="DriverDashboard.jsp" class="btn btn-secondary btn-custom">
            <i class="bi bi-arrow-left-circle"></i> Back to Dashboard
          </a>
        </div>
      </div>
    </div>
  </div>
  
  <!-- Bootstrap Bundle JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

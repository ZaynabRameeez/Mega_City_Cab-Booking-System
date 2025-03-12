<%-- 
    Document   : manageDriverRequests
    Created on : Mar 10, 2025, 9:09:36?PM
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
    PreparedStatement stmt = conn.prepareStatement(
        "SELECT dr.id, u.first_name, u.last_name, dr.mobile, dr.vehicle_type, dr.vehicle_model, dr.vehicle_plate, dr.status " +
        "FROM driver_requests dr JOIN users u ON dr.user_id = u.id ORDER BY dr.created_at DESC"
    );
    ResultSet rs = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manage Driver Requests - Mega City Cab</title>
  <!-- Bootstrap CSS and Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  <style>
    body {
      background: #f4f6f9;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .card {
      border: none;
      border-radius: 10px;
    }
    .card-header {
      background: #007bff;
      color: #fff;
      border-bottom: none;
      border-radius: 10px 10px 0 0;
      padding: 1.5rem;
      text-align: center;
    }
    .card-header h2 {
      margin-bottom: 0;
      font-size: 1.75rem;
    }
    .card-header small {
      font-size: 0.9rem;
    }
    .table th, .table td {
      vertical-align: middle;
    }
    .btn-action {
      min-width: 90px;
    }
    .badge-custom {
      font-size: 0.9rem;
      padding: 0.5em 0.75em;
    }
  </style>
</head>
<body>
  <div class="container my-5">
    <div class="card shadow">
      <div class="card-header">
        <h2><i class="bi bi-truck"></i> Manage Driver Requests</h2>
        <small>Approve or reject user requests to become drivers</small>
      </div>
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-hover table-striped mb-0">
            <thead class="table-light">
              <tr>
                <th>Name</th>
                <th>Mobile</th>
                <th>Vehicle Type</th>
                <th>Model</th>
                <th>Plate</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <% while (rs.next()) { 
                   String status = rs.getString("status");
              %>
              <tr>
                <td><%= rs.getString("first_name") %> <%= rs.getString("last_name") %></td>
                <td><%= rs.getString("mobile") %></td>
                <td><%= rs.getString("vehicle_type") %></td>
                <td><%= rs.getString("vehicle_model") %></td>
                <td><%= rs.getString("vehicle_plate") %></td>
                <td>
                  <span class="badge badge-custom <%= status.equals("Pending") ? "bg-warning text-dark" : (status.equals("Approved") ? "bg-success" : "bg-danger") %>">
                    <%= status %>
                  </span>
                </td>
                <td>
                  <% if (status.equals("Pending")) { %>
                    <form action="ApproveDriverRequestServlet" method="post" class="d-inline">
                      <input type="hidden" name="requestId" value="<%= rs.getInt("id") %>">
                      <button type="submit" class="btn btn-sm btn-success btn-action">
                        <i class="bi bi-check-circle"></i> Approve
                      </button>
                    </form>
                    <form action="RejectDriverRequestServlet" method="post" class="d-inline ms-2">
                      <input type="hidden" name="requestId" value="<%= rs.getInt("id") %>">
                      <button type="submit" class="btn btn-sm btn-danger btn-action">
                        <i class="bi bi-x-circle"></i> Reject
                      </button>
                    </form>
                  <% } %>
                </td>
              </tr>
              <% } %>
            </tbody>
          </table>
        </div>
        <div class="mt-4 text-center">
          <a href="AdminDashboard.jsp" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
          </a>
        </div>
      </div>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

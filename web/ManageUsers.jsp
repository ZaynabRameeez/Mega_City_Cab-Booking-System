<%-- 
    Document   : ManageUsers
    Created on : Mar 2, 2025, 7:33:32?PM
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

    Connection conn =DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement("SELECT id, first_name, last_name, email, mobile, role FROM users");
    ResultSet rs = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manage Users - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body {
      background: #f5f7fa;
      color: #333;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .container {
      background: #fff;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
      margin-top: 40px;
    }
    .btn-custom {
      transition: 0.3s;
    }
    .btn-custom:hover {
      transform: translateY(-3px);
      box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    }
    .search-bar {
      border-radius: 50px;
      padding: 10px 20px;
      border: 1px solid #ccc;
      margin-bottom: 20px;
    }
    .table thead {
      background-color: #e9ecef;
    }
    .table td, .table th {
      vertical-align: middle;
    }
  </style>
  <script>
    function searchUsers() {
      let input = document.getElementById("searchInput").value.toLowerCase();
      let rows = document.querySelectorAll("#userTable tbody tr");
      rows.forEach(row => {
          let name = row.cells[1].textContent.toLowerCase();
          let email = row.cells[2].textContent.toLowerCase();
          row.style.display = (name.includes(input) || email.includes(input)) ? "" : "none";
      });
    }
    function confirmDelete(userId) {
      return confirm("Are you sure you want to delete User ID " + userId + "?");
    }
  </script>
</head>
<body>
  <div class="container">
    <div class="text-center mb-4">
      <h2><i class="bi bi-people-fill"></i> Manage Users</h2>
      <p class="text-muted">Add, Edit, or Delete Users</p>
    </div>
    <!-- Search Bar -->
    <input type="text" id="searchInput" class="form-control search-bar" onkeyup="searchUsers()" placeholder="Search by Name or Email...">
    <!-- Add User Button -->
    <div class="d-flex justify-content-end mb-3">
      <a href="addUser.jsp" class="btn btn-success btn-custom">
        <i class="bi bi-plus-lg"></i> Add New User
      </a>
    </div>
    <!-- Users Table -->
    <div class="table-responsive">
      <table class="table table-striped" id="userTable">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Mobile</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <% while (rs.next()) { %>
            <tr>
              <td><%= rs.getInt("id") %></td>
              <td><%= rs.getString("first_name") %> <%= rs.getString("last_name") %></td>
              <td><%= rs.getString("email") %></td>
              <td><%= rs.getString("mobile") %></td>
              <td>
                <form action="UpdateUserRoleServlet" method="post">
                  <input type="hidden" name="userId" value="<%= rs.getInt("id") %>">
                  <select name="role" class="form-select form-select-sm" onchange="this.form.submit()">
                    <option value="Admin" <%= rs.getString("role").equals("Admin") ? "selected" : "" %>>Admin</option>
                    <option value="Driver" <%= rs.getString("role").equals("Driver") ? "selected" : "" %>>Driver</option>
                    <option value="Customer" <%= rs.getString("role").equals("Customer") ? "selected" : "" %>>Customer</option>
                  </select>
                </form>
              </td>
              <td>
                <a href="editUser.jsp?id=<%= rs.getInt("id") %>" class="btn btn-warning btn-sm btn-custom">
                  <i class="bi bi-pencil"></i> Edit
                </a>
                <a href="DeleteUserServlet?id=<%= rs.getInt("id") %>" class="btn btn-danger btn-sm btn-custom" onclick="return confirmDelete(<%= rs.getInt("id") %>);">
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
        <i class="bi bi-arrow-left"></i> Back to Dashboard
      </a>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

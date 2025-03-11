<%-- 
    Document   : EditUser
    Created on : Mar 2, 2025, 7:45:41?PM
    Author     : zainr
--%>
<%@ page session="true" import="java.sql.*, Utils.DBConfig" %>
<%
    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("Login.jsp?error=Session Expired");
        return;
    }

    int userId = Integer.parseInt(request.getParameter("id"));
    Connection conn = DBConfig.getConnection();
    PreparedStatement stmt = conn.prepareStatement("SELECT first_name, last_name, email, mobile, role FROM users WHERE id = ?");
    stmt.setInt(1, userId);
    ResultSet rs = stmt.executeQuery();

    if (!rs.next()) {
        response.sendRedirect("ManageUsers.jsp?error=User Not Found");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card shadow-lg p-4">
            <h2>Edit User ?</h2>
            <p>Modify user details and update their role.</p>

            <!-- Edit User Form -->
            <form action="UpdateUserServlet" method="post">
                <input type="hidden" name="userId" value="<%= userId %>">

                <div class="mb-3">
                    <label class="form-label">First Name:</label>
                    <input type="text" name="first_name" class="form-control" value="<%= rs.getString("first_name") %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Last Name:</label>
                    <input type="text" name="last_name" class="form-control" value="<%= rs.getString("last_name") %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" name="email" class="form-control" value="<%= rs.getString("email") %>" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mobile:</label>
                    <input type="text" name="mobile" class="form-control" value="<%= rs.getString("mobile") %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Role:</label>
                    <select name="role" class="form-control">
                        <option value="Admin" <%= rs.getString("role").equals("Admin") ? "selected" : "" %>>Admin</option>
                        <option value="Driver" <%= rs.getString("role").equals("Driver") ? "selected" : "" %>>Driver</option>
                        <option value="Customer" <%= rs.getString("role").equals("Customer") ? "selected" : "" %>>Customer</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100">Update User</button>
            </form>

            <div class="text-center mt-3">
                <a href="ManageUsers.jsp" class="btn btn-secondary">? Back to Manage Users</a>
            </div>
        </div>
    </div>
</body>
</html>

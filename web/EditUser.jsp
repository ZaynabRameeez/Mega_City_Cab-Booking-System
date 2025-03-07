<%-- 
    Document   : EditUser
    Created on : Mar 2, 2025, 7:45:41 PM
    Author     : zainr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.User" %>

<%
    User user = (User) request.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">
    <h2>Edit User</h2>

    <form action="UserServlet" method="POST">
        <input type="hidden" name="id" value="<%= user.getId() %>">

        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" class="form-control" value="<%= user.getUsername() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" value="<%= user.getEmail() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Phone</label>
            <input type="text" name="phone" class="form-control" value="<%= user.getPhone() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Role</label>
            <select name="role" class="form-control">
                <option value="User" <%= user.getRole().equals("User") ? "selected" : "" %>>User</option>
                <option value="Driver" <%= user.getRole().equals("Driver") ? "selected" : "" %>>Driver</option>
                <option value="Admin" <%= user.getRole().equals("Admin") ? "selected" : "" %>>Admin</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Update</button>
        <a href="ManageUsers.jsp" class="btn btn-secondary">Cancel</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

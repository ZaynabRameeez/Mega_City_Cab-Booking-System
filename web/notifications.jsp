<%-- 
    Document   : notifications
    Created on : Mar 10, 2025, 9:11:17 PM
    Author     : zainr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.List" %>
<%
    List<String> notifications = (List<String>) request.getAttribute("notifications");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card shadow-lg p-4">
            <h2>Notifications ??</h2>
            <% if (notifications == null || notifications.isEmpty()) { %>
                <div class="alert alert-info">No new notifications.</div>
            <% } else { %>
                <ul class="list-group">
                    <% for (String notification : notifications) { %>
                        <li class="list-group-item"><%= notification %></li>
                    <% } %>
                </ul>
            <% } %>
            <div class="text-center mt-4">
                <a href="dashboard.jsp" class="btn btn-secondary">? Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>
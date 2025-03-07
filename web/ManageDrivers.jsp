<%-- 
    Document   : ManageDrivers
    Created on : Mar 2, 2025, 3:54:16 PM
    Author     : zainr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.Driver"%>
<%@page import="DAO.AdminDAO"%>

<%
    List<Driver> pendingDrivers = AdminDAO.getPendingDrivers();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Drivers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Pending Driver Requests</h2>
        
        <% if (request.getSession().getAttribute("message") != null) { %>
            <div class="alert alert-success">
                <%= request.getSession().getAttribute("message") %>
            </div>
            <% request.getSession().removeAttribute("message"); %>
        <% } %>
        
        <% if (request.getSession().getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getSession().getAttribute("error") %>
            </div>
            <% request.getSession().removeAttribute("error"); %>
        <% } %>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Driver ID</th>
                    <th>User ID</th>
                    <th>License Number</th>
                    <th>Vehicle Number</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% for (Driver d : pendingDrivers) { %>
                    <tr>
                        <td><%= d.getDriverId() %></td>
                        <td><%= d.getId() %></td>
                        <td><%= d.getLicenseNumber() %></td>
                        <td><%= d.getVehicleNumber() %></td>
                        <td>
                            <form action="CRUDDriverServlet" method="post">
                                <input type="hidden" name="driverId" value="<%= d.getDriverId() %>">
                                <input type="hidden" name="userId" value="<%= d.getId() %>">
                                <button type="submit" name="action" value="approve" class="btn btn-success btn-sm">Approve</button>
                                <button type="submit" name="action" value="reject" class="btn btn-danger btn-sm">Reject</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>

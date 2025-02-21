<%@page import="java.util.List"%>
<%@page import="Model.User"%>
<%
    List<User> users = (List<User>) request.getAttribute("users");
    int totalUsers = (request.getAttribute("totalUsers") != null) ? (int) request.getAttribute("totalUsers") : 0;
    int totalDrivers = (request.getAttribute("totalDrivers") != null) ? (int) request.getAttribute("totalDrivers") : 0;
    int pendingApprovals = (request.getAttribute("pendingApprovals") != null) ? (int) request.getAttribute("pendingApprovals") : 0;

    int activeUsers = 0, inactiveUsers = 0;
    if (users != null) {
        for (User user : users) {
            if (user.isActive()) {
                activeUsers++;
            } else {
                inactiveUsers++;
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center mb-4">Admin Dashboard</h2>
        
        <!-- Dashboard Cards -->
        <div class="row text-center">
            <div class="col-md-4">
                <div class="card p-3">
                    <h4>Total Users</h4>
                    <p class="fw-bold"><%= totalUsers %></p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card p-3">
                    <h4>Total Drivers</h4>
                    <p class="fw-bold"><%= totalDrivers %></p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card p-3">
                    <h4>Pending Approvals</h4>
                    <p class="fw-bold text-warning"><%= pendingApprovals %></p>
                </div>
            </div>
        </div>
        
        <!-- Chart -->
        <div class="mt-4">
            <canvas id="userChart"></canvas>
        </div>
        
        <!-- User Table -->
        <div class="table-responsive mt-4">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (users != null && !users.isEmpty()) { %>
                        <% for (User user : users) { %>
                            <tr>
                                <td><%= user.getId() %></td>
                                <td><%= user.getUsername() %></td>
                                <td><%= user.getEmail() %></td>
                                <td><%= user.getRole() %></td>
                                <td class="<%= user.isActive() ? "text-success" : "text-danger" %>">
                                    <%= user.isActive() ? "Active" : "Inactive" %>
                                </td>
                                <td>
                                    <button class="btn btn-primary btn-sm">Edit</button>
                                    <button class="btn btn-danger btn-sm">Delete</button>
                                </td>
                            </tr>
                        <% } %>
                    <% } else { %>
                        <tr>
                            <td colspan="6" class="text-center">No users found.</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        // Chart.js Implementation with Dynamic Data
        const ctx = document.getElementById('userChart');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Active Users', 'Inactive Users'],
                datasets: [{
                    label: 'User Status',
                    data: [<%= activeUsers %>, <%= inactiveUsers %>],
                    backgroundColor: ['#28a745', '#dc3545'],
                }]
            }
        });
    </script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

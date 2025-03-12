<%-- 
    Document   : UserDashboard
    Created on : Feb 13, 2025, 10:18:33?AM
    Author     : zainr
--%>

<%@ page session="true" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    if (email == null || !role.equalsIgnoreCase("Customer")) {
        response.sendRedirect("Login.jsp?error=Unauthorized Access");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Dashboard - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <style>
    body {
      background: #f8f9fa;
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
    }
    /* Top Navigation Bar */
    .navbar-custom {
      background-color: #343a40;
    }
    .navbar-custom .navbar-brand,
    .navbar-custom .nav-link {
      color: #fff;
    }
    .navbar-custom .nav-link:hover {
      color: #ffc107;
    }
    /* Sidebar */
    .sidebar {
      background: #fff;
      min-height: 100vh;
      box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
    }
    .sidebar .nav-link {
      color: #495057;
      padding: 10px 20px;
      transition: background 0.3s, color 0.3s;
    }
    .sidebar .nav-link:hover,
    .sidebar .nav-link.active {
      background: #e9ecef;
      color: #212529;
      font-weight: 600;
    }
    /* Dashboard Cards */
    .card-hover {
      border: none;
      border-radius: 15px;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .card-hover:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 20px rgba(0,0,0,0.1);
    }
    .card-hover .card-body {
      padding: 2rem;
    }
    .card-hover i {
      font-size: 3rem;
      margin-bottom: 1rem;
    }
    /* Footer */
    footer {
      background: #343a40;
      color: #ccc;
      padding: 1rem 0;
      text-align: center;
    }
  </style>
  <script>
    function confirmLogout() {
      return confirm("Are you sure you want to logout?");
    }
  </script>
</head>
<body>
  <!-- Navigation Bar -->
  <nav class="navbar navbar-expand-lg navbar-custom shadow-sm">
    <div class="container">
      <a class="navbar-brand" href="#">Mega City Cab</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
              aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"><i class="bi bi-list" style="color:#fff;"></i></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav align-items-center">
          <li class="nav-item me-3">
            <span class="nav-link">Hello, <strong><%= email %></strong></span>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="NotificationServlet">
              <i class="bi bi-bell"></i> Notifications
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="requestDriver.jsp">
              <i class="bi bi-person-plus"></i> Become a Driver
            </a>
          </li>
          <li class="nav-item">
            <a class="btn btn-outline-light ms-3" href="LogoutServlet" onclick="return confirmLogout();">
              <i class="bi bi-box-arrow-right"></i> Logout
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Main Content Area -->
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar Navigation -->
      <nav class="col-md-3 col-lg-2 d-md-block sidebar py-4">
        <div class="position-sticky">
          <ul class="nav flex-column">
            <li class="nav-item mb-2">
              <a class="nav-link active" href="UsersDashboard.jsp">
                <i class="bi bi-speedometer2"></i> Dashboard
              </a>
            </li>
            <li class="nav-item mb-2">
              <a class="nav-link" href="BookRide.jsp">
                <i class="bi bi-car-front"></i> Book a Ride
              </a>
            </li>
            <li class="nav-item mb-2">
              <a class="nav-link" href="myBookings.jsp">
                <i class="bi bi-calendar-check"></i> My Bookings
              </a>
            </li>
            <li class="nav-item mb-2">
              <a class="nav-link" href="UserProfile.jsp">
                <i class="bi bi-person-circle"></i> My Profile
              </a>
            </li>
          </ul>
        </div>
      </nav>

      <!-- Dashboard Main Content -->
      <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
        <div class="card shadow-sm p-4 mb-4">
          <h3 class="mb-3 text-center">Welcome, <%= email %>!</h3>
          <p class="text-center text-muted">Manage your bookings and update your profile with ease.</p>
          <div class="row g-4 mt-3">
            <!-- Book a Ride Card -->
            <div class="col-md-4">
              <div class="card card-hover text-center shadow-sm">
                <div class="card-body">
                  <i class="bi bi-car-front text-primary"></i>
                  <h5 class="card-title mt-3">Book a Ride</h5>
                  <p class="card-text text-muted">Schedule your next trip quickly.</p>
                  <a href="BookRide.jsp" class="btn btn-primary">Book Now</a>
                </div>
              </div>
            </div>
            <!-- My Bookings Card -->
            <div class="col-md-4">
              <div class="card card-hover text-center shadow-sm">
                <div class="card-body">
                  <i class="bi bi-calendar-check text-success"></i>
                  <h5 class="card-title mt-3">My Bookings</h5>
                  <p class="card-text text-muted">View upcoming and past rides.</p>
                  <a href="myBookings.jsp" class="btn btn-success">View Bookings</a>
                </div>
              </div>
            </div>
            <!-- My Profile Card -->
            <div class="col-md-4">
              <div class="card card-hover text-center shadow-sm">
                <div class="card-body">
                  <i class="bi bi-person-circle text-warning"></i>
                  <h5 class="card-title mt-3">My Profile</h5>
                  <p class="card-text text-muted">Update your personal information.</p>
                  <a href="UserProfile.jsp" class="btn btn-warning text-white">View Profile</a>
                </div>
              </div>
            </div>
          </div>
          <!-- Extra Logout Button -->
          <div class="text-center mt-4">
            <a href="LogoutServlet" class="btn btn-outline-danger" onclick="return confirmLogout();">
              <i class="bi bi-box-arrow-right"></i> Logout
            </a>
          </div>
        </div>
      </main>
    </div>
  </div>

  <!-- Footer -->
  <footer class="bg-dark text-white text-center py-3">
    <div class="container">
      <small>&copy; 2025 Mega City Cab. All Rights Reserved.</small>
    </div>
  </footer>

  <!-- Bootstrap Bundle JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%-- 
    Document   : BookRide
    Created on : Feb 18, 2025, 1:35:40?PM
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
  <title>Book a Ride - Mega City Cab</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <style>
    body {
      background: #f8f9fa;
    }
    .card {
      border: none;
      border-radius: 1rem;
    }
    .card-header {
      background: #007bff;
      color: #fff;
      border-top-left-radius: 1rem;
      border-top-right-radius: 1rem;
      text-align: center;
      padding: 1rem;
    }
    .form-label {
      font-weight: 500;
    }
    .form-control {
      border-radius: 0.5rem;
    }
    .btn-custom {
      border-radius: 0.5rem;
    }
  </style>
  <script>
    function calculateFare() {
      let pickup = document.getElementById("pickup").value;
      let dropoff = document.getElementById("dropoff").value;
      let vehicleType = document.getElementById("vehicleType").value;

      let distances = {
        "Colombo-Kandy": 115,
        "Colombo-Galle": 126,
        "Colombo-Jaffna": 398,
        "Kandy-Galle": 200,
        "Galle-Jaffna": 470
      };

      let rates = {
        "Bike": { base: 50, perKm: 30, after2Km: 25 },
        "Threewheel": { base: 60, perKm: 40, after2Km: 35 },
        "MiniCar": { base: 100, perKm: 50, after2Km: 45 },
        "Car": { base: 150, perKm: 60, after2Km: 55 },
        "Van": { base: 200, perKm: 80, after2Km: 75 }
      };

      let routeKey = pickup + "-" + dropoff;
      let distance = distances[routeKey];

      if (!distance) {
        document.getElementById("fareEstimate").innerHTML = "<i class='bi bi-exclamation-circle'></i> Distance not available.";
        return;
      }

      let vehicleRate = rates[vehicleType];
      let estimatedFare = (distance <= 2) 
        ? vehicleRate.base + (distance * vehicleRate.perKm)
        : vehicleRate.base + (2 * vehicleRate.perKm) + ((distance - 2) * vehicleRate.after2Km);

    document.getElementById("fareEstimate").innerHTML = "Estimated Fare: Rs. " + estimatedFare.toFixed(2);
      document.getElementById("calculatedPrice").value = estimatedFare.toFixed(2);
    }
  </script>
</head>
<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow">
    <div class="container">
      <a class="navbar-brand" href="#">Mega City Cab</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <span class="nav-link">Hello, <strong><%= email %></strong></span>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="UsersDashboard.jsp"><i class="bi bi-house"></i> Dashboard</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Booking Form -->
  <div class="container my-5">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6">
        <div class="card shadow-lg">
          <div class="card-header">
            <h3>Book a Ride <i class="bi bi-car-front"></i></h3>
          </div>
          <div class="card-body">
            <form action="BookingServlet" method="post">
              <div class="mb-3">
                <label for="pickup" class="form-label">Pickup Location</label>
                <select id="pickup" name="pickup" class="form-control" required onchange="calculateFare()">
                  <option value="">Select Pickup Location</option>
                  <option value="Colombo">Colombo</option>
                  <option value="Kandy">Kandy</option>
                  <option value="Galle">Galle</option>
                  <option value="Jaffna">Jaffna</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="dropoff" class="form-label">Drop-off Location</label>
                <select id="dropoff" name="dropoff" class="form-control" required onchange="calculateFare()">
                  <option value="">Select Drop-off Location</option>
                  <option value="Colombo">Colombo</option>
                  <option value="Kandy">Kandy</option>
                  <option value="Galle">Galle</option>
                  <option value="Jaffna">Jaffna</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="vehicleType" class="form-label">Select Vehicle Type</label>
                <select id="vehicleType" name="vehicleType" class="form-control" required onchange="calculateFare()">
                  <option value="Bike"><i class="bi bi-bicycle"></i> Bike</option>
                  <option value="Threewheel"><i class="bi bi-truck"></i> Threewheel</option>
                  <option value="MiniCar"><i class="bi bi-car-front"></i> Mini Car</option>
                  <option value="Car"><i class="bi bi-car-front-fill"></i> Car</option>
                  <option value="Van"><i class="bi bi-truck-front"></i> Van</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="rideDate" class="form-label">Select Ride Date</label>
                <input type="date" id="rideDate" name="rideDate" class="form-control" required>
              </div>

              <div class="mb-3">
                <label for="rideTime" class="form-label">Select Ride Time</label>
                <input type="time" id="rideTime" name="rideTime" class="form-control" required>
              </div>

              <div class="mb-3 text-center">
                <p id="fareEstimate" class="text-success fw-bold"></p>
                <input type="hidden" id="calculatedPrice" name="calculatedPrice">
              </div>
                
             <div class="mb-3">
              <label for="paymentMethod" class="form-label">Payment Method</label>
              <select id="paymentMethod" name="paymentMethod" class="form-control" required>
              <option value="Cash">Cash</option>
              <option value="Card">Card</option>
            
           </select>
            </div>
              <button type="submit" class="btn btn-primary w-100 btn-custom">Book Now</button>
            </form>
            <div class="text-center mt-3">
              <a href="UsersDashboard.jsp" class="text-decoration-none">Back to Dashboard</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap Bundle JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>















<%-- 
    Document   : BookRide
    Created on : Feb 18, 2025, 1:35:40 PM
    Author     : zainr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book a Ride</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <style>
        body {
            transition: background 0.5s, color 0.5s;
        }
        .container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .taxi-icon {
            font-size: 50px;
            animation: bounce 1.5s infinite;
        }
        @keyframes bounce {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-10px); }
        }
        @media (max-width: 768px) {
            .container {
                width: 90%;
            }
        }
    </style>
</head>
<body>
    <div class="container text-center">
        <h2 class="mb-4">Book Your Ride</h2>
        <i class="fas fa-taxi taxi-icon mb-3"></i>
        <form action="BookRideServlet" method="post">
            <div class="mb-3">
                <input type="text" name="pickup" class="form-control" placeholder="Enter Pickup Location" required>
            </div>
            <div class="mb-3">
                <input type="text" name="dropoff" class="form-control" placeholder="Enter Drop-off Location" required>
            </div>
            <div class="mb-3">
                <select name="vehicle" class="form-select" required>
                    <option value="">Select Vehicle Type</option>
                    <option value="Standard">Standard</option>
                    <option value="Luxury">Luxury</option>
                    <option value="SUV">SUV</option>
                </select>
            </div>
            <div class="mb-3">
                <select name="payment" class="form-select" required>
                    <option value="">Select Payment Method</option>
                    <option value="Card">Card</option>
                    <option value="Cash">Cash</option>
                    <option value="Wallet">Wallet</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary w-100">Book Now</button>
        </form>
    </div>
</body>
</html>

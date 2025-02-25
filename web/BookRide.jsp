<%-- 
    Document   : BookRide
    Created on : Feb 18, 2025, 1:35:40 PM
    Author     : zainr
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Book a Ride</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="fareCalculator.js"></script> <!-- Include JS for fare calculation -->
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Book a Ride</h2>
        
        <!-- Step 1: Select Pickup & Dropoff -->
        <div id="step1">
            <div class="mb-3">
                <label class="form-label">Pickup Location:</label>
                <input type="text" class="form-control" id="pickup" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Dropoff Location:</label>
                <input type="text" class="form-control" id="dropoff" required>
            </div>
            <button id="nextStep" class="btn btn-primary w-100">Next</button>
        </div>

        <!-- Step 2 & 3 Combined: Select Vehicle & Payment -->
        <div id="step2_3" class="d-none">
            <h5>Select a Vehicle</h5>
            <div class="row">
                <div class="col-md-3 vehicle-card" data-id="1">
                    <img src="Img/CAR.png" class="img-fluid" alt="Car">
                    <p>Car - <span class="fare">Rs. 0.00</span></p>
                </div>
                <div class="col-md-3 vehicle-card" data-id="2">
                    <img src="van.png" class="img-fluid" alt="Van">
                    <p>Van - <span class="fare">Rs. 0.00</span></p>
                </div>
                <div class="col-md-3 vehicle-card" data-id="3">
                    <img src="bike.png" class="img-fluid" alt="Bike">
                    <p>Bike - <span class="fare">Rs. 0.00</span></p>
                </div>
                <div class="col-md-3 vehicle-card" data-id="4">
                    <img src="auto.png" class="img-fluid" alt="Auto">
                    <p>Auto - <span class="fare">Rs. 0.00</span></p>
                </div>
            </div>

            <!-- Payment Section (Initially Hidden) -->
            <div id="paymentSection" class="mt-3 d-none">
                <label class="form-label">Payment Method:</label>
                <select class="form-control" id="paymentMethod" required>
                    <option value="Cash">Cash</option>
                    <option value="Card">Card</option>
                </select>
                <button id="confirmRide" class="btn btn-danger w-100 mt-3">Confirm Ride</button>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
    let locations = [];

    // Fetch locations from backend
    $.ajax({
        url: "GetLocationsServlet",
        method: "GET",
        dataType: "json",
        success: function(data) {
            locations = data;
            $("#pickup, #dropoff").autocomplete({ source: locations });
        },
        error: function() { console.error("Error fetching locations."); }

});


            // Step 1: Move to Vehicle Selection & Payment
            $("#nextStep").click(function() {
                if ($("#pickup").val() && $("#dropoff").val()) {
                    $("#step1").hide();
                    $("#step2_3").removeClass("d-none");
                } else {
                    alert("Please enter both pickup and dropoff locations.");
                }
            });

            // Select Vehicle & Show Payment
            $(".vehicle-card").click(function() {
                $(".vehicle-card").removeClass("border border-primary");
                $(this).addClass("border border-primary");
                $("#paymentSection").removeClass("d-none");
            });

            // Confirm Ride & Send to Driver
            $("#confirmRide").click(function() {
                alert("Ride Request Sent to Driver!");
                // Here, you can send the ride request via AJAX to backend
            });
        });
    </script>
</body>
</html>


<!--<!DOCTYPE html>
<html>
<head>
    <title>Book a Ride</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Book a Ride</h2>
        
        <form id="rideForm" action="RideServlet" method="POST">
            <input type="hidden" name="vehicleType" id="selectedVehicleType">
            
            <div class="mb-3">
                <label class="form-label">Pickup Location:</label>
                <input type="text" class="form-control" id="pickup" required>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Dropoff Location:</label>
                <input type="text" class="form-control" id="dropoff" required>
            </div>

            <div id="vehicleSelection" class="row d-none">
                <h5>Select a Vehicle</h5>
                <div class="col-md-3 vehicle-card" data-type="Car" data-price="5">
                    <img src="car.png" class="img-fluid" alt="Car">
                    <p>Car - <span class="fare">$0.00</span></p>
                </div>
                <div class="col-md-3 vehicle-card" data-type="Van" data-price="7">
                    <img src="van.png" class="img-fluid" alt="Van">
                    <p>Van - <span class="fare">$0.00</span></p>
                </div>
                <div class="col-md-3 vehicle-card" data-type="Bike" data-price="3">
                    <img src="bike.png" class="img-fluid" alt="Bike">
                    <p>Bike - <span class="fare">$0.00</span></p>
                </div>
                <div class="col-md-3 vehicle-card" data-type="Auto" data-price="4">
                    <img src="auto.png" class="img-fluid" alt="Auto">
                    <p>Auto - <span class="fare">$0.00</span></p>
                </div>
            </div>

            <div id="paymentSection" class="d-none">
                <label class="form-label">Payment Method:</label>
                <select class="form-control" name="paymentMethod" required>
                    <option value="Cash">Cash</option>
                    <option value="Card">Card</option>
                </select>
            </div>

            <button type="submit" id="bookRide" class="btn btn-primary w-100 mt-3 d-none">Request Ride</button>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            let locations = [];
            
            $.ajax({
                url: "GetLocationsServlet",
                method: "GET",
                dataType: "json",
                success: function(data) { locations = data; },
                error: function() { console.error("Error fetching locations."); }
            });

            $("#pickup, #dropoff").autocomplete({
                source: locations
            });

            $("#pickup, #dropoff").on("change", function() {
                if ($("#pickup").val() && $("#dropoff").val()) {
                    let distance = Math.floor(Math.random() * 10) + 1;
                    $(".vehicle-card").each(function() {
                        let basePrice = $(this).data("price");
                        let fare = basePrice + (distance * 1.5);
                        $(this).find(".fare").text("$" + fare.toFixed(2));
                    });
                    $("#vehicleSelection").removeClass("d-none");
                }
            });

            $(".vehicle-card").click(function() {
                $(".vehicle-card").removeClass("border border-primary");
                $(this).addClass("border border-primary");
                $("#selectedVehicleType").val($(this).data("type"));
                $("#paymentSection, #bookRide").removeClass("d-none");
            });
        });
    </script>
</body>
</html>-->


















<!--<!DOCTYPE html>
<html>
<head>
    <title>Book a Ride</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Book a Ride</h2>
        
        <form id="rideForm" action="RideServlet" method="POST">
            <input type="hidden" name="action" value="bookRide">
            
            <div class="mb-3">
                <label class="form-label">Pickup Location:</label>
                <input type="text" class="form-control" name="pickup" id="pickup" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Dropoff Location:</label>
                <input type="text" class="form-control" name="dropoff" id="dropoff" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Select Vehicle Type:</label>
                <select class="form-control" name="vehicleType" id="vehicleType" required>
                    <option value="">-- Select Vehicle --</option>
                    <option value="Car">Car</option>
                    <option value="Van">Van</option>
                    <option value="Bike">Bike</option>
                    <option value="Auto">Auto</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Estimated Fare ($):</label>
                <input type="text" class="form-control" name="fare" id="fare" readonly>
            </div>

            <div class="mb-3">
                <label class="form-label">Payment Method:</label>
                <select class="form-control" name="paymentMethod" required>
                    <option value="Cash">Cash</option>
                    <option value="Card">Card</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary w-100">Request Ride</button>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $("#vehicleType, #pickup, #dropoff").change(function() {
                let pickup = $("#pickup").val();
                let dropoff = $("#dropoff").val();
                let vehicleType = $("#vehicleType").val();

                if (pickup && dropoff && vehicleType) {
                    let fare = calculateFare(pickup, dropoff, vehicleType);
                    $("#fare").val(fare.toFixed(2));
                }
            });

            function calculateFare(pickup, dropoff, vehicleType) {
                let baseFare = { "Car": 5, "Van": 7, "Bike": 3, "Auto": 4 };
                let distance = Math.floor(Math.random() * 10) + 1; // Simulating distance
                return baseFare[vehicleType] + (distance * 1.5); // Example fare calculation
            }
        });
    </script>
</body>
</html>

-->





<!--<!DOCTYPE html>
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
        <form action="BookingServlet" method="post">
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
</html>-->

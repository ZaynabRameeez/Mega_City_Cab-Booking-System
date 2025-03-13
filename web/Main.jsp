<%-- 
    Document   : Main
    Created on : Feb 18, 2025, 5:59:58 PM
    Author     : zainr
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #f8f9fa;
        }

        /* Navbar */
        .navbar {
            transition: all 0.3s ease-in-out;
        }
        .navbar-brand {
            font-weight: bold;
        }

        /* Hero Section */
        .hero-section {
            background: url('img/Car.jpg') no-repeat center center/cover;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            position: relative;
        }
        .hero-section::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
        }
        .hero-content {
            position: relative;
            z-index: 1;
        }
        .hero-content h1 {
            font-size: 3.5rem;
            font-weight: bold;
            text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.7);
        }

        /* Features Section */
        .feature-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out, border 0.3s ease-in-out;
            border: 2px solid transparent;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        .feature-card:hover {
            transform: scale(1.05);
            border-color: #ffc107;
            box-shadow: 0 5px 25px rgba(255, 193, 7, 0.5);
        }
        .feature-card i {
            font-size: 2.5rem;
            margin-bottom: 15px;
            transition: color 0.3s ease-in-out;
        }
        .feature-card:hover i {
            color: #ffc107;
        }

        /* About Us Section */
        .about-stats {
            display: flex;
            justify-content: center;
            gap: 50px;
            margin-top: 20px;
        }
        .about-stat {
            text-align: center;
            font-size: 1.5rem;
            font-weight: bold;
        }
        .about-stat span {
            display: block;
            font-size: 2.5rem;
            color: #ffc107;
        }

        /* Footer */
        .footer {
            background: #222;
            color: white;
            padding: 20px;
            text-align: center;
        }

        /* Buttons */
        .btn-primary {
            background: #007bff;
            border: none;
            transition: background 0.3s ease-in-out;
        }
        .btn-primary:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">Mega City Cab</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="#about">About</a></li>
                    <li class="nav-item"><a class="nav-link" href="#features">Features</a></li>
                    <li class="nav-item"><a class="nav-link btn btn-primary text-white mx-2" href="Login.jsp">Login</a></li>
                    <li class="nav-item"><a class="nav-link btn btn-primary text-white" href="Registration.jsp">Register</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content">
            <h1 data-aos="fade-up">Welcome to Mega City Cab</h1>
            <p class="lead" data-aos="fade-up" data-aos-delay="300">Safe, Fast, and Reliable Rides</p>
            <a href="Registration.jsp" class="btn btn-lg btn-primary" data-aos="fade-up" data-aos-delay="500">Register</a>
        </div>
    </section>

    <!-- About Us Section -->
    <section id="about" class="container py-5" data-aos="fade-up">
        <h2 class="text-center fw-bold">About Us</h2>
        <p class="text-center">Mega City Cab Booking is a trusted platform providing top-quality cab services with ease of access and comfort.</p>
        <div class="about-stats">
            <div class="about-stat">
                <span>10K+</span>
                Happy Customers
            </div>
            <div class="about-stat">
                <span>5K+</span>
                Drivers Available
            </div>
            <div class="about-stat">
                <span>20+</span>
                Cities Covered
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="bg-light py-5">
        <div class="container" data-aos="fade-up">
            <h2 class="text-center fw-bold mb-4">Why Choose Us?</h2>
            <div class="row text-center">
                <div class="col-md-4 mb-4">
                    <div class="feature-card p-4 shadow-sm" data-aos="fade-up" data-aos-delay="200">
                        <i class="fas fa-car text-primary"></i>
                        <h3 class="fw-bold">Fast & Reliable</h3>
                        <p>Get to your destination quickly with our trusted drivers.</p>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="feature-card p-4 shadow-sm" data-aos="fade-up" data-aos-delay="400">
                        <i class="fas fa-map-marker-alt text-danger"></i>
                        <h3 class="fw-bold">Real-time Tracking</h3>
                        <p>Track your ride in real-time with our user-friendly app.</p>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="feature-card p-4 shadow-sm" data-aos="fade-up" data-aos-delay="600">
                        <i class="fas fa-wallet text-success"></i>
                        <h3 class="fw-bold">Affordable Pricing</h3>
                        <p>Enjoy competitive and transparent pricing for every ride.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2025 Mega City Cab Booking | All Rights Reserved</p>
    </footer>

    <script>
        AOS.init();
    </script>
</body>
</html>

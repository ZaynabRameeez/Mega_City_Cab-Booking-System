# Mega_City_Cab-Booking-System
MegaCityCab is a web-based cab booking system that allows users to book rides, estimate fares, and manage profiles, while drivers can handle ride requests, update their vehicle details, and receive payments. The system includes an admin panel for managing users and bookings.

 Features
✅ User & Driver Authentication (Signup/Login)
✅ Ride Booking & Fare Estimation
✅ Driver Approval & Management
✅ Payment & Notification System
✅ Admin Panel for User & Ride Management

Tech Stack
Frontend: JSP, HTML, CSS, Bootstrap
Backend: Jakarta EE (Servlets, JSP), JDBC (MySQL Connector).
Database: MySQL workbench with MySQL Connector
Server: Apache Tomcat 11.0
IDE: NetBeans

/src/java
  /Controller  → Servlets for handling HTTP requests
  /DAO         → Database interaction (CRUD operations)
  /Model       → Java classes representing data models
  /Service     → Business logic layer
  /Utils       → Helper classes (e.g., password hashing, DB connection)
/web
  /img         → Static images & assets
  /WEB-INF     → Configuration files (e.g., web.xml)
/test          → Unit tests

Set up MySQL database:

Create a new database named mega_city_cab.
Import the provided SQL script (mega_city_cab.sql).
4️⃣ Configure DBConfig.java with your database credentials.
5️⃣ Deploy the project on Apache Tomcat.

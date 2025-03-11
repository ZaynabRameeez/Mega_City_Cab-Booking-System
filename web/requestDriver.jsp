<%-- 
    Document   : requestDriver
    Created on : Mar 10, 2025, 9:11:36?PM
    Author     : zainr
--%>

<%@ page session="true" import="java.sql.*, Utils.DBConfig"%>
<%
    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("Login.jsp?error=Session Expired");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request to Be a Driver - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card shadow-lg p-4">
            <h2>Request to Become a Driver ?</h2>
            <p>Fill out the form below to request approval from the admin.</p>
            
            <form action="SubmitDriverRequestServlet" method="post">
                <div class="mb-3">
                    <label class="form-label">Full Name:</label>
                    <input type="text" name="full_name" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mobile Number:</label>
                    <input type="text" name="mobile" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Vehicle Type:</label>
                    <select name="vehicle_type" class="form-control" required>
                        <option value="Bike">?? Bike</option>
                        <option value="Three-wheeler">? Three-wheeler</option>
                        <option value="Mini Car">? Mini Car</option>
                        <option value="Car">? Car</option>
                        <option value="Van">? Van</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Vehicle Model:</label>
                    <input type="text" name="vehicle_model" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Vehicle Plate Number:</label>
                    <input type="text" name="vehicle_plate" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary w-100">Submit Request</button>
            </form>

            <div class="text-center mt-3">
                <a href="UsersDashboard.jsp" class="btn btn-secondary">? Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>

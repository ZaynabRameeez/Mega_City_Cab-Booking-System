<%-- 
    Document   : UserProfile
    Created on : Feb 28, 2025, 2:17:05 PM
    Author     : zainr
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("Login.jsp"); // Redirect to login if user is not found
        return;
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<!-- Include Navbar -->
<jsp:include page="navbar.jsp"/>

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-6 col-sm-12">
            <h2 class="text-center">User Profile</h2>

            <!-- Profile Picture -->
            <div class="text-center">
               <img src="<%= request.getContextPath() + '/' + (user.getPhoto() != null ? user.getPhoto() : "assets/no-profile.jpg") %>"
                    
     class="rounded-circle img-fluid" width="150" height="150" id="profileImg">

                
                <input type="file" name="profilePhoto" id="profilePhoto" class="form-control mt-2" onchange="previewImage(event)">

                <!-- Remove Profile Photo Button -->
                <form method="post" action="RemovePhotoServlet">
                    <input type="hidden" name="userId" value="<%= (user != null) ? user.getId() : "" %>">
                    <button type="submit" class="btn btn-danger mt-2">Remove Photo</button>
                </form>
            </div>

            <!-- Profile Update Form -->
            <form method="post" action="ProfileServlet" enctype="multipart/form-data">
                


                
                
                <div class="mb-3">
                    <label>Username:</label>
                    <input type="text" name="username" class="form-control" value="<%=  user.getUsername() %>">
                </div>

                <div class="mb-3">
                    <label>Email:</label>
                    <input type="email" name="email" class="form-control" value="<%= (user != null) ? user.getEmail() : "" %>">
                </div>

                <div class="mb-3">
                    <label>Phone:</label>
                    <input type="text" name="phone" class="form-control" value="<%= (user != null) ? user.getPhone() : "" %>">
                </div>

                <div class="mb-3">
                    <label>Address:</label>
                    <input type="text" name="address" class="form-control" value="<%= (user != null) ? user.getAddress() : "" %>">
                </div>

                <h3 class="mt-4">Change Password</h3>
                <div class="mb-3">
                    <label>Old Password:</label>
                    <input type="password" name="oldPassword" class="form-control">
                </div>
                <div class="mb-3">
                    <label>New Password:</label>
                    <input type="password" name="newPassword" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Confirm Password:</label>
                    <input type="password" name="confirmPassword" class="form-control">
                </div>

                <!-- Save Changes Button -->
                <button type="submit" class="btn btn-primary w-100">Save Changes</button>
            </form>
        </div>
    </div>
</div>



<script>
function previewImage(event) {
    var reader = new FileReader();
    reader.onload = function(){
        document.getElementById('profileImg').src = reader.result;
    }
    reader.readAsDataURL(event.target.files[0]);
}

</script>

</body>
</html>

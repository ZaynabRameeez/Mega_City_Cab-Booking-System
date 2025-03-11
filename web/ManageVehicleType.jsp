<%-- 
    Document   : ManageVehicle
    Created on : Mar 2, 2025, 3:03:16 PM
    Author     : zainr
--%>



<%@ page import="java.util.List" %>
<%@ page import="Model.VehicleType" %>
<%@ include file="Adminsidebar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Vehicle Types</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container-fluid">
    <div class="row">
        <!-- Admin Sidebar -->
        <div class="col-md-3">
            <jsp:include page="Adminsidebar.jsp" />
        </div>

        <!-- Main Content -->
        <div class="col-md-9">
            <div class="container mt-5">
                <h2 class="mb-4">Manage Vehicle Types</h2>

                <!-- Form to Add Vehicle Type -->
                <div class="card p-4 shadow">
                    <form action="ManageVehicleTypeServlet" method="Post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="add">
                        
                        <div class="mb-3">
                            <label class="form-label">Vehicle Type Name:</label>
                            <input type="text" name="type_name" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Base Fare:</label>
                            <input type="number" name="base_fare" class="form-control" step="0.01" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Per KM Rate:</label>
                            <input type="number" name="per_km_rate" class="form-control" step="0.01" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Default Image:</label>
                            <input type="file" name="default_image" class="form-control" accept="image/*" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Add Vehicle Type</button>
                    </form>
                </div>

                <!-- Display Vehicle Types -->
                <h3 class="mt-5">Existing Vehicle Types</h3>
                <table class="table table-bordered mt-3">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Type Name</th>
                            <th>Base Fare</th>
                            <th>Per KM Rate</th>
                            <th>Default Image</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<VehicleType> vehicleTypes = (List<VehicleType>) request.getAttribute("vehicleTypes");
                            if (vehicleTypes != null && !vehicleTypes.isEmpty()) {
                                for (VehicleType vehicle : vehicleTypes) {
                        %>
                        <tr>
                            <td><%= vehicle.getId() %></td>
                            <td><%= vehicle.getType_name() %></td>
                            <td><%= vehicle.getBase_fare() %></td>
                            <td><%= vehicle.getPer_km_rate() %></td>
                            
                            <!-- Default Image Column -->
                            <td>
                                <img src="<%= request.getContextPath() + "/" + vehicle.getDefault_image_url() %>" width="100">
                            </td>
                            
                            <!-- Created At Column -->
                            <td><%= vehicle.getCreatedAt() %></td>
                            
                            <!-- Actions Column (Edit and Delete Buttons) -->
                            <td>
                                <!-- Edit Button - Opens a modal -->
                                <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal<%= vehicle.getId() %>">Edit</button>

                                <!-- Delete Form -->
                                <form action="ManageVehicleTypeServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= vehicle.getId() %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                            </td>
                        </tr>

                        <!-- Edit Modal -->
                        <div class="modal fade" id="editModal<%= vehicle.getId() %>" tabindex="-1" aria-labelledby="editModalLabel<%= vehicle.getId() %>" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Vehicle Type</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="ManageVehicleTypeServlet" method="post" enctype="multipart/form-data">
                                            <input type="hidden" name="action" value="edit">
                                            <input type="hidden" name="id" value="<%= vehicle.getId() %>">

                                            <div class="mb-3">
                                                <label class="form-label">Vehicle Type Name:</label>
                                                <input type="text" name="type_name" class="form-control" value="<%= vehicle.getType_name() %>" required>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Base Fare:</label>
                                                <input type="number" name="base_fare" class="form-control" step="0.01" value="<%= vehicle.getBase_fare() %>" required>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Per KM Rate:</label>
                                                <input type="number" name="per_km_rate" class="form-control" step="0.01" value="<%= vehicle.getPer_km_rate() %>" required>
                                            </div>

                                           
                                            <div class="mb-3">
                                                <label class="form-label">Current Image:</label><br>
                                                <img src="<%= request.getContextPath() + "/" + vehicle.getDefault_image_url() %>" width="100" class="mb-2">
                                                <label class="form-label">New Image (Optional):</label>
                                                <input type="file" name="default_image" class="form-control" accept="image/*">
                                            </div>

                                            <button type="submit" class="btn btn-success">Save Changes</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% 
                                }
                            } else { 
                        %>
                        <tr><td colspan="7" class="text-center">No vehicle types added yet.</td></tr>
                        <% 
                            } 
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
                   
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>




















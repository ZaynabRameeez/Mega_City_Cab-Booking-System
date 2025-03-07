

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */




package Controller;

import DAO.VehicleTypeDAO;
import Model.VehicleType;
import Utils.DBConfig;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ManageVehicleTypeServlet extends HttpServlet {
    private final VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
     private static final String UPLOAD_DIR = "Uploads/vehicles/";
     

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
     

        if ("add".equals(action)) {
            String typeName = request.getParameter("type_name");
            double baseFare = Double.parseDouble(request.getParameter("base_fare"));
            double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
            
            Part filePart = request.getPart("default_image");
            String fileName = filePart.getSubmittedFileName();
            
           // Prepare file upload path
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Handle file upload
            if (fileName != null && !fileName.isEmpty()) {
                filePart.write(uploadPath + File.separator + fileName);
            }

          
//            if (!uploadDir.exists()) uploadDir.mkdirs();
//            filePart.write(uploadPath + File.separator + fileName); 

            
            String imageUrl = UPLOAD_DIR + fileName;
            vehicleTypeDAO.addVehicleType(typeName, baseFare, perKmRate, imageUrl);
        }
       else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String typeName = request.getParameter("type_name");
            double baseFare = Double.parseDouble(request.getParameter("base_fare"));
            double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
             // Fetch the existing vehicle type to retain its image URL
    VehicleType existingVehicle = vehicleTypeDAO.getVehicleTypeById(id);
    String imageUrl = existingVehicle != null ? existingVehicle.getDefault_image_url() : null;
            

            // Handle file upload (check if a new file is uploaded)
            Part filePart = request.getPart("default_image");
            String fileName = filePart.getSubmittedFileName();
            

            if (fileName != null && !fileName.isEmpty()) {
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                filePart.write(uploadPath + File.separator + fileName);
                imageUrl = UPLOAD_DIR + fileName; // Update only if a new file is uploaded
            }

    // Update the vehicle type
    vehicleTypeDAO.updateVehicleType(id, typeName, baseFare, perKmRate, imageUrl);
}

        else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            vehicleTypeDAO.deleteVehicleType(id);
        }
            response.sendRedirect("ManageVehicleTypeServlet");
    
        
    }
    
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
 List<VehicleType> vehicleTypes = vehicleTypeDAO.getAllVehicleTypes(); 
 System.out.println("Fetched Vehicle Types in Servlet: " + vehicleTypes); // Debugging
    request.setAttribute("vehicleTypes", vehicleTypes);  // Set the vehicle types as an attribute
    request.getRequestDispatcher("ManageVehicle.jsp").forward(request, response);  // Forward to the JSP
}

    
    
}























//
//@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
//public class ManageVehicleTypeServlet extends HttpServlet {
//    private VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        
//        if ("add".equals(action)) {
//            addVehicleType(request, response);
//        } else if ("edit".equals(action)) {
//            UpdateVehicleType(request, response);
//        } else if ("delete".equals(action)) {
//            deleteVehicleType(request, response);
//        }
//        
//        doGet(request, response);
//    }
//    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<VehicleType> vehicleTypes = vehicleTypeDAO.getAllVehicleTypes();
//        request.setAttribute("vehicleTypes", vehicleTypes);
//        request.getRequestDispatcher("ManageVehicle.jsp").forward(request, response);
//    }
//    
//    private void addVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String typeName = request.getParameter("type_name");
//        double baseFare = Double.parseDouble(request.getParameter("base_fare"));
//        double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
//        Part filePart = request.getPart("default_image_url");
//        
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//        String UploadPath = getServletContext().getRealPath("/") + "Uploads/" + fileName;
//        filePart.write(UploadPath);
//        
//        String imageUrl = "Uploads/" + fileName;
//        VehicleType vehicleType = new VehicleType(0, typeName, baseFare, perKmRate, imageUrl, null);
//        vehicleTypeDAO.addVehicleType(vehicleType);
//    }
//    
////    private void UpdateVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException {
////        int id = Integer.parseInt(request.getParameter("id"));
////        String typeName = request.getParameter("type_name");
////        double baseFare = Double.parseDouble(request.getParameter("base_fare"));
////        double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
////        
////        vehicleTypeDAO.updateVehicleType(new VehicleType(id, typeName, baseFare, perKmRate, null, null));
////    }
//    private void UpdateVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//    int id = Integer.parseInt(request.getParameter("id"));
//    String typeName = request.getParameter("type_name");
//    double baseFare = Double.parseDouble(request.getParameter("base_fare"));
//    double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
//
//    // Get the existing vehicle type
//    VehicleType existingVehicle = vehicleTypeDAO.getVehicleTypeById(id);
//    
//    // Handle case where the vehicle type is not found
//    if (existingVehicle == null) {
//        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle Type not found.");
//        return;
//    }
//
//    String imageUrl = existingVehicle.getDefault_image_url(); // Retain existing image by default
//
//    // Handle file upload
//    Part filePart = request.getPart("default_image");
//    if (filePart != null && filePart.getSize() > 0) {
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//        String uploadPath = getServletContext().getRealPath("/") + "Uploads/" + fileName;
//        filePart.write(uploadPath);
//        imageUrl = "Uploads/" + fileName; // Update image URL if a new file is uploaded
//    }
//
//    // Update vehicle type in the database
//    vehicleTypeDAO.updateVehicleType(new VehicleType(id, typeName, baseFare, perKmRate, imageUrl, null));
//}
//
//
//    
//    private void deleteVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        vehicleTypeDAO.deleteVehicleType(id);
//    }
//
//
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
//        List<VehicleType> vehicleTypes = vehicleTypeDAO.getAllVehicleTypes();
//        request.setAttribute("vehicleTypes", vehicleTypes);
//        request.getRequestDispatcher("ManageVehicle.jsp").forward(request, response);
//    }
//}
//
//















//import DAO.VehicleTypeDAO;
//import Model.VehicleType;
//import Utils.DBConfig;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.List;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
//public class ManageVehicleTypeServlet extends HttpServlet {
//    private VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
//    
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        
//        if ("add".equals(action)) {
//            addVehicleType(request, response);
//        } else if ("edit".equals(action)) {
//            EditVehicleType(request, response);
//        } else if ("delete".equals(action)) {
//            deleteVehicleType(request, response);
//        }
//        
//        doGet(request, response);
//    }
//    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<VehicleType> vehicleTypes = vehicleTypeDAO.getAllVehicleTypes();
//        request.setAttribute("vehicleTypes", vehicleTypes);
//        request.getRequestDispatcher("ManageVehicle.jsp").forward(request, response);
//    }
//    
//    private void addVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String typeName = request.getParameter("type_name");
//        double baseFare = Double.parseDouble(request.getParameter("base_fare"));
//        double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
//        Part filePart = request.getPart("default_image_url");
//        String imageUrl = null;
//      // Check if a file was uploaded, otherwise redirect with an error message
//    if (filePart == null || filePart.getSize() == 0) {
//        // Redirect with a query parameter indicating the error
//        response.sendRedirect("ManageVehicle.jsp?error=Image+file+is+required.");
//        return; // Stop further processing
//    }
//
//    // Process the file if it's uploaded
//    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//    String uploadPath = getServletContext().getRealPath("/Uploads/") + fileName;
//    filePart.write(uploadPath);
//    imageUrl = "Uploads/" + fileName; // Set the image URL
//
//    // After successful image upload, create the VehicleType object
//    VehicleType vehicleType = new VehicleType(0, typeName, baseFare, perKmRate, imageUrl, null);
//    vehicleTypeDAO.addVehicleType(vehicleType);
//
//    // Redirect to ManageVehicle.jsp after success
//    response.sendRedirect("ManageVehicleTypeServlet");
//}
//        
////        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
////        String UploadPath = getServletContext().getRealPath("/Uploads/") + fileName;
////     
////        
////        String imageUrl = "Uploads/" + fileName;
////        VehicleType vehicleType = new VehicleType(0, typeName, baseFare, perKmRate, imageUrl, null);
////        vehicleTypeDAO.addVehicleType(vehicleType);
////    }
//    
////    private void UpdateVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException {
////        int id = Integer.parseInt(request.getParameter("id"));
////        String typeName = request.getParameter("type_name");
////        double baseFare = Double.parseDouble(request.getParameter("base_fare"));
////        double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
////        
////        vehicleTypeDAO.updateVehicleType(new VehicleType(id, typeName, baseFare, perKmRate, null, null));
////    }
//    private void EditVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//    int id = Integer.parseInt(request.getParameter("id"));
//    String typeName = request.getParameter("type_name");
//    double baseFare = Double.parseDouble(request.getParameter("base_fare"));
//    double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
//    // Get the existing vehicle type
//    VehicleType existingVehicle = vehicleTypeDAO.getVehicleTypeById(id);
//    
//    // Handle case where the vehicle type is not found
//    if (existingVehicle == null) {
//        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle Type not found.");
//        return; // Stop execution if the vehicle type does not exist
//    }
//
//    String imageUrl = null; // We'll set the image URL after checking for upload
//
//    // Handle file upload (if a new image is uploaded)
//    Part filePart = request.getPart("default_image"); // Get file part for new image
//    if (filePart != null && filePart.getSize() > 0) {
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//        String uploadPath = getServletContext().getRealPath("/Uploads/") + fileName;
//        filePart.write(uploadPath);
//        imageUrl = "Uploads/" + fileName; // Set new image URL
//    } else {
//        // If no new image is uploaded, retain the existing image
//        imageUrl = existingVehicle.getDefault_image_url();
//    }
//
//    // Update vehicle type in the database
//    vehicleTypeDAO.EditVehicleType(new VehicleType(id, typeName, baseFare, perKmRate, imageUrl, null));
//
//    // Redirect to refresh the page or show a success message
//    response.sendRedirect("ManageVehicleTypeServlet"); // Or use another method to show a success message
//}
////
////    // Get the existing vehicle type
////    VehicleType existingVehicle = vehicleTypeDAO.getVehicleTypeById(id);
////    
////    // Handle case where the vehicle type is not found
////    if (existingVehicle == null) {
////        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle Type not found.");
////        return; // Stop execution if the vehicle type does not exist
////    }
////
////    String imageUrl = existingVehicle.getDefault_image_url(); // Retain existing image by default
////
////    // Handle file upload (if a new image is uploaded)
////    Part filePart = request.getPart("default_image");
////    if (filePart != null && filePart.getSize() > 0) {
////        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
////        String uploadPath = getServletContext().getRealPath("/Uploads/")  + fileName;
////        filePart.write(uploadPath);
////        imageUrl = "Uploads/" + fileName; // Update image URL if a new file is uploaded
////    }
////
////    // Update vehicle type in the database
////    vehicleTypeDAO.updateVehicleType(new VehicleType(id, typeName, baseFare, perKmRate, imageUrl, null));
////}
//
//
//    
//    private void deleteVehicleType(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        vehicleTypeDAO.deleteVehicleType(id);
//    }
//}
//
//
//
//
//
//
//


















//package Controller;
//
//import DAO.VehicleTypeDAO;
//import Model.VehicleType;
//import Utils.DBConfig;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.List;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
//public class ManageVehicleTypeServlet extends HttpServlet {
//    private VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String typeName = request.getParameter("type_name");
//        double baseFare = Double.parseDouble(request.getParameter("base_fare"));
//        double perKmRate = Double.parseDouble(request.getParameter("per_km_rate"));
//
//        Part filePart = request.getPart("default_image");
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//        
////        String uploadPath = getServletContext().getRealPath("") + "Uploads/" + fileName;
////        filePart.write(uploadPath);
//// 🔥 Save image in webapp/uploads directory
//    String UploadPath = getServletContext().getRealPath("/Uploads") + "/" + fileName;
//    filePart.write(UploadPath);
//
//        String imageUrl = "Uploads/" + fileName;
//
//        VehicleType vehicleType = new VehicleType(0, typeName, baseFare, perKmRate, imageUrl, null);
//        vehicleTypeDAO.addVehicleType(vehicleType);
//
//                   
//      doGet(request,response);         
//
//    }
////     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////    List<VehicleType> vehicleTypes = vehicleTypeDAO.getAllVehicleTypes();
////    
////    // Debugging to check if data is being retrieved
////    System.out.println("Fetched Vehicle Types: " + vehicleTypes.size());
////    
////    request.setAttribute("vehicleTypes", vehicleTypes);
////    request.getRequestDispatcher("ManageVehicle.jsp").forward(request, response);
////}
//     
//   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    String action = request.getParameter("action");
//
//    if (action != null && action.equals("delete")) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        vehicleTypeDAO.deleteVehicleType(id);
//    }
//
//    List<VehicleType> vehicleTypes = vehicleTypeDAO.getAllVehicleTypes();
//    request.setAttribute("vehicleTypes", vehicleTypes);
//    request.getRequestDispatcher("ManageVehicle.jsp").forward(request, response);
//}
//       
//                  
//
//}

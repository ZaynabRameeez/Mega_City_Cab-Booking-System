/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 *
 * @author zainr
 */

package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import Model.User;
import Service.UserService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UpdateProfileServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "C:\\Users\\zainr\\Documents\\NetBeansProjects\\Mega_City_Cab Booking System\\web\\Uploads"; 
    private static final String DEFAULT_PHOTO = "assets/no-profile.jpg"; 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        UserService userService = new UserService();
        user = userService.getUserById(user.getId());

        if (user == null) {
            request.setAttribute("error", "User not found.");
            response.sendRedirect("error.jsp");
            return;
        }

        session.setAttribute("user", user); // Refresh user data
        request.setAttribute("user", user);
        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        UserService userService = new UserService();

        // Update user details
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);

        System.out.println("Updating user: " + user.getId() + " - " + user.getUsername());

        // Profile Photo Upload
        Part filePart = request.getPart("profilePhoto");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String filePath = UPLOAD_DIR + File.separator + fileName;
            filePart.write(filePath);

            // Save only the relative path (Uploads/filename.jpg)
            String relativePath = "Uploads/" + fileName;

            // Delete old profile photo if it's not the default image
            if (user.getPhoto() != null && !user.getPhoto().equals(DEFAULT_PHOTO)) {
                File oldFile = new File(UPLOAD_DIR + File.separator + user.getPhoto().substring(8)); // Remove "Uploads/"
                if (oldFile.exists()) oldFile.delete();
            }

            user.setPhoto(relativePath);
            userService.updateProfilePhoto(user.getId(), relativePath);
        }

        // Remove Profile Photo
        if ("true".equals(request.getParameter("removePhoto"))) {
            if (user.getPhoto() != null && !user.getPhoto().equals(DEFAULT_PHOTO)) {
                File oldFile = new File(UPLOAD_DIR + File.separator + user.getPhoto().substring(8));
                if (oldFile.exists()) oldFile.delete();
            }
            user.setPhoto(DEFAULT_PHOTO);
            userService.updateProfilePhoto(user.getId(), DEFAULT_PHOTO);
        }

        // Password update
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (oldPassword != null && !oldPassword.isEmpty()) {
            if (!userService.verifyPassword(user.getId(), oldPassword)) {
                request.setAttribute("error", "Incorrect old password.");
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
                return;
            }
            if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
                request.setAttribute("error", "Password must be at least 8 characters, include 1 letter, 1 number, and 1 special character.");
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "New password and confirm password do not match.");
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
                return;
            }
            userService.changePassword(user.getId(), oldPassword, newPassword);
            request.setAttribute("message", "Password updated successfully!");
        }

        // Save changes
        boolean updated = userService.updateUserProfile(user);
        if (updated) {
            session.setAttribute("user", user); // Update session
            request.setAttribute("message", "Profile updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update profile.");
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
    }
}



//
//package Controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//import Model.User;
//import Service.UserService;
//import Utils.PasswordUtils;
//
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//    maxFileSize = 1024 * 1024 * 10, // 10MB
//    maxRequestSize = 1024 * 1024 * 50 // 50MB
//)
//public class UpdateProfileServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private UserService userService = new UserService();
//     // ✅ Define the upload directory as a constant
//    private static final String UPLOAD_DIR = "Uploads";
//    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    response.sendRedirect("UserProfile.jsp"); // Redirect to profile page
//}
//
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//        throws ServletException, IOException {
//        
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }
//
//        String action = request.getParameter("action");
//
//        if ("updateProfile".equals(action)) {
//            updateProfile(request, response, user);
//        } else if ("uploadImage".equals(action)) {
//            uploadImage(request, response, user);
//        } else if ("changePassword".equals(action)) {
//            changePassword(request, response, user);
//        }
//    }
//
//    // ✅ Update Username & Phone
//    private void updateProfile(HttpServletRequest request, HttpServletResponse response, User user)
//        throws IOException {
//        
//        String username = request.getParameter("username");
//        String phone = request.getParameter("phone");
//
//        if (username.isEmpty() || phone.isEmpty() || !phone.matches("\\d{10}")) {
//            request.getSession().setAttribute("error", "Invalid input!");
//            response.sendRedirect("UserProfile.jsp");
//            return;
//        }
//
//        user.setUsername(username);
//        user.setPhone(phone);
//
//        boolean updated = userService.updateUserProfile(user);
//
//        if (updated) {
//           request.setAttribute("success", "Profile updated successfully!");
//
//        } else {
//            request.getSession().setAttribute("error", "Error updating profile!");
//        }
//
//       response.sendRedirect("UserProfile.jsp");
//
//    }
//
//    // ✅ Upload Profile Picture
//    private void uploadImage(HttpServletRequest request, HttpServletResponse response, User user)
//        throws IOException, ServletException {
//        
//        Part filePart = request.getPart("profileImage");
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//
//        if (fileName.isEmpty()) {
//            request.getSession().setAttribute("error", "Please select an image!");
//            response.sendRedirect("UserProfile.jsp");
//            return;
//        }
//        
//         // ✅ Get the absolute upload path
//        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) uploadDir.mkdir(); // Create directory if not exists
//
//        String filePath = uploadPath + File.separator + fileName;
//        filePart.write(filePath); // Save the file
//
//
//     
//        File savedFile = new File(filePath);
//      if (!savedFile.exists()) {
//    request.getSession().setAttribute("error", "File upload failed! The file was not saved.");
//    response.sendRedirect("UserProfile.jsp");
//    return;
//  }
//
//// This block was misplaced in your code. It should be executed after checking if the file was saved.
//  user.setPhoto(fileName);
//boolean updated = userService.updateUserPhoto(user);
//if (updated) {
//    request.getSession().setAttribute("success", "Profile picture updated!");
//} else {
//    request.getSession().setAttribute("error", "Failed to update profile picture.");
//}
//
//response.sendRedirect("UserProfile.jsp");
//    }
//
//
//
////
////        String uploadPath = getServletContext().getRealPath("") + File.separator + "Uploads";
////        File uploadDir = new File(uploadPath);
////        if (!uploadDir.exists()) uploadDir.mkdir();
////
////        String filePath = uploadPath + File.separator + fileName;
////        filePart.write(filePath);
////
////        user.setPhoto(fileName);
////        boolean updated = userService.updateUserPhoto(user);
////
////        if (updated) {
////            request.getSession().setAttribute("success", "Profile picture updated!");
////        } else {
////            request.getSession().setAttribute("error", "Failed to update profile picture.");
////        }
////
////        response.sendRedirect("UserProfile.jsp");
////    }
//
//    // ✅ Change Password
//    private void changePassword(HttpServletRequest request, HttpServletResponse response, User user)
//        throws IOException {
//        
//        String oldPassword = request.getParameter("oldPassword");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        if (!PasswordUtils.verifyPassword(oldPassword, user.getPassword())) {
//            request.getSession().setAttribute("error", "Incorrect current password!");
//            response.sendRedirect("UserProfile.jsp");
//            return;
//        }
//
//        if (!newPassword.equals(confirmPassword)) {
//            request.getSession().setAttribute("error", "New passwords do not match!");
//            response.sendRedirect("UserProfile.jsp");
//            return;
//        }
//
//        if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
//            request.getSession().setAttribute("error", "Password must have 8 characters, 1 letter, 1 number, 1 special char!");
//            response.sendRedirect("UserProfile.jsp");
//            return;
//        }
//
//        user.setPassword(PasswordUtils.hashPassword(newPassword));
//       boolean updated = userService.updateUserPassword(user.getEmail(), oldPassword, newPassword);
//
//        if (updated) {
//            request.getSession().setAttribute("success", "Password updated successfully!");
//        } else {
//            request.getSession().setAttribute("error", "Error updating password!");
//        }
//
//        response.sendRedirect("UserProfile.jsp");
//    }
//}
//
//

















//package Controller;
//
//import Model.User;
//import Service.UserService;
//import Utils.PasswordUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
//    maxFileSize = 1024 * 1024 * 10,       // 10MB
//    maxRequestSize = 1024 * 1024 * 50     // 50MB
//)
//public class UpdateProfileServlet extends HttpServlet {
//
//    private final UserService userService = new UserService();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("loggedUser");
//
//        if (user == null) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }
//
//        // Get form values
//        String username = request.getParameter("username");
//        String phone = request.getParameter("phone");
//        String address = request.getParameter("address");
//        String oldPassword = request.getParameter("oldPassword");
//        String newPassword = request.getParameter("newPassword");
//        Part filePart = request.getPart("photo");
//
//        String fileName = null;
//        String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
//        File uploadFolder = new File(uploadDir);
//        if (!uploadFolder.exists()) {
//            uploadFolder.mkdirs(); // Create directory if not exists
//        }
//
//        if (filePart != null && filePart.getSize() > 0) {
//            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//            fileName = "user_" + user.getId() + "_" + System.currentTimeMillis() + "_" + originalFileName;
//            String filePath = uploadDir + File.separator + fileName;
//            filePart.write(filePath);
//            user.setPhoto("uploads/" + fileName); // Store relative path
//        }
//
//        // Update user details
//        user.setUsername(username);
//        user.setPhone(phone);
//        user.setAddress(address);
//
//        if (oldPassword != null && newPassword != null && !newPassword.isEmpty()) {
//            if (PasswordUtils.verifyPassword(oldPassword, user.getPassword())) {
//                user.setPassword(PasswordUtils.hashPassword(newPassword));
//            } else {
//                request.setAttribute("error", "Old password is incorrect.");
//                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//                return;
//            }
//        }
//
//        // Update user in database
//        boolean updated = userService.updateUser(user);
//        if (updated) {
//            session.setAttribute("loggedUser", user);
//            request.setAttribute("message", "Profile updated successfully.");
//        } else {
//            request.setAttribute("error", "Profile update failed.");
//        }
//
//        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//    }
//}
//





//package Controller;
//
//import Model.User;
//import Service.UserService;
//import Utils.DBConfig;
//import Utils.PasswordUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.sql.SQLException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
//    maxFileSize = 1024 * 1024 * 10,       // 10MB
//    maxRequestSize = 1024 * 1024 * 50     // 50MB
//)
//public class UpdateProfileServlet extends HttpServlet {
//
//    private final UserService userService = new UserService();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Fetch user details and show profile page
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("loggedUser");
//
//        if (user == null) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }
//
//        request.setAttribute("user", user);
//        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("loggedUser");
//
//        if (user == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//
//        // Get updated user details from the form
//        String username = request.getParameter("username");
//        String phone = request.getParameter("phone");
//        String address = request.getParameter("address");
//        String oldPassword = request.getParameter("oldPassword");
//        String newPassword = request.getParameter("newPassword");
//        Part filePart = request.getPart("photo");
//
//        String fileName = null;
//        if (filePart != null && filePart.getSize() > 0) {
//            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//            String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
//            File fileSaveDir = new File(uploadDir);
//            if (!fileSaveDir.exists()) {
//                fileSaveDir.mkdirs();
//            }
//            filePart.write(uploadDir + File.separator + fileName);
//        }
//
//        // Update user object
//        user.setUsername(username);
//        user.setPhone(phone);
//        user.setAddress(address);
//
//        if (fileName != null) {
//            user.setPhoto(fileName);
//        }
//
//        if (oldPassword != null && newPassword != null && !newPassword.isEmpty()) {
//            if (PasswordUtils.verifyPassword(oldPassword, user.getPassword())) {
//                user.setPassword(PasswordUtils.hashPassword(newPassword));
//            } else {
//                request.setAttribute("error", "Old password is incorrect.");
//                request.getRequestDispatcher("profile.jsp").forward(request, response);
//                return;
//            }
//        }
//
//        // Update user in database
//        boolean updated = userService.updateUser(user);
//        if (updated) {
//            session.setAttribute("loggedUser", user);
//            request.setAttribute("message", "Profile updated successfully.");
//        } else {
//            request.setAttribute("error", "Profile update failed.");
//        }
//
//        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//    }
//}






//package Controller;
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//import Model.User;
//import Service.UserService;
//import Utils.PasswordUtils;
//
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//                 maxFileSize = 1024 * 1024 * 10,      // 10MB
//                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
//public class UpdateProfileServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private UserService userService;
//
//    public UpdateProfileServlet() {
//        super();
//        this.userService = new UserService();
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }
//
//        String username = request.getParameter("username");
//        String phone = request.getParameter("phone");
//        String nic = request.getParameter("nic");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//        Part profilePicPart = request.getPart("profilePic");  // Get profile picture
//
//        // Validate password match if changed
//        if (newPassword != null && !newPassword.trim().isEmpty() && !newPassword.equals(confirmPassword)) {
//            request.setAttribute("message", "Passwords do not match!");
//            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//            return;
//        }
//
//        // Hash password if provided
//        if (newPassword != null && !newPassword.trim().isEmpty()) {
//            user.setPassword(PasswordUtils.hashPassword(newPassword));
//        }
//
//        // Update user details
//        user.setUsername(username);
//        user.setPhone(phone);
//        user.setNic(nic);
//
//        boolean updateSuccess = userService.updateUserProfile(user, profilePicPart);
//
//        if (updateSuccess) {
//            session.setAttribute("user", user);
//            response.sendRedirect("UserProfile.jsp?message=Profile updated successfully.");
//        } else {
//            request.setAttribute("message", "Error updating profile. Try again.");
//            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//        }
//    }
//}
//
//


//package Controller;
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import Model.User;
//import Service.UserService;
//import Utils.PasswordUtils;
//
//public class UpdateProfileServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private UserService userService;
//
//    public UpdateProfileServlet() {
//        super();
//        this.userService = new UserService();
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }
//
//        String username = request.getParameter("username");
//        String phone = request.getParameter("phone");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        // Validate password match
//        if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
//            request.setAttribute("message", "Passwords do not match!");
//            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//            return;
//        }
//
//        // Hash password if changed
//        String hashedPassword = user.getPassword();
//        if (!newPassword.isEmpty()) {
//            hashedPassword = PasswordUtils.hashPassword(newPassword);
//        }
//
//        // Update user details
//        boolean updateSuccess = userService.updateUser(user.getId(), username, phone, hashedPassword);
//
//        if (updateSuccess) {
//            // Update session data
//            user.setUsername(username);
//            user.setPhone(phone);
//            user.setPassword(hashedPassword);
//            session.setAttribute("user", user);
//
//            response.sendRedirect("UserProfile.jsp?message=Profile updated successfully.");
//        } else {
//            request.setAttribute("message", "Error updating profile. Try again.");
//            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
//        }
//    }
//}

package Service;

import DAO.UserDAO;
import Model.User;
import Utils.PasswordUtils;
import jakarta.servlet.http.Part;

public class UserService {
    private final UserDAO userDAO;



    public UserService() {
        this.userDAO = new UserDAO();
     
    }

    // Register a New User
    public String registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getEmail() == null || user.getEmail().isEmpty() ||
            user.getPhone() == null || user.getPhone().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty() ||
            user.getNic() == null || user.getNic().isEmpty()) {
            return "Missing required fields.";
            
        }

        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Invalid email format.";
        }

        if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            return "Password must be at least 8 characters long, include 1 letter, 1 number, and 1 special character.";
        }

        if (!user.getPhone().matches("^\\d{10}$")) {
            return "Phone number must be exactly 10 digits.";
        }

        if (!user.getNic().matches("^(\\d{9}V|\\d{12})$")) {
            return "NIC must be in the format 123456789V or 123456789012.";
        }

        if (userDAO.isEmailExists(user.getEmail())) {
            return "Email already exists. Please use a different one.";
        }
        if (userDAO.isUsernameExists(user.getUsername())) {
            return "Username already exists. Please use a different one.";
        }
       

//        // Hash password before storing
//        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));

        return userDAO.registerUser(user) ? "Registration successful!" : "Error in registration.";
    }

//    // Request Driver Role
//    public String requestDriverRole(String email, String licenseNumber, String vehicleType, String vehicleNumber) {
//        User user = userDAO.getUserByEmail(email);
//        if (user == null) {  
//            return "User not found.";
//        }
//        if (!user.getRole().equalsIgnoreCase("User")) {
//            return "You are already a driver.";
//        }
//
//        // Check if user already submitted a request
//        if (userDAO.isDriverRequestPending(email)) {
//            return "You have already submitted a driver request. Please wait for approval.";
//        }
//
//        return userDAO.requestDriverRole(email, licenseNumber, vehicleType, vehicleNumber) ? 
//               "Driver role request submitted!" : "Error submitting request.";
//    }
    public boolean resetPassword(String email, String hashedPassword) {
    return userDAO.resetPassword(email, hashedPassword);
}
//    public boolean updateUserProfile(User user, Part profilePicPart) {
//    return userDAO.updateUserProfile(user, profilePicPart);
//}

  
    public boolean updateUserProfile(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty() ||
            user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return false; // Validation: No empty or null fields
        }

        // Email validation (Basic regex)
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return false;
        }

        // Phone validation (Ensure it's numeric and 10 digits)
        if (!user.getPhone().matches("\\d{10}")) {
            return false;
        }

        return userDAO.updateUserProfile(user);
    }

    public boolean removeProfilePhoto(int userId) {
        return userDAO.removeProfilePhoto(userId);
    }

  

    public boolean updateProfilePhoto(int userId, String photoPath) {
        return userDAO.updateProfilePhoto(userId, photoPath);
    }
public boolean verifyPassword(int userId, String oldPassword) {
    return userDAO.verifyOldPassword(userId, oldPassword);
}

   public boolean changePassword(int userId, String oldPassword, String newPassword) {
    if (!userDAO.verifyOldPassword(userId, oldPassword)) {
        return false; // Old password incorrect
    }

    // Hash new password before updating
    String hashedPassword = PasswordUtils.hashPassword(newPassword);
    return userDAO.updatePassword(userId, hashedPassword);
}

  public User getUserById(int userId){
      return userDAO.getUserById(userId);
  }
     public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
//     public boolean sendDriverRequest(int userId, String license, String vehicleType, String vehicleNumber) {
//        return userDAO.sendDriverRequest(userId, license, vehicleType, vehicleNumber);
//    }

 

   

  
 
}









//package Service;
//
//
//import DAO.UserDAO;
//import Model.User;
//import Utils.PasswordUtils;
//
//public class UserService {
//    private UserDAO userDAO;
//
//    public UserService() {
//        this.userDAO = new UserDAO();
//    }
//
//    // Register a New User
//    public String registerUser(User user) {
//        // Check required fields
//        if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || 
//            user.getPhone().isEmpty() || user.getPassword().isEmpty() || user.getNic().isEmpty()) {
//            return "Missing required fields.";
//        }
//
//        // Validate email format
//        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
//            return "Invalid email format.";
//        }
//
//        // Validate password
//        if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
//            return "Password must be at least 8 characters long, include 1 letter, 1 number, and 1 special character.";
//        }
//
//        // Validate phone number (exactly 10 digits)
//        if (!user.getPhone().matches("^\\d{10}$")) {
//            return "Phone number must be exactly 10 digits.";
//        }
//
//        // Validate NIC format (either 9 digits + 'V' or 12 digits)
//        if (!user.getNic().matches("^\\d{9}V|\\d{12}$")) {
//            return "NIC must be in the format 123456789V or 123456789012.";
//        }
//
//        // Ensure email and username are unique
//        if (userDAO.isEmailExists(user.getEmail())) {
//            return "Email already exists. Please use a different one.";
//        }
//        if (userDAO.isUsernameExists(user.getUsername())) {
//            return "Username already exists. Please use a different one.";
//        }
//
//        // Hash the password before storing it
//        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
//
//        // Insert into DB
//        boolean isRegistered = userDAO.registerUser(user);
//        return isRegistered ? "Registration successful!" : "Error in registration.";
//    }
//
//    // User Login
//    public User loginUser(String email, String password) {
//        User user = userDAO.authenticateUser(email, password);
//
//        if (user == null) {
//            System.out.println("Invalid email or password.");
//            return null;
//        }
//
//        // If the user is a driver, check if they are approved
//        if (user.getRole().equalsIgnoreCase("driver") && !user.isActive()) {
//            System.out.println("Your account is pending approval. Please wait for admin approval.");
//            return null;
//        }
//
//        return user; // Successful login
//    }
//
////    // Admin Approval for Drivers
////    public boolean approveDriver(int driverId) {
////        return userDAO.approveDriver(driverId);
////    }
//    
//    public User authenticateUser(String email, String password) {
//    UserDAO userDAO = new UserDAO();
//    User user = userDAO.getUserByEmail(email);
//
//    if (user != null) {
//        // Compare the stored hashed password with the entered password
//        if (PasswordUtils.verifyPassword(password, user.getPassword())) {
//            return user; // Successful login
//        }
//    }
//    return null; // Authentication failed
//}
//    public boolean resetPassword(String email, String newHashedPassword) {
//    return userDAO.updatePassword(email, newHashedPassword);
//}
//
//
//}

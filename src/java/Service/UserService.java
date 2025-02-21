package Service;


import DAO.UserDAO;
import Model.User;
import Utils.PasswordUtils;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Register a New User
    public String registerUser(User user) {
        // Check required fields
        if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || 
            user.getPhone().isEmpty() || user.getPassword().isEmpty() || user.getNic().isEmpty()) {
            return "Missing required fields.";
        }

        // Validate email format
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Invalid email format.";
        }

        // Validate password
        if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            return "Password must be at least 8 characters long, include 1 letter, 1 number, and 1 special character.";
        }

        // Validate phone number (exactly 10 digits)
        if (!user.getPhone().matches("^\\d{10}$")) {
            return "Phone number must be exactly 10 digits.";
        }

        // Validate NIC format (either 9 digits + 'V' or 12 digits)
        if (!user.getNic().matches("^\\d{9}V|\\d{12}$")) {
            return "NIC must be in the format 123456789V or 123456789012.";
        }

        // Ensure email and username are unique
        if (userDAO.isEmailExists(user.getEmail())) {
            return "Email already exists. Please use a different one.";
        }
        if (userDAO.isUsernameExists(user.getUsername())) {
            return "Username already exists. Please use a different one.";
        }

        // Hash the password before storing it
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));

        // Insert into DB
        boolean isRegistered = userDAO.registerUser(user);
        return isRegistered ? "Registration successful!" : "Error in registration.";
    }

    // User Login
    public User loginUser(String email, String password) {
        User user = userDAO.authenticateUser(email, password);

        if (user == null) {
            System.out.println("Invalid email or password.");
            return null;
        }

        // If the user is a driver, check if they are approved
        if (user.getRole().equalsIgnoreCase("driver") && !user.isActive()) {
            System.out.println("Your account is pending approval. Please wait for admin approval.");
            return null;
        }

        return user; // Successful login
    }

//    // Admin Approval for Drivers
//    public boolean approveDriver(int driverId) {
//        return userDAO.approveDriver(driverId);
//    }
    
    public User authenticateUser(String email, String password) {
    UserDAO userDAO = new UserDAO();
    User user = userDAO.getUserByEmail(email);

    if (user != null) {
        // Compare the stored hashed password with the entered password
        if (PasswordUtils.verifyPassword(password, user.getPassword())) {
            return user; // Successful login
        }
    }
    return null; // Authentication failed
}
    public boolean resetPassword(String email, String newHashedPassword) {
    return userDAO.updatePassword(email, newHashedPassword);
}


}


//import DAO.UserDAO;
//import Model.User;
//import Utils.PasswordUtils;
//import Utils.DBConfig;
//import java.sql.SQLException;
//import java.sql.Connection;
//
//public class UserService {
//    private final UserDAO userDAO;
//
//
//  // Constructor initializing DAO with a database connection
//    public UserService() throws SQLException {
//        Connection connection = DBConfig.getConnection(); // Might throw SQLException
//        this.userDAO = new UserDAO(connection);
//    }
//
//
//    // Register user with validation
//    public boolean registerUser(User user) {
//        if (userDAO.isEmailTaken(user.getEmail())) {
//            System.out.println("Email already in use.");
//            return false;
//        }
//        if (userDAO.isUsernameTaken(user.getUsername())) {
//            System.out.println("Username already taken.");
//            return false;
//        }
//        if (!isValidPassword(user.getPassword())) {
//            System.out.println("Invalid password format! Password must be at least 8 characters, include 1 letter, 1 number, and 1 special character.");
//            return false;
//        }
//        if (!isValidPhone(user.getPhone())) {
//            System.out.println("Invalid phone number format! Phone number must be 10 digits.");
//            return false;
//        }
//
//        // Hash the password before saving
//        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
//
//        return userDAO.registerUser(user);
//    }
//
//    // Authenticate user during login
//    public User authenticateUser(String email, String password) {
//        User user = userDAO.authenticateUser(email, password);
//        if (user != null && PasswordUtils.verifyPassword(password, user.getPassword())) {
//            return user; // Login success
//        }
//        return null; // Authentication failed
//    }
//
//    // Retrieve user by email
//    public User getUserByEmail(String email) {
//        return userDAO.getUserByEmail(email);
//    }
//
//    // Check if email is already taken
//    public boolean isEmailTaken(String email) {
//        return userDAO.isEmailTaken(email);
//    }
//
//    // Check if username is already taken
//    public boolean isUsernameTaken(String username) {
//        return userDAO.isUsernameTaken(username);
//    }
//
//    // Validate password: At least 8 characters, 1 letter, 1 number, 1 special character
//    private boolean isValidPassword(String password) {
//        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
//    }
//
//    // Validate phone number: Must be 10 digits
//    private boolean isValidPhone(String phone) {
//        return phone.matches("^\\d{10}$");
//    }
//}







//package Service;
//
//import DAO.UserDAO;
//import Model.User;
//import java.sql.Connection;
//import Utils.PasswordUtils;
//
//public class UserService {
//    private final UserDAO userDAO;
//
//     // Constructor accepting Connection
//    public UserService(Connection connection) {
//        this.userDAO = new UserDAO(connection);
//    }
//
//  
//    // Register user with validation
//    public boolean registerUser(User user) {
//        if (userDAO.isEmailTaken(user.getEmail())) {
//            System.out.println("Email already in use.");
//            return false;
//        }
//        if (userDAO.isUsernameTaken(user.getUsername())) {
//            System.out.println("Username already taken.");
//            return false;
//        }
//        if (!isValidPassword(user.getPassword())) {
//            System.out.println("Invalid password format! Password must be at least 8 characters, include 1 letter, 1 number, and 1 special character.");
//            return false;
//        }
//        if (!isValidPhone(user.getPhone())) {
//            System.out.println("Invalid phone number format! Phone number must be 10 digits.");
//            return false;
//        }
//
//        // Hash the password before saving
//        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
//
//        return userDAO.registerUser(user);
//    }
//
//    // Authenticate user
//    public User authenticateUser(String email, String password) {
//        return userDAO.authenticateUser(email, password);
//    }
//
//    // Password validation: At least 8 characters, 1 letter, 1 number, 1 special character
//    private boolean isValidPassword(String password) {
//        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
//    }
//
//    // Phone validation: Must be 10 digits
//    private boolean isValidPhone(String phone) {
//        return phone.matches("^\\d{10}$");
//    }
//
//    public boolean isEmailTaken(String email) {
//        return userDAO.isEmailTaken(email);
//    }
//    
//       // Retrieve user by email
//    public User getUserByEmail(String email) {
//        return userDAO.getUserByEmail(email);
//    }
//
//
//    public boolean isUsernameTaken(String username) {
//        return userDAO.isUsernameTaken(username);
//    }
//}

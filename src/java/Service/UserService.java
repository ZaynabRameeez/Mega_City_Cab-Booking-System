package Service;

import DAO.UserDAO;
import Model.User;
import Utils.PasswordUtils;


public class UserService {
  


    //Register a New User
     // ✅ Validate User Input & Register User
    public boolean registerUser(String firstName, String lastName, String birthday, String email, String mobile, String password, String address) {
          if (!isValidName(firstName) || !isValidName(lastName)) return false;
        if (!isValidEmail(email)) return false;
        if (!isValidMobile(mobile)) return false;
       if (!isValidPassword(password)) return false; 
         if (UserDAO.isUserExists(email, mobile)) return false; // Prevent duplicate users

        User newUser = new User(firstName, lastName, birthday, email, mobile, "Customer", password,address);
        return UserDAO.insertUser(newUser);
    }
        private boolean isValidName(String name) {
        return name.matches("^[A-Za-z ]{2,}$");
           }
       
    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
          
    private boolean isValidMobile(String mobile) {
        return mobile.matches("^\\+94\\d{9}$");
    }
    private boolean isValidPassword(String password){
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
        
        public boolean resetPassword(String email, String hashedPassword) {
    return UserDAO.resetPassword(email, hashedPassword); // Call static method correctly
}

    
 
}



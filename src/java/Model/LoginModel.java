/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import DAO.LoginDAO;

/**
 *
 * @author zainr
 */
public class LoginModel {
    
    // ✅ Authenticate User and Validate Password
    public static User authenticateUser(String email, String password) {
        User user = LoginDAO.getUserByEmail(email);
        
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return user if password matches
        }
        return null; // Return null if authentication fails
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Model.LoginModel;
import Model.User;

/**
 *
 * @author zainr
 */
public class LoginService {
    
    private LoginModel model;

    public LoginService() {
        this.model = new LoginModel();
    }

    public User Login(String email, String password) {
        System.out.println("Login Attempt: " + email); // Debugging log
        User user = model.authenticateUser(email, password);
        
        if (user != null) {
            System.out.println("User Found: " + user.getEmail()); // Debugging log
            return user;
        }
        
        System.out.println("Login Failed: Invalid Credentials"); // Debugging log
        return null;
    }
}

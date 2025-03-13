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
    
    
    // ✅ Authenticate User using DAO (handles password verification)
    public static User authenticateUser(String email, String password) {
        return LoginDAO.authenticateUser(email, password); // Use DAO method for proper hashing
    }
}

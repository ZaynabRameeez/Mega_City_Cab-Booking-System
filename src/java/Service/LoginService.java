/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Model.LoginModel;
import Model.User;
import DAO.LoginDAO;

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
    return LoginDAO.authenticateUser(email, password);
}
      
}

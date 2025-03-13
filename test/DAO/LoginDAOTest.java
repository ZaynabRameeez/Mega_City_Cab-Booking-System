/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;


import Model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginDAOTest {

    @Test
    public void testValidUserLogin() {
        System.out.println("Testing valid user login...");
        String email = "zay@gmail.com";  // Use an existing user
        String password = "Zay@123";      // Correct password
        
        User user = LoginDAO.authenticateUser(email, password);
        assertNotNull("User should be authenticated", user);
        assertEquals("Email should match", email, user.getEmail());
    }

    @Test
    public void testInvalidPassword() {
        System.out.println("Testing invalid password...");
        String email = "zay@gmail.com";  // Correct email
        String password = "WrongPassword";  // Incorrect password
        
        User user = LoginDAO.authenticateUser(email, password);
        assertNull("User should not be authenticated", user);
    }

    @Test
    public void testNonExistentUser() {
        System.out.println("Testing non-existent user...");
        String email = "nonexistent@gmail.com";  // Fake email
        String password = "SomePassword";
        
        User user = LoginDAO.authenticateUser(email, password);
        assertNull("User should not be authenticated", user);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author zainr
 */


public class PasswordUtilsTest {
    public static void main(String[] args) {
        String password = "Admin@123"; // Set your admin password here
        String hashedPassword = PasswordUtils.hashPassword(password);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}

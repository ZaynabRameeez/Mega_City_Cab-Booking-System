/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // Hash the password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify if the entered password matches the stored hashed password
    public static boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(storedHashedPassword);
    }
}







//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class PasswordUtils {
//
//    // Hash password using SHA-256
//    public static String hashPassword(String password) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = digest.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashedBytes) {
//                hexString.append(String.format("%02x", b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Method to verify if the provided password matches the stored hashed password
//    public static boolean verifyPassword(String providedPassword, String storedHash) {
//        String hashedProvidedPassword = hashPassword(providedPassword);
//        return storedHash.equals(hashedProvidedPassword);
//}
//}

//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class PasswordUtils {
//
//    public static String hashPassword(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = md.digest(password.getBytes());
//
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hashedBytes) {
//                sb.append(String.format("%02x", b)); // Convert to hex
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error hashing password", e);
//        }
//    }
//
//    public static boolean verifyPassword(String plainPassword, String storedHash) {
//        return hashPassword(plainPassword).equals(storedHash); // Compare hashed versions
//    }
//}

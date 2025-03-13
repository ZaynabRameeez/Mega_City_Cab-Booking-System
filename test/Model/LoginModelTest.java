/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Model;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginModelTest {

    @Test
    public void testAuthenticateUser() {
        System.out.println("authenticateUser");

        // ✅ Use real user credentials
        String email = "zay@gmail.com";  
        String password = "Zay@123";  

        // 🔹 Call authentication method
        User result = LoginModel.authenticateUser(email, password);

        // 🔍 Debugging: Print output
        System.out.println("User returned: " + result);

        // ✅ Assertions
        assertNotNull("User should be authenticated", result);
        assertEquals("Email should match", email, result.getEmail());
    }
}

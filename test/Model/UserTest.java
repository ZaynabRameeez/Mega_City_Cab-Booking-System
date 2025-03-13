/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zainr
 */


/**
 * Test class for User
 */
public class UserTest {
    
    private User instance;  // Declare instance variable

    @Before
    public void setUp() {
        // Initialize User object with ALL attributes
        instance = new User(1, "John", "Doe", "1990-01-01", "john@example.com", "1234567890", "user", "password123", "123 Street, City");
    }

    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        String expResult = "John";
        String result = instance.getFirstName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        String expResult = "Doe";
        String result = instance.getLastName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBirthday() {
        System.out.println("getBirthday");
        String expResult = "1990-01-01";
        String result = instance.getBirthday();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        String expResult = "john@example.com";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMobile() {
        System.out.println("getMobile");
        String expResult = "1234567890";
        String result = instance.getMobile();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetRole() {
        System.out.println("getRole");
        String expResult = "user";
        String result = instance.getRole();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        String expResult = "password123";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAddress() {
        System.out.println("getAddress");
        String expResult = "123 Street, City";
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.Timestamp;

/**
 *
 * @author zainr
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String nic;
    private String password;
    private String role;  
    private String gender;
    private String address; 
    private String photo;
    private boolean isActive;
    
    
    // Constructor
    public User(int id, String username, String email, String phone, String password, String nic, 
                String role, String gender, String address, String photo, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.nic = nic;
        this.role = role;
        this.gender = gender;
        this.address = address;
        this.photo = photo;
        this.isActive = isActive;
    }

    public User(String username, String email, String phone, String password, String nic, 
            String role, String gender, String address) {
    this.username = username;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.nic = nic;
    this.role = role;
    this.gender = gender;
    this.address = address;
    this.isActive = true; // Default value
    this.photo = null; // Photo will be added later
}


//    
//    public User(String username, String email, String phone, String nic, String password, String role, String gender, String address) {
//    this.username = username;
//    this.email = email;
//    this.phone = phone;
//    this.nic = nic;
//    this.password = password;
//    this.role = role;
//    this.gender = gender;
//    this.address = address;
//    this.active = true;  // Default value
//}
    
//
//     // Constructor with ID (for existing users)
//    public User(int id, String username, String email, String phone, String nic, String password, String role, String gender, String address, boolean active) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.phone = phone;
//        this.nic = nic;
//        this.password = password;
//        this.role = role;
//        this.gender = gender;
//        this.address = address;
//        this.active = active;
//    }

     public User() {}
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhoto(){
        return photo;
    }
    
     public void setPhoto(String photo) {
        this.photo = photo;
    }
    

 // Fix active getter method
    public boolean isActive() {
        return isActive;
    }

public void setActive(boolean isActive) {  // Correct method name
    this.isActive = isActive;
   
      }
}


    


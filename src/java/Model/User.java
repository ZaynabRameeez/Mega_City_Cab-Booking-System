/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    package Model;
    import java.sql.Timestamp;
    import java.time.Instant;
    /**
     *
     * @author zainr
     */
    public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;
    private String email;
    private String mobile;
    private String role;
    private String password;
    private String address;
    private String nic;
     
     public User(int id, String firstName, String lastName, String birthday, String email, String mobile, String role, String password, String address,String nic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.password = password;
        this.address = address;
        this.nic=nic;
    }



    public User(int id, String email, String role, String password) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
    }
        public User(String firstName, String lastName, String birthday, String email, String mobile, String role, String password,String address,String nic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.password = password;
        this.address = address;
        this.nic=nic;
        
   
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }
    
    // Setter for NIC (if needed)
    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNic() {
        return nic;
    }


    }      

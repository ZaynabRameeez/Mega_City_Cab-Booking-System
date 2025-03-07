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
        private String username;
        private String email;
        private String phone;
        private String nic;
        private String password;
        private String role;  
        private String gender;
        private String address; 
        private String photo;
        private boolean is_active;
        private boolean is_approved;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private String status; // Add this field



        // Constructor
        public User(int id, String username, String email, String phone, String password, String nic, 
                    String role, String gender, String address, String photo, boolean is_active, boolean is_approved,Timestamp createdAt, Timestamp updatedAt) {
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
            this.is_active = is_active;
            this.is_approved= is_approved;
            this.createdAt = createdAt != null ? createdAt : Timestamp.from(Instant.now());
             this.updatedAt = updatedAt != null ? updatedAt : Timestamp.from(Instant.now());
              this.status = status; // Set the status
        }

        public User(String username, String email, String phone, String password, String nic, 
                String gender,String address) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.nic = nic;
        this.role = "User";
        this.gender = gender;
        this.address = address;
        this.is_active = true; // Default value
        this.is_approved = false;
        this.photo = null; // Photo will be added later
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
        
    }
// Default Constructor (Ensuring Default Role)
    public User() {
        this.role = "user"; // Ensure new users default to "user"
        this.is_active = true;
        this.is_approved = false;
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }
       

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

        public boolean isActive() {
           return is_active;
          }


        public void setActive(boolean is_active) {
            this.is_active = is_active;
        }

        public boolean isApproved() {
            return is_approved;
        }

        public void setApproved(boolean is_approved) {
            this.is_approved = is_approved;
        }


        public Timestamp getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
        }

        public Timestamp getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
        }
        public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}



    }


    


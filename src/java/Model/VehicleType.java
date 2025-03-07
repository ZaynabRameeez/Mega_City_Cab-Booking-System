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
public class VehicleType {
    
    private int id;
    private String type_name;  
    private double base_fare;  
    private double per_km_rate; 
    private String default_image_url; 
    private Timestamp createdAt;
    
//    public VehicleType() {}
// // Constructor for adding a new vehicle type (without id)
//    public VehicleType(String type_name, double base_fare, double per_km_rate, String default_image_url, Timestamp createdAt) {
//        this.type_name = type_name;
//        this.base_fare = base_fare;
//        this.per_km_rate = per_km_rate;
//        this.default_image_url = default_image_url;
//        this.createdAt = createdAt;
//    }

    // Constructor for updating a vehicle type (with id)
    public VehicleType(int id, String type_name, double base_fare, double per_km_rate, String default_image_url,Timestamp createdAt) {
        this.id = id;
        this.type_name = type_name;
        this.base_fare = base_fare;
        this.per_km_rate = per_km_rate;
        this.default_image_url = default_image_url;
        this.createdAt = createdAt;
    }

    public VehicleType(){
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType_name() { return type_name; }
    public void setType_name(String type_name) { this.type_name = type_name; }

    public double getBase_fare() { return base_fare; }
    public void setBase_fare(double base_fare) { this.base_fare = base_fare; }

    public double getPer_km_rate() { return per_km_rate; }
    public void setPer_km_rate(double per_km_rate) { this.per_km_rate = per_km_rate; }

    public String getDefault_image_url() { return default_image_url; }
    public void setDefault_image_url(String default_image_url) { this.default_image_url = default_image_url; }

public Timestamp getCreatedAt() {
    return createdAt;
}

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

  


}


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tim
 */
@Named(value = "listings")
@SessionScoped
@ManagedBean
public class Listings implements Serializable{
    private int id;
    private String make;
    private String model;
    private String trim;
    private String year;
    private String carType;
    private String driveType;
    private String transmission;
    private String cylinders;
    private String fuelType;
    private String mpgCity;
    private String mpgHighway;
    private String price;
    private String color;
    private String mileage;
    private String condition;
    private String titleStatus;
    private String city;
    private String state;
    private String zip;
    private String description;
    private String url;
    private String phone;
    private int sellerId;
    
    private DBConnect dbConnect = new DBConnect();
    private List<Listings> myListings;    

    public Listings(int id, String make, String model, String trim, String year, String carType, String driveType,
            String transmission, String cylinders, String fuelType, String mpgCity, String mpgHighway,
            String price, String color, String mileage, String condition, String titleStatus, String city,
            String state, String zip, String description, String url, String phone, int sellerId) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.trim = trim;
        this.year = year;
        this.carType = carType;
        this.driveType = driveType;
        this.transmission = transmission;
        this.cylinders = cylinders;
        this.fuelType = fuelType;
        this.mpgCity = mpgCity;
        this.mpgHighway = mpgHighway;
        this.price = price;
        this.color = color;
        this.mileage = mileage;
        this.condition = condition;
        this.titleStatus = titleStatus;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.description = description;
        this.url = url;
        this.phone = phone;
        this.sellerId = sellerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
    
    
   
    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getTrim() {
        return trim;
    }

    public String getYear() {
        return year;
    }

    public String getCarType() {
        return carType;
    }

    public String getDriveType() {
        return driveType;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getCylinders() {
        return cylinders;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getMpgCity() {
        return mpgCity;
    }

    public String getMpgHighway() {
        return mpgHighway;
    }

    public String getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getMileage() {
        return mileage;
    }

    public String getCondition() {
        return condition;
    }

    public String getTitleStatus() {
        return titleStatus;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setCylinders(String cylinders) {
        this.cylinders = cylinders;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setMpgCity(String mpgCity) {
        this.mpgCity = mpgCity;
    }

    public void setMpgHighway(String mpgHighway) {
        this.mpgHighway = mpgHighway;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DBConnect getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public List<Listings> getMyListings() {
        return myListings;
    }

    public void setMyListings(List<Listings> myListings) {
        this.myListings = myListings;
    }
        
    
    
    
     
}
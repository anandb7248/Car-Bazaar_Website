
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.validator.ValidatorException;
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

@Named(value = "listingDetail")
@SessionScoped
@ManagedBean

public class ListingDetail {
    private int id;
    private Listings listing;
    private DBConnect dbConnect = new DBConnect();
    
    public void init() throws SQLException {
        String make;
        String model;
        String trim;
        int year;
        String yearStr;
        String carType;
        String driveType;
        String transmission;
        String cylinders;
        String fuelType;
        int mpgCity;
        String mpgCityStr;
        int mpgHighway;
        String mpgHighwayStr;
        int price;
        String priceStr;
        String color;
        int mileage;
        String mileageStr;
        String condition;
        String titleStatus;
        String city;
        String state;
        int zip;
        String zipStr;
        String description;
        String url;
        String phone;
        int sellerId;
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement("select * from listing l, cars c where l.car_id = c.id and ? = l.id");
        ps.setInt(1, id);
        
        ResultSet dbListing = ps.executeQuery();
        
        dbListing.next();
        
        description = dbListing.getString("description");
        price = dbListing.getInt("price");
        color = dbListing.getString("color");
        mileage = dbListing.getInt("mileage");
        transmission = dbListing.getString("transmission");
        condition = dbListing.getString("condition");
        zip = dbListing.getInt("zip");
        state = dbListing.getString("state");
        city = dbListing.getString("city");
        titleStatus = dbListing.getString("title_status");
        make = dbListing.getString("make");
        model = dbListing.getString("model");
        trim = dbListing.getString("trim");
        carType = dbListing.getString("size");
        fuelType = dbListing.getString("fuel_type");
        mpgCity = dbListing.getInt("mpg_city");
        mpgHighway = dbListing.getInt("mpg_hwy");
        driveType = dbListing.getString("drive_type");
        cylinders = dbListing.getString("engine");
        year = dbListing.getInt("year");
        url = dbListing.getString("imageurl");
        phone = dbListing.getString("phone");
        sellerId = dbListing.getInt("seller_id");
        
        if (mpgCity <= 0) {
            mpgCityStr = "N/A";
        }
        else {
            mpgCityStr = Integer.toString(mpgCity) + "mpg";
        }
        
        if (mpgHighway <= 0) {
            mpgHighwayStr = "N/A";
        }
        else {
            mpgHighwayStr = Integer.toString(mpgHighway) + "mpg";
        }
        
        if (price < 0) {
            priceStr = "N/A";
        }
        else {
            priceStr = "$" + Integer.toString(price);
        }
        
        if (mileage < 0) {
            mileageStr = "N/A";
        }
        else {
            mileageStr = Integer.toString(mileage) + " miles";
        }
        
        if (zip <= 0) {
            zipStr = "N/A";
        }
        else {
            zipStr = Integer.toString(zip);
        }
        
        
        listing = new Listings(id, make, model, trim, Integer.toString(year), carType, driveType, transmission, 
                    cylinders, fuelType, mpgCityStr, mpgHighwayStr, 
                    priceStr, color, mileageStr, condition, titleStatus, 
                    city, state, zipStr, description, url, phone, sellerId);
    }
    
    public void initConfirm() throws SQLException {
        String make;
        String model;
        String trim;
        int year;
        String yearStr;
        String carType;
        String driveType;
        String transmission;
        String cylinders;
        String fuelType;
        int mpgCity;
        String mpgCityStr;
        int mpgHighway;
        String mpgHighwayStr;
        int price;
        String priceStr;
        String color;
        int mileage;
        String mileageStr;
        String condition;
        String titleStatus;
        String city;
        String state;
        int zip;
        String zipStr;
        String description;
        String url;
        String phone;
        int sellerId;
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement("select * from cars, listing where cars.id = car_id order by listing.id desc limit 1");
        //PreparedStatement ps = con.prepareStatement("select * from listing l, cars c where l.car_id = c.id and ? = l.id");
        //ps.setInt(1, id);
        ResultSet dbListing = ps.executeQuery();
        
        dbListing.next();
        
        description = dbListing.getString("description");
        price = dbListing.getInt("price");
        color = dbListing.getString("color");
        mileage = dbListing.getInt("mileage");
        transmission = dbListing.getString("transmission");
        condition = dbListing.getString("condition");
        zip = dbListing.getInt("zip");
        state = dbListing.getString("state");
        city = dbListing.getString("city");
        titleStatus = dbListing.getString("title_status");
        make = dbListing.getString("make");
        model = dbListing.getString("model");
        trim = dbListing.getString("trim");
        carType = dbListing.getString("size");
        fuelType = dbListing.getString("fuel_type");
        mpgCity = dbListing.getInt("mpg_city");
        mpgHighway = dbListing.getInt("mpg_hwy");
        driveType = dbListing.getString("drive_type");
        cylinders = dbListing.getString("engine");
        year = dbListing.getInt("year");
        url = dbListing.getString("imageurl");
        phone = dbListing.getString("phone");
        sellerId = dbListing.getInt("seller_id");
        
        if (mpgCity <= 0) {
            mpgCityStr = "N/A";
        }
        else {
            mpgCityStr = Integer.toString(mpgCity) + "mpg";
        }
        
        if (mpgHighway <= 0) {
            mpgHighwayStr = "N/A";
        }
        else {
            mpgHighwayStr = Integer.toString(mpgHighway) + "mpg";
        }
        
        if (price < 0) {
            priceStr = "N/A";
        }
        else {
            priceStr = "$" + Integer.toString(price);
        }
        
        if (mileage < 0) {
            mileageStr = "N/A";
        }
        else {
            mileageStr = Integer.toString(mileage) + " miles";
        }
        
        if (zip <= 0) {
            zipStr = "N/A";
        }
        else {
            zipStr = Integer.toString(zip);
        }
        
        
        listing = new Listings(id, make, model, trim, Integer.toString(year), carType, driveType, transmission, 
                    cylinders, fuelType, mpgCityStr, mpgHighwayStr, 
                    priceStr, color, mileageStr, condition, titleStatus, 
                    city, state, zipStr, description, url, phone, sellerId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Listings getListing() {
        return listing;
    }

    public DBConnect getDbConnect() {
        return dbConnect;
    }

    public void setListing(Listings listing) {
        this.listing = listing;
    }

    public void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }
    
    public String confirm(){
        return "confirm";
    }
    
    public String cancel() throws SQLException{
        // Create a database connection
        Connection con = dbConnect.getConnection();
        
        if (con == null){
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement("select c.id as carID, l.id as listingID from cars c, listing l where c.id = car_id order by car_id desc limit 1");
        ResultSet dbListing = ps.executeQuery();
        dbListing.next();
        
        int carID = dbListing.getInt("carID");
        int listingID = dbListing.getInt("listingID");
        
        PreparedStatement deleteFromCars = con.prepareStatement("delete from cars where id = ?");
        deleteFromCars.setInt(1, carID);
        
        PreparedStatement deleteFromListing = con.prepareStatement("delete from listing where id = ?");
        deleteFromListing.setInt(1, listingID);
        
        deleteFromListing.executeUpdate();
        deleteFromCars.executeUpdate();
        
        return "cancel";
    }
}

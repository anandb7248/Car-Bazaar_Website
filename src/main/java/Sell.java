/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import static java.lang.Integer.MAX_VALUE;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
@Named(value = "sell")
@ManagedBean
@SessionScoped
public class Sell implements Serializable {
    private Part uploadedFile;

    public Part getUploadedFile() {
    return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
    this.uploadedFile = uploadedFile;
    }

    public String getURL(){

        try (InputStream input = uploadedFile.getInputStream()) {
            Random rand = new Random();
            
            int  n = rand.nextInt(MAX_VALUE) + 1;
            return postToAWS(String.valueOf(n), input);
            /*
            String fileName = uploadedFile.getSubmittedFileName();
            File imageFile = new File(folder, fileName);
            Files.copy(input, imageFile.toPath());
            
            return imageFile.getAbsolutePath();
            */
        }
        catch (IOException e) {
                e.printStackTrace();
        }
        
        return "EMPTY";
    }
   
    
    private String bucket_name = "csc366project2";

    private String[] makes = {
"Acura",
"Alfa Romeo",
"AMC",
"Aston Martin",
"Audi",
"Bentley",
"BMW",
"Bugatti",
"Buick",
"Cadillac",
"Chevrolet",
"Chrysler",
"Daewoo",
"Datsun",
"DeLorean",
"Dodge",
"Eagle",
"Ferrari",
"FIAT",
"Fisker",
"Ford",
"Freightliner",
"Genesis",
"Geo",
"GMC",
"Honda",
"HUMMER",
"Hyundai",
"INFINITI",
"Isuzu",
"Jaguar",
"Jeep",
"Kia",
"Lamborghini",
"Land Rover",
"Lexus",
"Lincoln",
"Lotus",
"Maserati",
"Maybach",
"Mazda",
"McLaren",
"Mercedes-Benz",
"Mercury",
"MINI",
"Mitsubishi",
"Nissan",
"Oldsmobile",
"Plymouth",
"Pontiac",
"Porsche",
"RAM",
"Rolls-Royce",
"Saab",
"Saturn",
"Scion",
"smartSRT",
"Subaru",
"Suzuki",
"Tesla",
"Toyota",
"Yugo",
"Volkswagen",
"Volvo"};

    
    private String[] states = {"AL",
"AK",
"AZ",
"AR",
"CA",
"CO",
"CT",
"DE",
"FL",
"GA",
"HI",
"ID",
"IL",
"IN",
"IA",
"KS",
"KY",
"LA",
"ME",
"MD",
"MA",
"MI",
"MN",
"MS",
"MO",
"MT",
"NE",
"NV",
"NH",
"NJ",
"NM",
"NY",
"NC",
"ND",
"OH",
"OK",
"OR",
"PA",
"RI",
"SC",
"SD",
"TN",
"TX",
"UT",
"VT",
"VA",
"WA",
"WV",
"WI",
"WY"};
    
    private String[] years = {"2018","2017","2016","2015","2014","2013","2012","2011",
        "2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","1999"
            ,"1998","1997","1996","1995","1994","1993","1992","1991","1990"};
            
    private String[] transmissions = {"Automatic", "Manual"};
    private String[] fuels = {"Gasoline", "Diesel","Hybrid","Electric","Hydrogen","Alternative"};
    private String[] styles = {"Convertible","Coupe","Sedan","Hatchback","SUV/Crossover","Truck","Van/Minivan","Wagon"};
    private String[] driveTypes = {"AWD", "Front Wheel Drive", "Rear Wheel Drive"};
    private String[] cylinders = {"3 Cylinder", "4 Cylinder", "5 Cylinder", "6 Cylinder","8 Cylinder","10 Cylinder", "12 Cylinder", "16 Cylinder", "Electric", "Hybrid", "Rotary Engine", "Fuel Cell"};
    private String[] colors = {"Black", "Beige","Blue","Brown","Burgundy","Charcoal","Gold","Gray","Green",
                            "Orange","Pink","Purple","Red","Silver","Tan","Turquoise","White","Yellow"};
    private String[] conditions = {"Excellent", "Good", "Fair"};
    private String[] titleOptions = {"Clean", "Salvage"};
    
    private String chosenMake;
    private String chosenYear;
    private String chosenTransmission;
    private String chosenFuel;
    private String chosenStyle;
    private String chosenCylinder;
    private String chosenColor;
    private String chosenCondition;
    private String chosenTitleStatus;
    private String chosenDriveType;
    private String chosenState;
    private String model;
    private String mpgcity;
    private String mpghway;
    private String trim;
    private String price;
    private String mileage;
    private String city;
    private String zip;
    private String phone;
    private String description;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getStates() {
        return states;
    }

    public void setStates(String[] states) {
        this.states = states;
    }

    public String getChosenState() {
        return chosenState;
    }

    public void setChosenState(String chosenState) {
        this.chosenState = chosenState;
    }

    private DBConnect dbConnect = new DBConnect();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChosenDriveType() {
        return chosenDriveType;
    }

    public void setChosenDriveType(String chosenDriveType) {
        this.chosenDriveType = chosenDriveType;
    }

    public DBConnect getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String[] getConditions() {
        return conditions;
    }

    public void setConditions(String[] conditions) {
        this.conditions = conditions;
    }

    public String[] getTitleOptions() {
        return titleOptions;
    }

    public void setTitleOptions(String[] titleOptions) {
        this.titleOptions = titleOptions;
    }

    public String getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(String chosenColor) {
        this.chosenColor = chosenColor;
    }

    public String getChosenCondition() {
        return chosenCondition;
    }

    public void setChosenCondition(String chosenCondition) {
        this.chosenCondition = chosenCondition;
    }

    public String getChosenTitleStatus() {
        return chosenTitleStatus;
    }

    public void setChosenTitleStatus(String chosenTitleStatus) {
        this.chosenTitleStatus = chosenTitleStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String[] getFuels() {
        return fuels;
    }

    public void setFuels(String[] fuels) {
        this.fuels = fuels;
    }

    public String[] getStyles() {
        return styles;
    }

    public void setStyles(String[] styles) {
        this.styles = styles;
    }

    public String[] getDriveTypes() {
        return driveTypes;
    }

    public void setDriveTypes(String[] driveTypes) {
        this.driveTypes = driveTypes;
    }

    public String[] getCylinders() {
        return cylinders;
    }

    public void setCylinders(String[] cylinders) {
        this.cylinders = cylinders;
    }

    public String getChosenFuel() {
        return chosenFuel;
    }

    public void setChosenFuel(String chosenFuel) {
        this.chosenFuel = chosenFuel;
    }

    public String getChosenStyle() {
        return chosenStyle;
    }

    public void setChosenStyle(String chosenStyle) {
        this.chosenStyle = chosenStyle;
    }

    public String getChosenCylinder() {
        return chosenCylinder;
    }

    public void setChosenCylinder(String chosenCylinder) {
        this.chosenCylinder = chosenCylinder;
    }
    
    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    
    public String getMpgcity() {
        return mpgcity;
    }

    public void setMpgcity(String mpgcity) {
        this.mpgcity = mpgcity;
    }

    public String getMpghway() {
        return mpghway;
    }

    public void setMpghway(String mpghway) {
        this.mpghway = mpghway;
    }

    public String[] getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(String[] transmissions) {
        this.transmissions = transmissions;
    }

    public String getChosenTransmission() {
        return chosenTransmission;
    }

    public void setChosenTransmission(String chosenTransmission) {
        this.chosenTransmission = chosenTransmission;
    }

    public String[] getYears() {
        return years;
    }

    public void setYears(String[] years) {
        this.years = years;
    }

    public String getChosenYear() {
        return chosenYear;
    }

    public void setChosenYear(String chosenYear) {
        this.chosenYear = chosenYear;
    }
    
    public String[] getMakes() {
        return makes;
    }

    public void setMakes(String[] makes) {
        this.makes = makes;
    }

    public String getChosenMake() {
        return chosenMake;
    }

    public void setChosenMake(String chosenMake) {
        this.chosenMake = chosenMake;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public void validateMPGCity(FacesContext context, UIComponent component, Object value)
        throws ValidatorException, SQLException {

        String input = value.toString();
        
        if(input.length() > 0){
            mpgcity = value.toString();
            try {
                Integer.parseInt(mpgcity);
            }
            catch (Exception e) {
                FacesMessage errorMessage = new FacesMessage("Must have a numeric value for MPG city");
                throw new ValidatorException(errorMessage);
            }            
        }
    }
    
    public void validateMPGHwy(FacesContext context, UIComponent component, Object value)
        throws ValidatorException, SQLException {

        String input = value.toString();
        
        if(input.length() > 0){
            mpghway = value.toString();
            try {
                Integer.parseInt(mpghway);
            }
            catch (Exception e) {
                FacesMessage errorMessage = new FacesMessage("Must have a numeric value for MPG highway");
                throw new ValidatorException(errorMessage);
            }
        }
    }
    
    public void validatePrice(FacesContext context, UIComponent component, Object value)
        throws ValidatorException, SQLException {
        
        price = value.toString();
        try {
            Integer.parseInt(price);
        }
        catch (NumberFormatException e) {
            FacesMessage errorMessage = new FacesMessage("Price should not include non-numbers");
            throw new ValidatorException(errorMessage);                
        }
    }
    
    public void validateMileage(FacesContext context, UIComponent component, Object value)
        throws ValidatorException, SQLException {
        
        mileage = value.toString();
        try {
            Integer.parseInt(mileage);
        }
        catch (NumberFormatException e) {
            FacesMessage errorMessage = new FacesMessage("Must only contain numbers");
            throw new ValidatorException(errorMessage);                
        }
    }
    
    public void validatePhoneNumber(FacesContext context, UIComponent component, Object value)
        throws ValidatorException, SQLException {
        
        phone = value.toString();
        try {
            Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
            Matcher matcher = pattern.matcher(phone); 
            if (!matcher.matches()) {
                FacesMessage errorMessage = new FacesMessage("Phone format [123-123-1234]");
                throw new ValidatorException(errorMessage); 
            }
        }
        catch (NumberFormatException e) {
            FacesMessage errorMessage = new FacesMessage("Must only contain numbers");
            throw new ValidatorException(errorMessage);                
        }
    }
    
    public void validateZipcode(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        
        zip = value.toString();
        
        if (zip.length() > 0) {
            try {
                Integer.parseInt(zip);
            }
            catch (NumberFormatException e) {
                FacesMessage errorMessage = new FacesMessage("Zipcode must contain only numbers");
                throw new ValidatorException(errorMessage);                
            }   
            
            if (zip.length() != 5) {
                FacesMessage errorMessage = new FacesMessage("Zip code number must be 5 digits");
                throw new ValidatorException(errorMessage);
            }
        }
    }
    
    public String postToAWS(String file_path, InputStream inputFile) throws IOException{
        /*
        InputStream is = file.getInputStream();
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(is.available());
            AmazonS3 s3Client = AwsUtil.s3Authentication();
            s3Client.putObject(new PutObjectRequest(uploadedFileLocation, updatedFileName, is, meta)
                    .withCannedAcl(CannedAccessControlList.Private));
        */
        
        BasicAWSCredentials creds = new BasicAWSCredentials("AKIAI32SNRAXQA5G6ALA", "MySm60zniDF0h+ve1G8K6z/Op8ayXMvFGD3cayQZ");
        AmazonS3 s3 = AmazonS3Client.builder()
            .withRegion("us-west-1")
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .build();
        
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
        
        String bucket_name = "csc366project2";
        System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(inputFile.available());
            s3.putObject(new PutObjectRequest(bucket_name, file_path, inputFile, meta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            
            return "https://s3-us-west-1.amazonaws.com/" + bucket_name + "/" + file_path;
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.err.println("In catch block");
        }
        //return "";
        return null;
    }
    
    public String postListing() throws ValidatorException, SQLException{
        // Create a database connection
        Connection con = dbConnect.getConnection();
        int carID;
        
        if (con == null){
            throw new SQLException("Can't get database connection");
        }
        
        // Post image to AWS
        //postToAWS();
        //String filepath = "/Users/anandb/Desktop"+image.getSubmittedFileName();
        
        // Insert into cars
        PreparedStatement ps = con.prepareStatement("insert into cars"
                + "(make, model, trim, size, fuel_type, engine, mpg_city, mpg_hwy, drive_type, year)"
                + "values(?,?,?,?,?,?,?,?,?,?)");
       
        ps.setString(1, chosenMake);
        ps.setString(2, model);
        ps.setString(3, trim);
        ps.setString(4, chosenStyle);
        ps.setString(5, chosenFuel);
        ps.setString(6, chosenCylinder);
        
        if(mpgcity.length() > 0)
            ps.setInt(7, Integer.parseInt(mpgcity));
        else
            ps.setNull(7, Types.INTEGER);
        
        if(mpghway.length() > 0)
            ps.setInt(8, Integer.parseInt(mpghway));
        else
            ps.setNull(8, Types.INTEGER);
        
        ps.setString(9, chosenDriveType);
        ps.setInt(10, Integer.parseInt(chosenYear));
        
        ps.executeUpdate();
        
        // Set carID
        ps = con.prepareStatement("SELECT id FROM cars ORDER BY id DESC LIMIT 1");
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            carID = rs.getInt(1);
        }else{
            carID = -10;
        }
        
        // Insert into listings table
        ps = con.prepareStatement("insert into listing"
                + "(description, price, color, mileage, transmission, condition, zip, city, state, title_status, car_id, imageurl, phone)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        ps.setString(1, description);
        ps.setInt(2, Integer.parseInt(price));
        ps.setString(3, chosenColor);
        ps.setInt(4, Integer.parseInt(mileage));
        ps.setString(5, chosenTransmission);
        ps.setString(6, chosenCondition);
        ps.setInt(7, Integer.parseInt(zip));
        if(city.length() > 0)
            ps.setString(8, city);
        else
            ps.setNull(8, Types.VARCHAR);
        
        ps.setString(9, chosenState);
        ps.setString(10, chosenTitleStatus);
        ps.setInt(11, carID);
        ps.setString(12, getURL());
        ps.setString(13, phone);
        
        ps.executeUpdate();
        
        return "continue";
    }
}
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@Named(value = "buy")
@ManagedBean
@SessionScoped
@ViewScoped
public class Buy implements Serializable {
    
    private List<Listings> listings;
    private Listings selectedListing;
    
    private String[] makes = {
        "",
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
    
    private String[] states = {
        "",
        "AL",
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
    
    private String[] years = {"","2018","2017","2016","2015","2014","2013","2012","2011",
        "2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","1999"
            ,"1998","1997","1996","1995","1994","1993","1992","1991","1990"};
            
    private String[] transmissions = {"","Automatic", "Manual"};
    private String[] fuels = {"","Gasoline", "Diesel","Hybrid","Electric","Hydrogen","Alternative"};
    private String[] styles = {"","Convertible","Coupe","Sedan","Hatchback","SUV/Crossover","Truck","Van/Minivan","Wagon"};
    private String[] driveTypes = {"","AWD", "Front Wheel Drive", "Rear Wheel Drive"};
    private String[] cylinders = {"","3 Cylinder", "4 Cylinder", "5 Cylinder", "6 Cylinder","8 Cylinder","10 Cylinder", "12 Cylinder", "16 Cylinder", "Electric", "Hybrid", "Rotary Engine", "Fuel Cell"};
    private String[] colors = {"","Black", "Beige","Blue","Brown","Burgundy","Charcoal","Gold","Gray","Green",
                            "Orange","Pink","Purple","Red","Silver","Tan","Turquoise","White","Yellow"};
    private String[] conditions = {"","Excellent", "Good", "Fair"};
    private String[] titleOptions = {"","Clean", "Salvage"};
    private String[] radiusOptions = {"","5","10","15","20","25","50"};
    private String carTypeValue;
    
    private String chosenMake;
    private String chosenMinYear;
    private String chosenMaxYear;
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
    private String minPrice;
    private String maxPrice;
    private String minMileage;
    private String maxMileage;
    private String city;
    private String zip;
    private String description;
    private String chosenRadius;
    private boolean carTypeEnabled = false;
    private boolean driveTypeEnabled = false;
    
    public Buy() throws SQLException {   
        listings = new ArrayList<Listings>();
        Connection con = dbConnect.getConnection();
        int lid;
        String lmake;
        String lmodel;
        String ltrim;
        int lyear;
        String lyearStr;
        String lcarType;
        String ldriveType;
        String ltransmission;
        String lcylinders;
        String lfuelType;
        int lmpgCity;
        String lmpgCityStr;
        int lmpgHighway;
        String lmpgHighwayStr;
        int lprice;
        String lpriceStr;
        String lcolor;
        int lmileage;
        String lmileageStr;
        String lcondition;
        String ltitleStatus;
        String lcity;
        String lstate;
        int lzip;
        String lzipStr;
        String ldescription;
        String lurl;
        String lphone;
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement queryListings = con.prepareStatement("select * from listing l, cars c where l.car_id = c.id");
        ResultSet dbListings = queryListings.executeQuery();
        
        while (dbListings.next()) {
            lid = dbListings.getInt(1);
            ldescription = dbListings.getString("description");
            lprice = dbListings.getInt("price");
            lcolor = dbListings.getString("color");
            lmileage = dbListings.getInt("mileage");
            ltransmission = dbListings.getString("transmission");
            lcondition = dbListings.getString("condition");
            lzip = dbListings.getInt("zip");
            lstate = dbListings.getString("state");
            lcity = dbListings.getString("city");
            ltitleStatus = dbListings.getString("title_status");
            lmake = dbListings.getString("make");
            lmodel = dbListings.getString("model");
            ltrim = dbListings.getString("trim");
            lcarType = dbListings.getString("size");
            lfuelType = dbListings.getString("fuel_type");
            lmpgCity = dbListings.getInt("mpg_city");
            lmpgHighway = dbListings.getInt("mpg_hwy");
            ldriveType = dbListings.getString("drive_type");
            lcylinders = dbListings.getString("engine");
            lyear = dbListings.getInt("year");
            lurl = dbListings.getString("imageurl");
            lphone = dbListings.getString("phone");
            
            if (lmpgCity <= 0) {
                lmpgCityStr = "N/A";
            }
            else {
                lmpgCityStr = Integer.toString(lmpgCity) + "mpg";
            }

            if (lmpgHighway <= 0) {
                lmpgHighwayStr = "N/A";
            }
            else {
                lmpgHighwayStr = Integer.toString(lmpgHighway) + "mpg";
            }

            if (lprice < 0) {
                lpriceStr = "N/A";
            }
            else {
                lpriceStr = "$" + Integer.toString(lprice);
            }

            if (lmileage < 0) {
                lmileageStr = "N/A";
            }
            else {
                lmileageStr = Integer.toString(lmileage) + " miles";
            }

            if (lzip <= 0) {
                lzipStr = "N/A";
            }
            else {
                lzipStr = Integer.toString(lzip);
            }
            
            listings.add(new Listings(lid, lmake, lmodel, ltrim, Integer.toString(lyear), lcarType, ldriveType, ltransmission, 
                    lcylinders, lfuelType, lmpgCityStr, lmpgHighwayStr, 
                    lpriceStr, lcolor, lmileageStr, lcondition, ltitleStatus, 
                    lcity, lstate, lzipStr, ldescription, lurl, lphone));
        }
        
        
    }
    
    public List<Listings> getListings() {
        return listings;
    }

    public Listings getSelectedListing() {
        return selectedListing;
    }

    public void setListings(List<Listings> listings) {
        this.listings = listings;
    }

    public void setSelectedListing(Listings selectedListing) {
        this.selectedListing = selectedListing;
    }    
    
    public boolean getCarTypeEnabled() {
        return carTypeEnabled;
    }

    public void setCarTypeEnabled(boolean carTypeEnabled) {
        this.carTypeEnabled = carTypeEnabled;
    }

    public boolean getDriveTypeEnabled() {
        return driveTypeEnabled;
    }
        
    public void setDriveTypeEnabled(boolean driveTypeEnabled) {
        this.driveTypeEnabled = driveTypeEnabled;
    }    
        
    public void toggleCarType(){
        carTypeEnabled = !carTypeEnabled;
    }
    
//    public String isCarTypeEnabled(){
//        if(carTypeEnabled == true)
//            return "true";
//        else
//            return "false";
//    }
//    
//    public String carTypeToggleLabel(){
//        if(carTypeEnabled == true){
//            return "v Car Type";
//        }else{
//            return "> Car Type";
//        }
//    }
    
    public void toggleDriveType(){
        driveTypeEnabled = !driveTypeEnabled;
    }
    
//    public String isDriveTypeEnabled(){
//        if(driveTypeEnabled == true)
//            return "true";
//        else
//            return "false";
//    }
//    
//    public String driveTypeToggleLabel(){
//        if(driveTypeEnabled == true){
//            return "v Drive Type";
//        }else{
//            return "> Drive Type";
//        }
//    }

    public String[] getRadiusOptions() {
        return radiusOptions;
    }

    public void setRadiusOptions(String[] radiusOptions) {
        this.radiusOptions = radiusOptions;
    }

    public String getChosenRadius() {
        return chosenRadius;
    }

    public void setChosenRadius(String chosenRadius) {
        this.chosenRadius = chosenRadius;
    }

    public String getCarTypeValue() {
        return carTypeValue;
    }

    public void setCarTypeValue(String carTypeValue) {
        this.carTypeValue = carTypeValue;
    }
    
    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
    
    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
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
    
    public String getMinMileage() {
        return minMileage;
    }

    public void setMinMileage(String minMileage) {
        this.minMileage = minMileage;
    }

    public String getMaxMileage() {
        return maxMileage;
    }

    public void setMaxMileage(String maxMileage) {
        this.maxMileage = maxMileage;
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

    public String getChosenMinYear() {
        return chosenMinYear;
    }

    public String getChosenMaxYear() {
        return chosenMaxYear;
    }

    public boolean isCarTypeEnabled() {
        return carTypeEnabled;
    }

    public boolean isDriveTypeEnabled() {
        return driveTypeEnabled;
    }

    public void setChosenMinYear(String chosenMinYear) {
        this.chosenMinYear = chosenMinYear;
    }

    public void setChosenMaxYear(String chosenMaxYear) {
        this.chosenMaxYear = chosenMaxYear;
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
    /*
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
    */
    /*
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
    */
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
    
    public void search() throws ValidatorException, SQLException, MalformedURLException, IOException, NumberFormatException{
        // Create & test database connection
        Connection con = dbConnect.getConnection();
        listings = new ArrayList<Listings>();
        int lid;
        String lmake;
        String lmodel;
        String ltrim;
        int lyear;
        String lyearStr;
        String lcarType;
        String ldriveType;
        String ltransmission;
        String lcylinders;
        String lfuelType;
        int lmpgCity;
        String lmpgCityStr;
        int lmpgHighway;
        String lmpgHighwayStr;
        int lprice;
        String lpriceStr;
        String lcolor;
        int lmileage;
        String lmileageStr;
        String lcondition;
        String ltitleStatus;
        String lcity;
        String lstate;
        int lzip;
        String lzipStr;
        String ldescription;
        String lurl;
        String lphone;        
        
        if (con == null){
            throw new SQLException("Can't get database connection");
        }
        //setup partial query statement for conditions
        String qZip;
        String qMake;
        String qModel;
        
        if (zip.equals("")) 
            qZip = "";
        else 
            qZip = "zip = " + zip + " and ";

        
        if (chosenMake == null) 
            qMake = "";
        else 
            qMake = "make = '" + chosenMake + "' and ";
        
        if (model.equals("")) 
            qModel = "";
        else 
            qModel = "model = '" + model + "' and ";
        
        String qTrim;
        if (trim.equals("")) {
            qTrim = "";
        } else {
            qTrim = "trim = '" + trim + "' and ";
        }
        
        String qYear;
        if (chosenMinYear == null && chosenMaxYear == null)
            qYear = "";
        else if (chosenMinYear!= null && chosenMaxYear == null) {
            qYear = "(year > " + chosenMinYear + ") and ";
        }
        else if (chosenMinYear == null && chosenMaxYear != null) {
            qYear = "(year < " + chosenMaxYear + ") and ";
        }
        else {
            qYear = "(year between " + chosenMinYear + " and " + chosenMaxYear + ") and ";       
        }
        
        String qPrice;        
        if (minPrice.equals("") && maxPrice.equals(""))
            qPrice = "";
        else if (!minPrice.equals("") && maxPrice.equals("")) {
            qPrice = "(price > " + minPrice + ") and ";
        }
        else if (minPrice.equals("") && !maxPrice.equals("")) {
            qPrice = "(price < " + maxPrice + ") and ";
        }
        else {
            qPrice = "(price between " + minPrice + " and " + maxPrice + ") and ";       
        }
        
        String qMileage;
        if (minMileage.equals("") && maxMileage.equals(""))
            qMileage = "";
        else if (!minMileage.equals("") && maxMileage.equals("")) {
            qMileage = "(mileage > " + minMileage + ") and ";
        }
        else if (minMileage.equals("") && !maxMileage.equals("")) {
            qMileage = "(mileage < " + maxMileage + ") and ";
        }
        else {
            qMileage = "(mileage between " + minMileage + " and " + maxMileage + ") and ";       
        }
            
        String qStyle;
        if (chosenStyle != null) {
            qStyle = "size = '" + chosenStyle + "' and ";
        } else {
            qStyle = "";
        }
        String qDriveType;
        if (chosenDriveType != null) {
            qDriveType = "drive_type = '" + chosenDriveType + "' and ";
        } else {
            qDriveType = "";
        }
        String qTransmission;
        if (chosenTransmission != null) {
            qTransmission = "transmission = '" + chosenTransmission + "' and ";
        } else {
            qTransmission = "";
        }
        String qEngine;
        if (chosenCylinder != null) {
            qEngine = "engine = '" + chosenCylinder + "' and ";
        } else {
            qEngine = "";
        }
        String qFuel;
        if (chosenFuel != null) {
            qFuel = "fuel_type = '" + chosenFuel + "' and ";
        } else {
            qFuel = "";
        }
        String qMpgCity;
        if (!mpgcity.equals("")) {
            qMpgCity = "mpg_city <= '" + mpgcity + "' and ";
        } else {
            qMpgCity = "";
        }
        String qMpgHwy;
        if (!mpghway.equals("")) {
            qMpgHwy = "mpg_hwy <= '" + mpghway + "' and ";
        } else {
            qMpgHwy = "";
        }
        String qColor;
        if (chosenColor != null) {
            qColor = "color = '" + chosenColor + "' and ";
        } else {
            qColor = "";
        }
        String qTitleStatus;
        if (chosenTitleStatus != null) {
            qTitleStatus = "title_status = '" + chosenTitleStatus + "' and ";
        } else {
            qTitleStatus = "";
        }
        String qCity;
        if (!city.equals("")) {
            qCity = "city = '" + city + "' and ";
        } else {
            qCity = "";
        }
        String qState;
        if (chosenState != null) {
            qState = "state = '" + chosenState + "'";
        } else {
            qState = "";
        }
        

        // outer join cars and listing attributes and search
        String searchQuery = "SELECT * FROM listing l, cars c where c.id = l.car_id and "
                + qZip + qMake + qModel + qTrim + qYear + qPrice + qMileage + qStyle
                + qDriveType + qTransmission + qEngine + qFuel + qMpgCity + qMpgHwy
                + qColor + qTitleStatus + qCity + qState + ";";
        

        String checkAnd = searchQuery.substring(searchQuery.length()-6, searchQuery.length());
        System.out.println("checkAnd " + checkAnd);
        if (checkAnd.equals(" and ;")) {
            searchQuery = searchQuery.substring(0,searchQuery.length()-6);
            searchQuery = searchQuery + ";";
            System.out.println("in here " + searchQuery);
        }
            
        

        System.out.println(searchQuery);
        
        PreparedStatement ps = con.prepareStatement(searchQuery);
        ResultSet dbListings = ps.executeQuery();
        
        while (dbListings.next()) {  
            lid = dbListings.getInt(1);
            ldescription = dbListings.getString("description");
            lprice = dbListings.getInt("price");
            lcolor = dbListings.getString("color");
            lmileage = dbListings.getInt("mileage");
            ltransmission = dbListings.getString("transmission");
            lcondition = dbListings.getString("condition");
            lzip = dbListings.getInt("zip");
            lstate = dbListings.getString("state");
            lcity = dbListings.getString("city");
            ltitleStatus = dbListings.getString("title_status");
            lmake = dbListings.getString("make");
            lmodel = dbListings.getString("model");
            ltrim = dbListings.getString("trim");
            lcarType = dbListings.getString("size");
            lfuelType = dbListings.getString("fuel_type");
            lmpgCity = dbListings.getInt("mpg_city");
            lmpgHighway = dbListings.getInt("mpg_hwy");
            ldriveType = dbListings.getString("drive_type");
            lcylinders = dbListings.getString("engine");
            lyear = dbListings.getInt("year");
            lurl = dbListings.getString("imageurl");
            lphone = dbListings.getString("phone");
            
            if (lmpgCity <= 0) {
                lmpgCityStr = "N/A";
            }
            else {
                lmpgCityStr = Integer.toString(lmpgCity) + "mpg";
            }

            if (lmpgHighway <= 0) {
                lmpgHighwayStr = "N/A";
            }
            else {
                lmpgHighwayStr = Integer.toString(lmpgHighway) + "mpg";
            }

            if (lprice < 0) {
                lpriceStr = "N/A";
            }
            else {
                lpriceStr = "$" + Integer.toString(lprice);
            }

            if (lmileage < 0) {
                lmileageStr = "N/A";
            }
            else {
                lmileageStr = Integer.toString(lmileage) + " miles";
            }

            if (lzip <= 0) {
                lzipStr = "N/A";
            }
            else {
                lzipStr = Integer.toString(lzip);
            }
            
            listings.add(new Listings(lid, lmake, lmodel, ltrim, Integer.toString(lyear), lcarType, ldriveType, ltransmission, 
                    lcylinders, lfuelType, lmpgCityStr, lmpgHighwayStr, 
                    lpriceStr, lcolor, lmileageStr, lcondition, ltitleStatus, 
                    lcity, lstate, lzipStr, ldescription, lurl, lphone));
            
            //show attribute values on console
//            System.out.println("Cid : " + tCid);
//            System.out.println("Make : " + tMake);
//            System.out.println("Model : " + tModel);
//            System.out.println("Trim : " + tTrim);
//            System.out.println("Size : " + tSize);
//            System.out.println("Fuel_type : " + tFuel_type);
//            System.out.println("Mpg_city : " + tMpg_city);
//            System.out.println("Mpg_hwy : " + tMpg_hwy);
//            System.out.println("Drive_type : " + tDrive_type);
//            System.out.println("Engine : " + tEngine);
//            System.out.println("Year : " + tYear);
//            System.out.println("Lid : " + tLid);
//            System.out.println("Description : " + tDescription);
//            System.out.println("Price : " + tPrice);
//            System.out.println("Color : " + tColor);
//            System.out.println("Mileage : " + tMileage);
//            System.out.println("Transmission : " + tTransmission);
//            System.out.println("Condition : " + tCondition);
//            System.out.println("State : " + tState);
//            System.out.println("City : " + tCity);
//            System.out.println("Title_status : " + tTitle_status);
//            System.out.println("Car_id : " + tCar_id);
//            System.out.println("Zip : " + tZip);
//            System.out.println("imageurl : " + tUrl);
        }        
    }
    
}
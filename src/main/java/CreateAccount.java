import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
@Named(value = "createAccount")
@SessionScoped
@ManagedBean
public class CreateAccount {
    private String username;
    private String password;
    private UIInput passwordUI;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String email;   
    private String phone;
    private String currPassword;
    private UIInput newPasswordUI;
    private String newPassword;
    private String confirmNewPassword;
    private DBConnect dbConnect = new DBConnect();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UIInput getPasswordUI() {
        return passwordUI;
    }

    public void setPasswordUI(UIInput passwordUI) {
        this.passwordUI = passwordUI;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DBConnect getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrPassword() {
        return currPassword;
    }

    public void setCurrPassword(String currPassword) {
        this.currPassword = currPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public UIInput getNewPasswordUI() {
        return newPasswordUI;
    }

    public void setNewPasswordUI(UIInput newPasswordUI) {
        this.newPasswordUI = newPasswordUI;
    }
             
    public void validateUsername(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {

        username = value.toString();
        
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement("select * from users where username = ?");
        ps.setString(1, username);
        ResultSet result = ps.executeQuery();
        
        if (result.next()) {
            FacesMessage errorMessage = new FacesMessage("Username has already been chosen");
            throw new ValidatorException(errorMessage);   
        }
    }   
    
    public void validatePassword(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        
        if (passwordUI.getLocalValue() != null) {
            password = passwordUI.getLocalValue().toString();
        }
        confirmPassword = value.toString();
            
        if (password != null && confirmPassword != null) { 
            if (!password.equals(confirmPassword)) {
                FacesMessage errorMessage = new FacesMessage("Passwords do not match");
                throw new ValidatorException(errorMessage);
            }
        }
        if (password == null) {
            FacesMessage errorMessage = new FacesMessage("Please enter a password");
            throw new ValidatorException(errorMessage);            
        }
    }

    public void validateEmail(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {

        int valid = 0;
        int atIndex;
        int comIndex;
        email = value.toString();

        if ((atIndex = email.indexOf('@')) > 0 && atIndex != 0) {
            if ((comIndex = email.indexOf(".com")) > 0 && comIndex > atIndex + 1) {
                valid = 1;
            }
            else if ((comIndex = email.indexOf(".edu")) > 0 && comIndex > atIndex + 1) {
                valid = 1;
            }            
        }
        
        if (valid == 0) {
            FacesMessage errorMessage = new FacesMessage("Email must have a valid domain");
            throw new ValidatorException(errorMessage);
        }
    }    
    
    public void validatePhone(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {    
        
        phone = value.toString();
        try {
            Long.parseLong(phone);
        }
        catch (NumberFormatException e) {
            FacesMessage errorMessage = new FacesMessage("Phone number must contain only numbers");
            throw new ValidatorException(errorMessage);                
        }
        if (phone.length() != 10) {
            FacesMessage errorMessage = new FacesMessage("Phone number must be 10 digits");
            throw new ValidatorException(errorMessage);
        }       
    }
    
    /* Next functions used for changing account passwords */
    
    public void validateOldPW(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        
        currPassword = value.toString();
        int currUser = Login.userId;
        
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement("select password from users where id = ?");
        ps.setInt(1, currUser);
        ResultSet result = ps.executeQuery();
        result.next();
        if (!((result.getString(1)).equals(currPassword))) {
            FacesMessage errorMessage = new FacesMessage("Current password incorrect");
            throw new ValidatorException(errorMessage);   
        }        
    }

    public void validateNewPW(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        
        if (newPasswordUI.getLocalValue() != null) {
            newPassword = newPasswordUI.getLocalValue().toString();
        }
        confirmNewPassword = value.toString();
            
        if (newPassword != null && confirmNewPassword != null) { 
            if (!newPassword.equals(confirmNewPassword)) {
                FacesMessage errorMessage = new FacesMessage("Passwords do not match");
                throw new ValidatorException(errorMessage);
            }
        }
        if (newPassword == null) {
            FacesMessage errorMessage = new FacesMessage("Please enter a new password");
            throw new ValidatorException(errorMessage);            
        }
    }    
    
    public String changePassword() throws ValidatorException, SQLException {
        Connection con = dbConnect.getConnection();
        int currUser = Login.userId;
        System.out.println(currUser);
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }  

        PreparedStatement ps = con.prepareStatement("update users set password = ? where id = ?");
        ps.setString(1, newPassword);
        ps.setInt(2, currUser);
        ps.executeUpdate();
        
        return "change";        
    }
    
    public void create() throws ValidatorException, SQLException {
       
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }        
        
        PreparedStatement ps = con.prepareStatement("insert into users"
                + "(first_name, last_name, email, phone, username, password) "
                + "values(?, ?, ?, ?, ?, ?)");        
        
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, email);
        ps.setString(4, phone);
        ps.setString(5, username);
        ps.setString(6, password);
        

        ps.executeUpdate();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal({title: \"Success!\", text: \"Account Created Successfully.\", type: \"success\",}).then(function(){window.location.href = \"home.xhtml\";})");
        
    }    
    
    public String login() {
        return "login";
    }
}



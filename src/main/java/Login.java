
import java.io.Serializable;
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


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author stanchev
 */
@Named(value = "login")
@SessionScoped
@ManagedBean
public class Login implements Serializable {

    private String login;
    private String password;
    private UIInput loginUI;
    private DBConnect dbConnect = new DBConnect();
    public static int userId;
//    private String loginType = new String();
    
    public UIInput getLoginUI() {
        return loginUI;
    }

    public void setLoginUI(UIInput loginUI) {
        this.loginUI = loginUI;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getLoginType() {
//        return loginType;
//    }
//
//    public void setLoginType(String loginType) {
//        this.loginType = loginType;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        if (loginUI.getLocalValue() != null) {
            login = loginUI.getLocalValue().toString();
        }
        if (login == null) {
            FacesMessage errorMessage = new FacesMessage("Username is required");
            throw new ValidatorException(errorMessage);            
        }        
        password = value.toString();
        
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        // Check if a user is logged in       
        PreparedStatement userPS = con.prepareStatement("select * from users where username = ? and password = ?");
        userPS.setString(1, login);
        userPS.setString(2, password);
        ResultSet userResult = userPS.executeQuery();

        if(!userResult.next()){
            // Login/password does not exist in any tables.
            FacesMessage errorMessage = new FacesMessage("Wrong username / password");
            throw new ValidatorException(errorMessage);
        } else {
            // Login/password does exist and get the id of the user that logged in 
            PreparedStatement uIdPS = con.prepareStatement("select id from users where username = ? and password = ?");
            uIdPS.setString(1, login);
            uIdPS.setString(2, password);
            ResultSet uIdResult = uIdPS.executeQuery();
            uIdResult.next();
            userId = uIdResult.getInt(1);
         }
    }

    public String login() {
        return "login";
    }
}
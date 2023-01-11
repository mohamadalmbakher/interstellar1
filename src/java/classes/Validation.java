/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author MOHAMAD ALMBAKHER
 */
@ManagedBean(name = "Validation", eager = true)
@RequestScoped

public class Validation {

    private String username;
    private String firstname;
    private String email;
    private String password;
    private String confilrmPassword;

    public Validation(String username, String firstname, String email, String password, String confilrmPassword) {
        this.username = username;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.confilrmPassword = confilrmPassword;
    }

    public Validation(){
        
    }
    
     public String checkUsernameIfExist() throws SQLException, ClassNotFoundException {
        Connection conn = DbConnection.getConnection();

        String selectquarey = "SELECT * from USERS WHERE USERNAME='" + getUsername() + "'";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(selectquarey);
        if (rs.next()) {
            return rs.getString("username")+ " This username already exists try another";
        } else {
            return getUsername()+"fgd";
        }
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfilrmPassword() {
        return confilrmPassword;
    }

    public void setConfilrmPassword(String confilrmPassword) {
        this.confilrmPassword = confilrmPassword;
    }

    public String response() {
        if (firstname != null && email != null && username != null && password != null && confilrmPassword != null) {
            return "<p style=\"background-color:yellow;width:200px;"
                    + "padding:5px\">Name: " + getFirstname() + "<br/>E-Mail: "
                    + getEmail() + "<br/>Phone: " + getEmail()+ "</p>";
        } else {
            return "";
        } // request has not yet been made
    } // end method getResult

}

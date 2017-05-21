/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecords;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.ResultSet;
import java.sql.SQLException;

//underWarrantyCol

/**
 *
 * @author samaggarwal
 */
public class Warrenty 
{
    private StringProperty registrationCol;
    
    private StringProperty companyNameCol;
    private StringProperty companyAddressCol;
    private StringProperty expiryDateCol;
    private StringProperty companyIDCol;
    
    

 public Warrenty (ResultSet rs) 
    {
        try 
        {
            this.registrationCol = new SimpleStringProperty(rs.getString("Registration"));
            
            this.companyNameCol = new SimpleStringProperty(rs.getString("CompanyName"));
            this.companyAddressCol = new SimpleStringProperty(rs.getString("CompanyAddress"));
            this.expiryDateCol = new SimpleStringProperty(rs.getString("ExpiryDate"));
            this.companyIDCol = new SimpleStringProperty(rs.getString("CompanyID"));
           
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
 
 
 
public String getregistrationCol() 
{
        return registrationCol.get();
    }

    public StringProperty registrationColProperty() {
        return registrationCol;
    }

    public void registrationCol(String registration) {
        this.registrationCol.set(registration);
    }
  
    
 
    
    public String getcompanyNameCol() {
        return companyNameCol.get();
    }

    public StringProperty companyNameColProperty() {
        return companyNameCol;
    }

    public void setcompanyNameCol(String companyName) {
        this.companyNameCol.set(companyName);
    }

    public String getcompanyAddressCol() {
        return companyAddressCol.get();
    }

    public StringProperty companyAddressColProperty() {
        return companyAddressCol;
    }

    public void setcompanyAddressCol(String companyAddress) {
        this.companyAddressCol.set(companyAddress);
    }

    public String getexpiryDateCol() {
        return expiryDateCol.get();
    }

    public StringProperty expiryDateColProperty() {
        return expiryDateCol;
    }

    public void setexpiryDateCol(String expiryDate) {
        this.expiryDateCol.set(expiryDate);
    }

    public String getcompanyIDCol() {
        return companyIDCol.get();
    }

    public StringProperty companyIDColProperty() {
        return companyIDCol;
    }

    public void setcompanyIDCol(String companyID) {
        this.companyIDCol.set(companyID);
    }
}



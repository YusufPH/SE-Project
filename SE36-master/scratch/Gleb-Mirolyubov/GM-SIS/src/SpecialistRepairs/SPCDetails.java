/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialistRepairs;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author gleb_mirolyubov
 */
public class SPCDetails {
    
    private SimpleIntegerProperty BookingID;
    private SimpleStringProperty Name;
    private SimpleStringProperty SPC;
    private SimpleStringProperty Type;
    private SimpleStringProperty Details;
    private SimpleIntegerProperty CustomerID;
    private SimpleStringProperty CustomerName;

    /* 
    *  A constructor to initialize SPC variables from data from the database
    */ 
    SPCDetails(ResultSet rs) throws SQLException {
            
            this.BookingID = new SimpleIntegerProperty(rs.getInt("BookingID"));
            this.SPC = new SimpleStringProperty(rs.getString("SPC"));
            this.Name = new SimpleStringProperty(rs.getString("Name"));
            this.Type = new SimpleStringProperty(rs.getString("Type"));
            this.Details = new SimpleStringProperty(rs.getString("Details"));
            this.CustomerID = new SimpleIntegerProperty(rs.getInt("CustomerID"));
            this.CustomerName = new SimpleStringProperty(rs.getString("CustomerName"));
        }

        /* 
        *  The following methods are getters and setters for this class
        */ 
    
        public int getBookingID() 
        {
            return BookingID.get();
        }
        
        public IntegerProperty BookingIDProperty()
        {
            return BookingID;
        }

        public String getSPC() 
        {
            return SPC.get();
        }

        public void setSPC(String spcName) 
        {
            SPC.set(spcName);
        }
        
        public String getType() 
        {
            return Type.get();
        }
 
        public void setType(String type) 
        {
            Type.set(type);
        }

        public String getName()
        {
            return Name.get();
        }

        public void setName(String name)
        {
            Name.set(name);
        }
        
        public String getDetails() 
        {
            return Details.get();
        }

        public void setDetails(String det) 
        {
            Details.set(det);
        }

        public int getCustomerID(){
            return CustomerID.get();
        }

        public void setCustomerID(int custID){
            CustomerID.set(custID);
        }

        public String getCustomerName() 
        {
            return CustomerName.get();
        }

        public void setCustomerName(String custName) 
        {
            CustomerName.set(custName);
        }
}

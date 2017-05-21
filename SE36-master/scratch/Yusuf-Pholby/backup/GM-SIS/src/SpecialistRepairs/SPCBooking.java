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
public class SPCBooking {
    
    private SimpleIntegerProperty BookingID;
    private SimpleStringProperty Name;
    private SimpleStringProperty SPC;
    private SimpleStringProperty Type;
    private SimpleStringProperty RegistrationNumber;
    private SimpleStringProperty Details;
    private SimpleIntegerProperty Cost;
    private SimpleIntegerProperty CustomerID;
    private SimpleStringProperty CustomerName;
    private SimpleStringProperty DeliveryDate;
    private SimpleStringProperty ReturnDate;

    /* 
    *  A constructor to initialize all variables by assigning values passed from the other class
    */ 
    public SPCBooking(ResultSet rs) throws SQLException {
            
            this.BookingID = new SimpleIntegerProperty(rs.getInt("BookingID"));
            this.SPC = new SimpleStringProperty(rs.getString("SPC"));
            this.Name = new SimpleStringProperty(rs.getString("Name"));
            this.Type = new SimpleStringProperty(rs.getString("Type"));
            this.RegistrationNumber = new SimpleStringProperty(rs.getString("RegistrationNumber"));
            this.Details = new SimpleStringProperty(rs.getString("Details"));
            this.Cost = new SimpleIntegerProperty(rs.getInt("Cost"));
            this.CustomerID = new SimpleIntegerProperty(rs.getInt("CustomerID"));
            this.CustomerName = new SimpleStringProperty(rs.getString("CustomerName"));
            this.DeliveryDate = new SimpleStringProperty(rs.getString("DeliveryDate"));
            this.ReturnDate = new SimpleStringProperty(rs.getString("ReturnDate"));
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
        
        public String getRegistrationNumber()
        {
            return RegistrationNumber.get();
        }
        
        public void setRegistrationNumber(String n)
        {
            RegistrationNumber.set(n);
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
        
        public Integer getCost()
        {
            return Cost.get();
        }
        
        public void setCost(int c)
        {
            Cost.set(c);
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
 
        public String getDeliveryDate() 
        {
            return DeliveryDate.get();
        }

        public void setDeliveryDate(String date) 
        {
            DeliveryDate.set(date);
        }
        
        public String getReturnDate() 
        {
            return ReturnDate.get();
        }
 
        public void setReturnDate(String date) 
        {
            ReturnDate.set(date);
        }
}
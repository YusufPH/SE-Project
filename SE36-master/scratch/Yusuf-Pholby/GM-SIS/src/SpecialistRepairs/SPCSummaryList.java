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
public class SPCSummaryList {
    private SimpleIntegerProperty BookingID;
    private SimpleStringProperty Name;
    private SimpleStringProperty Type;
    private SimpleStringProperty ReturnDate;

    /* 
    *  This constructor sets values passed from the database 
    */ 
    SPCSummaryList(ResultSet rs) throws SQLException 
    {        
        this.BookingID = new SimpleIntegerProperty(rs.getInt("BookingID"));
        this.Name = new SimpleStringProperty(rs.getString("Name"));
        this.Type = new SimpleStringProperty(rs.getString("Type"));
        this.ReturnDate = new SimpleStringProperty(rs.getString("ReturnDate"));
    }

    /* 
    *  This method gets the booking id
    */ 
        public int getBookingID() 
        {
            return BookingID.get();
        }
        
    /* 
    *  This method return the booking id
    */ 
        public IntegerProperty BookingIDProperty()
        {
            return BookingID;
        }
        
    /* 
    *  This method gets the name of booking
    */     
        public String getName()
        {
            return Name.get();
        }
        
    /* 
    *  This method sets the name of booking
    */ 
        public void setName(String name)
        {
            Name.set(name);
        }
        
    /* 
    *  This method gets the type of item
    */ 
        public String getType() 
        {
            return Type.get();
        }
 
    /* 
    *  This method sets the type of item
    */ 
        public void setType(String type) 
        {
            Type.set(type);
        }
        
    /* 
    *  This method gets the return date
    */ 
        public String getReturnDate() 
        {
            return ReturnDate.get();
        }
 
    /* 
    *  This method sets the return date
    */ 
        public void setReturnDate(String date) 
        {
            ReturnDate.set(date);
        }
}

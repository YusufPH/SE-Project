/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialistRepairs;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author gleb_mirolyubov
 */
       public class SPC {
 
        private SimpleIntegerProperty CustomerID;
        private SimpleIntegerProperty ID;
        private SimpleStringProperty Name;
        private SimpleStringProperty Address;
        private SimpleStringProperty Phone;
        private SimpleStringProperty Email;

        SPC(ResultSet rs) throws SQLException {
            
            this.CustomerID = new SimpleIntegerProperty(rs.getInt("CustomerID"));
            this.ID = new SimpleIntegerProperty(rs.getInt("ID"));
            this.Name = new SimpleStringProperty(rs.getString("Name"));
            this.Address = new SimpleStringProperty(rs.getString("Address"));
            this.Phone = new SimpleStringProperty(rs.getString("Phone"));
            this.Email = new SimpleStringProperty(rs.getString("Email"));
        }
 
        public int getCustomerID() {
            return CustomerID.get();
        }
        
        public IntegerProperty CustomerIDProperty()
        {
            return CustomerID;
        }
 
        public void setCustomerID(int CustID) {
            CustomerID.set(CustID);
        }
        
        public int getID() {
            return ID.get();
        }
        
        public IntegerProperty idProperty()
        {
            return ID;
        }
 
        public void setID(int spcId) {
            ID.set(spcId);
        }
 
        public String getName() {
            return Name.get();
        }
 
        public void setName(String spcName) {
            Name.set(spcName);
        }
        
        public String getAddress() {
            return Address.get();
        }
 
        public void setAddress(String fName) {
            Address.set(fName);
        }
        
        public String getPhone() {
            return Phone.get();
        }
 
        public void setPhone(String phone) {
            Phone.set(phone);
        }
 
        public String getEmail() {
            return Email.get();
        }
 
        public void setEmail(String fName) {
            Email.set(fName);
        }
    }
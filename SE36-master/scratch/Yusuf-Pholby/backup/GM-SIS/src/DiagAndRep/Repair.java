/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagAndRep;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yusuf
 */
public class Repair {
    private IntegerProperty bookingID;
    private StringProperty reg; 
    private IntegerProperty custID;
    private StringProperty fname;
    private StringProperty sname;
    private IntegerProperty mechID;
    private StringProperty type;
    private StringProperty date;
    private StringProperty time;
    private DoubleProperty durat;
    private IntegerProperty mileage;
    private StringProperty status;
    private IntegerProperty warranty;
    private DoubleProperty cost;
    
    public Repair(ResultSet rs)
    {
        try {
            this.bookingID = new SimpleIntegerProperty(rs.getInt("BookingID"));
            this.reg = new SimpleStringProperty(rs.getString("Registration"));
            this.custID = new SimpleIntegerProperty(rs.getInt("CustomerID"));
            this.fname = new SimpleStringProperty(rs.getString("FirstName"));
            this.sname = new SimpleStringProperty(rs.getString("Surname"));
            this.mechID = new SimpleIntegerProperty(rs.getInt("MechanicID"));
            this.type = new SimpleStringProperty(rs.getString("Type"));
            this.date = new SimpleStringProperty(rs.getString("BookingDate"));
            this.time = new SimpleStringProperty(rs.getString("BookingTime"));
            this.durat = new SimpleDoubleProperty(rs.getDouble("Duration"));
            this.mileage = new SimpleIntegerProperty(rs.getInt("CurrentMileage"));
            this.status = new SimpleStringProperty(rs.getString("Status"));
            this.warranty = new SimpleIntegerProperty(rs.getInt("HasWarranty"));
            this.cost = new SimpleDoubleProperty(rs.getDouble("Cost"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getBookingId() {
        return bookingID.get();
    }

    public IntegerProperty bookingIDProperty() {
        return bookingID;
    }

    public void setBookingId(int id) {
        this.bookingID.set(id);
    }
    
    public String getReg() {
        return reg.get();
    }

    public StringProperty regProperty() {
        return reg;
    }

    public void setReg(String fName) {
        this.reg.set(fName);
    }
    
    public int getCustomerId() {
        return custID.get();
    }

    public IntegerProperty custIDProperty() {
        return custID;
    }

    public void setCustomerId(int id) {
        this.custID.set(id);
    }
    
    public String getFname() {
        return fname.get();
    }

    public StringProperty fnameProperty() {
        return fname;
    }

    public void setFname(String fName) {
        this.fname.set(fName);
    }

    public String getSname() {
        return sname.get();
    }

    public StringProperty SnameProperty() {
        return sname;
    }

    public void setSname(String sName) {
        this.sname.set(sName);
    }
    
    public int getmechID() {
        return mechID.get();
    }

    public IntegerProperty mechIDProperty() {
        return mechID;
    }

    public void setmechID(int id) {
        this.mechID.set(id);
    }
    
    public String gettype() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void settype(String fName) {
        this.type.set(fName);
    }
    
    public String getdate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setdate(String fName) {
        this.date.set(fName);
    }
    
    public String gettime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void settime(String fName) {
        this.time.set(fName);
    }
    
    public double getdurat() {
        return durat.get();
    }

    public DoubleProperty duratProperty() {
        return durat;
    }

    public void setdurat(double drt) {
        this.durat.set(drt);
    }
    
    public int getmileage() {
        return mileage.get();
    }

    public IntegerProperty mileageProperty() {
        return mileage;
    }

    public void setmileage(int id) {
        this.mileage.set(id);
    }
    
    public String getstatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setstatus(String id) {
        this.status.set(id);
    }
    
    public int getwarranty() {
        return warranty.get();
    }

    public IntegerProperty warrantyProperty() {
        return warranty;
    }

    public void setwarranty(int id) {
        this.warranty.set(id);
    }
    
    public double getcost(){
        return cost.get();
    }
    
    public DoubleProperty costProperty(){
        return cost;
    }
    
    public void setcost(double id){
        this.cost.set(id);
    }
    
    
    
    
}

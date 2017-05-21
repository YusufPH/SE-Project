/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecords;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author samaggarwal
 */
public class Records 
{
    private IntegerProperty customerIDCol;
    private StringProperty registrationCol;
    private StringProperty typeCol;
    private StringProperty manufacturerCol;
    private StringProperty modelCol;
    private StringProperty engineSizeCol;
    private StringProperty fuelTypeCol;
    private StringProperty colourCol;
    private IntegerProperty currentMileageCol;
    private StringProperty lastServiceDateCol;
    private StringProperty motDateCol;
    private StringProperty underWarrantyCol;
    
    


    public Records(ResultSet rs) 
    {
        try 
        {
            this.customerIDCol = new SimpleIntegerProperty(rs.getInt("CustomerID"));
            this.registrationCol = new SimpleStringProperty(rs.getString("Registration"));
            this.typeCol = new SimpleStringProperty(rs.getString("type"));
            this.manufacturerCol = new SimpleStringProperty(rs.getString("Manufacturer"));
            this.modelCol = new SimpleStringProperty(rs.getString("Model"));
            this.engineSizeCol = new SimpleStringProperty(rs.getString("EngineSize"));
            this.fuelTypeCol = new SimpleStringProperty(rs.getString("FuelType"));
            this.colourCol = new SimpleStringProperty(rs.getString("Colour"));
            this.currentMileageCol = new SimpleIntegerProperty(rs.getInt("CurrentMileage"));
            this.lastServiceDateCol = new SimpleStringProperty(rs.getString("LastServiceDate"));
            this.motDateCol = new SimpleStringProperty(rs.getString("MOTDate"));
            this.underWarrantyCol = new SimpleStringProperty(rs.getString("UnderWarranty"));
            
            
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  //gets and sets 
    //customer id 

    public int getcustomerIDCol() {
        return customerIDCol.get();
    }

    public IntegerProperty customerIDColProperty() {
        return customerIDCol;
    }

    public void setcustomerIDCol(int customerIDCol) {
        this.customerIDCol.set(customerIDCol);
    }
    // registration
    
    
    public String getregistrationCol() {
        return registrationCol.get();
    }

    public StringProperty registrationColProperty() {
        return registrationCol;
    }

    public void setregistrationCol(String registrationCol) {
        this.registrationCol.set(registrationCol);
    }
    
    //type of vehicle

    public String gettypeCol() {
        return typeCol.get();
    }

    public StringProperty typeColProperty() {
        return typeCol;
    }

    public void settypeCol(String typeCol) {
        this.typeCol.set(typeCol);
    }
    
    //manufacturer

    public String getmanufacturerCol() {
        return manufacturerCol.get();
    }

    public StringProperty manufacturerColProperty() {
        return manufacturerCol;
    }

    public void setmanufacturerCol(String manufacturerCol) {
        this.manufacturerCol.set(manufacturerCol);
    }
    //model
    
    public String getmodelCol() {
        return modelCol.get();
    }

    public StringProperty modelColProperty() {
        return modelCol;
    }

    public void setmodelCol(String modelCol) {
        this.modelCol.set(modelCol);
    }
    
    //engine size
    
    public String getengineSizeCol() {
        return engineSizeCol.get();
    }

    public StringProperty engineSizeColProperty() {
        return engineSizeCol;
    }

    public void setengineSizeCol(String engineSizeCol) {
        this.engineSizeCol.set(engineSizeCol);
    }
    
    //fuel type
    
    public String getfuelTypeCol() {
        return fuelTypeCol.get();
    }

    public StringProperty fuelTypeColProperty() {
        return fuelTypeCol;
    }

    public void setfuelTypeCol(String fuelTypeCol) {
        this.fuelTypeCol.set(fuelTypeCol);
    }
    //colour
    
    public String getcolourCol() {
        return colourCol.get();
    }

    public StringProperty colourColProperty() {
        return colourCol;
    }

    public void setcolourCol(String colourCol) {
        this.colourCol.set(colourCol);
    }
    
    //current milage 
    
    public int getcurrentMileageCol() {
        return currentMileageCol.get();
    }

    public IntegerProperty currentMileageColProperty() {
        return currentMileageCol;
    }

    public void setcurrentMileageCol(int currentMileageCol) {
        this.currentMileageCol.set(currentMileageCol);
    }
    
    //the last service date 
    
    public String getlastServiceDateCol() {
        return lastServiceDateCol.get();
    }

    public StringProperty lastServiceDateColProperty() {
        return lastServiceDateCol;
    }

    public void setlastServiceDateCol(String lastServiceDateCol) {
        this.lastServiceDateCol.set(lastServiceDateCol);
    }
    
    //the mot date 
    
    public String getmotDateCol() 
    {
        return motDateCol.get();
    }

    public StringProperty motDateColProperty() {
        return motDateCol;
    }

    public void setmotDateCol(String motDateCol) {
        this.motDateCol.set(motDateCol);
    }
    
    
    //  under warrenty 
      
    public String getunderWarrantyCol() {
        return underWarrantyCol.get();
    }

    public StringProperty underWarrantyColProperty() {
        return underWarrantyCol;
    }

    public void setunderWarrantyCol(String underWarranty) {
        this.underWarrantyCol.set(underWarranty);
    }
    
    
    
   
    
}
    
    


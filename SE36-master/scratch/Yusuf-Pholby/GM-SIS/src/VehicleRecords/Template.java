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
public class Template 
{
   
    private StringProperty manufacturerTCol;
    private StringProperty modelTCol;
    private StringProperty engineSizeTCol;
    private StringProperty fuelTypeTCol;
    
    
    


    public Template(ResultSet rs) 
    {
        try 
        {
            
            this.manufacturerTCol = new SimpleStringProperty(rs.getString("Manufacturer"));
            this.modelTCol = new SimpleStringProperty(rs.getString("Model"));
            this.engineSizeTCol = new SimpleStringProperty(rs.getString("EngineSize"));
            this.fuelTypeTCol = new SimpleStringProperty(rs.getString("FuelType"));
            
            
            
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  //gets and sets for the templates

    
//manufacturer
    public String getmanufacturerTCol() {
        return manufacturerTCol.get();
    }

    public StringProperty manufacturerTColProperty() {
        return manufacturerTCol;
    }

    public void setmanufacturerTCol(String manufacturerTCol) {
        this.manufacturerTCol.set(manufacturerTCol);
    }
    //model 
    
    public String getmodelTCol() {
        return modelTCol.get();
    }

    public StringProperty modelTColProperty() {
        return modelTCol;
    }

    public void setmodelTCol(String modelTCol) {
        this.modelTCol.set(modelTCol);
    }
    
    //engine size
    
    public String getengineSizeTCol() {
        return engineSizeTCol.get();
    }

    public StringProperty engineSizeTColProperty() {
        return engineSizeTCol;
    }

    public void setengineSizeTCol(String engineSizeTCol) {
        this.engineSizeTCol.set(engineSizeTCol);
    }
    
    //fuel type 
    
    public String getfuelTypeTCol() {
        return fuelTypeTCol.get();
    }

    public StringProperty fuelTypeTColProperty() {
        return fuelTypeTCol;
    }

    public void setfuelTypeTCol(String fuelTypeTCol) {
        this.fuelTypeTCol.set(fuelTypeTCol);
    }
    
    
    
    
    
    
   
    
}
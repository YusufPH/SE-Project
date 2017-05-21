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
   
    private StringProperty manufacturerCol;
    private StringProperty modelCol;
    private StringProperty engineSizeCol;
    private StringProperty fuelTypeCol;
    
    
    


    public Template(ResultSet rs) 
    {
        try 
        {
            
            this.manufacturerCol = new SimpleStringProperty(rs.getString("Manufacturer"));
            this.modelCol = new SimpleStringProperty(rs.getString("Model"));
            this.engineSizeCol = new SimpleStringProperty(rs.getString("EngineSize"));
            this.fuelTypeCol = new SimpleStringProperty(rs.getString("FuelType"));
            
            
            
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  //getters and setters

    

    public String getmanufacturerCol() {
        return manufacturerCol.get();
    }

    public StringProperty manufacturerColProperty() {
        return manufacturerCol;
    }

    public void setmanufacturerCol(String manufacturerCol) {
        this.manufacturerCol.set(manufacturerCol);
    }
    //address
    
    public String getmodelCol() {
        return modelCol.get();
    }

    public StringProperty modelColProperty() {
        return modelCol;
    }

    public void setmodelCol(String modelCol) {
        this.modelCol.set(modelCol);
    }
    
    //city
    
    public String getengineSizeCol() {
        return engineSizeCol.get();
    }

    public StringProperty engineSizeColProperty() {
        return engineSizeCol;
    }

    public void setengineSizeCol(String engineSizeCol) {
        this.engineSizeCol.set(engineSizeCol);
    }
    
    //county
    
    public String getfuelTypeCol() {
        return fuelTypeCol.get();
    }

    public StringProperty fuelTypeColProperty() {
        return fuelTypeCol;
    }

    public void setfuelTypeCol(String fuelTypeCol) {
        this.fuelTypeCol.set(fuelTypeCol);
    }
    //postcode
    
    
    
    
    
   
    
}

package Parts;

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
 * @author aaron
 */
public class PartsAssigned {
    private IntegerProperty id; 
    private StringProperty name;
    private StringProperty reg;
    private IntegerProperty cost;
    private StringProperty warranty;
    
    public PartsAssigned(ResultSet rs) throws SQLException
    {
        try {
            this.reg = new SimpleStringProperty(rs.getString("Reg"));
            this.id = new SimpleIntegerProperty(rs.getInt("partID"));
            this.name = new SimpleStringProperty(rs.getString("partName"));
            this.cost = new SimpleIntegerProperty(rs.getInt("Cost"));
            this.warranty = new SimpleStringProperty(rs.getString("Warranty"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getname() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setname(String fName) {
        this.name.set(fName);
    }

    public String getreg() {
        return reg.get();
    }

    public StringProperty regProperty() {
        return reg;
    }

    public void setreg(String reg) {
        this.reg.set(reg);
    }

    public int getcost() {
        return cost.get();
    }

    public IntegerProperty qntProperty() {
        return cost;
    }

    public void setcost(int cost) {
        this.cost.set(cost);
    }
    
    public String getwarranty() {
        return warranty.get();
    }

    public StringProperty warrantyProperty() {
        return warranty;
    }

    public void setwarranty(String reg) {
        this.warranty.set(reg);
    }
    
}



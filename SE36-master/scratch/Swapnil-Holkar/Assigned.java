
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
public class Assigned {
    private StringProperty reg; 
    private StringProperty name;
    
    
    public Assigned(ResultSet rs) throws SQLException
    {
        try {
            this.reg = new SimpleStringProperty(rs.getString("Reg"));
            this.name = new SimpleStringProperty(rs.getString("PartName"));
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public String getname() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setname(String fName) {
        this.name.set(fName);
    }

    
    
}



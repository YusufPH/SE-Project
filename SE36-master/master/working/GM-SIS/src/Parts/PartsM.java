
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
public class PartsM {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty desc;
    private IntegerProperty qnt;
    private DoubleProperty cst;

    
    PartsM(ResultSet rs) throws SQLException
    {
        try {
            this.id = new SimpleIntegerProperty(rs.getInt("ID"));
            this.name = new SimpleStringProperty(rs.getString("Name"));
            this.desc = new SimpleStringProperty(rs.getString("Description"));
            this.qnt = new SimpleIntegerProperty(rs.getInt("StockQuantity"));
            this.cst = new SimpleDoubleProperty(rs.getDouble("Cost"));

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

    public String getdesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public void setdesc(String desc) {
        this.desc.set(desc);
    }

    public int getqnt() {
        return qnt.get();
    }

    public IntegerProperty qntProperty() {
        return qnt;
    }

    public void setqnt(int type) {
        this.qnt.set(type);
    }
    
    public double getcst() {
        return cst.get();
    }

    public DoubleProperty cstProperty() {
        return cst;
    }

    public void setcst(double cost) {
        this.cst.set(cost);
    }
}



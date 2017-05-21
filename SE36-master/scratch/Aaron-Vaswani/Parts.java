
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
class Parts {
    private IntegerProperty ID;
    private StringProperty Name;
    private StringProperty Desc;
    private IntegerProperty Quantity;
    private DoubleProperty Cost;

    
    Parts(ResultSet rs) throws SQLException
    {
        try {
            this.ID = new SimpleIntegerProperty(rs.getInt("ID"));
            this.Name = new SimpleStringProperty(rs.getString("Name"));
            this.Desc = new SimpleStringProperty(rs.getString("Description"));
            this.Quantity = new SimpleIntegerProperty(rs.getInt("StockQuantity"));
            this.Cost = new SimpleDoubleProperty(rs.getDouble("Cost"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getId() {
        return ID.get();
    }

    public IntegerProperty idProperty() {
        return ID;
    }

    public void setId(int id) {
        this.ID.set(id);
    }

    public String getpartName() {
        return Name.get();
    }

    public StringProperty partNameProperty() {
        return Name;
    }

    public void setpartName(String fName) {
        this.Name.set(fName);
    }

    public String getpartDesc() {
        return Desc.get();
    }

    public StringProperty partDescProperty() {
        return Desc;
    }

    public void setpartDesc(String desc) {
        this.Desc.set(desc);
    }

    public int getpartQuantity() {
        return Quantity.get();
    }

    public IntegerProperty partQuantityProperty() {
        return Quantity;
    }

    public void setpartQuantity(int type) {
        this.Quantity.set(type);
    }
    
    public double getpartCost() {
        return Cost.get();
    }

    public DoubleProperty partCostProperty() {
        return Cost;
    }

    public void setpartQuantity(double cost) {
        this.Cost.set(cost);
    }
}



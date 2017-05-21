package login;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Yusuf
 */
public class Users {
    private IntegerProperty id; // holding ID 
    private StringProperty fName; // holding first name 
    private StringProperty sName; // holding surname
    private IntegerProperty Type; // holding account type
    private StringProperty pword;


    public Users(ResultSet rs)
    {
        try {
            this.id = new SimpleIntegerProperty(rs.getInt("ID"));
            this.fName = new SimpleStringProperty(rs.getString("FirstName"));
            this.sName = new SimpleStringProperty(rs.getString("Surname"));
            this.Type = new SimpleIntegerProperty(rs.getInt("Type"));
            this.pword = new SimpleStringProperty(rs.getString("Password"));

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

    public String getfName() {
        return fName.get();
    }

    public StringProperty fNameProperty() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public String getsName() {
        return sName.get();
    }

    public StringProperty sNameProperty() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName.set(sName);
    }

    public int getType() {
        return Type.get();
    }

    public IntegerProperty typeProperty() {
        return Type;
    }

    public void setType(int type) {
        this.Type.set(type);
    }
    
    public String getPword() {
        return pword.get();
    }

    public StringProperty pwordProperty() {
        return pword;
    }

    public void setpword(String fName) {
        this.pword.set(fName);
    }

}

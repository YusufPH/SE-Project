package CustomerAccounts;
        
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Swapnil
 */
public class Customers {
    
    private IntegerProperty custID;
    private StringProperty fName;
    private StringProperty lName;
    private StringProperty cType;
    private StringProperty address;
    private StringProperty postcode;
    private StringProperty pnumber;
    private StringProperty email;
    

    public Customers(ResultSet rs)
    {
        try {
            this.custID = new SimpleIntegerProperty(rs.getInt("CustomerID"));
            this.cType = new SimpleStringProperty(rs.getString("CustomerType"));
            this.fName = new SimpleStringProperty(rs.getString("FirstName"));
            this.lName = new SimpleStringProperty(rs.getString("LastName"));
            this.address = new SimpleStringProperty(rs.getString("Address"));
            this.postcode = new SimpleStringProperty(rs.getString("PostCode"));
            this.pnumber = new SimpleStringProperty(rs.getString("PhoneNumber"));
            this.email = new SimpleStringProperty(rs.getString("EmailAddress"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  //getters and setters

    public int getCustID() {
        return custID.get();
    }

    public IntegerProperty CustIDProperty() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID.set(custID);
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

    public String getlName() {
        return lName.get();
    }

    public StringProperty lNameProperty() {
        return lName;
    }

    public void setlName(String sName) {
        this.lName.set(sName);
    }

    public String getcType() {
        return this.cType.get();
        
    }
    public void setcType(String Ctype) {
        this.cType.set(Ctype);
    }

    public StringProperty cTypeProperty() {
        return cType;
    }

    
    //address
    
    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
    
    //postcode
    
    public String getPostcode() {
        return postcode.get();
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    public void setType(String postcode) {
        this.postcode.set(postcode);
    }
    
    //pnumber
    
    public String getPnumber() {
        return pnumber.get();
    }

    public StringProperty pnumberProperty() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber.set(pnumber);
    }
    
    //email
    
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccounts;

import common.SQLiteConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Swapnil
 */
public class Add_InterfaceController implements Initializable {
   
        //add interface field names
        @FXML private TextField fname_field; 
        @FXML private TextField lname_field;
        @FXML private TextField address_field;
        @FXML private TextField city_field;
        @FXML private TextField county_field;
        @FXML private TextField postcode_field;
        @FXML private TextField pnumber_field;
        @FXML private TextField email_field;
        @FXML private MenuButton customerType;
        

        private SQLiteConnection db = SQLiteConnection.getInstance();


     public void AddCustomer(ActionEvent event) // ignore for now
        {
            try {
                if(!customerType.getText().trim().equals("")&&!fname_field.getText().trim().equals("")&&!lname_field.getText().trim().equals("")&&!address_field.getText().trim().equals("")&&!city_field.getText().trim().equals("")&&!county_field.getText().trim().equals("")&&!postcode_field.getText().trim().equals("")&&!pnumber_field.getText().trim().equals("")&&!email_field.getText().trim().equals(""))
                {

                        // SQLiteConnection db = SQLiteConnection.getInstance();
                        String stmt = "INSERT INTO CustomerAccounts  (CustomerType, FirstName, LastName, Address, City, County, PostCode, PhoneNumber, EmailAddress) VALUES ('" + customerType.getText() + "','" + fname_field.getText() + "', '" + lname_field.getText() + "', '" + address_field.getText() + "', '" + city_field.getText() +"', '" + county_field.getText() +"', '" + postcode_field.getText() +"', '" + pnumber_field.getText() +"','" + email_field.getText() +"')";
                        db.update(stmt);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Customer Added");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(fname_field.getText()+" was added successfully");
                        successAlert.showAndWait();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("One of the fields is empty");
                    alert.showAndWait();
                }

            }catch (Exception e)
            {

            }

        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //do
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialistRepairs;

import common.SQLiteConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author gleb_mirolyubov
 * 
 * This class adds a new record about one Specialist Repair Center that is not the same as any of the previous ones
 * 
 **/
public class AddSPC {
    
    /*
    * A constructor class used to add a new SPC.
    *
    * A check is done whether there are empty filed(s) or 
    * some data is repeated from previously created SPCs.
    *
    * Data about selected SPC is passed from controller class.
    */
    AddSPC(TableView<SPC> table, TextField nameField, TextField addressField, TextField phoneField, TextField emailField)
    {
        try 
        {
                if(!nameField.getText().trim().equals("") && !addressField.getText().trim().equals("") && !phoneField.getText().trim().equals("") && !emailField.getText().trim().equals(""))
                {
                SQLiteConnection db = SQLiteConnection.getInstance();
                String stmt = "INSERT INTO SpecialistRepairs (Name, Address, Phone, Email) VALUES ('" + nameField.getText() + "', '" + addressField.getText() + "', '" + phoneField.getText() + "','" + emailField.getText() + "')";
                db.update(stmt);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "At least one field is empty!", ButtonType.OK);
                    alert.showAndWait();
        }    
        } catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("One of the fields is empty");
            alert.showAndWait();
        }        
    }
    
}

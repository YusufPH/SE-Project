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
 * This class handles edit of a selected Specialist Repair Center record
 * 
 **/
public class EditSPC {
    
    /*
    * A constructor class used to edit selected SPC.
    *
    * Data about selected SPC is passed from controller class.
    */
    EditSPC(TableView<SPC> table, TextField nameField, TextField addressField, TextField phoneField, TextField emailField)
    {
        SPC selectedSPC = table.getSelectionModel().getSelectedItem(); 
        try 
        {
            if(!table.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this SPC record?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) {
            SQLiteConnection db = SQLiteConnection.getInstance();
            String stmt = "UPDATE SpecialistRepairs SET Name='"+nameField.getText()+"',Address='"+addressField.getText()+"',Phone='"+phoneField.getText()+"',Email='"+emailField.getText()+"' WHERE ID="+selectedSPC.getID()+"";
            db.update(stmt);
            }
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

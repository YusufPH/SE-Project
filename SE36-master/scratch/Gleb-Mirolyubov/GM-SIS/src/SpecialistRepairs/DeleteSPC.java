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

/**
 *
 * @author gleb_mirolyubov
 * 
 * This class handles the deletion of a record about one Specialist Repair Center
 * 
 **/
public class DeleteSPC {

    /*
    * A constructor class used to delete selected SPC.
    *
    * Data about selected SPC is passed from controller class.
    */
    DeleteSPC(TableView<SPC> table) 
    {
     SPC selectedSPC = table.getSelectionModel().getSelectedItem();
        try 
        {
            if(!table.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this SPC record?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) {
            SQLiteConnection db = SQLiteConnection.getInstance();
            String stmt = "DELETE FROM SpecialistRepairs WHERE Phone='"+selectedSPC.getPhone()+"'";
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

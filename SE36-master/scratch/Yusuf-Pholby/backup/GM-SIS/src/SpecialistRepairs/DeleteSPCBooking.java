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
 */
public class DeleteSPCBooking {
    
    /*
    * A constructor class used to delete selected SPC booking.
    *
    * Data about selected SPC booking is passed from controller class.
    */
    DeleteSPCBooking(TableView<SPCBooking> table){
        SPCBooking selectedSPC = table.getSelectionModel().getSelectedItem();
        try 
        {
            if(!table.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this SPC record?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) {
            SQLiteConnection db = SQLiteConnection.getInstance();
            String stmt = "DELETE FROM SPCBookings WHERE BookingID='"+selectedSPC.getBookingID()+"'";
            db.update(stmt);
            } 
            } else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "No SPC Booking Selected!", ButtonType.CLOSE);
            noSelection.showAndWait(); 
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

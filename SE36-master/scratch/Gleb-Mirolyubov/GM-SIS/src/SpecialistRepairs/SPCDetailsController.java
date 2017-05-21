/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialistRepairs;

import common.SQLiteConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author gleb_mirolyubov
 */
public class SPCDetailsController implements Initializable {
    
    @FXML
    private Label name;
    @FXML
    private TextField id;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    
    @FXML
    private TableView<SPCDetails> table;
    
    @FXML private TableColumn<SPCDetails, Integer> bookingIDCol;
    @FXML private TableColumn<SPCDetails, String>  nameCol ;
    @FXML private TableColumn<SPCDetails, String>  typeCol ;
    @FXML private TableColumn<SPCDetails, String> descCol ;
    @FXML private TableColumn<SPCDetails, Integer>  custIDCol ;
    @FXML private TableColumn<SPCDetails, String>  custNameCol ;

    private final SQLiteConnection db = SQLiteConnection.getInstance();
    
    /* 
    *  This method initializes the class' variables
    */ 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
        name.setText(SpecialistRepairsGUIController.currentSPCSelection.getName());
        id.setText(Integer.toString(SpecialistRepairsGUIController.currentSPCSelection.getID()));
        address.setText(SpecialistRepairsGUIController.currentSPCSelection.getAddress());
        phone.setText(SpecialistRepairsGUIController.currentSPCSelection.getPhone());
        email.setText(SpecialistRepairsGUIController.currentSPCSelection.getEmail());
    } 
    
    /* 
    *  This method sets value factories from the database
    */ 
    private void setValueFactories()
    {
        bookingIDCol.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("Details"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
    }
    
    /* 
    *  This method uses a predefined string to access the database and fetch selected data from it.
    *  Then it adds this data to the table which is connected to the TableView in the fxml.
    */ 
    private void fillTable()
    {
        setValueFactories();
        ArrayList<SPCDetails> tableValues = new ArrayList<>();
        String sql = "SELECT BookingID, Name, SPC, Type, Details, CustomerID, CustomerName FROM SPCBookings WHERE SPC = '"+SpecialistRepairsGUIController.currentSPCSelection.getName()+"'";
        ResultSet rs = db.query(sql);
        try {
            while (rs.next())
            {
                tableValues.add(new SPCDetails(rs));
            }
            table.setItems(FXCollections.observableArrayList(tableValues));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Closes window
    */ 
    public void CloseWindow(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
}

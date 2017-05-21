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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Specialist Repairs FXML Controller class
 *
 * @author Gleb Mirolyubov
 */
public class SpecialistRepairsGUIController implements Initializable {
    
    ObservableList<String> spcList = FXCollections.observableArrayList("SPC1","SPC2","SPC3");

    @FXML
    private ChoiceBox spcChoice;
    
    @FXML
    private GridPane pane;
    
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    
    @FXML
    private Button addSPCButton;
    @FXML
    private Button editSPCButton;
    
    @FXML
    private Button editSPC;
    @FXML
    private Button addSPC;
    @FXML
    private Button deleteSPC;
    
    @FXML
    private TableView<SPC> table;// = new TableView<Person>();

    @FXML private TableColumn<SPC, Integer> CustIDCol;
    @FXML private TableColumn<SPC, Integer> IDCol;
    @FXML private TableColumn<SPC, String>  nameCol ;
    @FXML private TableColumn<SPC, String>  addressCol ;
    @FXML private TableColumn<SPC, Integer> phoneCol ;
    @FXML private TableColumn<SPC, String>  emailCol ;
   
    private final SQLiteConnection db = SQLiteConnection.getInstance();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        spcChoice.setValue("SPC1");
        spcChoice.setItems(spcList);
        pane.setVisible(false);
        
        try {
            //table.getItems().setAll(this.data);
            fillTable();
        } catch (SQLException ex) {
            Logger.getLogger(SpecialistRepairsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    /* 
    *  Description for this method
    */
    public void fillTable() throws SQLException
    {
        setValueFactories();
        ArrayList<SPC> tableValues = new ArrayList<>();
        String sql = "SELECT CustomerID, ID, Name, Address, Phone, Email FROM SpecialistRepairs";
        ResultSet rs = db.query(sql);
        try {
            while (rs.next())
            {
                tableValues.add(new SPC(rs));
            }
            table.setItems(FXCollections.observableArrayList(tableValues));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Description for this method
    */
    private void setValueFactories() {
        CustIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));   
    }
    
    /* 
    *  Description for this method
    */ 
    public void enableAdd()
    {
    pane.setVisible(true);
    editSPCButton.setVisible(false);
    addSPCButton.setVisible(true);
    }
    
    /* 
    *  This method is called when Edit button is pressed given that one SPC is selected.
    *  Show the edit panel with data from selectod row which can be modified.
    */ 
    public void enableEdit()
    {
    pane.setVisible(true);
    addSPCButton.setVisible(false);
    editSPCButton.setVisible(true);
    
    // fetch existing information from the database and put it into editable textfields 
    SPC selectedSPC = table.getSelectionModel().getSelectedItem();
    idField.setText(String.valueOf(selectedSPC.getID()));
    nameField.setText(selectedSPC.getName());
    addressField.setText(selectedSPC.getAddress());
    phoneField.setText(selectedSPC.getPhone());
    emailField.setText(selectedSPC.getEmail());
    }
    
    /* 
    *  This method is called when Apply Edit button is pressed in the Edit SPC window.
    *  It updates the information in the system and database about specific SPC chosen by the user.
    */
    public void EditSPC()
    {
        SPC selectedSPC = table.getSelectionModel().getSelectedItem(); 
        try 
        {
            if(!table.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to edit this SPC record?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) {
            SQLiteConnection db = SQLiteConnection.getInstance();
            String stmt = "UPDATE SpecialistRepairs SET ID='"+idField.getText()+"', Name='"+nameField.getText()+"',Address='"+addressField.getText()+"',Phone='"+phoneField.getText()+"',Email='"+emailField.getText()+"' WHERE CustomerID="+selectedSPC.getCustomerID()+"";
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
               
        try 
        {
            fillTable();
            pane.setVisible(false);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(SpecialistRepairsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /* 
    *  Description for this method
    */
    public void DeleteSPC()
    {
     
    SPC selectedSPC = table.getSelectionModel().getSelectedItem();
        try 
        {
            if(!table.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this SPC record?", ButtonType.YES, ButtonType.NO);
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
        try 
        {
            fillTable();
            pane.setVisible(false);
        } catch (SQLException ex) 
        {
            Logger.getLogger(SpecialistRepairsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
    }

    /* 
    *  Description for this method
    */
    public void AddSPC()
    {
        try 
        {
                if(!nameField.getText().trim().equals("") && !addressField.getText().trim().equals("") && !phoneField.getText().trim().equals("") && !emailField.getText().trim().equals(""))
                {
                SQLiteConnection db = SQLiteConnection.getInstance();
                String stmt = "INSERT INTO SpecialistRepairs (ID, Name, Address, Phone, Email) VALUES ('" + idField.getText() + "','" + nameField.getText() + "', '" + addressField.getText() + "', '" + phoneField.getText() + "','" + emailField.getText() + "')";
                db.update(stmt);
                } else {
                    Alert alert = new Alert(AlertType.WARNING, "At least one field is empty!", ButtonType.OK);
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
        try 
        {
            //table.getItems().setAll(this.data);
            fillTable();
            pane.setVisible(false);
        } catch (SQLException ex) 
        {
            Logger.getLogger(SpecialistRepairsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialistRepairs;

import common.SQLiteConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Specialist Repairs FXML Controller class
 *
 * @author Gleb Mirolyubov
 */
public class SpecialistRepairsGUIController implements Initializable {
    
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    
    @FXML
    private TextField searchBar;
    //for searchbar this is called in the filltable method
    public static ObservableList<SPCBooking> searchData = FXCollections.observableArrayList();
    
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
    private Button cancelSelection;
    @FXML
    private Button showVehicles;
    
    @FXML
    private TableView<SPC> table;// = new TableView<Person>();

    @FXML private TableColumn<SPC, Integer> IDCol;
    @FXML private TableColumn<SPC, String>  nameCol ;
    @FXML private TableColumn<SPC, String>  addressCol ;
    @FXML private TableColumn<SPC, Integer> phoneCol ;
    @FXML private TableColumn<SPC, String>  emailCol ;
    
    @FXML
    private TableView<SPCBooking> SPCBookingsTable;// = new TableView<Person>();

    @FXML private TableColumn<SPCBooking, Integer> BookingIDCol;
    @FXML private TableColumn<SPCBooking, String>  NameCol ;
    @FXML private TableColumn<SPCBooking, String>  SPCCol ;
    @FXML private TableColumn<SPCBooking, String>  TypeCol ;
    @FXML private TableColumn<SPCBooking, String>  RegNoCol ;
    @FXML private TableColumn<SPCBooking, String> DetailsCol ;
    @FXML private TableColumn<SPCBooking, Integer>  CostCol ;
    @FXML private TableColumn<SPCBooking, Integer>  CustIDCol ;
    @FXML private TableColumn<SPCBooking, String>  CustNameCol ;
    @FXML private TableColumn<SPCBooking, String>  dDateCol ;
    @FXML private TableColumn<SPCBooking, String>  rDateCol ;
   
    private final SQLiteConnection db = SQLiteConnection.getInstance();
    
    FillSPCsTable FillSPCs;
    public static FillSPCBookingsTable FillSPCBookings;
    public static SPC currentSPCSelection;
    
    /* 
    *  This method initializes the class' variables
    */ 
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        disableGUI();
        
        editSPC.setVisible(false);
        deleteSPC.setVisible(false);
        FillSPCs = new FillSPCsTable(table, IDCol, nameCol, addressCol, phoneCol, emailCol);
        FillSPCBookings = new FillSPCBookingsTable(SPCBookingsTable, BookingIDCol, SPCCol, TypeCol,RegNoCol,CostCol, NameCol, DetailsCol,CustIDCol,CustNameCol, dDateCol, rDateCol);
        try {
            FillSPCs.fillTable();
            FillSPCBookings.fillTable();
        } catch (SQLException ex) {
            Logger.getLogger(SpecialistRepairsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /* 
    *  Show empty textfields to create a new SPC
    */ 
    public void enableAdd()
    {
        ClearTextFields();
        nameLabel.setVisible(true);
        addressLabel.setVisible(true);
        phoneLabel.setVisible(true);
        emailLabel.setVisible(true);
        nameField.setVisible(true);
        addressField.setVisible(true);
        phoneField.setVisible(true);
        emailField.setVisible(true);
        editSPCButton.setVisible(false);
        addSPCButton.setVisible(true);
        cancelSelection.setVisible(true);
    }
    
    /* 
    *  This method is called when Edit button is pressed given that one SPC is selected.
    *  Show the edit panel with data from selectod row which can be modified.
    */ 
    public void enableEdit()
    {
        ClearTextFields();
        nameLabel.setVisible(true);
        addressLabel.setVisible(true);
        phoneLabel.setVisible(true);
        emailLabel.setVisible(true);
        nameField.setVisible(true);
        addressField.setVisible(true);
        phoneField.setVisible(true);
        emailField.setVisible(true);
        addSPCButton.setVisible(false);
        editSPCButton.setVisible(true);
        cancelSelection.setVisible(true);
    // fetch existing information from the database and put it into editable textfields 
    SPC selectedSPC = table.getSelectionModel().getSelectedItem();
    nameField.setText(selectedSPC.getName());
    addressField.setText(selectedSPC.getAddress());
    phoneField.setText(selectedSPC.getPhone());
    emailField.setText(selectedSPC.getEmail());
    }
    
    /* 
    *  This method is called when Apply Edit button is pressed in the Edit SPC window.
    *
    *  It updates the information in the system and database about specific SPC chosen by the user.
    */
    public void editSPC()
    {
        EditSPC eSPC = new EditSPC(table, nameField, addressField, phoneField, emailField);
        disableGUI();
        ClearTextFields();
        cancelSelection.setVisible(false);
        FillSPCs.fillTable();
    }
    
    /* 
    *  Delete selected SPC by creating an instance of DeleteSPC class
    */
    public void deleteSPC()
    {
        DeleteSPC spcToDelte = new DeleteSPC(table);
        disableGUI();
        ClearTextFields();
        FillSPCs.fillTable();     
    }

    /* 
    *  Add new SPC by creating an instance if AddSPC class
    */
    public void addSPC()
    {
        AddSPC addNewSPC = new AddSPC(table, nameField, addressField, phoneField, emailField);
        disableGUI();
        ClearTextFields();
        cancelSelection.setVisible(false);
        FillSPCs.fillTable();
    } 
    
    /* 
    *  A seacrh function to filter data from user's input
    */
    public void searchBar()
    {
        FilteredList<SPCBooking> filteredData;
        filteredData = new FilteredList<>(searchData, p -> true);
        
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(SPCBooking -> {
			
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				//SPCBooking.getCustomerName().toLowerCase().contains(lowerCaseFilter)
				if (SPCBooking.getRegistrationNumber().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (SPCBooking.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}
				return false; 
			});
		});
		SortedList<SPCBooking> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(SPCBookingsTable.comparatorProperty());
		SPCBookingsTable.setItems(sortedData);
	}       
    
    /* 
    *  Calls method from FillSPCBookings class to fill SPC table
    */ 
    public void FillSPCTable() throws SQLException
    {
       FillSPCBookings.fillTable();
    }
    
    /* 
    *  Disable most of the GUI items when an action is performed
    */ 
    private void disableGUI()
    {
        nameLabel.setVisible(false);
        addressLabel.setVisible(false);
        phoneLabel.setVisible(false);
        emailLabel.setVisible(false);
        nameField.setVisible(false);
        addressField.setVisible(false);
        phoneField.setVisible(false);
        emailField.setVisible(false);
        addSPCButton.setVisible(false);
        editSPCButton.setVisible(false);
        cancelSelection.setVisible(false);
        showVehicles.setVisible(false);
        deleteSPC.setVisible(false);
        editSPC.setVisible(false);
    }
    
    /* 
    *  Shows a new window about SPC details
    */ 
    public void showMoreSPCGUI() throws IOException
    {
        currentSPCSelection = table.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("/SpecialistRepairs/SPCDetails.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    /* 
    *  Shows a new window with summary list
    */ 
    public void showSummaryList() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/SpecialistRepairs/SummaryList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();  
    }
    
    /* 
    *  Shows a new window to add SPC booking
    */
    public void addSPCBookingGUI() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/SpecialistRepairs/ModifySPCBooking.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
  
    /* 
    *  Deletes selected SPC booking
    */ 
    public void deleteSPCBooking() throws SQLException 
    {
        DeleteSPCBooking selectedBooking = new DeleteSPCBooking(SPCBookingsTable);
        FillSPCBookings.fillTable();
    }
    
    /* 
    *  Gets the selected row from SPC table
    */ 
    public void tableItemSelected()
    {
        SPC selectedSPC = table.getSelectionModel().getSelectedItem();
        showVehicles.setVisible(true);
        deleteSPC.setVisible(true);
        editSPC.setVisible(true);
    }
    
    /* 
    *  Cancel button
    */ 
    public void CancelButton()
    {
      disableGUI();
    }
    
    /* 
    *  Clear text fields
    */ 
    private void ClearTextFields()
    {
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        emailField.clear();
    }
}
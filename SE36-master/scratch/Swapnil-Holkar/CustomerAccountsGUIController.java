/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccounts;

import Parts.PartsM;
import VehicleRecords.Records;
import DiagAndRep.Repair;
import Parts.PartsAssigned;
import SpecialistRepairs.SPCBooking;

import common.SQLiteConnection;
import static java.awt.SystemColor.window;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Swapnil
 */
public class CustomerAccountsGUIController implements Initializable {

        //labels
        @FXML private Label editcustomerLabel;
        @FXML private Label addcustomerLabel;

        //buttons
        @FXML private Button addbuttonOption;
        @FXML private Button editbuttonOption;
        @FXML private Button addbuttonPane;
        @FXML private Button savebuttonPane;
        @FXML private Button makeBookingOption;
        
        //radio Buttons
        @FXML private RadioButton nameRadio;
        @FXML private RadioButton regRadio;
        
        
        //Gridpane
        @FXML private GridPane pane;
        
        //tabpane
        @FXML private TabPane tabPane;
        @FXML private Tab bookingTab;
        
        
        //search field
        @FXML private TextField searchBar;

        //add/edit interface field names
        @FXML private TextField fname_field; 
        @FXML private TextField lname_field;
        @FXML private TextField address_field;
        @FXML private TextField postcode_field;
        @FXML private TextField pnumber_field;
        @FXML private TextField email_field;
        
        //for choice box
        ObservableList<String> custList = FXCollections.observableArrayList("Private","Business");
        @FXML private ChoiceBox customerType;
         
        //for the table in gui
        private ArrayList<Customers> customers = new ArrayList<>();

        @FXML private TableView<Customers> customerTable;


        @FXML private TableColumn<Customers, String> fName;
        @FXML private TableColumn<Customers, String> lName;
        @FXML private TableColumn<Customers, String> cType;
        @FXML private TableColumn<Customers, String> address;   
        @FXML private TableColumn<Customers, String> postcode;
        @FXML private TableColumn<Customers, Integer> pnumber;
        @FXML private TableColumn<Customers, String> email;
        
        //for searchbar this is called in th1e filltable method
        private ObservableList<Customers> searchData = FXCollections.observableArrayList();
        
         
        //parts table
        @FXML private TableView<PartsAssigned> partsTable;
       
        @FXML private TableColumn<PartsAssigned, String> reg;
        @FXML private TableColumn<PartsAssigned, String> partName;
        
        //spc Table1
        @FXML private TableView<SPCBooking> spcTable;
       
        @FXML private TableColumn<SPCBooking, String> spcAssigned;
        @FXML private TableColumn<SPCBooking, String> vehicle_part;
        @FXML private TableColumn<SPCBooking, String> DeliveryDate ;
        @FXML private TableColumn<SPCBooking, String> returnDate;
        @FXML private TableColumn<SPCBooking, Integer> spcCost ;
        
        
        
        //vehicle records
        @FXML private TableView<Records> recordsTable;

        @FXML private TableColumn<Records, String> regNumber;
        @FXML private TableColumn<Records, String> model;
        @FXML private TableColumn<Records, String> make;
        @FXML private TableColumn<Records, Integer> engSize;   
        @FXML private TableColumn<Records, String> fuelType;
        @FXML private TableColumn<Records, String> colour;
        @FXML private TableColumn<Records, String> currentMileage;
        
        //booking
        @FXML private TableView<Repair> bookingTable;

        @FXML private TableColumn<Records, String> bookingType;
        @FXML private TableColumn<Records, String> fbookingDate;
        @FXML private TableColumn<Records, Integer> pbookingDate;
        @FXML private TableColumn<Records, Double> tbCost;
        //@FXML private TableColumn
        
        private SQLiteConnection db = SQLiteConnection.getInstance();

        @Override
        public void initialize(URL url, ResourceBundle rb) {
             customerType.setValue("Private");
             customerType.setItems(custList);
             pane.setVisible(false);
             editcustomerLabel.setVisible(false);
             addcustomerLabel.setVisible(false);
             searchBar.setDisable(true);
             
             
            
            fillTable();

        } 
        
       
    @FXML
        public void searchByName(){
            recordsTable.getItems().clear(); 
            bookingTable.getItems().clear();
            searchBar.setDisable(false);
            searchBar.setPromptText("by Firstname or Lastname");
            
            
        }
        
        
        @FXML 
        public void reloadCustomers(ActionEvent event){
            fillTable();
        }
        
        
        
            
//fills customer table
    @FXML
        public void fillTable()
        {
            searchData.clear();
            setValueFactories();   
            ArrayList<Customers> tableValues = new ArrayList<>();
            //ObservableList<Customers> table = FXCollections.observableArrayList();
            String sql = "SELECT * FROM CustomerAccounts";
            ResultSet rs = db.query(sql);
            try {
                while (rs.next())
                {
                    
                    searchData.add(new Customers(rs));
                    
                }
                customerTable.setItems(FXCollections.observableArrayList(searchData));
                customerTable.setOnMousePressed(new EventHandler<MouseEvent>(){
                   @Override
                   public void handle(MouseEvent e)
                   {
                       Customers cc=customerTable.getSelectionModel().getSelectedItem(); 
                       fillVehicleTableClick(cc.getCustID());
                       fillBookingTableSearch(cc.getCustID());
                       fillPartsTableSearch(cc.getCustID());
                       fillSPCBookingTableSearch(cc.getCustID());
                       
                   }
               });

            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }

        
        //fill in the partsTable
        public void fillPartsTableSearch(int ID)
        {
            setValueFactories();
           ArrayList<PartsAssigned> partsValues = new ArrayList<>();
            String sql = "SELECT * FROM PartsAssigned WHERE CustomerID=="+ID+";";
            ResultSet rs2 = db.query(sql);
            try {
                while (rs2.next())
                {
                    
                    partsValues.add(new PartsAssigned(rs2));
               }
               partsTable.setItems(FXCollections.observableArrayList(partsValues));
           } catch (SQLException e) {
                e.printStackTrace();  
            }
        }
        
        //shows vehicle
        public void fillVehicleTableClick(int ID)
        {
            recordsTable.getItems().clear();
            setValueFactories();
            
            ArrayList<Records> recordsValues = new ArrayList<>();
            String sql = "SELECT * FROM VehicleRecords WHERE CustomerID=="+ID+";";
            ResultSet rs = db.query(sql);
            try {
                while (rs.next())
                {               
                    recordsValues.add(new Records(rs));
               }
               recordsTable.setItems(FXCollections.observableArrayList(recordsValues));
                          } catch (SQLException e) {
                e.printStackTrace();  
            }
        }
        //shows booking
        public void fillBookingTableSearch(int ID)
        {
            setValueFactories();
            ArrayList<Repair> bookingValues = new ArrayList<>();
            String sql = "SELECT * FROM Bookings WHERE CustomerID=="+ID+";";
            ResultSet rs = db.query(sql);
            try {
                while (rs.next())
                {
                    //String test = rs2.getString("Name");
                    //cType.setCellValueFactory(new PropertyValueFactory(test));
                    //System.out.println(test);
                    bookingValues.add(new Repair(rs));
               }
               bookingTable.setItems(FXCollections.observableArrayList(bookingValues));
           } catch (SQLException e) {
                e.printStackTrace();  
            }
        }
        
        //method for populating the spc booking table
        public void fillSPCBookingTableSearch(int ID)
        {
            setValueFactories();
            ArrayList<SPCBooking> spcBookingValues = new ArrayList<>();
            String sql = "SELECT * FROM SPCBookings WHERE CustomerID=="+ID+";";
            
            ResultSet rs = db.query(sql);
            try {
                while (rs.next())
                {
                    //String test = rs2.getString("Name");
                    //cType.setCellValueFactory(new PropertyValueFactory(test));
                    //System.out.println(test);
                    spcBookingValues.add(new SPCBooking(rs));
               }
               spcTable.setItems(FXCollections.observableArrayList(spcBookingValues));
           } catch (SQLException e) {
                e.printStackTrace();  
            }
        }
        
        
        
        
        
@FXML
      //set the columns in tables
        private void setValueFactories()
        {
            cType.setCellValueFactory(new PropertyValueFactory<>("cType"));         
            fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
            lName.setCellValueFactory(new PropertyValueFactory<>("lName"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            postcode.setCellValueFactory(new PropertyValueFactory<>("postcode"));
            pnumber.setCellValueFactory(new PropertyValueFactory<>("pnumber"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            
            //parts
            reg.setCellValueFactory(new PropertyValueFactory<>("reg"));
            partName.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            //vehicle records
            regNumber.setCellValueFactory(new PropertyValueFactory<>("registrationCol"));
            model.setCellValueFactory(new PropertyValueFactory<>("modelCol"));
            make.setCellValueFactory(new PropertyValueFactory<>("manufacturerCol"));
            engSize.setCellValueFactory(new PropertyValueFactory<>("engineSizeCol"));
            fuelType.setCellValueFactory(new PropertyValueFactory<>("fuelTypeCol"));
            colour.setCellValueFactory(new PropertyValueFactory<>("colourCol"));
            currentMileage.setCellValueFactory(new PropertyValueFactory<>("currentMileageCol"));
            
            //bookings
            bookingType.setCellValueFactory(new PropertyValueFactory<>("type"));
            
            pbookingDate.setCellValueFactory(new PropertyValueFactory<>("status"));
            fbookingDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tbCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

            
            //spc bookings
            spcAssigned.setCellValueFactory(new PropertyValueFactory<>("SPC"));
            vehicle_part.setCellValueFactory(new PropertyValueFactory<>("Name"));
            DeliveryDate.setCellValueFactory(new PropertyValueFactory<>("DeliveryDate")); 
            returnDate.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
            spcCost.setCellValueFactory(new PropertyValueFactory<>("Cost")); 
        }   
       
        // delete record from the table and database
            
    @FXML
            public void delete()
        {
            Customers selected;
            selected = customerTable.getSelectionModel().getSelectedItem();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selected.getfName() + " " + selected.getlName() + " from the system?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("GMSIS-Customer Accounts");
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES)
            {
                db.update("DELETE FROM CustomerAccounts WHERE CustomerID = " + selected.getCustID());
                fillTable();
            }
        }
        
            //search function for sorting
    @FXML
            public void searchBar() throws SQLException{
            
                
            FilteredList<Customers> filteredData;
            filteredData = new FilteredList<>(searchData, p -> true);
		if(nameRadio.isSelected())
                {
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Customers -> {
			
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Customers.getfName().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (Customers.getlName().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}
				return false; 
			});
		});
		SortedList<Customers> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
		customerTable.setItems(sortedData);
                }
                else
                {
                    System.out.println(searchBar.getText()+"\n"+searchBar.getAccessibleText());
                    String sql="SELECT CustomerID FROM 'VehicleRecords' WHERE Registration LIKE '%"+searchBar.getText()+"%';";
                    ResultSet rs=db.query(sql);
                    ResultSet rt=null;
                    searchData.clear();
                    while(rs.next())
                    {
                        sql="SELECT* FROM 'CustomerAccounts' WHERE CustomerID=="+rs.getInt("CustomerID")+";";
                        rt=db.query(sql);
                        while(rt.next())
                        {
                            searchData.add(new Customers(rt));
                        }
                        
                    }
                    customerTable.setItems(searchData);
                }
	}         
        //show add interface
    @FXML
        public void showAdd()
        {
        pane.setVisible(true);
        addcustomerLabel.setVisible(true);
        editcustomerLabel.setVisible(false);
        addbuttonPane.setVisible(true);
        savebuttonPane.setVisible(false);
        fname_field.clear();
        lname_field.clear();
        address_field.clear();
        postcode_field.clear();
        pnumber_field.clear();
        email_field.clear();
        }
        
        
        //show edit interface
    @FXML
        public void enableEdit()
        {
             // fetch existing information from the database and put it into editable textfields 
        Customers selected;
        selected = customerTable.getSelectionModel().getSelectedItem();
        if(selected!=null){
        pane.setVisible(true);
        addcustomerLabel.setVisible(false);
        editcustomerLabel.setVisible(true);
        savebuttonPane.setVisible(true);
        addbuttonPane.setVisible(false);

       
        
        fname_field.setText(selected.getfName());
        lname_field.setText(selected.getlName());
        address_field.setText(selected.getAddress());
        postcode_field.setText(selected.getPostcode());
        pnumber_field.setText(selected.getPnumber());
        email_field.setText(selected.getEmail());
        customerType.setValue(selected.getcType());
            }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Field not selected");
                    alert.setHeaderText(null);
                    alert.setContentText("Select a Customer then click edit");
                    alert.showAndWait();
                
            }
        }
        
        
         //add button
    @FXML
     public void AddCustomer(ActionEvent event){
         
        String postcodeValidation = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        String phoneValidation = "^(0[12357])[0-9]{9}$";
        
            
            try {
                if(!customerType.getValue().equals("")&&!fname_field.getText().trim().equals("")&&!lname_field.getText().trim().equals("")&&!address_field.getText().trim().equals("")&&!postcode_field.getText().trim().equals("")&&!pnumber_field.getText().trim().equals("")&&!email_field.getText().trim().equals(""))
                {
                    if(pnumber_field.getText().matches(phoneValidation) && postcode_field.getText().matches(postcodeValidation) && email_field.getText().contains("@")&& email_field.getText().contains(".")) {
                        // SQLiteConnection db = SQLiteConnection.getInstance();
                        String stmt = "INSERT INTO CustomerAccounts  (CustomerType, FirstName, LastName, Address, PostCode, PhoneNumber, EmailAddress) VALUES ('" + customerType.getValue() + "','" + fname_field.getText() + "', '" + lname_field.getText() + "', '" + address_field.getText() + "','" + postcode_field.getText() +"', '" + pnumber_field.getText() +"','" + email_field.getText() +"')";
                        db.update(stmt);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Customer Added");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(fname_field.getText()+" was added successfully\n\nPlease DONT FORGET to add a vehicle");
                        successAlert.showAndWait();
                        pane.setVisible(false);
                        addcustomerLabel.setVisible(false);
                        customerTable.getItems().clear();
                        searchData.clear();
                        fillTable();
                    }else{
                        Alert numberFormatAlert = new Alert(Alert.AlertType.ERROR);
                        numberFormatAlert.setContentText("Please check the following:\n\n 1) Phone number is 11 digits and starts with 0 followed by any values 1-7\n\n2) Make sure the postcode is correct and capital.\n\n3) Please check that your email is in the correct format");
                        numberFormatAlert.showAndWait();
                    }
                    
                    }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("One of the fields is empty");
                    alert.showAndWait();
                   
                }

            }catch(Exception e) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot have the same email or phone number as someone else");
                    alert.showAndWait();
            }
        }
     
     
     //edit customer method
    @FXML
     public void EditCustomer()
    {
        String postcodeValidation = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        String phoneValidation="^(0[12357])[0-9]{9}$";
        Customers selected;
        selected = customerTable.getSelectionModel().getSelectedItem();
        try 
        {
            if(!customerTable.getSelectionModel().isEmpty())
            {
                if(pnumber_field.getText().matches(phoneValidation) && postcode_field.getText().matches(postcodeValidation) && email_field.getText().contains("@")&& email_field.getText().contains(".")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit the details of " + selected.getfName() + " " + selected.getlName() + "?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait(); 
                    if (alert.getResult() == ButtonType.YES) {
                    SQLiteConnection db = SQLiteConnection.getInstance();
                    String stmt = "UPDATE CustomerAccounts SET  CustomerType ='"+customerType.getValue()+"',FirstName='"+fname_field.getText()+"',LastName='"+lname_field.getText()+"',Address='"+address_field.getText()+"',PostCode='"+postcode_field.getText()+"', PhoneNumber='"+pnumber_field.getText()+"',EmailAddress='"+email_field.getText()+"' WHERE CustomerID="+selected.getCustID()+"";
                    db.update(stmt);
                    pane.setVisible(false);
                    editcustomerLabel.setVisible(false);
                    fillTable();
                    }
                }else{
                    Alert numberFormatAlert = new Alert(Alert.AlertType.ERROR);
                    numberFormatAlert.setContentText("Please check the following:\n\n 1) Phone number is 11 digits and starts with 0 followed by any values 1-7\n\n2) Make sure the postcode is correct and capital.\n\n3) Please check that your email is in the correct format");
                    numberFormatAlert.showAndWait();
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
     
    @FXML
    private void searchByReg(ActionEvent event) {
        recordsTable.getItems().clear();
        bookingTable.getItems().clear();
        searchBar.setDisable(false);
        searchBar.setPromptText("By Registration Number");
    }
    
    
}   



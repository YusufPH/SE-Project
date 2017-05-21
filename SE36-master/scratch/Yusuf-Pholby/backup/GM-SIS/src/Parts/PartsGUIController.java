/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;

import CustomerAccounts.Customers;
import DiagAndRep.Repair;
import VehicleRecords.Records;

import common.SQLiteConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Aaron
 */
public class PartsGUIController implements Initializable {
    
    @FXML
    private TableView<PartsM> table;    //assign Parts Table variable
    @FXML private TableColumn<PartsM, Integer> ID;  //assign Parts table Columns
    @FXML private TableColumn<PartsM, String> Name;
    @FXML private TableColumn<PartsM, String> Desc;
    @FXML private TableColumn<PartsM, Integer> Quantity;
    @FXML private TableColumn<PartsM, Double> Cost;
    
    @FXML private TableView<Repair> tablebooking;   //assigns booking table variable
    @FXML private TableColumn<Repair, String> bookingdate;  //assigns booking table columns
    @FXML private TableColumn<Repair, Integer> bookingid;
    @FXML private TableColumn<Repair, String> bookingreg;
    @FXML private TableColumn<Repair, Integer> bookingcust;
    @FXML private TableColumn<Repair, String> bookingfname;
    @FXML private TableColumn<Repair, String> bookingsname;   
    @FXML private TableColumn<Repair, String> bookingtype;
    @FXML private TableColumn<Repair, String> bookingstatus;
    @FXML private TableColumn<Repair, String> bookingdate2;    
    
    @FXML private TableView<PartsAssigned> tableparts;  //assigns parts assigned table
    @FXML private TableColumn<PartsAssigned, Integer> tablepartsid; //assigns parts assigned columns
    @FXML private TableColumn<PartsAssigned, String> tablepartsname;
    @FXML private TableColumn<PartsAssigned, String> tablepartwarranty;
    
    @FXML private Button withdrawButton;    //button to withdraw part
    @FXML private Button depositButton;     //button to deposit part
    @FXML private TextField withdrawId;     //button 
    @FXML private TextField depositId;   
    @FXML private TextField searchBar;      //Search bar to search
    
    @FXML private Button addPartButton;     //button to Add Part to table
    @FXML private Button deletePartButton;  //button to Delete part from table
    @FXML private Button editPartButton;
    @FXML private Button confirmEdit;
    @FXML private TextField idfield;        //Text fields for adding a part
    @FXML private TextField namefield;
    @FXML private TextField descfield;
    @FXML private TextField quanfield;
    @FXML private TextField costfield;
    
    @FXML private Label custid;
    @FXML private Label custfname;
    @FXML private Label custsname;
    @FXML private Label custtype;
    @FXML private Label custmanu;
    @FXML private Label custmodel;
    
    @FXML private AnchorPane pane;    //visibility anchor pane
    
    
    private ObservableList<PartsM> searchData = FXCollections.observableArrayList();    //observable list for search query
       
    String stt = "";    //initialize vars
    String sql = "";
    int switchDelete = 0;    
    
    private final SQLiteConnection db = SQLiteConnection.getInstance(); //db connection    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setVisible(false);
        idfield.setVisible(false);
        namefield.setVisible(false);
        descfield.setVisible(false);
        quanfield.setVisible(false);
        costfield.setVisible(false);
        confirmEdit.setVisible(false);
        fillTable();    //fill all tables
        fillTableBooking();        
    }   
    
    public void fillTableBooking()  //fills table Booking from database
    {
        setValueFactoriesBooking();
        ArrayList<Repair> tableValues2 = new ArrayList<>();
        String sqlbooking = "SELECT * FROM Bookings";
        ResultSet rs = db.query(sqlbooking); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {                                
                tableValues2.add(new Repair(rs));
            }            
            tablebooking.setItems(FXCollections.observableArrayList(tableValues2));                                   
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        
    }
       
    public void fillTablePartsAny()
    {
        PartsAssigned partSet = tableparts.getSelectionModel().getSelectedItem();
        Repair repairChoice = tablebooking.getSelectionModel().getSelectedItem();
        setValueFactoriesParts();
        ArrayList<PartsAssigned> tableval = new ArrayList<>();
        String sqlpart = "SELECT * FROM PartsAssigned WHERE reg = '"+repairChoice.getReg()+"'";
        ResultSet rs = db.query(sqlpart);
            try {
                while (rs.next())
                {
                   tableval.add(new PartsAssigned(rs));                   
                    
                }
                tableparts.setItems(FXCollections.observableArrayList(tableval));                                   
            } catch (SQLException e) {
                e.printStackTrace();                        
            }
    }      

    public void fillTable() //method to fill table in fxml
    {
        setValueFactories();
        ArrayList<PartsM> tableValues = new ArrayList<>(); // array list for individual table entries                  
        sql = "SELECT ID, Name, Description, StockQuantity, Cost FROM Parts"; //sql query decleration           
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {                
                tableValues.add(new PartsM(rs)); // add the values received from the database to the fxml table                                
                searchData.add(new PartsM(rs));
            }
            table.setItems(FXCollections.observableArrayList(tableValues));                                              
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }

    //set values factories for Parts table
    private void setValueFactories() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("cst"));               
    }
    
    // set values factories for Booking table
    private void setValueFactoriesBooking()
    {        
        bookingid.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        bookingreg.setCellValueFactory(new PropertyValueFactory<>("reg"));
        bookingcust.setCellValueFactory(new PropertyValueFactory<>("custID"));
        bookingfname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        bookingsname.setCellValueFactory(new PropertyValueFactory<>("sname"));                
        bookingdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        bookingtype.setCellValueFactory(new PropertyValueFactory<>("type"));
        bookingstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        bookingdate2.setCellValueFactory(new PropertyValueFactory<>("type"));
    }
       
    //set values for Parts Assigned Table 
    private void setValueFactoriesParts()
    {
        tablepartsid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablepartsname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablepartwarranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));
    }
    
    //withdraw button to withdraw a part
    public void withdrawButton(ActionEvent event) 
    {
        PartsM partSelection = table.getSelectionModel().getSelectedItem(); //get selected part
        int idTemp = partSelection.getqnt();                                                                          
        int newQnt = idTemp - 1;                                                                 
        
        try {           
            if(!table.getSelectionModel().isEmpty())
            {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to withdraw part " + partSelection.getname() + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait(); 
                if (alert.getResult() == ButtonType.YES) 
                {
                    SQLiteConnection db = SQLiteConnection.getInstance();
                    String stmt = "UPDATE Parts SET StockQuantity= '"+newQnt+"' WHERE ID="+partSelection.getId()+"";
                    db.update(stmt);
                    fillTable();
                            Calendar date = Calendar.getInstance();
                            date.setTime(new Date());
                            Format f = new SimpleDateFormat("dd-MMMM-yyyy");                            
                            Alert alert2 = new Alert(AlertType.INFORMATION);
                            Alert alert3 = new Alert(AlertType.INFORMATION);
                            alert2.setTitle("Withdrawn Part");
                            alert3.setTitle("Withdrawn Part");                                                         
                            alert2.setContentText("This part was withdrawn on: " + f.format(date.getTime()));
                            alert2.showAndWait();
                            date.add(Calendar.YEAR,1); 
                            alert3.setContentText("This parts warranty will run out on: " + f.format(date.getTime()));                                                                                   
                            alert3.showAndWait();                    
                }
            }
            else
            {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Nothing Selected!");
                alert2.setContentText("Please make a selection from the table");
                alert2.showAndWait();
            }
            
            
        }catch (Exception e)
        {
            
        }
        
    }
        //Deposit item into stock of Parts
    public void depositButton(ActionEvent event)
    {
        PartsM partSelection = table.getSelectionModel().getSelectedItem(); //parts selection
        int idTemp = partSelection.getqnt();                                                                          
        int newQnt = idTemp + 1;                    //add 1 to stock room
            
        try {           
            if(!table.getSelectionModel().isEmpty())
            {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to deposit part " + partSelection.getname() + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait(); 
                if (alert.getResult() == ButtonType.YES) 
                {
                    SQLiteConnection db = SQLiteConnection.getInstance();       //connect to db
                    String stmt = "UPDATE Parts SET StockQuantity= '"+newQnt+"' WHERE ID="+partSelection.getId()+"";    //update db
                    db.update(stmt);
                    fillTable();    //refill table
                }
            }
            
        }catch (Exception e)
        {
            
        }
        
    }
    //show parts of vehicle chosen
    public void showParts(ActionEvent event)
    {
        Repair repairChoice = tablebooking.getSelectionModel().getSelectedItem();   //booking selection                     
        try {              
            if(!tablebooking.getSelectionModel().isEmpty()) //make sure selection isnt empty
            {
               Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to show parts for " + repairChoice.getReg() + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait(); 
                if (alert.getResult() == ButtonType.YES) 
                {                    
                    custid.setText(Integer.toString(repairChoice.getCustomerId())); //fill fields with text from table
                    custfname.setText(repairChoice.getFname());
                    custsname.setText(repairChoice.getSname());
                    custtype.setText(repairChoice.gettype());
                    fillTablePartsAny();
                    getModManu();
                    pane.setVisible(true);       
                }
            }         
        }catch (Exception e)
        {            
        }
    }
    
    public void getModManu()    //method to access database and get value from separate table
    {
        try{
     Repair repairChoice = tablebooking.getSelectionModel().getSelectedItem();  //                         
     String sql = "SELECT * FROM VehicleRecords Where CustomerID = " + repairChoice.getCustomerId(); //sql query decleration
     ResultSet rs = db.query(sql);
     custmanu.setText(rs.getString("Manufacturer"));    //set text of label to result in field of label
     custmodel.setText(rs.getString("Model"));
    }catch(Exception e)
    {}
    }
    //delete part from a vehicle
    public void deletePart(ActionEvent event)
    {
        PartsAssigned partsChoice = tableparts.getSelectionModel().getSelectedItem();   //access parts assigned       
        Repair repairChoice = tablebooking.getSelectionModel().getSelectedItem();   //access vehicles
        try {
            if(!tableparts.getSelectionModel().isEmpty())   //make sure selection is made
            {                
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to remove " + partsChoice.getname() + " from vehicle with registration " + repairChoice.getReg() +"?", ButtonType.YES, ButtonType.NO); //confirm selection
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES)    //confirm decision
                {  
                            String sql = "DELETE FROM PartsAssigned WHERE Reg = '"+partsChoice.getreg()+"' AND partName = '"+partsChoice.getname()+"'"; //update db
                            db.update(sql);
                            fillTablePartsAny();    //fill the table                   
                }
            }
        }catch (Exception e)
        {
            
        }
    }        
    //method to add Part to a Vehicle
    public void addPart(ActionEvent event)
    {               
        Repair repairChoice = tablebooking.getSelectionModel().getSelectedItem();   //get booking information
        PartsM partAdd = table.getSelectionModel().getSelectedItem();   //get part information
        PartsAssigned partAss = tableparts.getSelectionModel().getSelectedItem();   //get assignment information
        if(table.getSelectionModel().isEmpty())
        {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You must choose a part to add from the Parts table!", ButtonType.OK);    //confirm a part is chose nfrom the field
            alert2.showAndWait();
        }
        int idTemp = partAdd.getqnt();                                                                          
        int newQnt = idTemp - 1;    //subtract from stock room part          
        try{            
            if(!tablebooking.getSelectionModel().isEmpty()) //ensure booking selection is made to add part to
            {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add A Part?");
                dialog.setHeaderText("Confirm your choice of part to be added to " +repairChoice.getReg() + " ?");
                dialog.setContentText("Confirm you wish to add " + partAdd.getname() + " to vehicle with Reg: " + repairChoice.getReg() + "\n Please confirm the ID of the Part: ");        //confirm part addition                

                    Optional<String> result = dialog.showAndWait(); //ensure something is entered into the field
                    if (result.isPresent()){
                        int idaddition = Integer.parseInt(result.get());                        
                            Calendar date = Calendar.getInstance();
                            date.setTime(new Date());   //gets Date
                            Format f = new SimpleDateFormat("dd-MM-yyyy");      //format of date                                                                                                            ;
                            date.add(Calendar.YEAR,1);  //ad a year to the date
                            f.format(date.getTime());   
                            String warr = f.format(date.getTime()); //puts the new date into string warranty warr
                        String stmt2 = "UPDATE Parts SET StockQuantity= '"+newQnt+"' WHERE ID= '"+ partAdd.getId() +"' ";   //update db
                        db.update(stmt2);
                        fillTable();
                        String stmt = "INSERT INTO PartsAssigned  (Reg, partID, partName, Cost, CustomerID, Warranty) VALUES ('" + repairChoice.getReg() + "','" + idaddition + "', '" + partAdd.getname() + "', '" + partAdd.getcst() + "','"+ repairChoice.getCustomerId() +"' , '"+ warr + "')";                        
                        db.update(stmt);
                        fillTablePartsAny();
                    }                                                      
            }       
        }catch(Exception e)
        {
            
        }                
    }
    
    public void deletePartBtn(ActionEvent event)    //delete Part from vehicle 
    {
        PartsM partDelete = table.getSelectionModel().getSelectedItem();    //select Part
        try{
            if(!table.getSelectionModel().isEmpty())    //enure selection is made
            {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to remove " + partDelete.getname() + " from the database? this cannot be reversed..", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES)
                {
                    String sql = "DELETE FROM Parts WHERE ID = '"+partDelete.getId()+"'";   //delete part
                    db.update(sql);
                    fillTable();                                        
                }
            }        
        }catch(Exception e)
        {
            
        }
    }
    //Edit part button 
    public void editPartBtn(ActionEvent event)
    {
        PartsM selected;
        selected = table.getSelectionModel().getSelectedItem(); //getselected item
        try 
        {
            if(!table.getSelectionModel().isEmpty())    //make sure table selection isnt empty
            {
            confirmEdit.setVisible(true);   //make fields visible
            idfield.setVisible(true);
            namefield.setVisible(true);
            descfield.setVisible(true);
            quanfield.setVisible(true);
            costfield.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit the details of " + selected.getname() + " ? \n Click the star 'Save Edit' when you wish to confirm the edit", ButtonType.YES, ButtonType.NO);  //confirm you want to edit the part 
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) { 
            idfield.setText(Integer.toString(selected.getId()));        //sets field to text from field
            namefield.setText(selected.getname());
            descfield.setText(selected.getdesc());
            quanfield.setText(Integer.toString(selected.getqnt()));
            costfield.setText(Double.toString(selected.getcst()));                                        
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
    //button to confirm edited fields of Part
    public void confirmEdit(ActionEvent event)
    {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to confirm this edit", ButtonType.YES, ButtonType.NO);  //alert to confirm edit choice
            alert2.showAndWait();
            if(alert2.getResult() == ButtonType.YES)
            {
            PartsM selected;
            selected = table.getSelectionModel().getSelectedItem();
            String stmt = "UPDATE Parts SET  ID = '" + Integer.parseInt(idfield.getText()) + "', Name = '" + namefield.getText() + "', Description= '" + descfield.getText() + "', StockQuantity= '" + Integer.parseInt(quanfield.getText()) + "', Cost = '"+ Double.parseDouble(costfield.getText())+ "' WHERE ID = "+selected.getId()+""; //update database parts with what they enter
            db.update(stmt);
            idfield.clear();    //clear all the fields after adding part
            namefield.clear();
            quanfield.clear();
            descfield.clear();
            costfield.clear();
            fillTable();    //fill Parts table with updated information
            confirmEdit.setVisible(false);  //make all fields  disappear
            idfield.setVisible(false);
            namefield.setVisible(false);
            descfield.setVisible(false);
            quanfield.setVisible(false);
            costfield.setVisible(false);
            }                     
    }   
    public void addPartBtn(ActionEvent event)   //add Part to Vehicle button
    {
            try {
                if(!namefield.equals("") && !costfield.equals(0) && !quanfield.equals(0) && !idfield.equals(0)) //make sure the fields arent empty
                {
                                idfield.setVisible(true);   //make fields visible
                                namefield.setVisible(true);
                                descfield.setVisible(true);
                                quanfield.setVisible(true);
                                costfield.setVisible(true);
                        Alert alert = new Alert(AlertType.INFORMATION); //alert to prompt the user to enter details
                        alert.setTitle("Part Added");
                        alert.setContentText("Enter the details of the Part then click 'Add Part' again!");
                        alert.showAndWait();
                        String stmt = "INSERT INTO Parts (ID, Name, Description, StockQuantity, Cost) VALUES ('" + Integer.parseInt(idfield.getText()) + "','" + namefield.getText() + "', '" + descfield.getText() + "', '" + Integer.parseInt(quanfield.getText()) + "','" + Double.parseDouble(costfield.getText()) +"')";     //statement to insert
                        db.update(stmt);    //update db
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Part Added");
                        successAlert.setHeaderText(null);   //alert to confirm success
                        successAlert.setContentText(namefield.getText()+" was added successfully");
                        successAlert.showAndWait();
                        idfield.clear();    //clear all the fields after adding part
                        namefield.clear();
                        quanfield.clear();
                        descfield.clear();
                        costfield.clear();
                        fillTable();
                        idfield.setVisible(false);  //make fields disappear 
                        namefield.setVisible(false);
                        descfield.setVisible(false);
                        quanfield.setVisible(false);
                        costfield.setVisible(false);
                    }
                    
                    else{   //catch error if field is empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("One of the fields is empty");
                    alert.showAndWait();
                   
                }

            }catch(NumberFormatException e) {
              System.out.println("error");
            }
        }
     
     
    
    //method for searchbar to search for parts
    public void searchBar(){   
        
        FilteredList<PartsM> filteredData;        
        filteredData = new FilteredList<>(searchData, p -> true);        

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(PartsM -> {
			
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				//match search to letters as they are entered
				if (PartsM.getname().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (PartsM.getname().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}
				return false; 
			});
		});
        
        SortedList<PartsM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        
    }
}
    
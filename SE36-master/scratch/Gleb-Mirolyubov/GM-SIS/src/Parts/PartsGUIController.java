/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;

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
    
    @FXML private TableView<Records> tablerecords;  //assigns record table var
    @FXML private TableColumn<Records, String> rreg;    //assigns record table columns
    @FXML private TableColumn<Records, Integer> rengsize;
    @FXML private TableColumn<Records, String> rmodel;
    @FXML private TableColumn<Records, String> rmanu;
    @FXML private TableColumn<Records, Integer> rmileage;    
    
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
    
    @FXML private AnchorPane pane;    //visibility anchor pane
    
    
    private ObservableList<PartsM> searchData = FXCollections.observableArrayList();    //observable list for search query
       
    String stt = "";    //initialize vars
    String sql = "";
    int switchDelete = 0;    
    
    private final SQLiteConnection db = SQLiteConnection.getInstance(); //db connection    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setVisible(false);       
        fillTable();    //fill all tables
        fillTableBooking();
        fillTableRecords();
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
   
    public void fillTableRecords()  //fills table Records from database
    {
            setValueFactoriesRecords();  
            ArrayList<Records> tableValues = new ArrayList<>();
            String sqlrecords = "SELECT * FROM VehicleRecords";
            ResultSet rs = db.query(sqlrecords);
            try {
                while (rs.next())
                {
                   tableValues.add(new Records(rs));                   
                    
                }
                tablerecords.setItems(FXCollections.observableArrayList(tableValues));                                   
            } catch (SQLException e) {
                e.printStackTrace();                        
            }
    }
    
    public void fillTableParts2()   //fills table Parts assigned from database
    {
        setValueFactoriesParts();
        ArrayList<PartsAssigned> tableval = new ArrayList<>();
        String sqlpart = "SELECT * FROM PartsAssigned WHERE reg = '11AA111'";       
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
    
    public void fillTableParts3()   //fill table parts assigned for otra vehicle
    {
        setValueFactoriesParts();
        ArrayList<PartsAssigned> tableval = new ArrayList<>();
        String sqlpart = "SELECT * FROM PartsAssigned WHERE reg = '22BB222'";       
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

    public void fillTableParts4()   //fill table parts assigned for otra mas vehicle
    {
        setValueFactoriesParts();
        ArrayList<PartsAssigned> tableval = new ArrayList<>();
        String sqlpart = "SELECT * FROM PartsAssigned WHERE reg = '33CC333'";       
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
    }
    
    //set values for Records table
    private void setValueFactoriesRecords()
    {        
        rreg.setCellValueFactory(new PropertyValueFactory<>("registrationCol"));
        rengsize.setCellValueFactory(new PropertyValueFactory<>("engineSizeCol"));
        rmodel.setCellValueFactory(new PropertyValueFactory<>("modelCol"));
        rmanu.setCellValueFactory(new PropertyValueFactory<>("manufacturerCol"));
        rmileage.setCellValueFactory(new PropertyValueFactory<>("currentMileageCol"));                
        //rlastservice.setCellValueFactory(new PropertyValueFactory<>("date"));
        //rwarranty.setCellValueFactory(new PropertyValueFactory<>("type"));
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
        
    public void depositButton(ActionEvent event)
    {
        PartsM partSelection = table.getSelectionModel().getSelectedItem();
        int idTemp = partSelection.getqnt();                                                                          
        int newQnt = idTemp + 1;                    
            
        try {           
            if(!table.getSelectionModel().isEmpty())
            {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to deposit part " + partSelection.getname() + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait(); 
                if (alert.getResult() == ButtonType.YES) 
                {
                    SQLiteConnection db = SQLiteConnection.getInstance();
                    String stmt = "UPDATE Parts SET StockQuantity= '"+newQnt+"' WHERE ID="+partSelection.getId()+"";
                    db.update(stmt);
                    fillTable();
                }
            }
            
        }catch (Exception e)
        {
            
        }
        
    }
    
    public void showParts(ActionEvent event)
    {
        Records recordChoice = tablerecords.getSelectionModel().getSelectedItem();
        //ArrayList<PartsAssigned> tableValuez = new ArrayList<>();
        try {
            if(!tablerecords.getSelectionModel().isEmpty())
            {
               Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to show parts for " + recordChoice.getregistrationCol() + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait(); 
                if (alert.getResult() == ButtonType.YES) 
                {
                    if (recordChoice.getcustomerIDCol() == 1)
                    {           
                        fillTableParts2();
                        pane.setVisible(true);                        
                    }
                    else if (recordChoice.getcustomerIDCol() == 2)
                    {            
                        fillTableParts3();                       
                        pane.setVisible(true);                        
                    }
                    else if (recordChoice.getcustomerIDCol() == 3)
                    {
                        fillTableParts4();
                        pane.setVisible(true);                        
                    }
                }
            }
            
        }catch (Exception e)
        {
            
        }
    }
    
    public void deletePart(ActionEvent event)
    {
        PartsAssigned partsChoice = tableparts.getSelectionModel().getSelectedItem();
        Records recordChoice = tablerecords.getSelectionModel().getSelectedItem();
        try {
            if(!tableparts.getSelectionModel().isEmpty())
            {                
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to remove " + partsChoice.getname() + " from vehicle with registration " + recordChoice.getregistrationCol() +"?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES)
                {  
                    if (partsChoice.getreg().equals("11AA111"))
                    {
                            String sql = "DELETE FROM PartsAssigned WHERE Reg = '11AA111' AND partName = '"+partsChoice.getname()+"'";
                            db.update(sql);
                            fillTableParts2();
                    }
                    else if (partsChoice.getreg().equals("22BB222"))
                    {
                            String sql = "DELETE FROM PartsAssigned WHERE Reg = '22BB222' AND partName = '"+partsChoice.getname()+"'";
                            db.update(sql);
                            fillTableParts3();                        
                    }
                    else if (partsChoice.getreg().equals("33CC333"))
                    {
                            String sql = "DELETE FROM PartsAssigned WHERE Reg = '33CC333' AND partName = '"+partsChoice.getname()+"'";
                            db.update(sql);
                            fillTableParts4();                        
                    }
                }
            }
        }catch (Exception e)
        {
            
        }
    }        
    
    public void addPart(ActionEvent event)
    {        

        Records recordChoice = tablerecords.getSelectionModel().getSelectedItem();
        PartsM partAdd = table.getSelectionModel().getSelectedItem();
        PartsAssigned partAss = tableparts.getSelectionModel().getSelectedItem();
        int idTemp = partAdd.getqnt();                                                                          
        int newQnt = idTemp - 1;   
        try{
            if(!tablerecords.getSelectionModel().isEmpty())
            {
                            if (!table.getSelectionModel().isEmpty())
                            {
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("WARNING!");
                                alert.setContentText("Please ensure you have SELECTED the Part you wish to add from the Parts table \n in the top left");
                                alert.showAndWait();
                            }
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add A Part?");
                dialog.setHeaderText("Confirm your choice of part to be added to " +recordChoice.getregistrationCol() + " ?");
                dialog.setContentText("Confirm you wish to add " + partAdd.getname() + " to vehicle with Reg: " + recordChoice.getregistrationCol() + "\n Please confirm the ID of the Part: ");
                System.out.println("DUCKZILLA");
                
                if (recordChoice.getregistrationCol().equals("11AA111"))
                {
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        int idaddition = Integer.parseInt(result.get());                        
                            Calendar date = Calendar.getInstance();
                            date.setTime(new Date());
                            Format f = new SimpleDateFormat("dd-MM-yyyy");                                                                                                              ;
                            date.add(Calendar.YEAR,1); 
                            f.format(date.getTime());
                            String warr = f.format(date.getTime());
                        String stmt2 = "UPDATE Parts SET StockQuantity= '"+newQnt+"' WHERE ID= '"+ partAdd.getId() +"' ";
                        db.update(stmt2);
                        fillTable();
                        String stmt = "INSERT INTO PartsAssigned  (Reg, partID, partName, Cost, Warranty) VALUES ('" + recordChoice.getregistrationCol() + "','" + idaddition + "', '" + partAdd.getname() + "', '" + partAdd.getcst() + "', '"+ warr + "')";                        
                        db.update(stmt);
                        fillTableParts2();
                    }                                       
                }
                else if (recordChoice.getregistrationCol().equals("22BB222"))
                {
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        int idaddition = Integer.parseInt(result.get());
                            Calendar date = Calendar.getInstance();
                            date.setTime(new Date());
                            Format f = new SimpleDateFormat("dd-MM-yyyy");                                                                                                              ;
                            date.add(Calendar.YEAR,1); 
                            f.format(date.getTime());
                            String warr = f.format(date.getTime());
                        String stmt = "INSERT INTO PartsAssigned  (Reg, partID, partName, Cost, Warranty) VALUES ('" + recordChoice.getregistrationCol() + "','" + idaddition + "', '" + partAdd.getname() + "', '" + partAdd.getcst() + "', '"+ warr + "')";
                        db.update(stmt);
                        fillTableParts3();
                    } 
                }
                else if (recordChoice.getregistrationCol().equals("33CC333"))
                {
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        int idaddition = Integer.parseInt(result.get());
                            Calendar date = Calendar.getInstance();
                            date.setTime(new Date());
                            Format f = new SimpleDateFormat("dd-MM-yyyy");                                                                                                              ;
                            date.add(Calendar.YEAR,1); 
                            f.format(date.getTime());
                            String warr = f.format(date.getTime());
                        String stmt = "INSERT INTO PartsAssigned  (Reg, partID, partName, Cost, Warranty) VALUES ('" + recordChoice.getregistrationCol() + "','" + idaddition + "', '" + partAdd.getname() + "', '" + partAdd.getcst() + "', '"+ warr + "')";
                        db.update(stmt);
                        fillTableParts4();
                    } 
                                                
            }
            else {                
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
    
    public void editPartBtn(ActionEvent event)
    {
        PartsM selected;
        selected = table.getSelectionModel().getSelectedItem();
        try 
        {
            if(!table.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit the details of " + selected.getname() + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) { 
            idfield.setText(Integer.toString(selected.getId()));
            namefield.setText(selected.getname());
            descfield.setText(selected.getdesc());
            quanfield.setText(Integer.toString(selected.getqnt()));
            costfield.setText(Double.toString(selected.getcst()));                
            
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Click the star '*' if you wish to confirm the edit", ButtonType.OK);
            alert2.showAndWait();
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
    
    public void confirmEdit(ActionEvent event)
    {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to confirm this editt", ButtonType.YES, ButtonType.NO);
            alert2.showAndWait();
            if(alert2.getResult() == ButtonType.YES)
            {
                PartsM selected;
            selected = table.getSelectionModel().getSelectedItem();
            String stmt = "UPDATE Parts SET  ID = '" + Integer.parseInt(idfield.getText()) + "', Name = '" + namefield.getText() + "', Description= '" + descfield.getText() + "', StockQuantity= '" + Integer.parseInt(quanfield.getText()) + "', Cost = '"+ Double.parseDouble(costfield.getText())+ "' WHERE ID = "+selected.getId()+"";
            db.update(stmt);
            idfield.clear();    //clear all the fields after adding part
            namefield.clear();
            quanfield.clear();
            descfield.clear();
            costfield.clear();
            fillTable();   
            }                     
    }
    
    public void addPartBtn(ActionEvent event)   //add Part to Vehicle button
    {
            try {
                if(!namefield.equals("") && !costfield.equals(0) && !quanfield.equals(0) && !idfield.equals(0)) //make sure the fields arent empty
                {                    
                        
                        String stmt = "INSERT INTO Parts (ID, Name, Description, StockQuantity, Cost) VALUES ('" + Integer.parseInt(idfield.getText()) + "','" + namefield.getText() + "', '" + descfield.getText() + "', '" + Integer.parseInt(quanfield.getText()) + "','" + Integer.parseInt(costfield.getText()) +"')";
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
                    }
                    
                    else{
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
    
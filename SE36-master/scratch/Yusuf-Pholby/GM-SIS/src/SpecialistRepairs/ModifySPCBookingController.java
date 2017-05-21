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
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author gleb_mirolyubov
 */
public class ModifySPCBookingController implements Initializable {
    
    ObservableList<String> spcList = FXCollections.observableArrayList();
    
    ObservableList<String> partsList = FXCollections.observableArrayList();
    
    ObservableList<String> customersList = FXCollections.observableArrayList();
    
    ObservableList<String> vehiclesList = FXCollections.observableArrayList();
    
    private final SQLiteConnection db = SQLiteConnection.getInstance();
    
    @FXML
    private Button addBookingBtn;
    
    @FXML
    private RadioButton partsChoice;
    
    @FXML
    private RadioButton vehiclesChoice;
    
    @FXML
    private Label partVehicleChoiceText;
    
    @FXML
    private Label chooseText;
    
    @FXML
    private ChoiceBox partVehicleChoice;
    
    @FXML
    private ChoiceBox spcChoice;
    
    @FXML
    private Label descriptionText;

    @FXML
    private TextArea descriptionTextField;
    
    @FXML
    private Label customerText;
    
    @FXML
    private ChoiceBox customerChoice;
    
    @FXML
    private Label datesText;
    
    @FXML
    private Label deliveryText;
    
    @FXML 
    private DatePicker deliveryField;
    
    @FXML
    private Label returnText;
    
    @FXML 
    private DatePicker returnField;
    
    private boolean isItPart = false;
    private boolean isItVehicle = false;
    
    /* 
    *  This method is called when the window is opened.
    *  Initializes all methods and variables to start the window properly.
    */ 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           addBookingBtn.setVisible(true);
           fetchPartsData();
           fetchVehiclesData();
           fetchSPCData();
           fetchCustomersData();
           customerChoice.setItems(customersList);
           spcChoice.setItems(spcList);
           partsChoice.setVisible(false);
           vehiclesChoice.setVisible(false);
           partVehicleChoiceText.setVisible(false);
           chooseText.setVisible(false);
           partVehicleChoice.setVisible(false);
           descriptionText.setVisible(false);
           descriptionTextField.setVisible(false);
           customerText.setVisible(false);
           customerChoice.setVisible(false);
           datesText.setVisible(false);
           deliveryText.setVisible(false);
           deliveryField.setVisible(false);
           returnText.setVisible(false);
           returnField.setVisible(false);
           addBookingBtn.setVisible(false);

    /* 
    *  When any item in SPC choice box is selected, execute SPCselected()
    */ 
    spcChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        public void changed(ObservableValue ov, Number value, Number new_value) {
            SPCselected();
        }
    });
    
    /* 
    *  When any item in PartsVehicles choice box is selected, execute PartVehicleSelected() 
    */ 
    partVehicleChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
       public void changed(ObservableValue ov, Number value, Number new_value) {
         PartVehicleSelected();
       }  
    });
    }
    
    /* 
    *  This method shows text fields and choice boxes after the previous selection has taken place
    */ 
    @FXML
    private void SPCselected()
    {
      if(!spcChoice.getSelectionModel().isEmpty())
      {
        partsChoice.setVisible(true);
        vehiclesChoice.setVisible(true);
        partVehicleChoiceText.setVisible(true); 
      }
    }
    
    /* 
    *  This method shows text fields and choice boxes after the previous selection has taken place
    */ 
    @FXML
    private void PartVehicleSelected()
    {
      if(!partVehicleChoice.getSelectionModel().isEmpty())
      {
         descriptionText.setVisible(true);
         descriptionTextField.setVisible(true);
        
        customerText.setVisible(true);
        customerChoice.setVisible(true);
        datesText.setVisible(true);
        deliveryText.setVisible(true);
        deliveryField.setVisible(true);
        returnText.setVisible(true);
        returnField.setVisible(true);
        addBookingBtn.setVisible(true);
      }
    }
    
    /* 
    *  If user choice was Parts, then assign the rest of elements correspondingly
    */ 
    @FXML
    private void partChoiceSelected()
    {
        chooseText.setVisible(true);
        chooseText.setText("Choose a part in your system from the list*");
        partVehicleChoice.setVisible(true);
        partVehicleChoice.setItems(partsList);
        descriptionText.setText("Add description about this part*");
        
        isItPart = true;
        isItVehicle = false;
    }
    
    /* 
    *  If user choice was Vehicles, then assign the rest of elements correspondingly
    */ 
    @FXML
    private void vehicleChoiceSelected()
    {
        chooseText.setVisible(true);
        chooseText.setText("Choose a vehicle in your system from the list*");
        partVehicleChoice.setVisible(true);
        partVehicleChoice.setItems(vehiclesList);
        descriptionText.setText("Add description about this vehicle*");
        
        isItPart = false;
        isItVehicle = true;
    } 
    
    /* 
    *  Fetch data from the database and populate choice box with it
    */ 
    private void fetchPartsData()
    {
        String sql = "SELECT Name FROM Parts";
        ResultSet rs = db.query(sql);
        try {
            String name = null;
            while (rs.next())
            {
                String n = rs.getString("Name");
                name = n.replace("\n", ",");
                partsList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Fetch data from the database and populate choice box with it
    */ 
    private void fetchVehiclesData()
    {
        String sql = "SELECT Model FROM VehicleRecords";
        ResultSet rs = db.query(sql);
        try {
            String name = null;
            while (rs.next())
            {
                String n = rs.getString("Model");
                name = n.replace("\n", ",");
                vehiclesList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Fetch data from the database and populate choice box with it
    */ 
    private void fetchSPCData()
    {
        String sql = "SELECT Name FROM SpecialistRepairs";
        ResultSet rs = db.query(sql);
        try {
            String name = null;
            while (rs.next())
            {
                String n = rs.getString("Name");
                name = n.replace("\n", ",");
                spcList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Fetch data from the database and populate choice box with it
    */ 
    private void fetchCustomersData()
    {
        String sql = "SELECT CustomerID, FirstName, LastName FROM CustomerAccounts";
        ResultSet rs = db.query(sql);
        try {
            String customer = null;
            while (rs.next())
            {
                int i = rs.getInt("CustomerID");
                String n = rs.getString("FirstName");
                String l = rs.getString("LastName");
                customer = ""+i+" "+n+" "+l+" ";
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Adds a new SPC booking by inserting a new row into table in the database
    */ 
    public void AddNewSPCBooking(ActionEvent event) throws SQLException
    {
        try 
        {
          if(true)
                {
                System.out.println(deliveryField.getValue().toString());
                String customerInfo = customerChoice.getValue().toString();
                String[] arr = customerInfo.split(" ");  
                int custID = Integer.valueOf(arr[0]);
                String flname = arr[1]+" "+arr[2];
                String typeChoice = "";
                float cost = 0;
                if (isItPart) {
                    typeChoice = "Part";
                    String partCost = "SELECT Cost FROM Parts WHERE Name = '"+partVehicleChoice.getValue().toString()+"'";
                    ResultSet rs = db.query(partCost);
                    //cost of the SPC Booking of a part is cost of the part + 5 pound tax
                    cost = rs.getFloat("Cost") + 5;
                    
                    SQLiteConnection db = SQLiteConnection.getInstance();
                    String stmt = "INSERT INTO SPCBookings (SPC, Name, Type, RegistrationNumber, Details, Cost, CustomerID, CustomerName, DeliveryDate, ReturnDate) VALUES ('"+spcChoice.getValue()+"','"+partVehicleChoice.getValue().toString()+"','"+typeChoice+"','-','"+descriptionTextField.getText()+"','"+cost+"','"+custID+"','"+flname+"','"+deliveryField.getValue().toString()+"','"+returnField.getValue().toString()+"')";
                    db.update(stmt);
                }
                else if (isItVehicle) {
                    typeChoice = "Vehicle";
                    String warranty = "SELECT UnderWarranty FROM VehicleRecords WHERE Model = '"+partVehicleChoice.getValue().toString()+"'";
                    ResultSet rs = db.query(warranty);
                    if (rs.getString("UnderWarranty").equals("Yes"))
                    { 
                        cost = 0;
                        
                    } else cost = 120;
                    
                    SQLiteConnection db = SQLiteConnection.getInstance();
                    String stmt = "INSERT INTO SPCBookings (SPC, Name, Type, RegistrationNumber, Details, Cost, CustomerID, CustomerName, DeliveryDate, ReturnDate) VALUES ('"+spcChoice.getValue()+"','"+partVehicleChoice.getValue().toString()+"','"+typeChoice+"',(SELECT Registration FROM VehicleRecords WHERE Model = '"+partVehicleChoice.getValue().toString()+"'),'"+descriptionTextField.getText()+"','"+cost+"','"+custID+"','"+flname+"','"+deliveryField.getValue().toString()+"','"+returnField.getValue().toString()+"')";
                    db.update(stmt);
                }
                
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
        SpecialistRepairsGUIController.FillSPCBookings.fillTable();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } 
    
    /* 
    *  Closes window
    */ 
    public void CloseWindow(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
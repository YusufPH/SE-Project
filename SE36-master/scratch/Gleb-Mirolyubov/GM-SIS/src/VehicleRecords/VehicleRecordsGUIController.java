package VehicleRecords;

import CustomerAccounts.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import common.SQLiteConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author samaggarwal
 */



public class VehicleRecordsGUIController implements Initializable 
        
{
    SQLiteConnection db = SQLiteConnection.getInstance();
    
    @FXML private Label addvehicleLabel;
    
    
    
    @FXML private Label editLabel;
    @FXML private Label addLabel;

    
    
    @FXML private Button add_vehicle;
    @FXML private Button saveButton;
    @FXML private Button editButton;
    @FXML private Button selectCustomer;
   



    @FXML private TextField customer_ID;
    @FXML private TextField registration;
    @FXML private TextField type;
    @FXML private TextField manufacturer; 
    @FXML private TextField model;
    @FXML private TextField engineSize;
    @FXML private TextField fuelType;
    @FXML private TextField colour;
    @FXML private TextField currentMileage;
    @FXML private TextField lastServiceDate;
    @FXML private TextField motDate;
    @FXML private TextField searchBar;
    
      ObservableList<String> warrantyList = FXCollections.observableArrayList("Yes","No");
        @FXML private ChoiceBox underWarranty;
    
    //the grid
    @FXML private GridPane pane;
        
    private ArrayList<Records> records = new ArrayList<>();

    @FXML private TableView<Records> recordsTable; 

    @FXML private TableColumn<Records, Integer> customerIDCol;
    @FXML private TableColumn<Records, String> registrationCol;
    @FXML private TableColumn<Records, String> typeCol;
    @FXML private TableColumn<Records, String> manufacturerCol;
    @FXML private TableColumn<Records, String> modelCol;
    @FXML private TableColumn<Records, String> engineSizeCol;
    @FXML private TableColumn<Records, String> fuelTypeCol;
    @FXML private TableColumn<Records, String> colourCol;
    @FXML private TableColumn<Records, Integer> currentMileageCol;
    @FXML private TableColumn<Records, String> lastServiceDateCol;
    @FXML private TableColumn<Records, String> motDateCol;
    @FXML private TableColumn<Records, String> underWarrantyCol;
    
    
    
    @FXML private TableView<Customers> custTable;


        @FXML private TableColumn<Customers, String> fName;
        @FXML private TableColumn<Customers, String> lName;
        @FXML private TableColumn<Customers, String> customerID;
        
    
     private ObservableList<Records> searchData = FXCollections.observableArrayList();

     
     //variables to hold the values from template
//     String manu="";
//     String mod="";
//     String fuelT="";
//     String engSize="";
//
//       public VehicleRecordsGUIController(String manu,String model,String fuelType,String engSize) 
//        {
//        this.manu = manu;
//        this.mod = model;
//        this.fuelT = fuelType;
//        this.engSize = engSize;
//        }

    @FXML
    void addButton(ActionEvent event) 
    {
        Records selected;
        selected = recordsTable.getSelectionModel().getSelectedItem();
        addLabel.setVisible(true);
        add_vehicle.setVisible(false);
        saveButton.setVisible(false);
        editLabel.setVisible(false);
        custTable.setVisible(true);
        selectCustomer.setVisible(true);
        
    }

    @FXML
    void deleteButton(ActionEvent event) 
    {
        Records selected;
            selected = recordsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this" + selected.getcustomerIDCol() +" " + selected.getmanufacturerCol()+"From the system", ButtonType.YES, ButtonType.NO);
            alert.setTitle("GMSIS - Vehicle Records");
            alert.showAndWait();
            if(alert.getResult()== ButtonType.YES)
            {
                db.update("DELETE FROM VehicleRecords WHERE CustomerID = " + selected.getcustomerIDCol());
                fillTable();
            }
        
    }

    @FXML
    void editButton(ActionEvent event) 
    {
        
        pane.setVisible(true);
        addLabel.setVisible(false);
        add_vehicle.setVisible(false);
        saveButton.setVisible(true);
        editLabel.setVisible(true);
        custTable.setVisible(true);
        
        Records selected;
        selected = recordsTable.getSelectionModel().getSelectedItem();
        if(selected!=null){
        pane.setVisible(true);
        addLabel.setVisible(false);
        editLabel.setVisible(true);
        saveButton.setVisible(true);
        add_vehicle.setVisible(false);

       
        int temp=selected.getcustomerIDCol();
        String cID = Integer.toString(temp);
        customer_ID.setText(cID);
        registration.setText(selected.getregistrationCol());
        type.setText(selected.gettypeCol());
        manufacturer.setText(selected.getmanufacturerCol());
        model.setText(selected.getmodelCol());
        engineSize.setText(selected.getengineSizeCol());
        fuelType.setText(selected.getfuelTypeCol());
        colour.setText(selected.getcolourCol());
        int temp2=selected.getcurrentMileageCol();
        String xID = Integer.toString(temp2);
        currentMileage.setText(xID);
        lastServiceDate.setText(selected.getlastServiceDateCol());
        motDate.setText(selected.getmotDateCol());
        underWarranty.setValue(selected.getunderWarrantyCol());
        
        
 
        
            }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Field not selected");
                    alert.setHeaderText(null);
                    alert.setContentText("Select a Customer then click edit");
                    alert.showAndWait();
                
            }
        
    }


     
    @Override
    public void initialize(URL url, ResourceBundle rb) 

    {
        pane.setVisible(false);   
        addLabel.setVisible(false);
        add_vehicle.setVisible(false);
        saveButton.setVisible(false);
        editLabel.setVisible(false);
        custTable.setVisible(false);
        selectCustomer.setVisible(false);
        
        fillTable();
        fillcustTable();
        underWarranty.setItems(warrantyList);
        
        //init the fields
        
//        manufacturer.setText(manu); 
//       model.setText(mod);
//       engineSize.setText(engSize);
//        fuelType.setText(fuelT);
        
        

    }
   

       public void fillTable()
        {
            
            setValueFactories();   
            ArrayList<Records> tableValues = new ArrayList<>();
            String sql = "SELECT * FROM VehicleRecords";
            ResultSet rs = db.query(sql);
            try 
            {
                while (rs.next())
                {
                   tableValues.add(new Records(rs));
                   searchData.add(new Records(rs));
                    
                }
                recordsTable.setItems(FXCollections.observableArrayList(tableValues));
            } catch (SQLException e) {
                e.printStackTrace();
                
                
            }
        }
        public void fillcustTable()
        {
            
            setValueFactories();   
            ArrayList<Customers> tableValues = new ArrayList<>();
            String sql = "SELECT * FROM CustomerAccounts";
            ResultSet rs = db.query(sql);
            try 
            {
                while (rs.next())
                {
                   tableValues.add(new Customers(rs));
                   
                    
                }
                custTable.setItems(FXCollections.observableArrayList(tableValues));
            } catch (SQLException e) {
                e.printStackTrace();
                
                
            }
        }
        


        private void setValueFactories()
        {
            
            underWarrantyCol.setCellValueFactory(new PropertyValueFactory<>("underWarrantyCol"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerIDCol"));
            registrationCol.setCellValueFactory(new PropertyValueFactory<>("registrationCol"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("typeCol"));
            manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerCol"));
            modelCol.setCellValueFactory(new PropertyValueFactory<>("modelCol"));
            engineSizeCol.setCellValueFactory(new PropertyValueFactory<>("engineSizeCol"));
            fuelTypeCol.setCellValueFactory(new PropertyValueFactory<>("fuelTypeCol"));
            colourCol.setCellValueFactory(new PropertyValueFactory<>("colourCol"));
            currentMileageCol.setCellValueFactory(new PropertyValueFactory<>("currentMileageCol"));
            lastServiceDateCol.setCellValueFactory(new PropertyValueFactory<>("lastServiceDateCol"));
            motDateCol.setCellValueFactory(new PropertyValueFactory<>("motDateCol"));
            
            
            
            customerID.setCellValueFactory(new PropertyValueFactory<>("custID"));
            fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
            lName.setCellValueFactory(new PropertyValueFactory<>("lName"));
          
            

        }   

        public void add_vehicle(ActionEvent event)
        {
            try {
                if(!customer_ID.getText().trim().equals("")&&!registration.getText().trim().equals("")&&!type.getText().trim().equals("")&&!manufacturer.getText().trim().equals("")&&!model.getText().trim().equals("")&&!engineSize.getText().trim().equals("")&&!fuelType.getText().trim().equals("")&&!colour.getText().trim().equals("")&&!currentMileage.getText().trim().equals("")&&!lastServiceDate.getText().trim().equals("")&&!motDate.getText().trim().equals("")&&!underWarranty.getValue().equals(""))
                {

                        
                        String stmt = "INSERT INTO VehicleRecords (CustomerID, Registration, Type, Manufacturer, Model, EngineSize, FuelType, Colour, CurrentMileage, LastServiceDate, MOTDate, UnderWarranty) VALUES ('" + customer_ID.getText() + "','" + registration.getText() + "','" + type.getText() + "', '" + manufacturer.getText() + "','" + model.getText() +"', '" + engineSize.getText() + "','" + fuelType.getText() + "','" + colour.getText() + "','" + currentMileage.getText() + "','" +  lastServiceDate.getText() + "','" + motDate.getText() + "','" + underWarranty.getValue()+ "')";
                        db.update(stmt);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Vehicle Added");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(registration.getText()+" was added successfully");
                        successAlert.showAndWait();
                        //pane.setVisible(false);
                        addLabel.setVisible(false);
                        fillTable();
                        System.out.println("hello");
                        

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("One of the fields is empty");
                    alert.showAndWait();
                   
                }

            }catch (Exception e)
            {

            }

        }
        
        public void addSelectedCustomer()
         {
            pane.setVisible(true);
            addLabel.setVisible(true);
            add_vehicle.setVisible(true);
            saveButton.setVisible(false);
            editLabel.setVisible(false);
            custTable.setVisible(true);
            selectCustomer.setVisible(true);
             Customers select;
        select = custTable.getSelectionModel().getSelectedItem();
        if(!custTable.getSelectionModel().isEmpty())
            {
                int temp = select.getCustID();
                String show = Integer.toString(temp);
                customer_ID.setText(show);
            }
         }
        @FXML
        public void EditRecord()
    {
        Records selected;
        selected = recordsTable.getSelectionModel().getSelectedItem();
        try 
        {
            if(!recordsTable.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit the details of " + selected.getregistrationCol()+ " " + selected.getmanufacturerCol()+ "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) {
            SQLiteConnection db = SQLiteConnection.getInstance();
            String stmt = "UPDATE VehicleRecords SET CustomerID='"+customer_ID.getText()+"', Registration='"+registration.getText()+"', Type='"+type.getText()+"', Manufacturer='"+manufacturer.getText()+"', Model='"+model.getText()+"', EngineSize='"+engineSize.getText()+"', FuelType='"+fuelType.getText()+"', Colour='"+colour.getText()+"', CurrentMileage='"+currentMileage.getText()+"', LastServiceDate='"+lastServiceDate.getText()+"', MOTDate='"+motDate.getText()+"', UnderWarranty='"+underWarranty.getValue()+"' WHERE Registration = '"+selected.getregistrationCol()+"'";
            db.update(stmt);
            pane.setVisible(false);
            editLabel.setVisible(false);
            fillTable();
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
        public void searchBar(){
            
                
            FilteredList<Records> filteredData;
            filteredData = new FilteredList<>(searchData, p -> true);
		
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Records -> {
			
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Records.getmanufacturerCol().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				} else if (Records.getregistrationCol().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}
				return false; 
			});
		});
		SortedList<Records> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(recordsTable.comparatorProperty());
		recordsTable.setItems(sortedData);
	}         
        
        
        public void handleWarrentyButtonAction (ActionEvent event) throws IOException
        {
             
            String plate = recordsTable.getSelectionModel().getSelectedItem().getregistrationCol();
            
            WarrentyController controller = new WarrentyController(plate);
           FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Warrenty.fxml"));
           fxmlloader.setController(controller);
           Parent root1 = (Parent) fxmlloader.load();
           Stage stage = new Stage();
           stage.setScene(new Scene(root1));
           stage.show();
              
           
        }
        public void handleTemplateButtonAction(ActionEvent event) throws IOException{
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("template.fxml"));
             Parent root1 = (Parent) fxmlLoader.load();
             Stage stage = new Stage();
             stage.setScene(new Scene(root1)); 
             stage.show();    
         }
     
     
    

}

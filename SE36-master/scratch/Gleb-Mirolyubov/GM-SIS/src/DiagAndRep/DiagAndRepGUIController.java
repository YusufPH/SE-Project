/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagAndRep;

import CustomerAccounts.Customers;
import VehicleRecords.Records;
import common.SQLiteConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.Users;

/**
 * FXML Controller class
 *
 * @author Yusuf
 */
public class DiagAndRepGUIController implements Initializable {

    //tables 
    @FXML private TableView<Repair> booking_table;
    @FXML private TableColumn<Repair, Integer> bookingID_col; 
    @FXML private TableColumn<Repair, String> reg_col; 
    @FXML private TableColumn<Repair, Integer> custID_col; 
    @FXML private TableColumn<Repair, String> fname_col; 
    @FXML private TableColumn<Repair, String> sname_col; 
    @FXML private TableColumn<Repair, Integer> mechID_col; 
    @FXML private TableColumn<Repair, String> type_col; 
    @FXML private TableColumn<Repair, String> date_col; 
    
    @FXML private TableView<Customers> addBookingCustomer_table; 
    @FXML private TableColumn<Customers, Integer> addCCustomerID_col; 
    @FXML private TableColumn<Customers, String> addCFname_col; 
    @FXML private TableColumn<Customers, String> addCSname_col; 
    
    @FXML private TableView<Records> addBookingVehicle_table; 
    @FXML private TableColumn<Records, Integer> addVCustomerID_col; 
    @FXML private TableColumn<Records, String> addVReg_col; 
    @FXML private TableColumn<Records, String> addVModel_col; 
    
    // fields
    @FXML private TextField mileage_field;
    @FXML private TextField search_field;
    @FXML private TextField editMileage_field;
    
    //calander
    @FXML private DatePicker date_picker;
    @FXML private DatePicker editDate_picker;
    
    // choice box
    @FXML private ChoiceBox mech_choice;
    @FXML private ChoiceBox startTime_choice;
    @FXML private ChoiceBox searchType_choice;
    @FXML private ChoiceBox editMech_choice;
    @FXML private ChoiceBox editStartTime_choice;
    
    //radio
    @FXML private RadioButton warranty_radio;
    
    //buttons
    @FXML private Button add_button;
    @FXML private Button edit_button;
    @FXML private Button delete_button;
    @FXML private Button cancel_button1;
    @FXML private Button repair_button;
    @FXML private Button spc_button;
    @FXML private Button confirm_button;
    @FXML private Button cancel_button2;
    @FXML private Button editConfirm_button;
    
    // labels
    @FXML private Label addBooking_label;
    @FXML private Label EditBooking_label;
    
    @FXML private Label CustomerID_label;
    @FXML private Label AccountType_label;
    @FXML private Label FirstName_label;
    @FXML private Label Surname_label;
    @FXML private Label Address_label;
    @FXML private Label Postcode_label;
    @FXML private Label PhoneNumber_label;
    @FXML private Label Email_label;
    
    @FXML private Label bookingId_label;
    @FXML private Label registration_label;
    @FXML private Label customerId_label;
    @FXML private Label mechanicId_label;
    @FXML private Label bookingType_label;
    @FXML private Label bookingDate_label;
    @FXML private Label bookingTime_label;
    @FXML private Label warranty_label;
    @FXML private Label mileage_label;
    @FXML private Label duration_label;
    
    @FXML private Label vrCustomerID_label;
    @FXML private Label vrRegistration_label;
    @FXML private Label vrType_label;
    @FXML private Label vrManufacturer_label;
    @FXML private Label vrModel_label;
    @FXML private Label vrEngine_label;
    @FXML private Label vrFuel_label;
    @FXML private Label vrColour_label;
    @FXML private Label vrService_label;
    @FXML private Label vrMOT_label;
    
    // choice box options
    ObservableList<String> MechanicList = FXCollections.observableArrayList();
    ObservableList<String> TimesList = FXCollections.observableArrayList();
    ObservableList<String> SearchType = FXCollections.observableArrayList();
    
    private static Repair selected1;
    private static Customers selected2;
    private static Records selected3;

    private static String bookingtype = "Diagnostic and repair"; 
    private static String newStatus = "Ongoing";

    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private SQLiteConnection db = SQLiteConnection.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
        
        SearchType.addAll(Arrays.asList("Customer Name", "Registration", "Manufacturer"));
        searchType_choice.setItems(SearchType);
        
        booking_table.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selected1 = booking_table.getSelectionModel().getSelectedItem();
                    SetCustomerAccounts();
                    SetBookingDetails();
                    SetBookingHistory();
                    SetVehicleRecord();
                });
        
        addBookingCustomer_table.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selected2 = addBookingCustomer_table.getSelectionModel().getSelectedItem();
                    FillVehiclesTable(selected2.getCustID());
                });
        
        date_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            setBookingTimes(date_picker);
        
        date_picker.getEditor().setDisable(true);
        });
        
        editDate_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            setBookingTimes(editDate_picker);
        
        editDate_picker.getEditor().setDisable(true);
        });
        
        fetchMechanicData();
    }    
    
    public void fillTable(){
        setValueFactories();
        ArrayList<Repair> tableValues = new ArrayList<>(); // array list for individual table entries
        String sql = "SELECT * FROM Bookings"; //sql query decleration
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                tableValues.add(new Repair(rs)); // add the values received from the database to the fxml table
            }
            booking_table.setItems(FXCollections.observableArrayList(tableValues)); // adding the arraylist holding the info as an observalble list to the fxml table
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public void fillCustomerTable(){
        addCCustomerID_col.setCellValueFactory(new PropertyValueFactory<>("custID"));
        addCFname_col.setCellValueFactory(new PropertyValueFactory<>("fName"));
        addCSname_col.setCellValueFactory(new PropertyValueFactory<>("lName"));
        ArrayList<Customers> tableValues = new ArrayList<>(); // array list for individual table entries
        String sql = "SELECT * FROM CustomerAccounts"; //sql query decleration
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                tableValues.add(new Customers(rs)); // add the values received from the database to the fxml table
            }
            addBookingCustomer_table.setItems(FXCollections.observableArrayList(tableValues)); // adding the arraylist holding the info as an observalble list to the fxml table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void FillVehiclesTable(int ID){
        addVCustomerID_col.setCellValueFactory(new PropertyValueFactory<>("customerIDCol"));
        addVReg_col.setCellValueFactory(new PropertyValueFactory<>("registrationCol"));
        addVModel_col.setCellValueFactory(new PropertyValueFactory<>("modelCol"));
        ArrayList<Records> tableValues = new ArrayList<>(); // array list for individual table entries
        String sql = "SELECT * FROM VehicleRecords Where CustomerID = " +ID+";"; //sql query decleration
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                tableValues.add(new Records(rs)); // add the values received from the database to the fxml table
            }
            addBookingVehicle_table.setItems(FXCollections.observableArrayList(tableValues)); // adding the arraylist holding the info as an observalble list to the fxml table
        } catch (SQLException e) {
            e.printStackTrace();    
        }
    }
    
    private void setValueFactories() //set the property of the cell of each column
    {
        bookingID_col.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        reg_col.setCellValueFactory(new PropertyValueFactory<>("reg"));
        custID_col.setCellValueFactory(new PropertyValueFactory<>("custID"));
        fname_col.setCellValueFactory(new PropertyValueFactory<>("fname"));
        sname_col.setCellValueFactory(new PropertyValueFactory<>("sname"));
        mechID_col.setCellValueFactory(new PropertyValueFactory<>("mechID"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    @FXML
    public void allowAdd(){
        repair_button.setVisible(true);
        spc_button.setVisible(true);
        cancel_button1.setVisible(true);
        edit_button.setDisable(true);
        delete_button.setDisable(true);
    }
    
    @FXML
    public void allowEdit(){
        if(selected1 !=null){
            editConfirm_button.setVisible(true);
            cancel_button2.setVisible(true);
            add_button.setDisable(true);
            delete_button.setDisable(true);

            EditBooking_label.setVisible(true);
            editMileage_field.setVisible(true);
            editMech_choice.setVisible(true);
            editDate_picker.setVisible(true);
            editStartTime_choice.setVisible(true);

            editMech_choice.setItems(MechanicList);
            editStartTime_choice.setItems(TimesList);

            editDate_picker.setValue(LocalDate.now());
            Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
            editDate_picker.setDayCellFactory(dayCellFactory);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Field not selected");
            alert.setHeaderText(null);
            alert.setContentText("Select a field then click edit");
            alert.showAndWait();  
        }
    }
    
    @FXML
    public void cancel1(){
        repair_button.setVisible(false);
        spc_button.setVisible(false);
        add_button.setDisable(false);
        edit_button.setDisable(false);
        delete_button.setDisable(false);
        cancel_button1.setVisible(false);
    }
    
    @FXML
    public void delete(){
        selected1 = booking_table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("GMSIS");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Booking " + selected1.getBookingId() + "?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK)
        {
            db.update("DELETE FROM Bookings WHERE BookingID = " + selected1.getBookingId());
            fillTable();
        }
    }
    
    @FXML
    public void chooseRepair(){
        cancel_button1.setVisible(false);
        repair_button.setVisible(false);
        spc_button.setVisible(false);
        add_button.setDisable(true);
        addBooking_label.setVisible(true);
        addBookingCustomer_table.setVisible(true);
        fillCustomerTable();
        addBookingVehicle_table.setVisible(true);
        mileage_field.setVisible(true);
        mech_choice.setVisible(true);
        date_picker.setVisible(true);
        warranty_radio.setVisible(true);
        startTime_choice.setVisible(true);
        confirm_button.setVisible(true);
        cancel_button2.setVisible(true);
       
        mech_choice.setItems(MechanicList);
        
        date_picker.setValue(LocalDate.now());
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        date_picker.setDayCellFactory(dayCellFactory);
        
    }
    
    @FXML
    public void chooseSPC() throws IOException{
           Stage primaryStage = new Stage();
           Parent root = FXMLLoader.load(getClass().getResource("/SpecialistRepairs/ModifySPCBooking.fxml"));
           Scene scene;
           scene = new Scene(root);
           scene.getStylesheets().add(getClass().getResource("/Resources/style.css").toExternalForm());
           primaryStage.setScene(scene);
           primaryStage.setResizable(false);
           primaryStage.show();
    }
    
    @FXML
    public void addBooking(){
        int warranty = 0;
        if(warranty_radio.isSelected()){
            warranty = 1;
        }
        selected3 = addBookingVehicle_table.getSelectionModel().getSelectedItem();
        String stmt = "INSERT INTO Bookings (Registration, CustomerID, FirstName, Surname, MechanicID, Type, BookingDate, BookingTime, CurrentMileage, Status, HasWarranty) VALUES " + "(" + "'" + selected3.getregistrationCol() + "', '" + selected2.getCustID() + "', '" + selected2.getfName() + "', '" + selected2.getlName() +"','" + mech_choice.getValue() +"','" + bookingtype + "','" +dateTimeFormatter.format(date_picker.getValue()) + "','" + startTime_choice.getValue() + "','" + mileage_field.getText() + "','" + newStatus + "'," + warranty + ")";
        db.update(stmt);
        fillTable(); 
        
    }
    
    @FXML
    public void editBooking(){
            
                try{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("GMSIS");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to edit Booking:" + selected1.getBookingId()+"?");
                Optional<ButtonType> response = alert.showAndWait();
                if(response.get() == ButtonType.OK)
                {
                    String stmt = "UPDATE Bookings SET MechanicID = '" + editMech_choice.getValue() + "',CurrentMileage = '" + editMileage_field.getText() + "',BookingDate = '" + dateTimeFormatter.format(editDate_picker.getValue()) + "',BookingTime = '" + editStartTime_choice.getValue() + "'  WHERE BookingID = " + selected1.getBookingId();
                    db.update(stmt);
                    alert.setTitle("confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Booking " + selected1.getBookingId() +  " has been updated");
                    alert.showAndWait();
                    fillTable();
                }
                else{
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Booking " + selected1.getBookingId() +  " has not been updated");
                    alert.showAndWait();
                }
            }catch(NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("One of the fields is empty");
                alert.showAndWait();
            }
            
    }
    
    @FXML 
    public void cancel2(){
        addBooking_label.setVisible(false);
        addBookingCustomer_table.setVisible(false);
        addBookingVehicle_table.setVisible(false);
        mileage_field.setVisible(false);
        mech_choice.setVisible(false);
        date_picker.setVisible(false);
        startTime_choice.setVisible(false);
        warranty_radio.setVisible(false);
        confirm_button.setVisible(false);
        cancel_button2.setVisible(false);
        add_button.setDisable(false);
        edit_button.setDisable(false);
        delete_button.setDisable(false);
        editMileage_field.setVisible(false);
        editMech_choice.setVisible(false);
        editStartTime_choice.setVisible(false);
        editDate_picker.setVisible(false);
        editConfirm_button.setVisible(false);
        EditBooking_label.setVisible(false);
    }
    
    private Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);


                        if (item.getDayOfWeek() == DayOfWeek.SUNDAY //
                                || item.compareTo(LocalDate.now()) <= 0) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
    
    private void fetchMechanicData()
    {
        String sql = "SELECT * FROM Mechanics";
        ResultSet rs = db.query(sql);
        try {
            String mechanic = null;
            while (rs.next())
            {
                String m = rs.getString("MechanicID");
                mechanic = m;
                MechanicList.add(mechanic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private void setBookingTimes(DatePicker datepicker) {
        LocalDate date = datepicker.getValue();
        if(date.getDayOfWeek() == DayOfWeek.SATURDAY){
            TimesList.clear();
            TimesList.addAll(Arrays.asList("9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00"));
            startTime_choice.setItems(TimesList);
            editStartTime_choice.setItems(TimesList);
        }
        else{
            TimesList.clear();
            TimesList.addAll(Arrays.asList("9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"));
            startTime_choice.setItems(TimesList);
            editStartTime_choice.setItems(TimesList);
        }
    }
    
    private void SetCustomerAccounts(){
        try {
            String sql = "SELECT * FROM CustomerAccounts Where CustomerID = " + selected1.getCustomerId(); //sql query decleration
            ResultSet rs = db.query(sql);
            CustomerID_label.setText(rs.getString("CustomerID"));
            AccountType_label.setText(rs.getString("CustomerType"));
            FirstName_label.setText(rs.getString("FirstName"));
            Surname_label.setText(rs.getString("LastName"));
            Address_label.setText(rs.getString("Address"));
            Postcode_label.setText(rs.getString("PostCode"));
            PhoneNumber_label.setText(rs.getString("PhoneNumber"));
            Email_label.setText(rs.getString("EmailAddress"));
        } catch (SQLException ex) {
            Logger.getLogger(DiagAndRepGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetBookingDetails() {
        try {
            String sql = "SELECT * FROM Bookings Where BookingID = " + selected1.getBookingId(); //sql query decleration
            ResultSet rs = db.query(sql);
            bookingId_label.setText(rs.getString("BookingID"));
            registration_label.setText(rs.getString("Registration"));
            customerId_label.setText(rs.getString("CustomerID"));
            mechanicId_label.setText(rs.getString("MechanicID"));
            bookingType_label.setText(rs.getString("Type"));
            bookingDate_label.setText(rs.getString("BookingDate"));
            bookingTime_label.setText(rs.getString("BookingTime"));
            warranty_label.setText(rs.getString("HasWarranty"));
            mileage_label.setText(rs.getString("CurrentMileage"));
            duration_label.setText(rs.getString("Duration"));
        } catch (SQLException ex) {
            Logger.getLogger(DiagAndRepGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetBookingHistory() {

    }

    private void SetVehicleRecord() {
        try {
            String sql = "SELECT * FROM VehicleRecords Where CustomerID = " + selected1.getCustomerId(); //sql query decleration
            ResultSet rs = db.query(sql);
            vrCustomerID_label.setText(rs.getString("CustomerID"));
            vrRegistration_label.setText(rs.getString("Registration"));
            vrType_label.setText(rs.getString("Type"));
            vrManufacturer_label.setText(rs.getString("Manufacturer"));
            vrModel_label.setText(rs.getString("Model"));
            vrEngine_label.setText(rs.getString("EngineSize"));
            vrFuel_label.setText(rs.getString("FuelType"));
            vrColour_label.setText(rs.getString("Colour"));
            vrService_label.setText(rs.getString("LastServiceDate"));
            vrMOT_label.setText(rs.getString("MOTDate"));
        } catch (SQLException ex) {
            Logger.getLogger(DiagAndRepGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void searchBar() throws SQLException{    
        
    } 
    
    
}

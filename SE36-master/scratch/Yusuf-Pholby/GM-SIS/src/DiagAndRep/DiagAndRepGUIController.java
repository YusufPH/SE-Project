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
import javafx.scene.control.ListView;
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
    @FXML private TextField duration_field;
    @FXML private TextField editCompMileage_field;
    
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
    @FXML private RadioButton complete_radio;
    
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
    @FXML private Button editOngoing_button;
    
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
    @FXML private Label status_label;
    @FXML private Label cost_label;
    
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
    
    //ListView
    @FXML private ListView pastBooking_listview;
    @FXML private ListView futureBooking_listview;
    ObservableList<String> pastBookingItems =FXCollections.observableArrayList ();
    ObservableList<String> futureBookingItems =FXCollections.observableArrayList ();
    
    private static Repair selected1; // selected field for the bookings table 
    private static Customers selected2; // selected field for the customers table 
    private static Records selected3; // selected field for the vehicle records table

    final private static String bookingtype = "Diagnostic and repair"; // default booking type
    final private static String newStatus = "Not Started"; // default booking status
    private static String setCompleteStatus; // complete status for past bookings

    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy"); // global date format type
    private SQLiteConnection db = SQLiteConnection.getInstance(); // db connection instance
    
    //for searchbar this is called in the filltable method
    private ObservableList<Repair> searchData = FXCollections.observableArrayList(); // observable list of bookings values
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
        
        SearchType.addAll(Arrays.asList("Customer Name", "Registration")); // set the search choice box values
        searchType_choice.setItems(SearchType);
        searchType_choice.getSelectionModel().selectFirst();
        
        booking_table.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue.intValue() >= 0){
                        selected1 = booking_table.getSelectionModel().getSelectedItem();
                        SetCustomerAccounts();
                        SetBookingDetails();
                        SetBookingHistory();
                        SetVehicleRecord();
                    }
                }); // listener for the bookings table to set the customer accoutn, behile records, booking info tabs
        
        addBookingCustomer_table.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue.intValue() >= 0){
                    selected2 = addBookingCustomer_table.getSelectionModel().getSelectedItem();
                    FillVehiclesTable(selected2.getCustID());
                    }
                }); // listener for the selected customers table to view the vehicles they own
        
        date_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            setBookingTimes(date_picker); 
        
        date_picker.getEditor().setDisable(true);
        }); // listener for the add booking date picker
        
        search_field.textProperty().addListener((obv, oldV, newV) -> { search();});
        
        editDate_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            setBookingTimes(editDate_picker);
        
        editDate_picker.getEditor().setDisable(true);
        }); // listener for the edit booking date picker
        
        fetchMechanicData(); // fetch the mechanics data
    }    
    
    public void fillTable() // method to fill the bookings table list
    {
        searchData.clear();
        setValueFactories();
        //ArrayList<Repair> tableValues = new ArrayList<>(); // array list for individual table entries
        String sql = "SELECT * FROM Bookings"; //sql query decleration
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                searchData.add(new Repair(rs)); // add the values received from the database to the fxml table
            }
            booking_table.setItems(FXCollections.observableArrayList(searchData)); // adding the arraylist holding the info as an observalble list to the fxml table
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public void fillCustomerTable() // fill the table of customers for adding a new booking
    {
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
    
    public void FillVehiclesTable(int ID) // fill the table of vehicles belonging to teh selected customer in the add method
    {
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
    public void allowAdd() // setting up the software allowing for add funciton to work
    {
        repair_button.setVisible(true);
        spc_button.setVisible(true);
        cancel_button1.setVisible(true);
        edit_button.setDisable(true);
        delete_button.setDisable(true);
    }
    
    @FXML
    public void allowEdit() // setting up the sytem allowing for the user to edit 
    {
        if(selected1 !=null){
            if(LocalDate.parse(selected1.getdate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).isAfter(LocalDate.now())) //checking whether the chosen booking is a past or future booking to choose which edit UI to show          
            {
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
                EditBooking_label.setVisible(true);
                cancel_button2.setVisible(true);
                add_button.setDisable(true);
                delete_button.setDisable(true);
                
                editCompMileage_field.setVisible(true);
                duration_field.setVisible(true);
                complete_radio.setVisible(true);
                editOngoing_button.setVisible(true);
            }
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
    public void cancel1() // first stage cancel choosing add, edit or delete
    {
        repair_button.setVisible(false);
        spc_button.setVisible(false);
        add_button.setDisable(false);
        edit_button.setDisable(false);
        delete_button.setDisable(false);
        cancel_button1.setVisible(false);
    }
    
    @FXML
    public void delete() // delete function 
    {
        if(selected1 != null)
        {
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
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Field not selected");
            alert.setHeaderText(null);
            alert.setContentText("Select a field then click delete");
            alert.showAndWait();  
        }
    }
    
    @FXML
    public void chooseRepair() // setting up add if you choose a regular diag and rep booking 
    {
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
    public void chooseSPC() throws IOException // set up UI for new SPC booking from within Diag&rep module
    {
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
    public void addBooking() // add booking method
    {
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
    public void editOngoingBooking() // that have not already started yet 
    {
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
            cancel2();
    }
    
    @FXML
    public void editCompleteBooking() // edit bookings that have started or completed already
    {
        try{
                String stm = "SELECT * FROM Mechanics Where MechanicID = " + selected1.getmechID() + ";";
                ResultSet rs = db.query(stm);
                double mechanicCost = 0.00;
            try {
                mechanicCost = rs.getDouble("HourlyRate");
            } catch (SQLException ex) {
                Logger.getLogger(DiagAndRepGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("GMSIS");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to edit Booking:" + selected1.getBookingId()+"?");
                Optional<ButtonType> response = alert.showAndWait();
                if(response.get() == ButtonType.OK)
                {
                    if(complete_radio.isSelected()) setCompleteStatus = "Complete";
                        else setCompleteStatus = "Ongoing";
                    double duration = Double.parseDouble(duration_field.getText());
                    String stmt = "UPDATE Bookings SET Duration = '" + duration_field.getText() + "', Status = '" + setCompleteStatus + "', CurrentMileage = '" + editCompMileage_field.getText() + "', Cost = '" + (duration*mechanicCost) + "' WHERE BookingID = " + selected1.getBookingId();
                    db.update(stmt);
                    alert.setTitle("confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Booking " + selected1.getBookingId() +  " has been updated");
                    alert.showAndWait();
                    fillTable();
                }
                else{
                    alert2.setTitle("Confirmation");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Booking " + selected1.getBookingId() +  " has not been updated");
                    alert2.showAndWait();
                }
            }catch(NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("One of the fields is empty");
                alert.showAndWait();
            }
            cancel2();
    }
    
    @FXML 
    public void cancel2() // second cancel button which is used once add or edit has been started
    {
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
        duration_field.setVisible(false);
        complete_radio.setVisible(false);
        editOngoing_button.setVisible(false);
        editCompMileage_field.setVisible(false);
    }
    
    private Callback<DatePicker, DateCell> getDayCellFactory() // method sorting out the date picker so that past or holiday days can not be chosen from the db
    {
        
        String sql = "SELECT * FROM Holidays";
        ResultSet rs = db.query(sql);
        ArrayList<LocalDate> dates = new ArrayList<>();
        try
        {
            while (rs.next()) {
                dates.add(LocalDate.parse(rs.getString("Date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);             

                        if (item.getDayOfWeek() == DayOfWeek.SUNDAY //
                                || item.compareTo(LocalDate.now()) <= 0 || dates.contains(LocalDate.parse(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
    
    private void fetchMechanicData() // fetch data about mechanics for the add function 
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

    private void setBookingTimes(DatePicker datepicker) // set the choice box for the times of a new booking
    {
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
    
    private void SetCustomerAccounts() // set the customer accounts tab for a chosen booking
    {
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

    private void SetBookingDetails() // set the booking details for chosen booking
    {
        try {
            String sql = "SELECT * FROM Bookings Where BookingID = " + selected1.getBookingId(); //sql query decleration
            ResultSet rs = db.query(sql);
            rs.next();
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
            cost_label.setText(rs.getString("Cost"));
            status_label.setText(rs.getString("Status"));
        } catch (SQLException ex) {
            Logger.getLogger(DiagAndRepGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetBookingHistory() //set the booking history for a chosen booking
    {
        pastBooking_listview.getItems().clear();
        futureBooking_listview.getItems().clear();
        String sql = "SELECT * FROM Bookings Where CustomerID = " + selected1.getCustomerId(); //sql query decleration
        ResultSet rs = db.query(sql);
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                if(LocalDate.parse(rs.getString("BookingDate"), DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now()))
                {
                    pastBookingItems.add(rs.getString("BookingDate"));
                }
                else{
                    futureBookingItems.add(rs.getString("BookingDate"));
                }
            }
            pastBooking_listview.setItems(pastBookingItems);
            futureBooking_listview.setItems(futureBookingItems);
        } catch (SQLException e) {
            e.printStackTrace();    
        }
    }

    private void SetVehicleRecord() //setting the vehicle record for a chosen booking
    {
        try {
            String sql = "SELECT * FROM VehicleRecords WHERE CustomerID = '" + selected1.getCustomerId()+ "'"; //sql query decleration
            ResultSet rs = db.query(sql);
            if(!rs.next()) return;
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
            ex.printStackTrace();
            Logger.getLogger(DiagAndRepGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void search() //search function 
    {
        
        booking_table.setPlaceholder(new Label("No results found for " + search_field.getText()));
        booking_table.getItems().clear();
        setValueFactories();
        ArrayList<Repair> searchedItems = new ArrayList<>();
        if(searchType_choice.getValue().equals("Customer Name")){
            for (Repair repair : searchData) {
                if (((repair.getFname().toLowerCase()).contains(search_field.getText().toLowerCase()))) {
                    searchedItems.add(repair);
                }
            }
        }
        else if(searchType_choice.getValue().equals("Registration"))
        {
            for (Repair repair : searchData) {
                if (((repair.getReg()).contains(search_field.getText()))) {
                    searchedItems.add(repair);
                }
            }
        }
        setValueFactories();
        booking_table.setItems(FXCollections.observableArrayList(searchedItems));

    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialistRepairs;

import common.SQLiteConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author gleb_mirolyubov
 */
public class FillSPCBookingsTable {
    
    private final SQLiteConnection db = SQLiteConnection.getInstance();

    @FXML
    private TableView<SPCBooking> SPCBookingsTable;// = new TableView<Person>();

    @FXML private TableColumn<SPCBooking, Integer> BookingIDCol;
    @FXML private TableColumn<SPCBooking, String>  NameCol ;
    @FXML private TableColumn<SPCBooking, String>  SPCCol ;
    @FXML private TableColumn<SPCBooking, String>  TypeCol ;
    @FXML private TableColumn<SPCBooking, String> DetailsCol ;
    @FXML private TableColumn<SPCBooking, Integer>  CustIDCol ;
    @FXML private TableColumn<SPCBooking, String>  CustNameCol ;
    @FXML private TableColumn<SPCBooking, String>  dDateCol ;
    @FXML private TableColumn<SPCBooking, String>  rDateCol ;
    @FXML private TableColumn<SPCBooking, String>  RegNoCol ;
    @FXML private TableColumn<SPCBooking, Integer>  CostCol ;
    
    /* 
    *  A constructor to initialize all variables by assigning values passed from the other class
    */ 
    public FillSPCBookingsTable(TableView<SPCBooking> spcBookingsTable, TableColumn<SPCBooking, Integer> bookingIDCol,TableColumn<SPCBooking, String> spcCol,TableColumn<SPCBooking, String> typeCol,TableColumn<SPCBooking, String> regNoCol, TableColumn<SPCBooking,Integer>costCol,TableColumn<SPCBooking, String> nameCol,TableColumn<SPCBooking, String> detailsCol,TableColumn<SPCBooking, Integer> customerIDCol,TableColumn<SPCBooking, String> customerNameCol,TableColumn<SPCBooking, String> ddateCol,TableColumn<SPCBooking, String> rdateCol)
    {
        SPCBookingsTable = spcBookingsTable;
        BookingIDCol = bookingIDCol;
        SPCCol = spcCol;
        TypeCol = typeCol;
        NameCol = nameCol;
        DetailsCol = detailsCol;
        CustIDCol = customerIDCol;
        CustNameCol = customerNameCol;
        dDateCol = ddateCol;
        rDateCol = rdateCol;
        RegNoCol = regNoCol;
        CostCol = costCol;
    }
    
    /* 
    *  This method uses a predefined string to access the database and fetch selected data from it.
    *  Then it adds this data to the table which is connected to the TableView in the fxml.
    */ 
    public void fillTable() throws SQLException
    {
        setValueFactories();
        ArrayList<SPCBooking> tableValues = new ArrayList<>();
        String sql = "SELECT BookingID,Name, SPC, Type, RegistrationNumber, Details, Cost, CustomerID, CustomerName, DeliveryDate, ReturnDate FROM SPCBookings";
        ResultSet rs = db.query(sql);
        try {
            while (rs.next())
            {
                tableValues.add(new SPCBooking(rs));
                SpecialistRepairsGUIController.searchData.add(new SPCBooking (rs));
            }
            SPCBookingsTable.setItems(FXCollections.observableArrayList(tableValues));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Sets value factories for local variables
    */
    private void setValueFactories() {
        BookingIDCol.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        SPCCol.setCellValueFactory(new PropertyValueFactory<>("SPC"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        RegNoCol.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber")); 
        DetailsCol.setCellValueFactory(new PropertyValueFactory<>("Details"));
        CostCol.setCellValueFactory(new PropertyValueFactory<>("Cost")); 
        CustIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        CustNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        dDateCol.setCellValueFactory(new PropertyValueFactory<>("DeliveryDate"));
        rDateCol.setCellValueFactory(new PropertyValueFactory<>("ReturnDate")); 
        
    }
    
}

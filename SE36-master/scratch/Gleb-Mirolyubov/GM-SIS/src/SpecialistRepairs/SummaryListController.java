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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author gleb_mirolyubov
 */
public class SummaryListController implements Initializable {
    
    @FXML
    private Label dateText;
    
    @FXML
    private TableView<SPCSummaryList> table;// = new TableView<Person>();

    @FXML private TableColumn<SPCSummaryList, Integer> BookingIDCol;
    @FXML private TableColumn<SPCSummaryList, String>  NameCol ;
    @FXML private TableColumn<SPCSummaryList, String>  TypeCol ;
    @FXML private TableColumn<SPCSummaryList, Integer> ReturnCol ;
    
    private final SQLiteConnection db = SQLiteConnection.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String date = getBackupFolderName();
        dateText.setText("Today's date: "+date);
        fillTable();
    } 
    
    /* 
    *  This method sets value factories from the database
    */ 
    private void setValueFactories()
    {
        BookingIDCol.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ReturnCol.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
    }
    
    /* 
    *  This method uses a predefined string to access the database and fetch selected data from it.
    *  Then it adds this data to the table which is connected to the TableView in the fxml.
    */  
    private void fillTable()
    {
        setValueFactories();
        ArrayList<SPCSummaryList> tableValues = new ArrayList<>();
        java.util.Date currentDate = Calendar.getInstance().getTime();
        String sql = "SELECT BookingID, Name, Type, ReturnDate FROM SPCBookings WHERE ReturnDate > '"+getBackupFolderName()+"'";
        ResultSet rs = db.query(sql);
        try {
            while (rs.next())
            {
                tableValues.add(new SPCSummaryList(rs));
            }
            table.setItems(FXCollections.observableArrayList(tableValues));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    /* 
    *  Closes window
    */ 
    public void CloseWindow(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    /* 
    *  Gets today's date from the system and formats it
    */ 
    public String getBackupFolderName() 
    {
        java.util.Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    } 
}

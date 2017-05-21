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
public class FillSPCsTable {
    
    @FXML
    private TableView<SPC> table;
    
    private final SQLiteConnection db = SQLiteConnection.getInstance();

    @FXML private TableColumn<SPC, Integer> IDCol;
    @FXML private TableColumn<SPC, String>  nameCol ;
    @FXML private TableColumn<SPC, String>  addressCol ;
    @FXML private TableColumn<SPC, Integer> phoneCol ;
    @FXML private TableColumn<SPC, String>  emailCol ;
    
    /* 
    *  A constructor to initialize all variables by assigning values passed from the other class
    */ 
    public FillSPCsTable(TableView<SPC> Table, TableColumn<SPC, Integer> idCol, TableColumn<SPC, String> NameCol, TableColumn<SPC, String> AddressCol, TableColumn<SPC, Integer> PhoneCol, TableColumn<SPC, String> EmailCol) 
    {
        table = Table;
        IDCol = idCol;
        nameCol = NameCol;
        addressCol = AddressCol;
        phoneCol = PhoneCol;
        emailCol = EmailCol;
    }
    
    /* 
    *  This method uses a predefined string to access the database and fetch selected data from it.
    *  Then it adds this data to the table which is connected to the TableView in the fxml.
    */ 
    public void fillTable(){
        setValueFactories();
        ArrayList<SPC> tableValues = new ArrayList<>();
        String sql = "SELECT ID, Name, Address, Phone, Email FROM SpecialistRepairs";
        ResultSet rs = db.query(sql);
        try {
            while (rs.next())
            {
                tableValues.add(new SPC(rs));
            }
            table.setItems(FXCollections.observableArrayList(tableValues));
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
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));   
    }
    
}

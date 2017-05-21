/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecords;


import common.SQLiteConnection;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;


/**
 * FXML Controller class
 *
 * @author samaggarwal
 */
public class WarrentyController implements Initializable 
{
    //Recquried for the sql connection    
    SQLiteConnection db = SQLiteConnection.getInstance();
        
        
        
        

        
        
        @FXML private TextField companyName; 
        @FXML private TextField companyAddress; 
        @FXML private TextField expiryDate;
        @FXML private TextField companyID;
    
        private String registration;
        
        
        
        public WarrentyController(String registration) 
        {
        this.registration = registration;
        }
    
        private ArrayList<Warrenty> warrenty = new ArrayList<>();
        @FXML private TableView<Warrenty> warrentyTable;
        @FXML private TableColumn<Warrenty, String> registrationCol;
        
        @FXML private TableColumn<Warrenty, String> companyNameCol;
        @FXML private TableColumn<Warrenty, String> companyAddressCol;
        @FXML private TableColumn<Warrenty, String> expiryDateCol;
        @FXML private TableColumn<Warrenty, String> companyIDCol;
        
        
        @FXML
        private Button addWarrenty;
        

        @FXML
                //edit warrenty method for when the button is clicked 
        void editWarrenty(ActionEvent event) 
    {
        
        Warrenty selected;
        selected = warrentyTable.getSelectionModel().getSelectedItem();
        if(selected!=null){
        

       
        
        companyID.setText(selected.getcompanyIDCol());
        companyAddress.setText(selected.getcompanyAddressCol());
        companyName.setText(selected.getcompanyNameCol());
        expiryDate.setText(selected.getexpiryDateCol());
        
        
 
        
            }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Field not selected");
                    alert.setHeaderText(null);
                    alert.setContentText("Select a Customer then click edit");
                    alert.showAndWait();
                
            }
        
    }
        
        @FXML
            //delete warrenty method for deleting warrenty details from table view
    void deleteWarrenty(ActionEvent event) 
    {
        Warrenty selected;
            selected = warrentyTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this Warrenty entry from the system?", ButtonType.YES, ButtonType.NO);

            alert.setTitle("GMSIS - Warrenty");
            alert.showAndWait();
            if(alert.getResult()== ButtonType.YES)
            {
                System.out.println("DELETE FROM Warrenty WHERE CompanyID = " + selected.getcompanyIDCol());
                db.update("DELETE FROM Warrenty WHERE CompanyID = '" + selected.getcompanyIDCol()+"'");
                fillwarrentyTable();

            }
        
    }


   
    @Override
    
    public void initialize(URL url, ResourceBundle rb) 
    {
       fillwarrentyTable();
    }
        //fill table method to fill the warrenty table
        public void fillwarrentyTable()
        {
            
            setValueFactories();   
            ArrayList<Warrenty> tableValues = new ArrayList<>();
            String sql = "SELECT * FROM Warrenty WHERE Registration = '" + registration + "'";
            
            ResultSet rs = db.query(sql);

            try {
                while (rs.next())
                {
                    
                    tableValues.add(new Warrenty(rs));
                    
                }
            
                warrentyTable.setItems(FXCollections.observableArrayList(tableValues));
                } catch (SQLException e) {
                e.printStackTrace();
                
                
            }
        }
        
    

        // setting the columns in the tables
        private void setValueFactories() 
        {
            registrationCol.setCellValueFactory(new PropertyValueFactory<>("registrationCol"));
            
            companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyNameCol"));
            companyAddressCol.setCellValueFactory(new PropertyValueFactory<>("companyAddressCol"));
            
            expiryDateCol.setCellValueFactory(new PropertyValueFactory<>("expiryDateCol"));
            companyIDCol.setCellValueFactory(new PropertyValueFactory<>("companyIDCol"));
           
        }

        
        
        
        
        //add warrenty into the table view
        public void add_Warrenty(ActionEvent event) 
        {
                
            try 
            {
                if(!companyID.getText().trim().equals("")&&!companyName.getText().trim().equals("")&&!companyAddress.getText().trim().equals("")&&!expiryDate.getText().equals(""))
                {

                        
                        String stmt = "INSERT INTO Warrenty (CompanyID, CompanyName, CompanyAddress, ExpiryDate, Registration) VALUES ('" + companyID.getText() + "','" + companyName.getText() + "','" + companyAddress.getText() + "', '" + expiryDate.getText() + "','" + registration + "')";
                        db.update(stmt);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Warrenty Added");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(companyAddress.getText()+" was added successfully");
                        successAlert.showAndWait();
                        
                        fillwarrentyTable();
                        

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("One of the fields is empty");
                    alert.showAndWait();
                   
                }

            }
            catch (Exception e)
            {

            }
        }
        //edit warrenty method 
            @FXML
        public void edit_Warrenty()
        {
        Warrenty selected;
        selected = warrentyTable.getSelectionModel().getSelectedItem();
        try 
        {
            if(!warrentyTable.getSelectionModel().isEmpty())
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait(); 
            if (alert.getResult() == ButtonType.YES) {
            SQLiteConnection db = SQLiteConnection.getInstance();
            String editdata = "UPDATE Warrenty SET ExpiryDate='"+expiryDate.getText()+"', CompanyName='"+ companyName.getText() +"', CompanyAddress='"+ companyAddress.getText()+"' WHERE companyID = '"+selected.getcompanyIDCol()+"'";
            db.update(editdata);
            
            fillwarrentyTable();
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

        
       
        
        

        
}


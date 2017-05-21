/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;

import common.SQLiteConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Aaron
 */
public class PartsGUIController implements Initializable {
    
    @FXML
    private TableView<PartsM> table;// = new TableView<Person>();    
    @FXML private TableColumn<PartsM, Integer> ID;
    @FXML private TableColumn<PartsM, String> Name;
    @FXML private TableColumn<PartsM, String> Desc;
    @FXML private TableColumn<PartsM, Integer> Quantity;
    @FXML private TableColumn<PartsM, Double> Cost;
    
    @FXML private Button withdrawButton;
    @FXML private Button depositButton;
    @FXML private TextField withdrawId;
    @FXML private TextField depositId;   
    
    @FXML 
    private ComboBox<String> vehicleList;
    private ObservableList<String> myComboBoxData = FXCollections.observableArrayList();
       
        
    private final SQLiteConnection db = SQLiteConnection.getInstance();    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myComboBoxData.add("car");
        myComboBoxData.add("truck");
        myComboBoxData.add("van");
        vehicleList.setItems(myComboBoxData);
        filtration(vehicleList);
        fillTable();        
    }   

    public void fillTable() //method to fill table in fxml
    {
        setValueFactories();
        ArrayList<PartsM> tableValues = new ArrayList<>(); // array list for individual table entries               
        String sql = "SELECT ID, Name, Description, StockQuantity, Cost FROM Parts"; //sql query decleration   
               
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                tableValues.add(new PartsM(rs)); // add the values received from the database to the fxml table                
            }
             // adding the arraylist holding the info as an observalble list to the fxml table
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(FXCollections.observableArrayList(tableValues));                      
//       finally {
//            try { rs.close(); } catch (Exception e) { /* ignored */ }
//        }
    }
    
    public void filtration(ComboBox cb) 
    {
        cb.setOnAction((event) -> {
        String stmt = (String) cb.getSelectionModel().getSelectedItem();
        System.out.println(stmt);
        if (stmt.equals("car"))
        {
            System.out.println("duck");
            SQLiteConnection db = SQLiteConnection.getInstance();
            //String sttt = "SELECT ID, Name, Description, StockQuantity, Cost FROM Parts WHERE ID = 4";
            //db.query(sttt);
            //fillTable();
        } 
       
		});
    } 

    private void setValueFactories() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("cst")); 
    }
    
    public void withdrawButton(ActionEvent event) 
    {
        PartsM partSelection = table.getSelectionModel().getSelectedItem();
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
}

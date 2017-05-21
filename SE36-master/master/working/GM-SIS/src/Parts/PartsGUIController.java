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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML private TableColumn<PartsM, String> Desc ;
    @FXML private TableColumn<PartsM, Integer> Quantity ;
    @FXML private TableColumn<PartsM, Double> Cost ;
    
    private final SQLiteConnection db = SQLiteConnection.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //table.getItems().setAll(this.data);
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

    private void setValueFactories() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("cst"));      
    }
}

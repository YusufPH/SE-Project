/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;

import SpecialistRepairs.SQLiteConnection;
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

ObservableList<String> spcList = FXCollections.observableArrayList("SPC1","SPC2","SPC3");
    
    @FXML
    private TableView<Parts> table;// = new TableView<Person>();

    @FXML private TableColumn<Parts, Integer> ID;
    @FXML private TableColumn<Parts, String> Name;
    @FXML private TableColumn<Parts, String>  Desc ;
    @FXML private TableColumn<Parts, Integer>  Quantity ;
    @FXML private TableColumn<Parts, Double> Cost ;
    
    private final SQLiteConnection db = SQLiteConnection.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //table.getItems().setAll(this.data);
            fillTable();
        } catch (SQLException ex) {
            Logger.getLogger(PartsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    private void fillTable() throws SQLException
    {
        setValueFactories();
        ArrayList<Parts> valueTable = new ArrayList<>();
        String sql = "SELECT * FROM Parts";
        JOptionPane.showMessageDialog(null, sql);
        
        ResultSet rs = db.query(sql);
        try {
            while (rs.next())
            {
                valueTable.add(new Parts(rs));
            }
            table.setItems(FXCollections.observableArrayList(valueTable));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private void setValueFactories() {
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("StockQuantity"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));      
    }
}

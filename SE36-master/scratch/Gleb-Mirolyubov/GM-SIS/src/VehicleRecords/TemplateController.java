/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecords;

import common.SQLiteConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author samaggarwal
 */
public class TemplateController implements Initializable 
{
        
     SQLiteConnection db = SQLiteConnection.getInstance();
     
    private ArrayList<Template> template = new ArrayList<>();

    @FXML private TableView<Template> templateTable;

    @FXML private TableColumn<Template, String> manufacturerCol;
    @FXML private TableColumn<Template, String> modelCol;
    @FXML private TableColumn<Template, String> engineSizeCol;
    @FXML private TableColumn<Template, String> fuelTypeCol;
    
    private void setValueFactories()
        {
            
            manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerCol"));
            modelCol.setCellValueFactory(new PropertyValueFactory<>("modelCol"));
            engineSizeCol.setCellValueFactory(new PropertyValueFactory<>("engineSizeCol"));
            fuelTypeCol.setCellValueFactory(new PropertyValueFactory<>("fuelTypeCol"));
            
        }   
    
    
    public void fillTable()
        {
            
            setValueFactories();   
            ArrayList<Template> tableValues = new ArrayList<>();
            String sql = "SELECT * FROM Template";
            ResultSet rs = db.query(sql);
            try 
            {
                while (rs.next())
                {
                   tableValues.add(new Template(rs));
                   
                    
                }
                templateTable.setItems(FXCollections.observableArrayList(tableValues));
            } catch (SQLException e) {
                e.printStackTrace();
                
                
            }
        }
    
    
    //method to select the vehicle 
//    public void selectTemplate (ActionEvent event) throws IOException
//        {
//             
//            String manu = templateTable.getSelectionModel().getSelectedItem().getmanufacturerCol();
//            String model = templateTable.getSelectionModel().getSelectedItem().getmodelCol();
//            String fuelType = templateTable.getSelectionModel().getSelectedItem().getengineSizeCol();
//            String engSize = templateTable.getSelectionModel().getSelectedItem().getfuelTypeCol();
//            
//            VehicleRecordsGUIController controller = new VehicleRecordsGUIController(manu,model,fuelType,engSize);
//   
//        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    }    
    
}

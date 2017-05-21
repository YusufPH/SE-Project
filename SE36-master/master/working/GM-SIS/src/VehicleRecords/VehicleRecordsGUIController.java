package VehicleRecords;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VehicleRecordsGUIController 

{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_search;
    @FXML
    private Button btn_menu;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_delete;
    

    @FXML
    void search(ActionEvent event) {
           System.out.println("Search results");
    }
     @FXML
    void menu(ActionEvent event) {
           System.out.println("show drop down menu");
    }
     @FXML
    void add(ActionEvent event) {
           System.out.println("add vehicles");
    }
     @FXML
    void edit(ActionEvent event) {
           System.out.println("edit vehicles");
    }
     @FXML
    void delete(ActionEvent event) {
           System.out.println("delete  vehicles");
    }

    @FXML
    void initializeSearch() 
    {
        assert btn_search != null : "fx:id=\"btn_search\" was not injected: check your FXML file 'VehicleRecordsGUI.fxml'.";
    }
    @FXML
    void initializeMenu() 
    {
        assert btn_menu != null : "fx:id=\"btn_search\" was not injected: check your FXML file 'VehicleRecordsGUI.fxml'.";
    }
    @FXML
    void initializeAdd() 
    {
        assert btn_add != null : "fx:id=\"btn_search\" was not injected: check your FXML file 'VehicleRecordsGUI.fxml'.";
    }
    @FXML
    void initializeEdit() 
    {
        assert btn_edit != null : "fx:id=\"btn_search\" was not injected: check your FXML file 'VehicleRecordsGUI.fxml'.";
    }
    @FXML
    void initializeDelete() 
    {
        assert btn_delete != null : "fx:id=\"btn_search\" was not injected: check your FXML file 'VehicleRecordsGUI.fxml'.";
    }

            public void addButton(ActionEvent event) throws IOException
            {
           
           Stage primaryStage = new Stage();
           Parent root = FXMLLoader.load(getClass().getResource("add_Popup.fxml"));
           Scene scene;
           scene = new Scene(root);
           scene.getStylesheets().add(getClass().getResource("/Resources/style.css").toExternalForm());
           primaryStage.setScene(scene);
           primaryStage.setResizable(false);
           primaryStage.show();
            }
}

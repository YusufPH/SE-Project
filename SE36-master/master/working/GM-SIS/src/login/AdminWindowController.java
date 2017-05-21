/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import common.SQLiteConnection;  
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Yusuf
 */
public class AdminWindowController implements Initializable {

    private final ArrayList<Users> users = new ArrayList<>(); // ignore for now
    
    @FXML private TableView<Users> userTable; // table in fxml
    @FXML private TableColumn<Users, Integer> id; // ID column of table
    @FXML private TableColumn<Users, String> fName; // first name colum for table
    @FXML private TableColumn<Users, String> sName; // surname column for table
    @FXML private TableColumn<Users, Integer> aType; // account type for table

    //ignore for now
    @FXML private TextField fname_field; 
    @FXML private TextField sname_field;
    @FXML private TextField id_field;
    @FXML private TextField pword_field;
    @FXML private TextField confpword_field;
    private int Type = 0;
    
    private static Users selected;
    
    private SQLiteConnection db = SQLiteConnection.getInstance(); // get a database connection instance

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable(); // method to fill table
    }    
    
    public void fillTable() //method to fill table in fxml
    {
        setValueFactories();
        ArrayList<Users> tableValues = new ArrayList<>(); // array list for individual table entries
        String sql = "SELECT FirstName, Surname, ID, Type FROM Authentication"; //sql query decleration
        ResultSet rs = db.query(sql); // result set - what is received from the database
        try {
            while (rs.next()) // while there is a next entry in the table
            {
                tableValues.add(new Users(rs)); // add the values received from the database to the fxml table
            }
            userTable.setItems(FXCollections.observableArrayList(tableValues)); // adding the arraylist holding the info as an observalble list to the fxml table
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    private void setValueFactories() //set the property of the cell of each column
    {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        sName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        aType.setCellValueFactory(new PropertyValueFactory<>("Type"));

    }
    
    @FXML
    public void AddUser(ActionEvent event) // ignore for now
    {
        try {
            if(!fname_field.getText().trim().equals("")&&!sname_field.getText().trim().equals("")&&!id_field.getText().trim().equals("")&&!pword_field.getText().trim().equals("")&&!confpword_field.getText().trim().equals(""))
            {
                if((pword_field.getText()).equals(confpword_field.getText()))
                {
                    // SQLiteConnection db = SQLiteConnection.getInstance();
                    String stmt = "INSERT INTO Authentication (FirstName, Surname, ID, Password, Type) VALUES ('" + fname_field.getText() + "', '" + sname_field.getText() + "', '" + id_field.getText() + "', '" + pword_field.getText() +"'," + Type +")";
                    db.update(stmt);
                    fillTable(); 
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("One of the fields is empty");
                alert.showAndWait();
            }
            
        }catch (Exception e)
        {
            
        }
         
    }
    
    @FXML
    public void delete()
    {
        Users selected;
        selected = userTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("GMSIS");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + selected.getfName() + " " + selected.getsName() + "?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK)
        {
            db.update("DELETE FROM Authentication WHERE ID = " + selected.getId());
            fillTable();
        }
    }
}

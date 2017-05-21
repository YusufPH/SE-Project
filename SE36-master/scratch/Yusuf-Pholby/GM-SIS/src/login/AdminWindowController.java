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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static login.MainController.adminPanelOpened;

/**
 * FXML Controller class
 *
 * @author Yusuf
 */
public class AdminWindowController implements Initializable {
    
    @FXML private Button add_btn;
    @FXML private Button edit_btn;
    @FXML private Button delete_btn;
    @FXML private Button addConfirm_btn;
    @FXML private Button editConfirm_btn;
    @FXML private Button cancel_btn;
    @FXML private Button close_btn;
    
    @FXML private TextField fname_field; 
    @FXML private TextField sname_field;
    @FXML private TextField id_field;
    @FXML private TextField pword_field;
    @FXML private TextField confpword_field;
    
    @FXML private Label fname_label; 
    @FXML private Label sname_label;
    @FXML private Label id_label;
    @FXML private Label pword_label;
    @FXML private Label confpword_label;
    
    @FXML private TableView<Users> userTable; // table in fxml
    @FXML private TableColumn<Users, Integer> id; // ID column of table
    @FXML private TableColumn<Users, String> fName; // first name colum for table
    @FXML private TableColumn<Users, String> sName; // surname column for table
    @FXML private TableColumn<Users, Integer> aType; // account type for table

    private int Type = 0;
    
    private static Users selected;
    
    private SQLiteConnection db = SQLiteConnection.getInstance(); // get a database connection instance

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fname_field.setVisible(false); 
        sname_field.setVisible(false);
        id_field.setVisible(false);
        pword_field.setVisible(false);
        confpword_field.setVisible(false);
        
        fname_label.setVisible(false); 
        sname_label.setVisible(false);
        id_label.setVisible(false);
        pword_label.setVisible(false);
        confpword_label.setVisible(false);
        
        addConfirm_btn.setVisible(false);
        editConfirm_btn.setVisible(false);
        cancel_btn.setVisible(false);
        fname_field.setVisible(false);
        sname_field.setVisible(false);
        id_field.setVisible(false);
        pword_field.setVisible(false);
        confpword_field.setVisible(false);
        
        fillTable(); // method to fill table
    }    
    
    public void fillTable() //method to fill table in fxml
    {
        setValueFactories();
        ArrayList<Users> tableValues = new ArrayList<>(); // array list for individual table entries
        String sql = "SELECT FirstName, Surname, ID, Password, Type FROM Authentication"; //sql query decleration
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
        //pword.setCellValueFactory(new PropertyValueFactory<>("pword"));

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
    public void editUser()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        selected = userTable.getSelectionModel().getSelectedItem();
        if(pword_field.getText().equals(confpword_field.getText()))
        {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("GMSIS");
            alert2.setHeaderText(null);
            alert2.setContentText("Are you sure you want to edit " + fname_field.getText() + " " + sname_field.getText() + "?");
            Optional<ButtonType> response = alert.showAndWait();
            if(response.get() == ButtonType.OK)
            {
                String stmt = "UPDATE Authentication SET Password = '" + pword_field.getText() + "' WHERE ID = " + id_field.getText();
                db.update(stmt);
                alert2.setTitle("confirmation");
                alert2.setHeaderText(null);
                alert2.setContentText(fname_field.getText() + " " + sname_field.getText() + "'s account has been updated");
                alert2.showAndWait();
                cancel();
            }
            else{
                pword_field.setText(selected.getPword());
                confpword_field.setText(selected.getPword());
            }
        }
        else{
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("password field does not match confirmation");
                alert.showAndWait();
        }
    }
    
    @FXML
    public void delete()
    {
        selected = userTable.getSelectionModel().getSelectedItem();
        if(selected.getType() == 0)
        {  
            
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
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("GMSIS");
            alert.setHeaderText(null);
            alert.setContentText("WARNING!!! Can not delete admin");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void allowAdd()
    {
        fname_field.setVisible(true); 
        sname_field.setVisible(true);
        id_field.setVisible(true);
        pword_field.setVisible(true);
        confpword_field.setVisible(true);
        
        fname_label.setVisible(true); 
        sname_label.setVisible(true);
        id_label.setVisible(true);
        pword_label.setVisible(true);
        confpword_label.setVisible(true);
        
        addConfirm_btn.setVisible(true);
        cancel_btn.setVisible(true);
        fname_field.setVisible(true);
        sname_field.setVisible(true);
        id_field.setVisible(true);
        pword_field.setVisible(true);
        confpword_field.setVisible(true);
        
        close_btn.setVisible(false);
    }
    
    @FXML
    public void allowEdit()
    {
        //Users selected;
        selected = userTable.getSelectionModel().getSelectedItem();
        
        if(selected != null)
        {
            fname_field.setVisible(true); 
            sname_field.setVisible(true);
            id_field.setVisible(true);
            pword_field.setVisible(true);
            confpword_field.setVisible(true);

            fname_label.setVisible(true); 
            sname_label.setVisible(true);
            id_label.setVisible(true);
            pword_label.setVisible(true);
            confpword_label.setVisible(true);

            editConfirm_btn.setVisible(true);
            cancel_btn.setVisible(true);
            fname_field.setVisible(true);
            sname_field.setVisible(true);
            id_field.setVisible(true);
            pword_field.setVisible(true);
            confpword_field.setVisible(true);
            
            close_btn.setVisible(false);
            
            fname_field.setText(selected.getfName()); 
            sname_field.setText(selected.getsName());
            id_field.setText(Integer.toString(selected.getId()));
            pword_field.setText(selected.getPword());
            confpword_field.setText(selected.getPword());
            
            fname_field.setDisable(true);
            sname_field.setDisable(true);
            id_field.setDisable(true);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Field not selected");
            alert.setHeaderText(null);
            alert.setContentText("Select a field then click edit");
            alert.showAndWait();
                
        }
        
    }
    
    @FXML
    public void cancel()
    {
        fname_field.setVisible(false); 
        sname_field.setVisible(false);
        id_field.setVisible(false);
        pword_field.setVisible(false);
        confpword_field.setVisible(false);
        
        fname_field.clear(); 
        sname_field.clear();
        id_field.clear();
        pword_field.clear();
        confpword_field.clear();
        
        fname_label.setVisible(false); 
        sname_label.setVisible(false);
        id_label.setVisible(false);
        pword_label.setVisible(false);
        confpword_label.setVisible(false);
        
        addConfirm_btn.setVisible(false);
        editConfirm_btn.setVisible(false);
        cancel_btn.setVisible(false);
        fname_field.setVisible(false);
        sname_field.setVisible(false);
        id_field.setVisible(false);
        pword_field.setVisible(false);
        confpword_field.setVisible(false);
        
        close_btn.setVisible(true);
        
        fname_field.setDisable(false);
        sname_field.setDisable(false);
        id_field.setDisable(false);
    }
    
    @FXML 
    public void close(ActionEvent event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
        adminPanelOpened = false;
    }
   
}

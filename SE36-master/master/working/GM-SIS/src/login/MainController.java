/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Yusuf
 */
public class MainController implements Initializable {

    @FXML
    private Button closeAdminPanel;
    
    @FXML
    private Button adminPanel;
    
    @FXML
    private Label userInfo;
    
    public static boolean adminPanelOpened = false;

    private static boolean isAdmin;
    
    public static void setIsAdmin(boolean type)
    {
        isAdmin = type;
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.exit(0);
    }
    
    ////
    // this function opens admin panel (only in admin mode)
    ////
    public void adminPanel(ActionEvent event) throws Exception
    {
        if (adminPanelOpened == false)
        {
          adminPanelOpened = true;
          Stage primaryStage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("AdminWindow.fxml"));
           Scene scene;
           scene = new Scene(root);
           scene.getStylesheets().add(getClass().getResource("/Resources/style.css").toExternalForm());
           primaryStage.setScene(scene);
           primaryStage.setResizable(false);
           primaryStage.show();
        } else if (adminPanelOpened == true) 
        {
          adminPanelOpened = false;
                  
          //close admin panel
          Stage currentStage = (Stage) closeAdminPanel.getScene().getWindow();
          currentStage.close(); 
        }
    }
    
    ////
    // this function does logout process returning to the Login screen of a program
    // called when Logout button is pressed
    ////
    public void Logout() throws Exception
    {
        
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
           Scene scene;
           scene = new Scene(root);
           scene.getStylesheets().add(getClass().getResource("/Resources/style.css").toExternalForm());
           primaryStage.setScene(scene);
           primaryStage.setResizable(false);
           primaryStage.show();
        
         //close main window
         Stage currentStage = (Stage) userInfo.getScene().getWindow();
         currentStage.close(); 
    }
    
    ////
    // when this controller is initialized check value of 'admin' bool 
    // if admin then show button Admin Panel; otherwise hide
    ////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        if (!isAdmin) {
            userInfo.setText("Logged as User");
            adminPanel.setVisible(false);
        }
        else
        {
           userInfo.setText("Logged as Admin");
           adminPanel.setVisible(true);
        } 
           
        }
    }       
    


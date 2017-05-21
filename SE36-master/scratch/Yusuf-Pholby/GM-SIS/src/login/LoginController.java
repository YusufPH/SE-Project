package login;

import common.SQLiteConnection;       
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yusuf
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblStatus;
        
    @FXML
    private TextField txtUsername;
    
    @FXML
    private TextField txtPassword;
    
    SQLiteConnection db = SQLiteConnection.getInstance();
    ResultSet rs = null;
    
    @FXML
    ////
    // this function handles login process
    // called when Login button is pressed
    ////
    public void Login(ActionEvent event) throws Exception
    {
        String sql = "SELECT ID, Password, Type FROM Authentication";
     try {
            rs = db.query(sql);
            String user = txtUsername.getText();
            String password = txtPassword.getText();
            while (rs.next()) {
                if (rs.getString("ID").equals(user) && rs.getString("password").equals(password))
                {
                        MainController.setIsAdmin(rs.getBoolean("type"));
                        Parent root = FXMLLoader.load(getClass().getResource("/login/Main.fxml"));
                        Scene scene = new Scene(root);
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        return;

                }
            }
        }
        catch (Exception e)
        {e.printStackTrace();
        }
            lblStatus.setText("incorrect");
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
}

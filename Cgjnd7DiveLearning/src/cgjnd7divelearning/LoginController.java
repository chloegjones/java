/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7divelearning;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chloejones
 */
public class LoginController implements Initializable{
    private Stage stage; 
    public Scene aboutScene; 
    public Scene menuScene;
    public AboutController aboutController; 
    public MenuController menuController; 
    
    @FXML
    private TextField username;
    
    @FXML
    private TextField password;
    
    @FXML
    private Text status;
    
    @FXML
    Button login;
    
    @FXML
    private void goBackToAbout(ActionEvent event) {
        stage.setScene(aboutScene); 
    }
    
    @FXML
    private void login(ActionEvent event) {
        if(username.getText().equals("user") && password.getText().equals("pass")){
            if(menuScene == null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                    Parent menuRoot = loader.load();
                    menuController = loader.getController();
                    menuController.loginScene = menuScene;
                    menuController.loginController = this;
                    menuScene = new Scene(menuRoot);
                    stage.setScene(menuScene);
                    menuController.start(stage);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }else{
            status.setText("Login Failed");
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void start(Stage stage){
        this.stage = stage; 
    }
}

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
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author chloejones
 */
public class AboutController implements Initializable{
    private Stage stage;
    private Scene aboutScene;
    private Scene loginScene;
    private LoginController loginController;
    public Scene menuScene;
    public AboutController aboutController; 
    public MenuController menuController; 
    
    @FXML
    private Text title;
    
    @FXML
    private Text about;
       
    @FXML
    private void goToLogin(ActionEvent event) {
        System.out.println("Going to login");
        if(loginScene == null){
            try {
                System.out.println("login is null");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent loginRoot = loader.load();
                loginController = loader.getController();
                loginController.aboutScene = aboutScene;
                loginController.aboutController = this;
                loginScene = new Scene(loginRoot);
                
            } catch (IOException ex) {
                Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stage.setScene(loginScene); 
//        loginController.info = "Hello World!"; 
        loginController.start(stage);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage){
        title.setText("Welcome to DIVE Learning!");
        about.setText("\n\nMy name is Chloe Jones, and I am the founder of DIVE Learning."
                + "\n\nDIVE Learning is an application created for my Object Oriented Programming class final project, which provides free educational tools"
                + " for all ages to promote inclusivity within all spaces. The idea behind this application came from the lack of "
                + " free tools available to the general public for diversity and inclusivity training. If spaces are going"
                + " to change, these resources need to be available to everyone."
                + "\n\nIf you would like to check it out, first, click the"
                + " 'Continue' button and you will be prompted to sign-in or create a new account. Next, you will be provided"
                + " with several different general topics (laws, history, etc.). Click on which one you are interested in. After"
                + " this, you can choose a specific area (LGBTQ+, African-American, etc.). Contine to check out more features!");
        this.stage = stage; 
        aboutScene = stage.getScene(); 
    }
}

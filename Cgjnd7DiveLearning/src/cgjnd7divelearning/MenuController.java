/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7divelearning;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//https://data.cityofnewyork.us/api/views/tncb-agv4/rows.json?accessType=DOWNLOAD
//https://data.austintexas.gov/stories/s/2018-Diversity-And-Inclusion/t9p7-kw62
//https://catalog.data.gov/dataset/municipality-equality-index-performance-measure-3-12
//https://catalog.data.gov/harvest/object/75653735-636a-4057-9877-515ea0d272cb
/**
 * FXML Controller class
 *
 * @author chloejones
 */
public class MenuController implements Initializable{
    private Stage stage; 
//    History history = new History();
    public Scene menuScene;
    public Scene loginScene;
    public LoginController loginController;
    String[] urls = new String[5];
    private String searchWomen = "women";
    private String searchLgbtq = "lgbtq";
    private String searchAfrican = "african americans";
    private String searchHispanic = "Hispanic americans";
    private String searchD = "disabilities";
    private List<String> comment = new ArrayList<>();
    ObservableList<String> newsListItemsWomen;
    ObservableList<String> newsListItemsLgbtq;
    ObservableList<String> newsListItemsHispanic;
    ObservableList<String> newsListItemsD;
    ObservableList<String> newsListItemsAfrican;
    ArrayList<NYTNewsStory> stories;
    private NYTNewsManager newsManager;
    
    @FXML
    private ListView newsListViewWomen;
    @FXML
    private ListView newsListViewLgbtq;
    @FXML
    private ListView newsListViewAfrican;
    @FXML
    private ListView newsListViewHispanic;
    @FXML
    private ListView newsListViewD;
    
    @FXML
    private WebView webViewWomen;
    @FXML
    private WebView webViewLgbtq;
    @FXML
    private WebView webViewD;
    @FXML
    private WebView webViewAfrican;
    @FXML
    private WebView webViewHispanic;
    
    @FXML 
    private AnchorPane menuPane;
    
    @FXML 
    private AnchorPane womenPane;
    @FXML 
    private AnchorPane lgbtqPane;
    @FXML 
    private AnchorPane africanPane;
    @FXML 
    private AnchorPane dPane;
    @FXML 
    private AnchorPane hispanicPane;
            
    @FXML 
    private TextArea womenText;
    @FXML 
    private TextArea LText;
    @FXML 
    private TextArea AText;
    @FXML 
    private TextArea HText;
    @FXML 
    private TextArea DText;
    
    @FXML 
    private Button save;
    
    @FXML
    private void goToLogin(ActionEvent event) {
        System.out.println("Going to login");
        if(loginScene == null){
            try {
                System.out.println("login is null");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent loginRoot = loader.load();
                loginController = loader.getController();
                loginController.menuScene = menuScene;
                loginController.menuController = this;
                loginScene = new Scene(loginRoot);
                
            } catch (IOException ex) {
                Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        stage.setScene(loginScene); 
//        loginController.info = "Hello World!"; 
        loginController.start(stage);
        
    }
    
    @FXML
    private void getNews(ActionEvent event){
        clear(stage);
        newsManager = new NYTNewsManager();
        newsListItemsWomen = FXCollections.observableArrayList();
        newsListItemsLgbtq = FXCollections.observableArrayList();
        newsListItemsD = FXCollections.observableArrayList();
        newsListItemsAfrican = FXCollections.observableArrayList();
        newsListItemsHispanic = FXCollections.observableArrayList();
        
        womenPane.getChildren().addAll(webViewWomen, newsListViewWomen);
        lgbtqPane.getChildren().addAll(webViewLgbtq, newsListViewLgbtq);
        hispanicPane.getChildren().addAll(webViewHispanic, newsListViewHispanic);
        africanPane.getChildren().addAll(webViewAfrican, newsListViewAfrican);
        dPane.getChildren().addAll(webViewD, newsListViewD);
        
        newsListViewWomen.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if((int) new_val < 0 || (int) new_val > (stories.size() - 1)){
                        return; 
                    }
                    
                    NYTNewsStory story = stories.get((int) new_val); 
                    webViewWomen.getEngine().load(story.webUrl);    
                }
        ); 
        loadWomenNews();
        newsListViewLgbtq.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if((int) new_val < 0 || (int) new_val > (stories.size() - 1)){
                        return; 
                    }
                    
                    NYTNewsStory story = stories.get((int) new_val); 
                    webViewLgbtq.getEngine().load(story.webUrl);    
                }
        ); 
        loadLgbtqNews();
        newsListViewHispanic.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if((int) new_val < 0 || (int) new_val > (stories.size() - 1)){
                        return; 
                    }
                    
                    NYTNewsStory story = stories.get((int) new_val); 
                    webViewHispanic.getEngine().load(story.webUrl);    
                }
        ); 
        loadHispanicNews();
        
        newsListViewD.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if((int) new_val < 0 || (int) new_val > (stories.size() - 1)){
                        return; 
                    }
                    
                    NYTNewsStory story = stories.get((int) new_val); 
                    webViewD.getEngine().load(story.webUrl);    
                }
        ); 
        loadDNews();
        
        newsListViewAfrican.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if((int) new_val < 0 || (int) new_val > (stories.size() - 1)){
                        return; 
                    }
                    
                    NYTNewsStory story = stories.get((int) new_val); 
                    webViewAfrican.getEngine().load(story.webUrl);    
                }
        ); 
        loadAfricanNews();
    }
    
    @FXML
    private void getHistory(ActionEvent event){
        clear(stage);
        urls[0] = "https://www.youtube.com/embed/m_UjYOfmkn8";
        urls[1] = "https://www.youtube.com/embed/Q1D65SxzojI";
        urls[2] = "https://www.youtube.com/embed/3XMndYNEGFA";
        urls[3] = "https://www.youtube.com/embed/EmLI6tuq22Y";
        urls[4] = "https://www.youtube.com/embed/dzfdINufpSE";
        
        webViewWomen.getEngine().load(urls[0]);
        webViewLgbtq.getEngine().load(urls[1]);
        webViewD.getEngine().load(urls[2]);
        webViewAfrican.getEngine().load(urls[3]);
        webViewHispanic.getEngine().load(urls[4]);
        
        menuPane.getChildren().addAll(save);
        womenPane.getChildren().addAll(webViewWomen, womenText);
        africanPane.getChildren().add(webViewAfrican);
        lgbtqPane.getChildren().add(webViewLgbtq);
        dPane.getChildren().add(webViewD);
        hispanicPane.getChildren().add(webViewHispanic);
    }
    
    @FXML 
    void save(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        
        comment.add(womenText.getText());
        comment.add(AText.getText());
        comment.add(HText.getText());
        comment.add(DText.getText());
        comment.add(LText.getText());
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
             
            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
             
            if(file != null){
                SaveFile(comment.toString(), file);
            }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void start(Stage stage) {
        this.stage = stage;
        clear(stage);
    }
    private void loadWomenNews() {
        try {
            newsManager.load(searchWomen); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        stories = newsManager.getNewsStories(); 
        newsListItemsWomen.clear(); 
        
        for(NYTNewsStory story : stories){
            newsListItemsWomen.add(story.headline); 
        }
        newsListViewWomen.setItems(newsListItemsWomen);
        
        if(stories.size() > 0){
            newsListViewWomen.getSelectionModel().select(0);
            newsListViewWomen.getFocusModel().focus(0); 
            newsListViewWomen.scrollTo(0); 
        }
    }
    private void loadLgbtqNews(){
        try {
            newsManager.load(searchLgbtq); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        stories = newsManager.getNewsStories(); 
        newsListItemsLgbtq.clear(); 
        
        for(NYTNewsStory story : stories){
            newsListItemsLgbtq.add(story.headline); 
        }
        newsListViewLgbtq.setItems(newsListItemsLgbtq);
        
        if(stories.size() > 0){
            newsListViewLgbtq.getSelectionModel().select(0);
            newsListViewLgbtq.getFocusModel().focus(0); 
            newsListViewLgbtq.scrollTo(0); 
        }
    }
    
    private void loadHispanicNews(){
        try {
            newsManager.load(searchHispanic); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        stories = newsManager.getNewsStories(); 
        newsListItemsHispanic.clear(); 
        
        for(NYTNewsStory story : stories){
            newsListItemsHispanic.add(story.headline); 
        }
        newsListViewHispanic.setItems(newsListItemsHispanic);
        
        if(stories.size() > 0){
            newsListViewHispanic.getSelectionModel().select(0);
            newsListViewHispanic.getFocusModel().focus(0); 
            newsListViewHispanic.scrollTo(0); 
        }
    }
    
    private void loadDNews(){
        try {
            newsManager.load(searchD); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        stories = newsManager.getNewsStories(); 
        newsListItemsD.clear(); 
        
        for(NYTNewsStory story : stories){
            newsListItemsD.add(story.headline); 
        }
        newsListViewD.setItems(newsListItemsD);
        
        if(stories.size() > 0){
            newsListViewD.getSelectionModel().select(0);
            newsListViewD.getFocusModel().focus(0); 
            newsListViewD.scrollTo(0); 
        }
    }
    private void loadAfricanNews(){
        try {
            newsManager.load(searchAfrican); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        stories = newsManager.getNewsStories(); 
        newsListItemsAfrican.clear(); 
        
        for(NYTNewsStory story : stories){
            newsListItemsAfrican.add(story.headline); 
        }
        newsListViewAfrican.setItems(newsListItemsAfrican);
        
        if(stories.size() > 0){
            newsListViewAfrican.getSelectionModel().select(0);
            newsListViewAfrican.getFocusModel().focus(0); 
            newsListViewAfrican.scrollTo(0); 
        }
    }
    
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;
              
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Cgjnd7DiveLearning.class
                .getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    public void clear(Stage stage){
        for(int i = 0; i< urls.length; i++)
        {
            urls[i] = null;
        }
        webViewWomen.getEngine().load(urls[1]);
        
        menuPane.getChildren().remove(save);
        womenPane.getChildren().removeAll(webViewWomen, newsListViewWomen, womenText);
        africanPane.getChildren().removeAll(webViewAfrican, newsListViewAfrican);
        lgbtqPane.getChildren().removeAll(webViewLgbtq, newsListViewLgbtq);
        dPane.getChildren().removeAll(webViewD, newsListViewD);
        hispanicPane.getChildren().removeAll(webViewHispanic, newsListViewHispanic);
    }
   
    private void displayExceptionAlert(Exception ex) {
        System.out.println("Exception");
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Exception");
//        alert.setHeaderText("An Exception Occurred!");
//        alert.setContentText("An exception occurred.  View the exception information below by clicking Show Details.");
//
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        ex.printStackTrace(pw);
//        String exceptionText = sw.toString();
//
//        Label label = new Label("The exception stacktrace was:");
//
//        TextArea textArea = new TextArea(exceptionText);
//        textArea.setEditable(false);
//        textArea.setWrapText(true);
//
//        textArea.setMaxWidth(Double.MAX_VALUE);
//        textArea.setMaxHeight(Double.MAX_VALUE);
//        GridPane.setVgrow(textArea, Priority.ALWAYS);
//        GridPane.setHgrow(textArea, Priority.ALWAYS);
//
//        GridPane expContent = new GridPane();
//        expContent.setMaxWidth(Double.MAX_VALUE);
//        expContent.add(label, 0, 0);
//        expContent.add(textArea, 0, 1);
//
//        alert.getDialogPane().setExpandableContent(expContent);
//
//        alert.showAndWait();
    }
}

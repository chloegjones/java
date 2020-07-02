/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7mvcstopwatchfxmls20;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static java.lang.String.format;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author chloejones
 */
public class FXMLDocumentController implements Initializable, PropertyChangeListener {
    Model model;
    DigitalModel digital;
    
    @FXML
    private Button startStop;
    @FXML
    private Button recordReset;
    @FXML
    private ImageView handImage;
    @FXML
    private Label timer;
    @FXML
    private Label lap;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private XYChart.Series<String, Number> series;
   
    @FXML
    private void startStopButton(ActionEvent event) {
        if (model.isRunning){
            model.pause();
            digital.pause();
            startStop.setText("Start");
            recordReset.setText("Reset");
            model.isRunning = false;
        }else{
            model.start();
            digital.start();
            startStop.setText("Stop");
            recordReset.setText("Record");
            model.isRunning = true;
        }
    }
    
    @FXML
    private void recordResetButton(ActionEvent event) {
        if(model.isRunning){
            model.record();
            digital.record();
            digital.recordCounter++;
           
        }else{
            model.reset();
            digital.reset();
            recordReset.setText("Record");
            handImage.setRotate(0);
            series.getData().clear();
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        series = new XYChart.Series();                                          //initialize Record chart
        lineChart.getData().add(series);
        
        model = new Model();
        digital = new DigitalModel(); 
        
        model.setupMonitor();
        digital.setupMonitor();
        
        handImage.setRotate(0);
        
        model.addPropertyChangeListener(this);
        digital.addPropertyChangeListener(this);
        
        lineChart.setAnimated(false);
    }    

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //update the view
        if(evt.getPropertyName().equals("Analog")){
            handImage.setRotate((double) evt.getNewValue());
        }else if(evt.getPropertyName().equals("Digital")){
            timer.setText(evt.getNewValue().toString());
        }else if(evt.getPropertyName().equals("Lap Time")){
            lap.setText(evt.getNewValue().toString());
        }else if(evt.getPropertyName().equals("Reset")){
            timer.setText(evt.getNewValue().toString());
            lap.setText(evt.getNewValue().toString());
        }else if(evt.getPropertyName().equals("Reset Hand")){
            handImage.setRotate((double) evt.getNewValue());
        }else if(evt.getPropertyName().equals("Line Chart")){
            series.getData().add(new XYChart.Data(Integer.toString(model.laps), evt.getNewValue()));
        }
    }
}
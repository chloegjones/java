/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7mvcstopwatchfxmls20;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

/**
 *
 * @author chloejones
 */
public class AbstractModel {
    protected PropertyChangeSupport propertyChangeSupport;
    protected Timeline timeline;
    protected KeyFrame keyFrame;
    protected double tickTimeInSeconds;
    protected boolean isRunning = false;
    protected int laps;
    protected double oldRotation;
    protected double oldClickTime;
    protected double lapTime;
    protected String lapTimeString;
    protected String resetString;
    protected double secondsElapsed;
    protected double rotation = 0;
    SimpleDateFormat format = new SimpleDateFormat("mm:ss:SS");
    protected String oldLapString;
    protected double lineTime;
    
    public AbstractModel()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
        tickTimeInSeconds = 0.1;
        secondsElapsed = 0.0;
    }
    public boolean isRunning(){
        if(timeline != null){
            if(timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;
    }
        
    public void setupMonitor() {        
        keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds * 1000), (ActionEvent actionEvent) ->{
            updateMonitor();
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    public void updateMonitor() {
        double secondsElapsed = getSecondsElapsed();
    }
    public double getSecondsElapsed(){
        return secondsElapsed += tickTimeInSeconds;
    }
    public void pause(){
        timeline.pause();
    }
    
    public void start(){
        timeline.play();
    }
    
    public void record(){
        laps++;
        lapTime = secondsElapsed - oldClickTime;
        oldClickTime = secondsElapsed;
        
        oldLapString = lapTimeString;
        lapTimeString = getLapString(lapTime);
      
        firePropertyChange("Line Chart", null, lapTime);
        firePropertyChange("Lap Time", oldLapString, lapTimeString);
    }

    public String getLapString(double lapTime){
        return "Lap " + laps + ": " + format.format(lapTime*1000);
    }
    
    public void reset(){
        laps = 0;
        String oldReset = resetString;
        resetString = format.format(0);
        oldRotation = rotation;
        rotation = 0;
        firePropertyChange("Reset", oldReset, resetString);
        firePropertyChange("Reset Hand", oldRotation, rotation);
    }
        
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}

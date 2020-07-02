/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7mvcstopwatchfxmls20;

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
public class Model extends AbstractModel {       
    private double angleDeltaPerSecond;
    
    public Model(){
        angleDeltaPerSecond = 6.0;
    }
    @Override
    public void updateMonitor() {
        super.updateMonitor();
        updateAnalog(secondsElapsed);
    }
    
    public void updateAnalog(double secondsElapsed){
        oldRotation = rotation;
        secondsElapsed += tickTimeInSeconds;
        rotation = secondsElapsed * angleDeltaPerSecond;
        firePropertyChange("Analog", oldRotation, rotation);
    }
    
    

    

    
    
    
}

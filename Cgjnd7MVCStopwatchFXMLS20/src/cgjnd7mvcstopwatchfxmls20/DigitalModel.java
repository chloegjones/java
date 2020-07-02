/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7mvcstopwatchfxmls20;

/**
 *
 * @author chloejones
 */
public class DigitalModel extends AbstractModel{
    private String oldString;
    private String digitalString;
    public int recordCounter = 0;
    
    public DigitalModel(){

    }   
    @Override
    public void updateMonitor() {
        super.updateMonitor();
        updateDigital(secondsElapsed);
    }
    public void updateDigital(double secondsElapsed){
        oldString = digitalString;
        digitalString = getSecondsElapsedString(secondsElapsed);
        firePropertyChange("Digital", oldString, digitalString);
    }
    public String getSecondsElapsedString(double secondsElapsed){
        return format.format(secondsElapsed * 1000);
    }
    
}

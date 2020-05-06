/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;


import static java.lang.Integer.min;
import static java.lang.Math.PI;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
/**
 *
 * @author chloejones
 */
public class Cgjnd7SpaceVisualizer implements Visualizer{

    private final String name = "Chloe's Visualizer";
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minEllipseRadius = 10.0;  // 10.0
    
    private double width = 0.0;
    private double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    private final Double startHue = 260.0;
    private Sphere sphere1;
    private List<Sphere> spheres;
    
    private Circle[] circles;
    private Circle circle;
    private Ellipse ellipse;
    private Ellipse[] ellipses;
    private Double angle = 0.0;
    int number;
    private String vizPaneInitialStyle = "";
    final double radius = 150;
    final double centerX = 250;
    final double centerY = 250;
    private Double x;
    private Double y;
    public Cgjnd7SpaceVisualizer() {

    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        vizPaneInitialStyle = vizPane.getStyle();
        this.numOfBands = numBands;
        this.vizPane = vizPane;
        spheres = new ArrayList<>();
       
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        sphere1 = new Sphere();
        sphere1.setRadius(50);
        
        sphere1.setTranslateX(width/2);
        sphere1.setTranslateY(height/2);
        spheres.add(sphere1);
        ellipses = new Ellipse[numBands];
        circles = new Circle[numBands];
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIME);
        material.setSpecularColor(Color.LIME);
        material.setSpecularPower(10.0);

        sphere1.setMaterial(material);
        
            vizPane.getChildren().addAll(sphere1);
            for(int i = 0; i < numBands/2; i++){
                angle = ((double)i *((2*PI)/numBands));
                circle = new Circle();
                circle.setRadius(10);
                x = (width/2 + ((Math.cos(angle))*150));
                y = (height/2 + ((Math.sin(angle))*150));
                circle.setTranslateX(x); 
                circle.setTranslateY(y); 
                circle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
                circles[i] = circle;
                
                vizPane.getChildren().add(circle); 
            }
            
            for(int i = 0; i < (numBands/2) + 1; i++){
                angle = (-1)*(((double)i*((2*PI)/numBands)));
                ellipse = new Ellipse();
                ellipse.setRadiusY(10);
                ellipse.setRadiusX(10);
                x = (width/2 + ((Math.cos(angle))*150));
                y = (height/2 + ((Math.sin(angle))*150));
                ellipse.setTranslateX(x); 
                ellipse.setTranslateY(y); 
                ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
                ellipses[i] = ellipse;
                
                vizPane.getChildren().add(ellipse); 
        }
           
    }
    
        
    @Override
    public void end() {
        if(sphere1 != null){
            vizPane.getChildren().remove(sphere1);
        }
        if (circles != null) {
            for (Circle circle : circles) {
                vizPane.getChildren().remove(circle);
            }
            circles = null;
        }     
        
        if (ellipses != null) {
            for (Ellipse ellipse : ellipses) {
                vizPane.getChildren().remove(ellipse);
            }
            ellipses = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
        }     
    }
    
    @Override
    public void draw(double timestamp, double length, float[] magnitudes, float[] phases) {
        Integer num = min(ellipses.length/2, magnitudes.length);
        Integer num2 = min(circles.length/2, magnitudes.length);
        Integer num3 = min(1, magnitudes.length);
        
        for(int i = 0; i <= num3; i++){
            sphere1.setRadius((((60.0 + magnitudes[i])/200.0) * halfBandHeight + minEllipseRadius));
            if(sphere1.getRadius() <= 51){   
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.DEEPPINK);
                material.setSpecularColor(Color.PINK);
                material.setSpecularPower(10.0);
                sphere1.setMaterial(material);
            }
             if(sphere1.getRadius() > 51 && sphere1.getRadius() < 52){   
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.LIME);
                material.setSpecularColor(Color.LIME);
                material.setSpecularPower(10.0);
                sphere1.setMaterial(material);
            }
             
            if(sphere1.getRadius() >= 52 && sphere1.getRadius() <= 55){   
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.CYAN);
                material.setSpecularColor(Color.CYAN);
                material.setSpecularPower(10.0);
                sphere1.setMaterial(material);
            }
           
        }
        
        for(int i = 0; i < num2; i++){
            circles[i].setRadius((((60.0 + magnitudes[i/2])/150.0) * halfBandHeight + minEllipseRadius)/2);
            circles[i].setFill(Color.hsb(startHue - (magnitudes[i/2] * -6.0), 1.0, 1.0, 1.0));
        }
        
        for(int i = 0; i < num +1; i++) {
            ellipses[i].setRadiusX((((60.0 + magnitudes[i/2])/150.0) * halfBandHeight + minEllipseRadius)/2);
            ellipses[i].setRadiusY((((60.0 + magnitudes[i/2])/150.0) * halfBandHeight + minEllipseRadius)/2);
            ellipses[i].setFill(Color.hsb(startHue - (magnitudes[i/2] * -6.0), 1.0, 1.0, 1.0));
        }
        
    }
}

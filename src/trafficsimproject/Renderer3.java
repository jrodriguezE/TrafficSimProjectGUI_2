/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author paleo
 */
//THIS IS THE GOOD ONE!!!!!!!!!!!!!!!
public class Renderer3 extends JFrame{


    ArrayList<Road> roads;
    ArrayList<Shape> roadShapes = new ArrayList<Shape>();
    ArrayList<Shape> intersectShapes = new ArrayList<Shape>();

    //drawRoad = new Line2D.Float(lons.get(i)/scale,lats.get(i)/scale,lons.get(i+1)/scale,lats.get(i+1)/scale);
    
    //for adjusting map
    double scale = 1;
    public void setScale(double scale){
        this.scale = scale;
    }    
    //for transforming the long&lat to pixels
    

    int scale1 = 100000;
    
    public void setMap(){

        for(int i = 0; i<roads.size();i++){

            for(int j = 0 ; j<roads.get(i).nodeList.size()-1;j++){


                Double y1=(roads.get(i).nodeList.get(j).getLat()-minLat)*scale1;
                Double x1=(roads.get(i).nodeList.get(j).getLong()-minLon)*scale1;
                Double y2=(roads.get(i).nodeList.get(j+1).getLat()-minLat)*scale1;
                Double x2=(roads.get(i).nodeList.get(j+1).getLong()-minLon)*scale1;
                
                Shape tempRoad = new Line2D.Double(x1/scale,y1/scale,x2/scale,y2/scale);

                roadShapes.add(tempRoad);
                
            }
            for(int j=0; j<roads.get(i).intersections.size();j++){
                Double x1 = (roads.get(i).intersections.get(j).getLong()-minLon)*scale1;
                Double y1 = (roads.get(i).intersections.get(j).getLat()-minLat)*scale1;
                

                Shape tempCept = new Rectangle2D.Double(x1/scale,y1/scale,5,5);
                intersectShapes.add(tempCept);
            }
        }
        System.out.println("minLon"+minLon);
        System.out.println("maxLon"+maxLon);
        System.out.println("minLat"+minLat);
        System.out.println("maxLat"+maxLon);
    }
    
    
    Shape testCar = new Rectangle2D.Double(0,0,0,0);
    
    public void setAutoPos(Nd node){
        Double x1=(node.getLong()-minLon)*scale1;
        Double y1=(node.getLat()-minLat)*scale1;
        
        testCar = new Rectangle2D.Double(x1/scale,y1/scale,10,10);
        
        drawWindow();
        
        System.out.println("STEP");
    }

    
    // I was using these to test some things but a few of the functions
    // rely on them now. need to fix this shit so they are not global. 
    double minLat=0;
    double minLon=0;
    double maxLon=0;
    double maxLat =0;
    int latRange=0;
    int lonRange=0;
    
    Graphics2D graph2;
    
    
    // this is the constructor... i was using it as a spot to 
    // test code in the class.it takes the values for max and min borders of
    // the whole map as parameters.
    Renderer3(ArrayList<Road> roads, double minLat, double maxLat, double minLon, double maxLon){
        this.roads=roads;
        this.minLat=minLat;
        this.minLon=minLon;
        this.maxLon=maxLon;
        this.maxLat=maxLat;// i made these global because of testing. sys out.prints are all debug testing related
        
        drawWindow();
//        latRange = (int)((maxLat-minLat)*scale1);// calculate the size of the window
//        lonRange = (int)((maxLon- minLon)*scale1);
//        System.out.println("SIZE OF WINDOW: " + lonRange + " " +latRange );
//        this.setSize(lonRange,latRange); // regular stuff for jframe 
//        
//        this.setTitle("Traffic Sim");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.add(new DrawStuff(), BorderLayout.CENTER);
//        this.setVisible(true);
        
        
    }
    
    public void drawWindow(){
        latRange = (int)((maxLat-minLat)*scale1);// calculate the size of the window
        lonRange = (int)((maxLon- minLon)*scale1);
        System.out.println("SIZE OF WINDOW: " + lonRange + " " +latRange );
        this.setSize(lonRange,latRange); // regular stuff for jframe 
        
        this.setTitle("Traffic Sim");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new DrawStuff(), BorderLayout.CENTER);
        this.setVisible(true);

    }
    

    
    // this draws stuff
    // 
    private class DrawStuff extends JComponent
    {
        public void paint(Graphics g){
            graph2 = (Graphics2D)g;
            
            //see how it looks
            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
//            Shape drawLine = new Line2D.Float(20,90,55,250);
            
            graph2.setPaint(Color.BLACK);
            //graph2.draw(drawLine);
            
            // this is where the points are drawn on the screen
            
            int counter = 0;
            for(int i =0; i<roadShapes.size();i++)
            {
                
                graph2.draw(roadShapes.get(i));

                
            }
            for(int i=0; i<intersectShapes.size();i++){
                graph2.setPaint(Color.RED);
                graph2.draw(intersectShapes.get(i));
 //               System.out.println("*********************************************************DRAWING INTERCEPT");
 //               System.out.println("INTERSECT X: "+intersectShapes.get(i).getBounds().x);
 //               System.out.println("INTERSECT Y: "+intersectShapes.get(i).getBounds().y);
            }
            
            //TEST CAR
            graph2.setPaint(Color.GREEN);
            graph2.draw(testCar);
            
//            System.out.println("SIZE OF WINDOW: " + (maxLon*100000-minLon*100000) + " " +(maxLat*100000-minLat*100000) );

            
        }

        
        // lots of crap 
        public void renderFrame(){
            
        }
        
        public void newFrame(){
            
        }
        
        public void addLine(int x1, int y1, int x2, int y2){
            
        }
        
        public void addPoint(int x, int y){
            
        }
        
        
        
        
    }
    
    
}
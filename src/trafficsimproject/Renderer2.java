package trafficsimproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
/**
 *
 * @author aome1
 */
public class Renderer2 extends JFrame
{
    // these hold coords to draw the nodes on the map. The values in these
    // arrays have been changed from long and lat to x and y for pixel placement.
    ArrayList<Integer> lons = new ArrayList<Integer>();
    ArrayList<Integer> lats = new ArrayList<Integer>();
    
    // this draws the nodes on the map
    ArrayList<Road> roads;
    
    
    
    
    
    ArrayList<Integer> skipThisIndex = new ArrayList<Integer>();
    public void setMap(){
        this.roads=roads;
//        int arrayIndex =0;
        
        int runTotal=0;
        for(int i = 0; i<roads.size();i++){
            int jcount =0;
            for(int j = 0 ; j<roads.get(i).nodeList.size();j++){
                jcount++;
                
                double lon = roads.get(i).nodeList.get(j).getLong();
                double lat = roads.get(i).nodeList.get(j).getLat();
                lons.add((int)((roads.get(i).nodeList.get(j).getLong()*100000-minLon*100000)+0));
                lats.add((int)(latRange-(roads.get(i).nodeList.get(j).getLat()*100000-minLat*100000)+0));     
                
                
                // al the system outs are for debug purposes
//                System.out.println( "MATH"+(roads.get(i).nodeList.get(j).getLong()*100000) +" - "+ (minLon*100000)  );
//                System.out.println("lon "+lon);
//                System.out.println("lat "+lat);
                if(j<roads.get(i).nodeList.size()-1)
                {
                    
                }
                
                
                
            }
            
            skipThisIndex.add(jcount+runTotal);
            runTotal+=runTotal;

        }
        System.out.println("maxLon"+minLon);
        System.out.println("minLat"+minLat);
    }
    

    
    // I was using these to test some things but a few of the functions
    // rely on them now. need to fix this shit so they are not global. 
    double minLat=0;
    double minLon=0;
    double maxLon=0;
    double maxLat =0;
    int latRange=0;
    int lonRange=0;
    
    // this is the constructor... i was using it as a spot to 
    // test code in the class.it takes the values for max and min borders of
    // the whole map as parameters.
    Renderer2(ArrayList<Road> roads, double minLat, double maxLat, double minLon, double maxLon){
        this.roads=roads;
        this.minLat=minLat;
        this.minLon=minLon;
        this.maxLon=maxLon;
        this.maxLat=maxLat;// i made these global because of testing. sys out.prints are all debug testing related
        System.out.println("SIZE OF WINDOW: " + (maxLon*100000-minLon*100000) + " " +(maxLat*100000-minLat*100000) );
        latRange = (int)(maxLat*100000-minLat*100000);// calculate the size of the window
        lonRange = (int)(maxLon*100000- minLon*100000);
        
        this.setSize(lonRange,latRange); // regular stuff for jframe 
        
        this.setTitle("Traffic Sim");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new DrawStuff(), BorderLayout.CENTER);
        this.setVisible(true);
        
        
    }
    
    // this draws stuff
    // 
    private class DrawStuff extends JComponent{
        public void paint(Graphics g){
            Graphics2D graph2 = (Graphics2D)g;
            
            //see how it looks
            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
//            Shape drawLine = new Line2D.Float(20,90,55,250);
            
            graph2.setPaint(Color.BLACK);
            //graph2.draw(drawLine);
            
            // this is where the points are drawn on the screen
            int j = 0;
            int counter = 0;
            for(int i =0; i<lats.size();i++)
            {
                Shape drawPoint = new Line2D.Float(lons.get(i)/scale,lats.get(i)/scale,lons.get(i)/scale+5,lats.get(i)/scale+5);
                System.out.println("DRAWING POINT: "+lons.get(i)+ " " +lats.get(i));
                graph2.draw(drawPoint);
                
                
                boolean skip = false;
                if(i==skipThisIndex.get(counter)){
                    counter++;
                    skip=true;
                }
                
                
                //// drawing roads?
                Shape drawRoad;
                if(!skip&&i<lons.size()-1&&// i-counter<lons.size()-1&&
                        lons.get(i)>0&&lats.get(i)>0&&lons.get(i+1)>0&&lats.get(i+1)>0
                        &&lons.get(i)<lonRange&&lats.get(i)<latRange&&lons.get(i+1)<lonRange&&lats.get(i+1)<latRange){
                    
                    //System.out.println(roads.get(j).nodeList.size()+"nodes in"+j);
                    
                    drawRoad = new Line2D.Float(lons.get(i)/scale,lats.get(i)/scale,lons.get(i+1)/scale,lats.get(i+1)/scale);
                    System.out.println(" DRAWING ROAD");
                    graph2.draw(drawRoad);
                }
                
                
                //System.out.println("___________ j: "+j);
                
                
            }
            
            
            System.out.println("SIZE OF WINDOW: " + (maxLon*100000-minLon*100000) + " " +(maxLat*100000-minLat*100000) );

            
        }
        int scale = 7;//scale where drawings are on map... 
        
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.ArrayList;

/**
 *
 * @author paleo
 */
public class RoadAnalyzer {
    ArrayList<Road> roads = new ArrayList<Road>();
        
    RoadAnalyzer(){
        
    }
    
    public void setRoads(ArrayList<Road> roads){
        this.roads=roads;
        
    }
    
}

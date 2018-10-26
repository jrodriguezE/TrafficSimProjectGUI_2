/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author paleo
 */
public class RoadBuilder implements Serializable{
    int id = 0;   //identification # of road
    ArrayList<NdBuilder> nodeList = new ArrayList<NdBuilder>();  // Nodes that make up road
    ArrayList<NdBuilder> intersections = new ArrayList<NdBuilder>();
    boolean oneWay = false;
    public double speed=.00016;
    int numLanes=1;
    RoadBuilder(){
        
    }
}

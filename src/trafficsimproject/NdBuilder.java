/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.io.Serializable;

/**
 *
 * @author paleo
 */
public class NdBuilder implements Serializable{
    long ref = 0;
    double longitude = 0;
    double latitude = 0;
    boolean isIntersection = false;
    boolean isStop = false;
    boolean isStopLight=false;
    double speedLimit = .00016; // 44 feet per second, 30 mph.  default
    int numLanes=1;
    NdBuilder(){
        
    }
}

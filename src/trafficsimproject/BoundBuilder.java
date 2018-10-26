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
public class BoundBuilder implements Serializable{
    public double minLat=0;
    public double minLon=0;
    public double maxLon=0;
    public double maxLat =0;
    BoundBuilder(){
        
    }
}

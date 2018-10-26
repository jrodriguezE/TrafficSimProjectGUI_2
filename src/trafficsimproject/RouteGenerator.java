/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author paleo
 */
public class RouteGenerator implements Runnable{
    boolean run=true;
    ArrayList<Road> roads;
    RouteBuffer buffer;
    double range;
    
    RouteGenerator(ArrayList<Road> rds,double range,RouteBuffer rts){
        this.roads=rds;
        this.buffer=rts;
        this.range=range;//maxLon-minLon
    }
    
    public Directions makeDirection(){

        boolean repeat = true;
        Random rand = new Random();
        Directions route = new Directions();
        while(repeat){
            int roadNum1 = rand.nextInt(roads.size());
            int nodeNum1 = rand.nextInt(roads.get(roadNum1).nodeList.size());
            int roadNum2 = rand.nextInt(roads.size());
            int nodeNum2 = rand.nextInt(roads.get(roadNum2).nodeList.size());
            if(roads.get(roadNum1).nodeList.get(nodeNum1).calcDistance(roads.get(roadNum2).nodeList.get(nodeNum2))<(range)/4){
//                System.out.println("continue="+(maxLon-minLon)/4);
                continue;
            }
            route = new Directions(roads.get(roadNum1).nodeList.get(nodeNum1));
            route = route.findRoute(roads, roads.get(roadNum1).nodeList.get(nodeNum1), roads.get(roadNum2).nodeList.get(nodeNum2));
            route.start = roads.get(roadNum1).nodeList.get(nodeNum1);
            
            if(!route.isEmpty()){
                repeat=false;
            }
        }
        
        return route;
        
    }
    public void run() {
        while(run){
            try{
                buffer.blockingPut(makeDirection());
//                System.out.println("buffer.put");
            }catch(Exception ex){
                
            }
        }
    }
    
}

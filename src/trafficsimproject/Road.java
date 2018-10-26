/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.ArrayList;

/**
 *
 * TODO: figure what else we need here, currently just using to test 
 * // parser
 */

// creates a Road data type for us. 
public class Road {
    private int id = 0;   //identification # of road
    ArrayList<Nd> nodeList = new ArrayList<Nd>();  // Nodes that make up road
    ArrayList<IntersectNd> intersections = new ArrayList<IntersectNd>();
    ArrayList<Double> distList = new ArrayList<Double>(); // distances between nodes
    boolean oneWay = false;
    public double speed=.00016;
    public double distance =0;
    int numLanes =1;
    
    Road(){
        
    }
    
    public void buildLocalIntercets(){
        for(int i = 0; i< nodeList.size()-1;i++){
            nodeList.get(i).addConnection(nodeList.get(i+1), calcDistance(nodeList.get(i),nodeList.get(i+1)));
            
            if(!oneWay){
                nodeList.get(i+1).addConnection(nodeList.get(i), calcDistance(nodeList.get(i+1),nodeList.get(i)));
            }
//            nodeList.get(i+1).addConnection(nodeList.get(i), calcDistance(nodeList.get(i+1),nodeList.get(i)));
        }
    }
    
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    
    public void addNode(Nd node){
        nodeList.add(node);
    }
    
    public ArrayList<Nd> getNodes(){
        return nodeList;
    }
    public void addIntersection(IntersectNd nd){
        intersections.add(nd);
    }
    public void setIntersections(ArrayList<IntersectNd> list){
        intersections=list;
    }
    public ArrayList<IntersectNd> getIntersections(){
        return intersections;
    }
    
    // calculates distances between nodes
    // AND total distance of road
    public void buildRoads(){
        for(int i = 0;i<nodeList.size()-1;i++){
            double dist = calcDistance(nodeList.get(i),nodeList.get(i+1));
            distList.add(dist);
            distance+=dist;
        }
        
    }
    
    
    // distance=sqrt((x1-x2)^2+(y1-y2)^2)
    private double calcDistance(Nd node1, Nd node2){
        double long1=node1.getLong();
        double long2=node2.getLong(); 
        double lat1=node1.getLat(); 
        double lat2=node2.getLat();
        double distance = Math.sqrt(Math.pow((long1-long2), 2)+Math.pow(lat1-lat2,2));
        
        return distance;
    }
}

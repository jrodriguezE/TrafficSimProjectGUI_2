/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.ArrayList;

/**
 *
 * These are the nodes, Node was already reserved by another library in use. 
 */
// This is a Node, Node was a reserved word and 
// Nd is used to indicate a Node for a <way> in the original .OSM file.
// its as u see
public class Nd {
    private long ref = 0;
    private double longitude = 0;
    private double latitude = 0;
    boolean isIntersection = false;
    boolean isStop = false;
    boolean isStopLight=false;
    boolean blocked = false;
    double trafficLoad = 0; //lets measure this in seconds, average car loiter time
    double speedLimit = .00016; // 44 feet per second, 30 mph.  default
    Road parentRoad = new Road();
    Auto parentAuto;
    int numLanes=1;

    ArrayList<Nd> cars = new ArrayList<Nd>();
    ArrayList<Nd> connections = new ArrayList<Nd>();
    ArrayList<Double> weights = new ArrayList<Double>();
    ArrayList<Nd> stopQ = new ArrayList<Nd>();
 
    public void addConnection(Nd node, double weight){
        this.connections.add(node);
        this.weights.add(weight);
    }
    
    Nd(){

    }
    Nd(Nd node){
        this.ref=node.ref;
        this.latitude=node.latitude;
        this.longitude=node.longitude;
        this.connections= new ArrayList<Nd>(node.connections);
        this.weights= new ArrayList<Double>(node.weights);
    }

    public void setPos(Nd nd){
        setLong(nd.getLong());
        setLat(nd.getLat());
    }
    
    public void setRef(long ref){
        this.ref = ref;
    }
    public long getRef(){
        return ref;
    }
    public void setLong(double longitude){
        this.longitude = longitude;
    }
    public double getLong(){
        return longitude;
    }
    public void setLat(double latitude){
        this.latitude = latitude;
    }
    public double getLat(){
        return latitude;
    }    
    
    public void addCar(Nd carNode){
        cars.add(carNode);
    }
    public void removeCar(Nd carNode){
        cars.remove(carNode);
    }
    
    public double calcDistance(Nd node2){
        double long1=this.getLong();
        double long2=node2.getLong(); 
        double lat1=this.getLat(); 
        double lat2=node2.getLat();
        double distance = Math.sqrt(Math.pow((long1-long2), 2)+Math.pow(lat1-lat2,2));
        
        return distance;
    }
}
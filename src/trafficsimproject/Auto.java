/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author paleo
 */
/*
//******************************************************
//***  Class Name
//***  Class Author (i.e. Your name)
//******************************************************
//*** Purpose of the class (Why did you write this class?)
//***
//******************************************************
//*** Date
//******************************************************
//*** List of changes with dates. 
//******************************************************
//*** Look at this!
//*** List all the places in the code where you did something interesting,
//*** clever or elegant.  If you did good work in this program and you want
//*** me to consider it in your grade, point it out here.
//*******************************************************
*/
public class Auto {
    

    Directions directions = new Directions();
    
    Nd posNode = new Nd();
    Nd targetNode = new Nd();
    Nd waypointNode =  new Nd();
    Nd lastWaypointNode = new Nd();
    double distNext = 0;
    double velocity=0;
    double maxVelocity=0;
    double acceleration =.000040;
    boolean stop = false;
    static int carID=0;
    boolean accelerationOn=true;
    
    int lane =1; // remember to [lane-1]

    
    
    
//******************************************************
//***  Method Name
//***  Method Author (i.e. Your name)
//******************************************************
//*** Purpose of the Method (Why did you write this Method?)
//*** Method Inputs:
//*** List all the method parameters with their expected value ranges
//*** Return value:
//*** If this is a function list the return data type and the expected range of 
//*** values to be returned.
//******************************************************
//*** Date
//******************************************************

    Auto(){
        posNode.setRef(carID++);
        setNodeParent();
    }
    Auto(Directions dir){
        posNode.setRef(carID++);
        setDirections(dir);
        setNodeParent();
    }
    
    private void setNodeParent(){
        posNode.parentAuto=this;
    }
    
    public void setDirections(Directions directions){
        this.directions=directions;
        setPos(directions.start);
        
        //HANDLE ITERATION INSIDE THE DIRECTION CLASS!!!!!
        try{
        waypointNode = directions.start.connections.get(this.directions.next());
        waypointNode.addCar(this.posNode);
        targetNode.setPos(waypointNode);
        lastWaypointNode=posNode;
        }
        catch(Exception e){
            
        }
    }

    public void setPos(Nd node){
        posNode.setLat(node.getLat());
        posNode.setLong(node.getLong());
    }
    public void setPos(double lon, double lat){
        posNode.setLat(lat);
        posNode.setLong(lon);
    }

    public Nd getPos(){
        return posNode;
    }
    
    public void ping(){
        System.out.println("****CAR**** #"+ posNode.getRef());
        System.out.println("this.LON "+posNode.getLong());
        System.out.println("this.LAT "+posNode.getLat());
        System.out.println("distNext "+posNode.calcDistance(waypointNode));
        
//        System.out.println("NEXT.ref "+posNode.getRef());
//        System.out.println("NEXT.connects: "+posNode.connections.size());
        
//        System.out.println("next.connects REF: "+nextNode.connections.get(1).getRef());
        
        
    }

    double carSpacing =.00008;
    double stopSpacing=.00006;
    int justGo =0;
    boolean stoppedOnce=false;
    
    
   double stopOffset =0;
    
    public void calcPos(double timeIncrement){

        
        
        double newVelocity = waypointNode.speedLimit;
        if(accelerationOn){
            if(velocity<newVelocity){
                velocity=velocity+acceleration*timeIncrement;
            }else if(velocity>newVelocity){
                velocity=velocity-acceleration*timeIncrement;
            }
            
        }else{
            velocity=newVelocity;
        }
        
        double x1=posNode.getLong();
        double y1=posNode.getLat();
        double x2=targetNode.getLong();
        double y2=targetNode.getLat();
        double d = velocity*timeIncrement;
        double D = posNode.calcDistance(targetNode);





        if(waypointNode.isStop){
            if(D-stopOffset<stopSpacing){


                if(!waypointNode.stopQ.contains(this.posNode)){
                    waypointNode.stopQ.add(posNode);
                    stoppedOnce=true;
                    if(waypointNode.connections.size()>3){
                        d=0;
                        this.velocity=0;
                        
                    }

//                    System.out.println("add");
                }
                
                if(this.posNode==waypointNode.stopQ.get(0)){

                }else{

                    justGo++;
                    if(justGo>10){
                        justGo=0;
                        d=newVelocity*timeIncrement;

                    }else{
                        d=0;
                        this.velocity=0;
                    }
                }
            }
        }

        for(int i = 0;i< waypointNode.cars.size();i++){
            if(waypointNode.cars.get(i).parentAuto.lastWaypointNode==this.lastWaypointNode
                    &&waypointNode.cars.get(i)!=this.posNode
                    &&waypointNode.cars.get(i).parentAuto.lane==lane){
                double otherDist=waypointNode.cars.get(i).calcDistance(targetNode);
                if(otherDist<D){
                    double spacing = D-otherDist;
                    
                    int targLane =1;
                    if(lane==1){
                        targLane=2;
                    }else if(lane==2){
                        targLane=1;
                    }
//                    double spacing = posNode.calcDistance(waypointNode.cars.get(i));
                    if(spacing<carSpacing){
                        
                        if(isLaneClear(targLane,D)){
                            lane=targLane;
                            
                            targetNode=calcOffset(lastWaypointNode, waypointNode, waypointNode, laneDists[lane]);
                        }else{
                        
                            justGo++;
                            if(justGo>30){
                                justGo=0;
                                d=newVelocity*timeIncrement;
                                break;

                            }
//                        d=0;
//                        velocity=velocity-acceleration*3;
                            this.velocity=0;
//                        System.out.println("car distance");
                            return;
                        }
                    }

                }
            }
            else if(waypointNode.cars.get(i).parentAuto.lastWaypointNode==waypointNode
                    &&waypointNode.cars.get(i).parentAuto.waypointNode!=this.lastWaypointNode
                    &&waypointNode.cars.get(i).parentAuto.lane==lane){
                
                int targLane =1;
                if(lane==1){
                    targLane=2;
                }else if(lane==2){
                    targLane=1;
                }
                    
                if(posNode.calcDistance(waypointNode.cars.get(i))<carSpacing){
                    if(isLaneClear(targLane,D)){
                        lane=targLane;
                            
                        targetNode=calcOffset(lastWaypointNode, waypointNode, waypointNode, laneDists[lane]);
                    }else{
                        justGo++;
                        if(justGo>30){
                            justGo=0;
                            
                            d=newVelocity*timeIncrement;
                            break;

                        }
                        
//                    d=0;
//                    velocity=velocity-acceleration*3;
                        this.velocity=0;
                        return;
                    }
                }
                
            }
        }

//stopsigns

        while(d>(D-stopOffset)){

                    
            d=d-D;
            
            if(!nextWaypoint()){
                break;
            }
            D = posNode.calcDistance(targetNode);
        }
        if(d<(D-stopOffset)){
//            System.out.println("d<D");
            double x3 = -(x1-x2)*(d/D)+x1;
            double y3 = -(y1-y2)*(d/D)+y1;
//            Nd temp=calcOffset(posNode, x3, y3);
//            posNode.setLong(temp.getLong());
//            posNode.setLat(temp.getLat());
            posNode.setLat(y3);
            posNode.setLong(x3);

        }

        

    }
    
//    private int calcTurnDir(){
//        return 1;
//    }
    private int calcTurnDir(){
        int turnDir=0;
        if(directions.stepIndex<directions.directions.size()-1){
            int connectionTurn = directions.directions.get(directions.stepIndex);
            //A
            if(waypointNode.connections.size()>connectionTurn
                    &&lastWaypointNode!=null&&waypointNode!=null){
                
            
            double turn=(waypointNode.connections.get(connectionTurn).getLong()-lastWaypointNode.getLong())
                    *(waypointNode.getLat()-lastWaypointNode.getLat())
                    -(waypointNode.connections.get(connectionTurn).getLat()-lastWaypointNode.getLat())
                    *(waypointNode.getLong()-lastWaypointNode.getLong());
//            System.out.println("turn d="+turn);
            if(turn>=-.00000001&&turn<=.00000001){
            turnDir=0;    
            }else if(turn>.00000001){
                turnDir=1;
            }else if(turn<-.00000001){
                turnDir=-1;
            }
            }
        }else{

        }
        return turnDir;
    }
    
    private boolean isLaneClear(int targetLane, double D){
        boolean clear =false;
        
        if(targetLane>waypointNode.numLanes){
            return false;
        }
        
        for(int i = 0;i< waypointNode.cars.size();i++){
            if(waypointNode.cars.get(i).parentAuto.lastWaypointNode==this.lastWaypointNode
                    &&waypointNode.cars.get(i)!=this.posNode
                    &&waypointNode.cars.get(i).parentAuto.lane==targetLane){//check other lane

                if(posNode.calcDistance(waypointNode.cars.get(i))<carSpacing*2){
                    clear=false;

                        


                }
            }
            
            else if(waypointNode.cars.get(i).parentAuto.lastWaypointNode==waypointNode
                    &&waypointNode.cars.get(i).parentAuto.waypointNode!=this.lastWaypointNode
                    &&waypointNode.cars.get(i).parentAuto.lane==targetLane){
//                posNode.calcDistance(waypointNode.cars.get(i));
                if(posNode.calcDistance(waypointNode.cars.get(i))<carSpacing*2){
                    clear=false;
                }
                
            }else{
                clear=true;
            }
        }
        
        return clear;
    }
    
    //for start of leg
    private Nd calcOffset(Nd startPoint, Nd endPoint, Nd disPoint, double offSet){
            double px=startPoint.getLong()-endPoint.getLong();
            double py=startPoint.getLat()-endPoint.getLat();
        
            double nx=(-py);
            double ny= px;
            double norm = (Math.sqrt(nx*nx+ny*ny));
            nx=nx/norm;
            ny=ny/norm;
        
            Nd offNode = new Nd();
            double cx=disPoint.getLong()+offSet*nx;
            double cy=disPoint.getLat()+offSet*ny;
            offNode.setLong(cx);
            offNode.setLat(cy);
            return offNode;
        }

//    double laneDist1=.00004;
//    double laneDist2=.00012;        
    double[] laneDists={0,.00004,.00012,0002};
    
    public boolean nextWaypoint(){

        
        
        if(waypointNode.blocked){
            stop=true;
        }

        if(waypointNode.isStop){
            stoppedOnce=false;
//            System.out.println("har");
//            passedIntersection=true;
            waypointNode.stopQ.remove(this.posNode);
        }
        
        lastWaypointNode.removeCar(posNode);
        lastWaypointNode=waypointNode;

        if(!directions.inProgress()){
            waypointNode.stopQ.remove(this.posNode);
            lastWaypointNode.removeCar(this.posNode);
//            System.out.println("!inProgress()");
            waypointNode.removeCar(posNode);
            return false;            
        }

        int choice = directions.next();
        waypointNode=waypointNode.connections.get(choice);            
        waypointNode.addCar(posNode);
//        if(waypointNode.isStop==true){
//            System.out.println(calcTurnDir());
//        }
        //10/11/2018/4:55am 
        // get target..
        
        int dirInt=0;
        if(waypointNode.isStop==true){
            dirInt = calcTurnDir();
        
        }else{
            
        }
        
        if(dirInt>0){
            stopOffset=lane*.00004;
        }else if(dirInt<0){
//            stopOffset=-lane*.00004;
        }else{
            stopOffset=0;
        }
        
        if(lane==0){
            lane=1;
        }
        if(lane>waypointNode.numLanes){
            lane=waypointNode.numLanes;
        }
        if(dirInt>0&&waypointNode.numLanes>1){
            lane=waypointNode.numLanes-1;
        }if(dirInt<0){
            lane=0;
        }
        double laneDistance =laneDists[lane];
        
        targetNode.setPos(calcOffset(lastWaypointNode,waypointNode,waypointNode,laneDistance));
//        targetNode.setPos((waypointNode));


        
        
        return true;
    }
   

    
//    boolean accelSet=false;
    
    public void step(double timeStep){
        if(stop){
            velocity=0;
            return;
        }
        //CHECK IF DONE
        // this is temporary
        // write function call in Node to get coords from a node specifically
        
//        this.maxVelocity=maxVelocity;
//        if(velocity<maxVelocity){
//            velocity+=acceleration;
//            if(velocity>maxVelocity){
//                velocity=maxVelocity;
//            }
//        }
//        if(!accelSet){
//            acceleration=.00002;
//            accelSet=true;
//        }
//        
//        System.out.println("distance to waypoint: "+ posNode.calcDistance(waypointNode));
        calcPos(timeStep);
        
        //ping();
        
        
        
    }
        

    
    public double calcDistance(Nd node1, Nd node2){

        double long1=node1.getLong();
        double long2=node2.getLong(); 
        double lat1=node1.getLat(); 
        double lat2=node2.getLat();
        double distance = Math.sqrt(Math.pow((long1-long2), 2)+Math.pow(lat1-lat2,2));
        
        return distance;
    }
    
    public Nd getCurrentNd(){

        return posNode;

    }
    
    
    
    
    public void debug(){
        Scanner sc = new Scanner(System.in);
//        boolean running = true;
//        while(running){
        System.out.println("***DEBUG***");
        System.out.println(" ref: " +waypointNode.getRef());
        for(int i =0;i<waypointNode.connections.size();i++){
            System.out.println("  Connection #" + i+" "+waypointNode.connections.get(i).getRef());
           //FIND ROAD CONNECTIONS() 
        }
        String choice = sc.nextLine();
        if(choice.charAt(0)=='q'){
            System.out.println("ending debug");
//            running=false;
        }else{
            try{
            waypointNode=waypointNode.connections.get(Integer.parseInt(choice));
            posNode.setLat(waypointNode.getLat());
            posNode.setLong(waypointNode.getLong());
            }catch(Exception ex){
                
            }
        }
//        }
        return;
    }
    
    

    
    
    
}

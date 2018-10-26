/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author paleo
 */
public class Directions {
    
    Nd start;
    Nd end;
    ArrayList<Integer> directions = new ArrayList<Integer>();
    public double score;
    int stepIndex=0;
//    ArrayList<Road> roads=new ArrayList<Road>();

    
    Directions(){
        
    }
    
    Directions(Nd start, Nd end){
        this.start=start;
        this.end=end;
    }
    
    Directions(Directions dir){
        start = dir.start;
        directions= new ArrayList<Integer>(dir.directions);
    }
    
    Directions(int start){
        directions.add(start);
    }
    Directions(Nd start){
        this.start=start;
    }
    
    
    public int getNextIndex(){
//        System.out.println("stepindex="+stepIndex);
        if(stepIndex<directions.size()-1){
            
        
            return stepIndex;
        }
        return 0;
    }
    
    public boolean inProgress(){
        if (stepIndex<directions.size()){
            return true;
        }
        else return false;
    }
    
    public boolean hasStarted(){
        if(stepIndex==0){
            return false;
        }
        else return true;
    }
    

    public int next(){
        if(directions.isEmpty()){
            return 0;
        }
        return directions.get(stepIndex++);
    }
    
    public void add(int dir,double score){
        directions.add(dir);
        this.score = score;
        
    }
    
    private boolean empty = false;
    public void setEmpty(){
        empty=true;
    }
    public boolean isEmpty(){
        return empty;
    }
    
    
    double traffHeurWeight=5;
    
    public Directions findRoute(ArrayList<Road> roads, Nd start, Nd end){
        this.start=start;
        this.end=end;
        Directions route = new Directions();
        route.end=end;
        ArrayList<Directions> queue = new ArrayList<Directions>();
        ArrayList<Long> visitedRef = new ArrayList<Long>();
        ArrayList<Long> visitedRef2 = new ArrayList<Long>();
        boolean found = false;        
        boolean started = false;

        
        while(!found){
            


            Directions tempDirection= new Directions();
            tempDirection.end=end;    
                
            if(queue.size()>0){
                tempDirection = findLowest(queue);
                tempDirection.end=end;
//                queue.remove(0);
            }
            else if(queue.isEmpty()&&started==true){
//                System.out.println("NO RESULT");
                tempDirection.setEmpty();
                return tempDirection;
                
            }
                
            Nd current=start;
            for(int j=0;j<tempDirection.directions.size();j++){
                current=current.connections.get(tempDirection.directions.get(j));
                if(current.getRef()==end.getRef()){
 //                   System.out.println("FOUND ROUTE");
                    found=true;
                    return tempDirection;
                     
                }
            }
                
            int count=0;
            if(wasVisited(current.getRef(),visitedRef)||current.blocked){
                continue;
//                    if(wasVisited(current.getRef(),visitedRef2)){
//                        continue;
//                    }
//                    else{
//                        visitedRef2.add(current.getRef());
////                        System.out.println("visitedRef2");
//                    }
            }
            else{
                visitedRef.add(current.getRef());
//                System.out.println("visitedRef1");
            }
            for(int j =0;j<current.connections.size();j++){
                
                Directions temp2Direction = new Directions(tempDirection);
//                temp2Direction.add(j, current.connections.get(j).calcDistance(end));
                temp2Direction.add(j, current.connections.get(j).calcDistance(end)/.00026);

//                Double displace = current.calcDistance(current.connections.get(j));
                Double displace = current.calcDistance(current.connections.get(j))/current.connections.get(j).speedLimit;
                double trafficHeur = current.connections.get(j).cars.size();
//                System.out.println("score="+temp2Direction.score);
//                
//                System.out.println("displace="+displace);
//                System.out.println("traffic="+trafficHeur);
                temp2Direction.score=temp2Direction.score+displace+trafficHeur*(traffHeurWeight);
                temp2Direction.end=end;
                queue.add(temp2Direction);
            }          
        started=true;
        queue=sortQueue(queue);
            
        }
        route.start=start;
        route.end=end;
//        this.roads=roads;
        return route;
    }
//    
//    public Directions findRouteOLD(ArrayList<Road> roads, Nd start, Nd end){
//        Directions route = new Directions();
//        ArrayList<Directions> queue = new ArrayList<Directions>();
//        ArrayList<Long> visitedRef = new ArrayList<Long>();
//        
//        
//        boolean found = false;        
//        boolean started = false;
//
//        
//        while(!found){
//            Nd current=start;
//            
//            for(int i =0;i< current.connections.size();i++){
//
//                Directions tempDirection= new Directions();
//                
//                
//                if(queue.size()>0){
//                    tempDirection = new Directions(queue.get(0));
//                    queue.remove(0);
//                }else if(queue.size()<=0&&started==true){
//                    return tempDirection;
//                }
//                
//                //get current node
//                current=start;
//                for(int j=0;j<tempDirection.directions.size();j++){
//                    current=current.connections.get(tempDirection.directions.get(j));
//                    if(current.getRef()==end.getRef()){
//                        System.out.println("FOUND ROUTE");
//                        found=true;
//                        return tempDirection;
//                        
//                    }
//                }
//                
//                // add direction list with score to queue
//                for(int j =0;j<current.connections.size();j++){
////                    if(wasVisited(current.connections.get(j).getRef(),visitedRef)){
////                        //VISITED LIST
////                        continue;
////                    }
////                    visitedRef.add(current.connections.get(j).getRef());
//                    Directions temp2Direction = new Directions(tempDirection);
//                    temp2Direction.add(j, current.connections.get(j).calcDistance(end));
////                    System.out.println("Adding direction");
//                    System.out.println("distance to target "+current.connections.get(j).calcDistance(end)*100);
//                    queue.add(temp2Direction);
//                }          
//                
//                
//                
//            }
//            started=true;
//            queue=sortQueue(queue);
//            
//
//
//            
//            
//            
//            
//            
//            
//        }
//        
//        
//        
//        
//        
//        return route;
//    }
    

    private boolean wasVisited(long ref, ArrayList<Long> visited){
        boolean wasVisited = false;
        
        for(int i = 0;i<visited.size();i++){
            if(ref==visited.get(i)){
                wasVisited=true;
                return true;
            }
        }
        
        return wasVisited;
    }
    
    
// this needs to be tested
//https://docs.oracle.com/javase/6/docs/api/java/util/Comparator.html
    //TEST THIS OUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private ArrayList<Directions> sortQueue(ArrayList<Directions> queue){
        Collections.sort(queue, new Comparator<Directions>(){
            public int compare(Directions d1, Directions d2){
                return Double.valueOf(d1.score).compareTo(d2.score);
            }
        });
        
        return queue;
    }
    
    public int compareTo(Directions d){
        if(this.score>d.score)return 1;
        if(this.score<d.score)return -1;
        else return 0;
    }
    public int compareTo(double score){
        if(this.score>score)return 1;
        if(this.score<score)return -1;
        else return 0;
    }
    
    private Directions findLowest(ArrayList<Directions> dirs){
        if(dirs.size()<1){
            return new Directions();
        }
        Directions dir = dirs.get(0);
        int foundInd =0;
        for(int i =1; i<dirs.size();i++){
            if(dirs.get(i).score<dir.score){
                dir=dirs.get(i);
                foundInd=i;
            }
        }
        dirs.remove(foundInd);
        return dir;
    }
    
}

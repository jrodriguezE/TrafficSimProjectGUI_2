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
public class GraphBuilder {
    
    ArrayList<Road> roads = new ArrayList<Road>();
//    ArrayList<Nd> intersects = new ArrayList<Nd>();
    ArrayList<Nd> stops = new ArrayList<>();

    
        GraphBuilder(ArrayList<Road> roads){
//            System.out.println("...HEY # "+roads.size());
        this.roads=roads;
        //go through each road
        int counter =0;
        for(int i = 0;i<roads.size();i++){
            counter++;
            if(counter%100==0){
                System.out.println("...building intersections # "+counter);
            }
            //REMEMBER TO UNCOMMENT THIS
//            roads.get(i).buildLocalIntercets();
            
            
//            System.out.println("Road#"+i+" size: "+roads.get(i).nodeList.size()+" intercept size: "+ roads.get(i).intersections.size());            

            //Go through each node
            for(int j = 0; j<roads.get(i).getNodes().size();j++){
                roads.get(i).nodeList.get(j).speedLimit=roads.get(i).speed;
//                System.out.println("\tnode ref:"+roads.get(i).getNodes().get(j).getRef());
                roads.get(i).nodeList.get(j).numLanes=roads.get(i).numLanes;
            // check each node again all intersections. 
                for(int k=0;k<roads.get(i).getIntersections().size();k++){

                    //9/29/2018 4:15AM TODO:
                    // intersection found.... make a new node to connect them here
                    // and make a list of intersection nodes that we can grab from
                    // here. this list will be for handling stops. 
                    
                   if(roads.get(i).nodeList.get(j).getRef()==roads.get(i).getIntersections().get(k).getRef()){
                       //MUST FIND AND LINK ALL instance of this intersect across all roads
//                       Nd tempNode = findNode(roads.get(i).nodeList.get(j).getRef(),i,j);
                       ArrayList<Indexes> foundIJs = findNodeIndex(roads.get(i).nodeList.get(j).getRef(),i,j);
//                       if(tempNode==roads.get(i).nodeList.get(j)){
//                           continue;
//                       }
                       
                       
                       for(int l =0; l<foundIJs.size();l++){
//                           System.out.println("foundIJs.size()"+foundIJs.size());
                            makeIntersectNode(i,j, foundIJs.get(l));
                           
                       }
                       


//                       roads.get(i).nodeList.get(j).addConnection(tempNode, 0);
//                       if(!tempNode.connections.contains(roads.get(i).nodeList.get(j))){
//                           tempNode.addConnection(roads.get(i).nodeList.get(j), 0);
//                       }
////                       System.out.println("IM WORKING******");
//                       
                   }
                }
                
            }
            roads.get(i).buildLocalIntercets();
        }
        fixIntersections();
        
    }
        
    private void fixIntersections(){
       
        for(int i=0;i<roads.size();i++){
            if(i%100==0){
                System.out.println("fixing Intersection "+i);
            }
            if(roads.get(i).numLanes==2){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).numLanes=2;
                }

            }else if(roads.get(i).numLanes==3){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).numLanes=3;
                }
            }
            
            if(roads.get(i).speed==.0001){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).speedLimit=.0001;
                }
            }else if(roads.get(i).speed==.00016){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).speedLimit=.00016;
                }
            }else if(roads.get(i).speed==.00022){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).speedLimit=.00022;
                }
            }else if(roads.get(i).speed==.00027){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).speedLimit=.00027;
                }
            }else if(roads.get(i).speed==.0004){
                for(int j=0;j<roads.get(i).nodeList.size();j++){
                    roads.get(i).nodeList.get(j).speedLimit=.0004;
                }
            }
            
        }
    }

    private void makeIntersectNode(int i, int j, Indexes fIJ){
        
    
        if(roads.get(i).nodeList.get(j)==roads.get(fIJ.i).nodeList.get(fIJ.j)){
            return;
        }
        roads.get(i).nodeList.get(j).isStop=true;
        //following line was unneeded 9/30/2018 MAKE SURE
//        roads.get(i).nodeList.get(j).connections.add(roads.get(fIJ.i).nodeList.get(fIJ.j));
        if(roads.get(fIJ.i).nodeList.get(fIJ.j).parentRoad.numLanes>roads.get(i).nodeList.get(j).parentRoad.numLanes){
            roads.get(i).nodeList.get(j).numLanes=roads.get(fIJ.i).nodeList.get(fIJ.j).parentRoad.numLanes;
        }
        roads.get(fIJ.i).nodeList.remove(fIJ.j);
        roads.get(fIJ.i).nodeList.add(fIJ.j, roads.get(i).nodeList.get(j));

        
    }
    

    //struct
    private class Indexes{
        int i =0;
        int j =0;
    }
    
//    private Indexes findNodeIndexOLD(long ref,int roadsIndex, int interIndex){
//        Indexes indexes = new Indexes();
//        Nd temp = new Nd();
//        for(int i = 0; i<roads.size();i++){
//            for(int j =0; j<roads.get(i).nodeList.size();j++){
//                if(i==roadsIndex&&j==interIndex){
//                    continue;
//                }
//                if(roads.get(i).nodeList.get(j).getRef()==ref){
//                    temp = roads.get(i).nodeList.get(j);
//                    indexes.i=i;
//                    indexes.j=j;
//                    
//
//                }
//            }
//        }
//        
//        return indexes;
//    }
    
    private ArrayList<Indexes> findNodeIndex(long ref,int roadsIndex, int interIndex){
        ArrayList<Indexes> indexes = new ArrayList();
        Nd temp = new Nd();
        for(int i = 0; i<roads.size();i++){
            for(int j =0; j<roads.get(i).nodeList.size();j++){
                if(i==roadsIndex&&j==interIndex){
                    continue;
                }
                if(roads.get(i).nodeList.get(j).getRef()==ref){
                    temp = roads.get(i).nodeList.get(j);
                    Indexes index = new Indexes();
                    index.i=i;
                    index.j=j;
                    indexes.add(index);
                    

                }
            }
        }
        
        return indexes;
    }


    
    private Nd findNode(long ref,int roadsIndex, int interIndex){
        Nd temp = new Nd();
        for(int i = 0; i<roads.size();i++){
            for(int j =0; j<roads.get(i).nodeList.size();j++){
                if(i==roadsIndex&&j==interIndex){
                    continue;
                }
                if(roads.get(i).nodeList.get(j).getRef()==ref){
                    temp = roads.get(i).nodeList.get(j);
                    

                }
            }
        }
        
        return temp;
    }
    

    
//    public void buildStops(){
//        for(int i=0;i<roads.size();i++){
//            for(int j=0;j<roads.get(i).nodeList.size();j++){
//                
//                if(roads.get(i).nodeList.get(j).connections.size()>2){
//                    stops.add(roads.get(i).nodeList.get(j));
////                    System.out.println("found stop, size="+roads.get(i).nodeList.get(j).connections.size());
//                }
//            }
//        }
//    }
//    
//    public ArrayList<Nd> getStops(){
//        return stops;
//    }
    
    public ArrayList<Road> getRoads(){
        return roads;
    }
    
    public Nd findNode(double x, double y){
        Nd output=new Nd();
//        System.out.println("x="+x+"     y="+y);
     
        for(int i=0;i<roads.size();i++){
 
            for(int j=0;j<roads.get(i).nodeList.size();j++){

                if(roads.get(i).nodeList.get(j).getLat()<y+.0002
                        &&roads.get(i).nodeList.get(j).getLat()>y-.0002
                        &&roads.get(i).nodeList.get(j).getLong()>x-.0002
                        &&roads.get(i).nodeList.get(j).getLong()<x+.0002){
                    output=roads.get(i).nodeList.get(j);
//                    System.out.println("FOUND IT");
//                    System.out.println("lon="+output.getLong()+" lat="+output.getLat());
                    return output;
                }
            }
        }
        
        return output;
    }
}

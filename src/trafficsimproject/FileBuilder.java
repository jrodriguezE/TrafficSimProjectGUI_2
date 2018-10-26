/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author paleo
 */
public class FileBuilder {
    ArrayList<RoadBuilder> rds = new ArrayList<RoadBuilder>();
    ArrayList<Road> roads = new ArrayList();
    BoundBuilder boundBuilder = new BoundBuilder();
    ArrayList<Directions> dirs = new ArrayList();
    ArrayList<DirectionBuilder> dirB = new ArrayList();
    FileBuilder(){
        
    }
    //
    
    public void buildDirectionBuilders(ArrayList<Directions> dirs){
        dirB = new ArrayList();
        for(int i =0;i<dirs.size();i++){
            DirectionBuilder tempB = new DirectionBuilder();
            
            tempB.directions=(dirs.get(i).directions);
            NdBuilder tempStart = new NdBuilder();
            tempStart.ref=dirs.get(i).start.getRef();
//            System.out.println("start ref "+tempStart.ref);
//            System.out.println("getRef "+dirs.get(i).end.getRef());
            NdBuilder tempEnd = new NdBuilder();
            tempEnd.ref=dirs.get(i).end.getRef();
            tempB.start=tempStart;
            tempB.end=tempEnd;
            dirB.add(tempB);
        }
    }
    
    public ArrayList<Directions> buildDirections(){
        dirs=new ArrayList();
//                    System.out.println("buldDirections "+ dirB.size());
        for(int i=0;i<dirB.size();i++){
//                        System.out.println("buldDirections1");
            Directions tempDir = new Directions();
//                        System.out.println("buldDirections2");
//                        System.out.println("size DirB "+dirB.get(i).start.ref);
            tempDir.start=(findNd(dirB.get(i).start.ref));
//                        System.out.println("buldDirections3");
            tempDir.end=(findNd(dirB.get(i).end.ref));
//                        System.out.println("buldDirections4");
            tempDir.directions=dirB.get(i).directions;
//            System.out.println("buldDirections5");
//            tempDir.start=dirB.get(i).start;
            dirs.add(tempDir);
        }
        
        
        
        return dirs;
    }
    
    void writeDirectionsFile(String fName){
        try {
            FileOutputStream f = new FileOutputStream(new File(fName));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(dirB);
            System.out.println("size DIRB "+dirB.size());
//            for(int i =0;i<dirB.size();i++){
//                System.out.println("direction #"+i);
//                for(int j=0;j<dirB.get(i).directions.size();j++){
//                    System.out.println(" "+dirB.get(i).directions.get(j));
//                }
//            }
            o.close();
            f.close();
        }
        catch (Exception e) {
//            System.out.println("WRITE1");
	} 

        System.out.println("DONE WRITING DIRECTIONS FILE");
    }   
    
    ArrayList<Directions> readDirectionsFile(String fName){
        this.dirs= new ArrayList<Directions>();
        try {
            
            FileInputStream fi = new FileInputStream(new File(fName));
            ObjectInputStream oi = new ObjectInputStream(fi); 
                        
            this.dirB= ((ArrayList<DirectionBuilder>) oi.readObject());
//                        System.out.println("SIZE rds"+rds.size());
            this.dirs=buildDirections();
//            System.out.println("SIZE roads"+roads.size());
            oi.close();
            fi.close();
            

        }
        catch (Exception e) {
            System.out.println("READING1 "+e.getMessage()+" "+e.getLocalizedMessage());
	}
        
        return dirs;
    }

    
    private Nd findNd(long ref){
//        System.out.println("findND");
        Nd temp = new Nd();
        for(int i =0;i<roads.size();i++){
            for(int j=0;j<roads.get(i).nodeList.size();j++){
                if(roads.get(i).nodeList.get(j).getRef()==ref){
                    temp= roads.get(i).nodeList.get(j);
                }
            }
        }
        return temp;
    }
    
    
    
    
    
    
    public void buildBounds(double minLat, double maxLat, double minLon, double maxLon){
        boundBuilder.maxLat=maxLat;
        boundBuilder.minLat=minLat;
        boundBuilder.maxLon=maxLon;
        boundBuilder.minLon=minLon;
        
    }
//    int ada=0;
    public ArrayList<RoadBuilder> buildRoadBuilders(ArrayList<Road> roads){
        rds = new ArrayList<RoadBuilder>();
        for(int i =0;i<roads.size();i++){
//            System.out.println("hello "+ada);
            RoadBuilder tempRd=new RoadBuilder();
            tempRd.id=roads.get(i).getID();
            tempRd.speed=roads.get(i).speed;
            tempRd.oneWay=roads.get(i).oneWay;
            tempRd.numLanes=roads.get(i).numLanes;
            for(int j=0;j<roads.get(i).intersections.size();j++){
                NdBuilder tmpNd = new NdBuilder();
                tmpNd.isIntersection=roads.get(i).intersections.get(j).isIntersection;
                tmpNd.ref=roads.get(i).intersections.get(j).getRef();
                tmpNd.isStop=roads.get(i).intersections.get(j).isStop;
                tmpNd.isStopLight=roads.get(i).intersections.get(j).isStopLight;
                tmpNd.latitude=roads.get(i).intersections.get(j).getLat();
                tmpNd.longitude=roads.get(i).intersections.get(j).getLong();
                tmpNd.speedLimit=roads.get(i).intersections.get(j).speedLimit;
                
                tempRd.intersections.add(tmpNd);
                

            }
            for(int j=0;j<roads.get(i).nodeList.size();j++){
                NdBuilder tmpNd = new NdBuilder();
                tmpNd.isIntersection=roads.get(i).nodeList.get(j).isIntersection;
                tmpNd.ref=roads.get(i).nodeList.get(j).getRef();
                tmpNd.isStop=roads.get(i).nodeList.get(j).isStop;
                tmpNd.isStopLight=roads.get(i).nodeList.get(j).isStopLight;
                tmpNd.latitude=roads.get(i).nodeList.get(j).getLat();
                tmpNd.longitude=roads.get(i).nodeList.get(j).getLong();
                tmpNd.speedLimit=roads.get(i).nodeList.get(j).speedLimit;
                tempRd.nodeList.add(tmpNd);                
                
                
            }
            rds.add(tempRd);
        }
        
        
//        System.out.println("RDS SIZE"+rds.size());
        return rds;
    }
    
    public ArrayList<Road> buildRoads(ArrayList<RoadBuilder> rds){
//        System.out.println("rds size"+rds.size());
        roads = new ArrayList();
        for(int i =0; i<rds.size();i++){
//            System.out.println("BUILDING ROADS"+rds.size());
            Road tempRoad = new Road();
            tempRoad.oneWay=rds.get(i).oneWay;
            tempRoad.speed=rds.get(i).speed;
            tempRoad.numLanes=rds.get(i).numLanes;
//                        System.out.println("BUILDING ROADS2"+rds.size());
            for(int j=0;j<rds.get(i).intersections.size();j++){
//                                       System.out.println("BUILDING ROADS 3 "+rds.size());
                IntersectNd iNd = new IntersectNd();
                iNd.isStopLight=rds.get(i).intersections.get(j).isStopLight;
                iNd.speedLimit=rds.get(i).intersections.get(j).speedLimit;
                iNd.setRef(rds.get(i).intersections.get(j).ref);
                iNd.setLat(rds.get(i).intersections.get(j).latitude);
                iNd.setLong(rds.get(i).intersections.get(j).longitude);
                iNd.isIntersection=rds.get(i).intersections.get(j).isIntersection;
                iNd.parentRoad=tempRoad;
                tempRoad.intersections.add(iNd);
                                
            }
            for(int j=0;j<rds.get(i).nodeList.size();j++){
//                                       System.out.println("BUILDING ROADS 3_2 "+rds.size());
                Nd tmpNd = new Nd();
                tmpNd.isIntersection=rds.get(i).nodeList.get(j).isIntersection;
                tmpNd.setRef(rds.get(i).nodeList.get(j).ref);
                tmpNd.isStop=rds.get(i).nodeList.get(j).isStop;
                tmpNd.isStopLight=rds.get(i).nodeList.get(j).isStopLight;
                tmpNd.setLat(rds.get(i).nodeList.get(j).latitude);
                tmpNd.setLong(rds.get(i).nodeList.get(j).longitude);
                tmpNd.speedLimit=rds.get(i).nodeList.get(j).speedLimit;
                tmpNd.parentRoad=tempRoad;
                tmpNd.numLanes=rds.get(i).numLanes;
                tempRoad.nodeList.add(tmpNd);   
            }
            roads.add(tempRoad);
        }

        
        return roads;
    }
    
    void writeFile(String fName){
        try {
            FileOutputStream f = new FileOutputStream(new File(fName));
            ObjectOutputStream o = new ObjectOutputStream(f);
            
            o.writeObject(rds);

            o.close();
            f.close();
        }
        catch (Exception e) {
//            System.out.println("WRITE1");
	} 
        try {
            FileOutputStream f = new FileOutputStream(new File(fName+"BOUNDS"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(boundBuilder);

            
            o.close();
            f.close();
        }
        catch (Exception e) {
            System.out.println("WRITE2");
	} 
        System.out.println("DONE WRITING FILE");
    }        
    
    ArrayList<Road> readFile(String fName){
        this.roads= new ArrayList<Road>();
        try {
            
            FileInputStream fi = new FileInputStream(new File(fName));
            ObjectInputStream oi = new ObjectInputStream(fi); 
                        
            this.rds= ((ArrayList<RoadBuilder>) oi.readObject());
//                        System.out.println("SIZE rds"+rds.size());
            roads=buildRoads(rds);
//            System.out.println("SIZE roads"+roads.size());
            oi.close();
            fi.close();

        }
        catch (Exception e) {
            System.out.println("READING1 "+e.getMessage()+" "+e.getLocalizedMessage());
	}
        try {
            
//            System.out.println("GAGASFSA1");            
            
            FileInputStream fi = new FileInputStream(new File(fName+"BOUNDS"));

            
            ObjectInputStream oi = new ObjectInputStream(fi);
//            System.out.println("GAGASFSA2");
                        
            boundBuilder= (BoundBuilder) oi.readObject();

//            System.out.println("GAGASFSA3");
            oi.close();
            fi.close();

        }
        catch (Exception e) {
            System.out.println("READING2"+e.getMessage());
            
	}
        
        return roads;
    }
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

// get rid of these when done
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;

/**
 *
 * @author paleo
 */
public class Simulation implements Runnable{
    GraphBuilder gb;
//    private ArrayList<Road> roads = new ArrayList<Road>();
    
    //TODO implement this cars
    
    int tickCounter =0;
    private ArrayList<Auto> cars = new ArrayList<Auto>();
    
    
    Renderer display;
    
    // TODO: localize the scope of these variables
    public double minLat=0;
    public double minLon=0;
    public double maxLon=0;
    public double maxLat =0;
    boolean run=true;
    //you'll have to use openMap() yourself
    Simulation(){
        
    }
    
    //constructor to start the object up while loading a file
    Simulation(String fileName){
        openMap(fileName);
    }
    
    //opens map
    public void openMap(String fileName){
        MapReader reader = new MapReader(fileName);
        GraphBuilder gb = new GraphBuilder(reader.getRoads());
//        StopBuilder sb = new StopBuilder(gb.roads);
//        roads=gb.roads;
        this.gb=gb;
        minLat=reader.minLat;
        minLon=reader.minLon;
        maxLon=reader.maxLon;
        maxLat =reader.maxLat;
//        saveMap();
        
    }
    
    
    public void openPro(String fName){
//        MapReader reader = new MapReader(fileName);
        FileBuilder fb = new FileBuilder();
        fb.readFile(fName);


        GraphBuilder graphb = new GraphBuilder(fb.roads);
//        StopBuilder sb = new StopBuilder(gb.roads);
//        roads=gb.roads;
        this.gb=graphb;
        this.minLat=fb.boundBuilder.minLat;
        this.maxLat=fb.boundBuilder.maxLat;
        this.minLon=fb.boundBuilder.minLon;
        this.maxLon=fb.boundBuilder.maxLon;
 
        loadCars(fb.readDirectionsFile("mapDIR"));
        
        
//        saveMap();
        
    }
    
    
    public void saveMap(){
        String fName ="./src/roads.obj";
        try{
            FileOutputStream fileOut = new FileOutputStream(fName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gb.roads);
            System.out.println("file saved");
        }catch(Exception e){
            System.out.println("system out oops*******");
        }
    }
    
    public void loadMap(){
        String fName ="./src/roads.obj";
        try{
            FileInputStream fileIn = new FileInputStream(fName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            gb=(GraphBuilder)objectIn.readObject();
            
            
        }catch(Exception e){
            System.out.println(e.getMessage());            
        }
    }
    
    // to start renderer
    public void startRenderer(double scale){
        this.display = new Renderer(scale, gb.roads,minLat,maxLat,minLon,maxLon);
        display.setMap();
//        display.reSizeWindow();
        
        
    }
    
    public void setScale(double sca){
        display.setScale(sca);
    }
    

    // return this shit as a string
    public void getSimInfo(){
        for(int i = 0;i<gb.roads.size();i++){
            System.out.println("road#: "+i);
            System.out.println("intercepts: "+ gb.roads.get(i).intersections.size());
            for(int j = 0;j<gb.roads.get(i).intersections.size();j++){
                System.out.println("intersept ref:\t"+gb.roads.get(i).intersections.get(j).getRef());
            }
            for(int j = 0; j<gb.roads.get(i).nodeList.size();j++){
                System.out.println( "\tnode ref:\t"+gb.roads.get(i).nodeList.get(j).getRef());
                System.out.println( "\tconnections:"+gb.roads.get(i).nodeList.get(j).connections.size());
                
            }
        }
        System.out.println("end"+gb.roads.size());
    }
    
    
    public void setCars(int totalCars){
        Random rand = new Random();
        int routeCounter=0;
        for(int i = 0;i<totalCars;i++){
//            boolean repeat = true;
//
//
//            int roadNum1=0; 
//            int nodeNum1=0; 
//            int roadNum2=0; 
//            int nodeNum2=0;
            Directions route = new Directions();
//                    =makeDirection();
            
            
            try{
                route=(buffer.blockingGet());
                    
            }catch(Exception ex){
                    
            }
            
            
            
            routeCounter++;
            if(routeCounter%100==0){
                System.out.println("...route # "+routeCounter);
            }
//           
//            while(repeat){
//                roadNum1 = rand.nextInt(roads.size());
//                nodeNum1 = rand.nextInt(roads.get(roadNum1).nodeList.size());
//                roadNum2 = rand.nextInt(roads.size());
//                nodeNum2 = rand.nextInt(roads.get(roadNum2).nodeList.size());
//                route = new Directions(roads.get(roadNum1).nodeList.get(nodeNum1));
//                route = route.findRoute(roads, roads.get(roadNum1).nodeList.get(nodeNum1), roads.get(roadNum2).nodeList.get(nodeNum2));
//    
//                if(!route.isEmpty()){
//                    repeat=false;
//                }
//            }
  
            

            
//            route.start = roads.get(roadNum1).nodeList.get(nodeNum1);
            
            Auto tempCar = new Auto();
            tempCar.setDirections(route);
            cars.add(tempCar);
            
        }
//        System.out.println("Cars Size="+cars.size());

    }
    


    public Directions makeDirection(){

        boolean repeat = true;
        Random rand = new Random();
        Directions route = new Directions();
        while(repeat){
            int roadNum1 = rand.nextInt(gb.roads.size());
            int nodeNum1 = rand.nextInt(gb.roads.get(roadNum1).nodeList.size());
            int roadNum2 = rand.nextInt(gb.roads.size());
            int nodeNum2 = rand.nextInt(gb.roads.get(roadNum2).nodeList.size());
            if(gb.roads.get(roadNum1).nodeList.get(nodeNum1).calcDistance(gb.roads.get(roadNum2).nodeList.get(nodeNum2))<(maxLon-minLon)/4){
//                System.out.println("continue="+(maxLon-minLon)/4);
                continue;
            }
            route = new Directions(gb.roads.get(roadNum1).nodeList.get(nodeNum1));
            route = route.findRoute(gb.roads, gb.roads.get(roadNum1).nodeList.get(nodeNum1), gb.roads.get(roadNum2).nodeList.get(nodeNum2));
            route.start = gb.roads.get(roadNum1).nodeList.get(nodeNum1);
            
            if(!route.isEmpty()){
                repeat=false;
            }
        }
        
        return route;
        
    }
    
    
    // this is broke... way points are deleted after trip complete.. maybe not necissary. 
    //revisit auto updatepos 
    public Directions makeDirection(Nd start){
        boolean repeat = true;
        Random rand = new Random();
        Directions route = new Directions();
        while(repeat){
            int roadNum2 = rand.nextInt(gb.roads.size());
            int nodeNum2 = rand.nextInt(gb.roads.get(roadNum2).nodeList.size());
            route = new Directions(start);
            route = route.findRoute(gb.roads, start, gb.roads.get(roadNum2).nodeList.get(nodeNum2));
            route.start = start;
            
            if(!route.isEmpty()){
                repeat=false;
            }
        }
        
        return route;
        
    }
    
   // 10/2/2018 -ww 
   // make a function to set this on its own. simulator should keep track of all
   // this shit on its own and not rely on main()
   // should be able to modify these options from GUI 
    
    private void updateClock(){
       
        String curTime=("clock: "+String.format("%02d", clock.hour)+":"+String.format("%02d", clock.min)+":"+String.format("%02d", (int)clock.second));
        display.timeLabel.setText(curTime);
        clock.minAlarm=false;
//          
    }
    
    double stepSize =0;
    SimClock clock = new SimClock();
    boolean fastForward = false;
    double ffstep=3600;
    int routesCompleted=0;
//    double fpsFrames=0;
    
    
    public void step(double velocity, double stepSize){       
//        System.out.println(System.currentTimeMillis());

        tickCounter++;
//        if(0==tickCounter%3600){
//            System.out.println("tick # "+tickCounter);
//        }   
        

        
        if(display.playSpeed!=1){
            if(display.playSpeed==0){
                return;
            }else if(display.playSpeed>1){
               stepSize=stepSize*(display.playSpeed)*(display.playSpeed);
            }
        }
        
        if(fastForward){
            
//            if(clock.targetHour-clock.hour<2){
//                stepSize=64;
//            }

            if(clock.targetFound){
//                System.out.println("clock.targetFound");
                display.playSpeed=2;
                clock.targetFound=false;
                fastForward=false;
                
                //

            }else{
                stepSize=ffstep;
            }
            
            
        }
        clock.stepSec(stepSize);
        updateClock();
//        if(clock.minAlarm){
////            fpsFrames=System.currentTimeMillis()-fpsFrames;
//            updateClock();
////            System.out.println("fps"+fpsFrames/tickCounter);
////            tickCounter=0;
//        }
        
        if(display.timeFrame!=null){
            if(display.timeFrame.changeTime==true){
                int steps=clock.stepsTill(stepSize, display.timeFrame.hour, 0);
                System.out.println("secs to new time"+steps/stepSize);
                clock.setTarget(display.timeFrame.hour);
                fastForward=true;
//                clock.stepSec(steps*stepSize);
//                stepSize*=steps;
//                updateClock();
                display.timeFrame.changeTime=false;
            }
        }
        
        
        if(display.carSearchPlease){
            Auto searchResult = findAuto(display.mouseLong,display.mouseLat);
            display.carsPanel.auto=searchResult;
            display.carSearchPlease=false;
            display.carsPanel.setFields();
            
            
        }
        else if(display.carButtonOn){
            display.carsPanel.setFields();
        }
        if(display.nodeSearchPlease){
            Nd searchResult = gb.findNode(display.mouseLong, display.mouseLat);
//            System.out.println("ndPlease");
            display.nodeInfoFrame.node=searchResult;
            display.nodeSearchPlease=false;
            display.nodeInfoFrame.setFields();
            display.nodeInfoFrame.setFieldsOnce();
//            display.nodeInfoFrame.repaint();
            
        }
        else if(display.nodeButtonOn){
            display.nodeInfoFrame.setFields();
        }
        if(display.roadSearchPlease){
            Nd searchResult = gb.findNode(display.mouseLong, display.mouseLat);
//            System.out.println("rdPlease"+searchResult.getRef());
            display.roadFrame.setRoad(searchResult);
            display.roadSearchPlease=false;
            display.roadFrame.setFields();;
//            display.nodeInfoFrame.repaint();
            
        }
        else if(display.roadButtonOn&&display.roadFrame.road!=null){
            display.roadFrame.setFields();
        }
        
        this.stepSize=stepSize;

        for(int j=0;j<cars.size();j++){
//            if(checkStop(j)){
//                continue;
//            }
//            if(cars.get(j).waypointNode.blocked){
//                Directions tempDir = new Directions();
//                cars.get(j).setDirections(tempDir.findRoute(gb.roads, cars.get(j).waypointNode, cars.get(j).lastWaypointNode));
//                
//            }

            cars.get(j).step(stepSize);
            if(!cars.get(j).directions.inProgress()){
                routesCompleted++;
                display.totalCar.setText("    routes completed="+routesCompleted);
                int flowRate = (int)((routesCompleted/((tickCounter*stepSize)))*3600);
                display.flowRate.setText("    rate="+flowRate+"/hour    "
                +flowRate*24+"/day");

                cars.get(j).waypointNode.stopQ.remove(cars.get(j).posNode);
                
                cars.get(j).waypointNode.removeCar(cars.get(j).posNode);
                cars.get(j).lastWaypointNode.removeCar(cars.get(j).posNode);
//                cars.get(j).setDirections(makeDirection());
                try{
                    cars.get(j).setDirections(buffer.blockingGet());
                    
                }catch(Exception ex){
                    
                }
                
//                cars.get(j).setDirections(makeDirection(cars.get(j).waypointNode));
 //               System.out.println("makeDirection()");
            }        
        }
    

        
    }
    
    private boolean checkStop(int carIndex){
         if(cars.get(carIndex).stop==true){
            return true;
        }
        return false;
    }
    
    public void updateRenderer(){
        display.stepCars(cars);
    }
    
    private Auto findAuto(double x, double y){
        Auto output=new Auto();
//        System.out.println("x="+x+"     y="+y);
     
        for(int i=0;i<cars.size();i++){
            if(cars.get(i).posNode.getLat()<y+.0002
                    &&cars.get(i).posNode.getLat()>y-.0002
                    &&cars.get(i).posNode.getLong()>x-.0002
                    &&cars.get(i).posNode.getLong()<x+.0002){
                
                output=cars.get(i);
//                    System.out.println("FOUND IT");
//                    System.out.println("lon="+output.getLong()+" lat="+output.getLat());
                return output;
            }
        }
        
        
        return output;
    
    }
    
    
    
    public ArrayList<Directions> saveCars(int totalCars){
        ArrayList<Directions> directions = new ArrayList();
        Random rand = new Random();
        int routeCounter=0;
        for(int i = 0;i<totalCars;i++){
//            boolean repeat = true;
//
//
//            int roadNum1=0; 
//            int nodeNum1=0; 
//            int roadNum2=0; 
//            int nodeNum2=0;
            Directions route = new Directions();
//                    =makeDirection();
            
            
            try{
                route=(buffer.blockingGet());
                    
            }catch(Exception ex){
                    
            }
            
            
            
            routeCounter++;
            if(routeCounter%100==0){
                System.out.println("...route # "+routeCounter);
            }
//           
//            while(repeat){
//                roadNum1 = rand.nextInt(roads.size());
//                nodeNum1 = rand.nextInt(roads.get(roadNum1).nodeList.size());
//                roadNum2 = rand.nextInt(roads.size());
//                nodeNum2 = rand.nextInt(roads.get(roadNum2).nodeList.size());
//                route = new Directions(roads.get(roadNum1).nodeList.get(nodeNum1));
//                route = route.findRoute(roads, roads.get(roadNum1).nodeList.get(nodeNum1), roads.get(roadNum2).nodeList.get(nodeNum2));
//    
//                if(!route.isEmpty()){
//                    repeat=false;
//                }
//            }
  
            

            
//            route.start = roads.get(roadNum1).nodeList.get(nodeNum1);
            
            directions.add(route);
//            tempCar.setDirections(route);
            
        }
//        System.out.println("Cars Size="+cars.size());
        return directions;


    }
    
    
    public void loadCars(ArrayList<Directions> directions){
        for(int i = 0;i<directions.size();i++){
//            boolean repeat = true;
//
//
//            int roadNum1=0; 
//            int nodeNum1=0; 
//            int roadNum2=0; 
//            int nodeNum2=0;
            
            
            int routeCounter=0;
            routeCounter++;
            if(routeCounter%100==0){
                System.out.println("...route # "+routeCounter);
            }
            Auto tempCar = new Auto();
            tempCar.setDirections(directions.get(i));
            cars.add(tempCar);
            
        }
//        System.out.println("Cars Size="+cars.size());

    }
    
    RouteBuffer buffer = new RouteBuffer();
    public void setBuffer(RouteBuffer buffer){
        this.buffer=buffer;
    }

    
    boolean saveCars=false;
    boolean loadCarFile=false;
    @Override
    public void run() {
//        System.out.println("RUN");
//        setCars(carTotal);
        if(saveCars){
            
            FileBuilder fb = new FileBuilder();
            fb.buildDirectionBuilders(saveCars(carTotal));
            fb.writeDirectionsFile("mapDIR");
        }else if(loadCarFile){
//            FileBuilder fb = new FileBuilder();
//            loadCars(fb.readDirectionsFile("mapDIR"));
            
            
            
        }else{
            setCars(carTotal);
        }
        
        while(run){
//            System.out.println("RUN");
            try{
                step(0.00016, .022);
                if(display.saveRoute){
            
                    FileBuilder fb = new FileBuilder();
                    fb.buildDirectionBuilders(saveCars(carTotal));
                    fb.writeDirectionsFile("mapDIR");
                    display.saveRoute=false;
                }   
                if(display.saveMap){
            
                    FileBuilder fb = new FileBuilder();
                    fb.buildRoadBuilders(gb.roads);
                    fb.boundBuilder.minLat=minLat;
                    fb.boundBuilder.maxLat=maxLat;
                    fb.boundBuilder.minLon=minLon;
                    fb.boundBuilder.maxLon=maxLon;
        
                    fb.writeFile("mapEDIT.pro");
                    display.saveMap=false;
                }
                Thread.sleep(20);
                updateRenderer();
            }catch(Exception ex){
                
            }
        }
    }
    int carTotal=500;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author paleo
 */

// Just being used to test stuff right now. 
public class TrafficSimProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        threadTest();
//        fileSave();
        fileLoad();
//        fileDirectionsSave();
    }
    
    static void fileDirectionsSave(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Simulation sim = new Simulation();

//        sim.openMap("./src/MediumMap.osm");
//        sim.startRenderer(2);
//        sim.carTotal=500;
        FileBuilder fb = new FileBuilder();
//        fb.buildRoadBuilders(sim.gb.roads);
//        fb.boundBuilder.minLat=sim.minLat;
//        fb.boundBuilder.maxLat=sim.maxLat;
//        fb.boundBuilder.minLon=sim.minLon;
//        fb.boundBuilder.maxLon=sim.maxLon;
//        
//        fb.writeFile("map.pro");
        sim.saveCars=true;
        sim.openPro("KilleenSection.oms");

        sim.startRenderer(2);
        sim.carTotal=500;

//        
        RouteBuffer buffer = new RouteBuffer();
        sim.setBuffer(buffer);
        RouteGenerator routeGen = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen2 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen3 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen4 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen5 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen6 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen7 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        
        executorService.execute(routeGen);
        executorService.execute(routeGen2);
        executorService.execute(routeGen3);
        executorService.execute(routeGen4);
        executorService.execute(routeGen5);
        executorService.execute(routeGen6);
        executorService.execute(routeGen7);

//
        executorService.execute(sim);

        executorService.shutdown();
//
//        
    }
    
    static void fileSave(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Simulation sim = new Simulation();

        sim.openMap("./src/class.osm");
//        sim.startRenderer(2);
//        sim.carTotal=500;
        FileBuilder fb = new FileBuilder();
        fb.buildRoadBuilders(sim.gb.roads);
        fb.boundBuilder.minLat=sim.minLat;
        fb.boundBuilder.maxLat=sim.maxLat;
        fb.boundBuilder.minLon=sim.minLon;
        fb.boundBuilder.maxLon=sim.maxLon;
        
        fb.writeFile("map.pro");
//        sim.openPro("map.pro");
//
//        sim.startRenderer(2);
//        sim.carTotal=2500;

        
//        RouteBuffer buffer = new RouteBuffer();
//        sim.setBuffer(buffer);
//        RouteGenerator routeGen = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        RouteGenerator routeGen2 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        RouteGenerator routeGen3 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        RouteGenerator routeGen4 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        RouteGenerator routeGen5 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        RouteGenerator routeGen6 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        RouteGenerator routeGen7 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
//        
//        executorService.execute(routeGen);
//        executorService.execute(routeGen2);
//        executorService.execute(routeGen3);
//        executorService.execute(routeGen4);
//        executorService.execute(routeGen5);
//        executorService.execute(routeGen6);
//        executorService.execute(routeGen7);
////        sim.setCars(500); // 200 is the amount of cars 
//
//        executorService.execute(sim);
//
//        executorService.shutdown();

        
    }    
    
    static void fileLoad(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Simulation sim = new Simulation();

//        sim.openMap("./src/MediumMap.osm");
//        sim.startRenderer(2);
//        sim.carTotal=500;
        FileBuilder fb = new FileBuilder();
//        fb.buildRoadBuilders(sim.gb.roads);
//        fb.boundBuilder.minLat=sim.minLat;
//        fb.boundBuilder.maxLat=sim.maxLat;
//        fb.boundBuilder.minLon=sim.minLon;
//        fb.boundBuilder.maxLon=sim.maxLon;
//        
//        fb.writeFile("map.pro");
        sim.openPro("map.pro");
        sim.carTotal=5000;
        sim.startRenderer(2);
        sim.loadCarFile=true;
        
        
//        sim.loadCars(fb.readDirectionsFile("mapDIR"));
            
            
            
        
        


        
        RouteBuffer buffer = new RouteBuffer();
        sim.setBuffer(buffer);
        RouteGenerator routeGen = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen2 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen3 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen4 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen5 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen6 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen7 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        
        executorService.execute(routeGen);
        executorService.execute(routeGen2);
        executorService.execute(routeGen3);
        executorService.execute(routeGen4);
        executorService.execute(routeGen5);
        executorService.execute(routeGen6);
        executorService.execute(routeGen7);
//        sim.setCars(500); // 200 is the amount of cars 

        executorService.execute(sim);

        executorService.shutdown();

        
    }
    
    static void threadTest(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Simulation sim = new Simulation();

        sim.openMap("./src/MediumMap.osm");
        sim.startRenderer(1);
        sim.carTotal=1000;
        
        RouteBuffer buffer = new RouteBuffer();
        sim.setBuffer(buffer);
        RouteGenerator routeGen = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen2 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen3 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen4 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen5 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen6 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        RouteGenerator routeGen7 = new RouteGenerator(sim.gb.roads,(sim.display.maxLat-sim.display.minLat),buffer);
        
        executorService.execute(routeGen);
        executorService.execute(routeGen2);
        executorService.execute(routeGen3);
        executorService.execute(routeGen4);
        executorService.execute(routeGen5);
        executorService.execute(routeGen6);
        executorService.execute(routeGen7);
//        sim.setCars(500); // 200 is the amount of cars 

        executorService.execute(sim);

        executorService.shutdown();

//        boolean run = true;
//        while(run){ //play for a long time
//            sim.step(0.00016, .022);         //.00016.05 used to be    
//            try{
//                
//                TimeUnit.MILLISECONDS.sleep(20); //
//            }catch(Exception ex){
//                System.out.println("TimeUnit.SECONDS.sleep(1)");
//            }
// //           String pauseStr = sc.next();
//
// //           System.out.println("Frame "+i);
//            sim.updateRenderer();
////            sim.setScale(i);
//        }
//        sim.demo();
//        sim.startRenderer(1);
    }


}

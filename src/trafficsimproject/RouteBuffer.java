/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author paleo
 */
public class RouteBuffer implements Buffer{
    
    public final ArrayBlockingQueue<Directions> buffer;
    
    RouteBuffer(){
        buffer = new ArrayBlockingQueue<Directions>(28);
    }
    

    @Override
    public void blockingPut(Directions dir) throws InterruptedException {
        buffer.put(dir);
    }

    @Override
    public Directions blockingGet() throws InterruptedException {
        Directions dir = buffer.take();
        
        return dir;
    }


    
}

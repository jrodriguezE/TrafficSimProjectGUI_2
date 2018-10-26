/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

/**
 *
 * @author paleo
 */
public class RoadData {
    private int id = 0;
    int arrSize =10;
    int[] history = new int[arrSize];
    Road rd;
    RoadData(Road rd){
        this.rd=rd;
    }
    
    RoadData(){
        
    }
    
    public void update(){
        int trafficSize=0;
        for(int i=0;i<rd.nodeList.size();i++){
            if(i%2==0){
                continue; // drop half since cars are listed on nodes of their either side 
            }
            trafficSize+=rd.nodeList.get(i).cars.size();
        }
        updateHistory(trafficSize);
    }
    
    public void updateHistory(int trafficSize){
        for(int i =0;i<arrSize-1;i++){
            history[i]=history[i+1];            
        }
        history[9]=trafficSize;
        
    }
    
    public double getAverage(){
        double avg=0;
        for(int i=0;i<arrSize;i++){
            avg+=history[i];
        }
        return avg/10;
    }
    
}

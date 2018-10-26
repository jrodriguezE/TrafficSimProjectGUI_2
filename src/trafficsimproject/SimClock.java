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
public class SimClock {
//    double stepSize=1;
    int hour=6;
    int min=0;
    double second =0;
    boolean minAlarm=false;
    boolean hourAlarm=false;
    
    int targetHour=0;
    boolean targetFound=false;
    SimClock(){

    }
    
    public void setStart(int hour, int min){
        this.hour=hour;
        this.min = min;
        
        
    }
    
    public void stepSec(double stepSize){
        second+=stepSize;
        while(second>59){
            second=second-60;
            stepMin();
        }
    }
    private void stepMin(){
        min++;
        minAlarm=true;
        if(min>59){
            min=0;
            stepHour();
//            minAlarm=true;
        }
    }
    private void stepHour(){

        hourAlarm=true;
        hour++;
        
        if(hour>23){
           hour=0;
        }
        if(hour==targetHour){
           targetFound=true;
           System.out.println("hour==targetHour:"+hour);
        }
    }
    
    public void setTarget(int targH){
        this.targetHour=targH;
    }
    
    public int stepsTill(double stepSize, int targHour, int targMin){
        double steps =0;
        
        if(targMin<min){
            targMin=+60;
            targHour--;
            steps=(targMin-min)*60;
        }
        
        if(targHour<hour){
            steps+=(24-(hour-targHour))*60*60;//
            
        }else{
            steps+=(targHour-hour)*60*60;
        }
        
        
        return (int)(steps/stepSize);
        
        
    }
}

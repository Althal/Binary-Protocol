/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.util.BitSet;


public class TimeThread implements Runnable {
    int time;
    private boolean done;
    
    public boolean getDone(){
        return done;
    }
    
    public int getTime(){
        return time;
    }
    
    public void setDone(boolean done){
        this.done = done;
    }
    
    TimeThread(int time){
        this.time = time;
        done = false;
    }
    
    public BitSet getStringTime(){
        int s= time;
        BitSet ret = new BitSet(8);
        for(int i=7; i>=0; i--){
            if(s%2==1) ret.set(i);
            s/=2;
        } 
        return ret;
    }

    @Override
    public void run() {
        long current = System.currentTimeMillis(); 
        
        int i = 15;
        while((i >= 0) && (getTime() >0)){
            //System.out.println(getDone());
            while(done){}
            while(i>0&& (getTime() >0)){
                if(System.currentTimeMillis() - current > 1000){ 
                    i--;
                    time--;
                    current = System.currentTimeMillis(); 
                    System.out.println("Time: "+time);
                } 
            }
            i = 15;
            done = true;
            //System.out.println(getDone());
        }
    }
}

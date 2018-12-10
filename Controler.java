/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author ArkadiuszDokowicz Milosz Szkudlarek
 */
public class Controler {
    public BitSet val = new BitSet(8);
    int secretvalue;
    byte secretvalueinByte;
    public int getSecretvalue() {
        return secretvalue;
    }
    BitSet id1 = new BitSet(5);    
    BitSet id2 = new BitSet(5);

    public BitSet getId1() {
        return id1;
    }

    public BitSet getId2() {
        return id2;
    }
    
    
    int los;
    int cl = 0; //clientCounter
    int maxTime;
    
    boolean fullSerwer;
    
    public int getCl() {
        return cl;
    }
    
    public void incCl(){
        if(cl==1) fullSerwer=true;
        else cl=1;
    }

    public void idSesionSet() {
        int s =(int)(System.currentTimeMillis()%32);
        System.out.println("Przydzielono id " + s);
        
        if(cl==1){
            if(s==los) {
                s=(s+1)%32;
                //System.out.println("Wywołano dodanie");
            }
        }
        else los=s;
        
        for(int i=4; i>=0; i--){
            if(s%2==1){
                if(cl==0)id1.set(i);
                else id2.set(i);
            }
            s/=2;
        } 
    }

    public void showId1() {
        for(int i=4; i>=0; i--){
            System.out.print(id1.get(i)? "1":"0");
        }
        System.out.println();
    }

    public void showId2() {
        for(int i=4; i>=0; i--){
            System.out.print(id2.get(i)? "1":"0");
        }
        System.out.println();
    }
    
    public String bitsetToString(int arg){
        StringBuilder ret = new StringBuilder();
        if(arg==0){
            for(int i=0;i<5;i++){
                char a = id1.get(i)? ('1'):('0');
                ret.append(a);
            }
        }
        else if(arg==1){
            for(int i=0;i<5;i++){
                char a = id2.get(i)? ('1'):('0');
                ret.append(a);
            }
        }
        return ret.toString();
    }
    
    public int bitsetToInt(int arg){ 
        int ret=0;
        if(arg==0){
            for(int i=4;i>=0;i--){
                if(id1.get(i)) ret+=pow(2,i);
            }
        }
        else if(arg==1){
            for(int i=4;i>=0;i--){
                if(id2.get(i)) ret+=pow(2,i);
            }
        }
        return ret;
    }
    
    public void setGameTime(){
        maxTime = ((abs(bitsetToInt(1) + bitsetToInt(2))*74)%90)+25;
    }
    
    public void setSecretValue(){
        Random r = new Random();
        int s = r.nextInt(32);
        int s2 = s;
        for(int i=7;i>=0;i--){
            if(s2%2==1){
				this.val.set(i);
			}
			s2/=2;
        }
		
        this.secretvalue=s;
        this.secretvalueinByte=(byte)s;
        System.out.println("Wylosowana sekretna liczba: " + s );
    }
    
    public int getPlayer(String a){
        for(int i=0; i<5; i++){
            boolean bit = false;
            if(a.charAt(i)=='1') bit = true;
            if(bit != id1.get(i)) return 2;
        }
        return 1;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.util.BitSet;

/**
 *
 * @author ArkadiuszDokowicz Milosz Szkudlarek
 */
public class Message {
    public BitSet mes = new BitSet(26);
    public String s1;
    public byte[] bytes;
	
    public Message(BitSet a, BitSet b, BitSet c, BitSet d){ //id,operacja,odpowiedz,liczba
        if(a.get(0)) mes.set(0);
        if(a.get(1)) mes.set(1);
        if(a.get(2)) mes.set(2);
        if(a.get(3)) mes.set(3);
        if(a.get(4)) mes.set(4);
        
        if(b.get(0)) mes.set(5);
        if(b.get(1)) mes.set(6);
        if(b.get(2)) mes.set(7);
        
        if(c.get(0)) mes.set(8);
        if(c.get(1)) mes.set(9);
        if(c.get(2)) mes.set(10);
        
        if(d.get(0)) mes.set(11);
        if(d.get(1)) mes.set(12);
        if(d.get(2)) mes.set(13);
        if(d.get(3)) mes.set(14);
        if(d.get(4)) mes.set(15);
        if(d.get(5)) mes.set(16);
        if(d.get(6)) mes.set(17);
        if(d.get(7)) mes.set(18);
    }
    
    public Message(BitSet a, String b, String c, String d){ //id,operacja,odpowiedz,liczba
        if(a.get(0)) mes.set(0);
        if(a.get(1)) mes.set(1);
        if(a.get(2)) mes.set(2);
        if(a.get(3)) mes.set(3);
        if(a.get(4)) mes.set(4);
        
        if(b.charAt(0)=='1') mes.set(5);
        if(b.charAt(1)=='1') mes.set(6);
        if(b.charAt(2)=='1') mes.set(7);
        
        if(c.charAt(0)=='1') mes.set(8);
        if(c.charAt(1)=='1') mes.set(9);
        if(c.charAt(2)=='1') mes.set(10);
        
        if(d.charAt(0)=='1') mes.set(11);
        if(d.charAt(1)=='1') mes.set(12);
        if(d.charAt(2)=='1') mes.set(13);
        if(d.charAt(3)=='1') mes.set(14);
        if(d.charAt(4)=='1') mes.set(15);
        if(d.charAt(5)=='1') mes.set(16);
        if(d.charAt(6)=='1') mes.set(17);
        if(d.charAt(7)=='1') mes.set(18);
    }
    
    public Message(BitSet a, String b, String c, BitSet d){ //id,operacja,odpowiedz,liczba
        if(a.get(0)) mes.set(0);
        if(a.get(1)) mes.set(1);
        if(a.get(2)) mes.set(2);
        if(a.get(3)) mes.set(3);
        if(a.get(4)) mes.set(4);
        
        if(b.charAt(0)=='1') mes.set(5);
        if(b.charAt(1)=='1') mes.set(6);
        if(b.charAt(2)=='1') mes.set(7);
        
        if(c.charAt(0)=='1') mes.set(8);
        if(c.charAt(1)=='1') mes.set(9);
        if(c.charAt(2)=='1') mes.set(10);
        
        if(d.get(0)) mes.set(11);
        if(d.get(1)) mes.set(12);
        if(d.get(2)) mes.set(13);
        if(d.get(3)) mes.set(14);
        if(d.get(4)) mes.set(15);
        if(d.get(5)) mes.set(16);
        if(d.get(6)) mes.set(17);
        if(d.get(7)) mes.set(18);
    }
    
    public Message(String a, String b, String c, String d){ //id,operacja,odpowiedz,liczba
        if(a.charAt(0)=='1')mes.set(0);
        if(a.charAt(1)=='1')mes.set(1);
        if(a.charAt(2)=='1')mes.set(2);
        if(a.charAt(3)=='1')mes.set(3);
        if(a.charAt(4)=='1')mes.set(4);
        
        if(b.charAt(0)=='1') mes.set(5);
        if(b.charAt(1)=='1') mes.set(6);
        if(b.charAt(2)=='1') mes.set(7);
        
        if(c.charAt(0)=='1') mes.set(8);
        if(c.charAt(1)=='1') mes.set(9);
        if(c.charAt(2)=='1') mes.set(10);
        
        if(d.charAt(0)=='1') mes.set(11);
        if(d.charAt(1)=='1') mes.set(12);
        if(d.charAt(2)=='1') mes.set(13);
        if(d.charAt(3)=='1') mes.set(14);
        if(d.charAt(4)=='1') mes.set(15);
        if(d.charAt(5)=='1') mes.set(16);
        if(d.charAt(6)=='1') mes.set(17);
        if(d.charAt(7)=='1') mes.set(18);
    }
    
    public Message(String a, String b, String c, BitSet d){ //id,operacja,odpowiedz,liczba
        if(a.charAt(0)=='1')mes.set(0);
        if(a.charAt(1)=='1')mes.set(1);
        if(a.charAt(2)=='1')mes.set(2);
        if(a.charAt(3)=='1')mes.set(3);
        if(a.charAt(4)=='1')mes.set(4);
        
        if(b.charAt(0)=='1') mes.set(5);
        if(b.charAt(1)=='1') mes.set(6);
        if(b.charAt(2)=='1') mes.set(7);
        
        if(c.charAt(0)=='1') mes.set(8);
        if(c.charAt(1)=='1') mes.set(9);
        if(c.charAt(2)=='1') mes.set(10);
        
        if(d.get(0)) mes.set(11);
        if(d.get(1)) mes.set(12);
        if(d.get(2)) mes.set(13);
        if(d.get(3)) mes.set(14);
        if(d.get(4)) mes.set(15);
        if(d.get(5)) mes.set(16);
        if(d.get(6)) mes.set(17);
        if(d.get(7)) mes.set(18);
    }
    
    /*public String toString(){
        StringBuilder ret = new StringBuilder();
        
        for(int i=0;i<19;i++){
            char a = mes.get(i)? ('1'):('0');
            ret.append(a);
        }
        return ret.toString();
    }*/
    
    @Override
    public String toString(){
        int bajt1 = 0;
       
        if(mes.get(0)) bajt1+=128;
        if(mes.get(1)) bajt1+=64;
        if(mes.get(2)) bajt1+=32;
        if(mes.get(3)) bajt1+=16;
        if(mes.get(4)) bajt1+=8;
        if(mes.get(5)) bajt1+=4;
        if(mes.get(6)) bajt1+=2;
        if(mes.get(7)) bajt1+=1;
        
        int bajt2 = 0;
        if(mes.get(8)) bajt2+=128;
        if(mes.get(9)) bajt2+=64;
        if(mes.get(10)) bajt2+=32;
        if(mes.get(11)) bajt2+=16;
        if(mes.get(12)) bajt2+=8;
        if(mes.get(13)) bajt2+=4;
        if(mes.get(14)) bajt2+=2;
        if(mes.get(15)) bajt2+=1;
        
        int bajt3 = 0;
        if(mes.get(16)) bajt3+=128;
        if(mes.get(17)) bajt3+=64;
        if(mes.get(18)) bajt3+=32;
        
        StringBuilder ret = new StringBuilder();
        ret.append((byte)bajt1);
        ret.append((byte)bajt2);
        ret.append((byte)bajt3);
        byte [] ret2 = new byte[3];
        ret2[0]=(byte)bajt1;
        ret2[1]=(byte)bajt2;
        ret2[2]=(byte)bajt3;
        this.s1=ret.toString();
        //System.out.println(ret.toString());
        return ret.toString();
    }
    
public byte[] toByteTab(){
        int bajt1 = 0;
       
        if(mes.get(5)) bajt1+=128;
        if(mes.get(6)) bajt1+=64;
        if(mes.get(7)) bajt1+=32;
        if(mes.get(8)) bajt1+=16;
        if(mes.get(9)) bajt1+=8;
        if(mes.get(10)) bajt1+=4;
        if(mes.get(0)) bajt1+=2;
        if(mes.get(1)) bajt1+=1;
       
        int bajt2 = 0;
  
        if(mes.get(2)) bajt2+=128;
        if(mes.get(3)) bajt2+=64;
        if(mes.get(4)) bajt2+=32;
        if(mes.get(11)) bajt2+=16;
        if(mes.get(12)) bajt2+=8;
        if(mes.get(13)) bajt2+=4;
        if(mes.get(14)) bajt2+=2;
        if(mes.get(15)) bajt2+=1;
        
        int bajt3 = 0;
        if(mes.get(16)) bajt3+=128;
        if(mes.get(17)) bajt3+=64;
        if(mes.get(18)) bajt3+=32;

       byte first,second,last;
       first= (byte)bajt1;
       second=(byte)bajt2;
       last=(byte)bajt3;
        byte [] ret2 = new byte[3];
        ret2[0]=first;
        ret2[1]=second;
        ret2[2]=last;
        this.bytes=ret2;
        return ret2;
    }
    



    public static String read(String m){
        StringBuilder ret = new StringBuilder();
        int b1 = m.charAt(0);
        int b2 = m.charAt(1);
        int b3 = m.charAt(2);
        
        StringBuilder rob = new StringBuilder();
        for(int i=0; i<8;i++){
            rob.append(b1%2);
            b1/=2;
        }
        rob.toString();
        for(int i=7; i>=0;i--){
            ret.append(rob.charAt(i));
        }
        
        rob = new StringBuilder();
        for(int i=0; i<8;i++){
            rob.append(b2%2);
            b2/=2;
        }
        rob.toString();
        for(int i=7; i>=0;i--){
            ret.append(rob.charAt(i));
        }
        
        rob = new StringBuilder();
        for(int i=0; i<8;i++){
            rob.append(b3%2);
            b3/=2;
        }
        rob.toString();
        for(int i=7; i>=0;i--){
            ret.append(rob.charAt(i));
        }
        
        System.out.println(ret.toString());
        return ret.toString();
    }
}
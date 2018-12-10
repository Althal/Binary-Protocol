/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.BitSet;
import java.util.Date;
import klient.Klient;
import static klient.Klient.ByteTabtoString;


public class Serwer {
    
    BitSet id1 = new BitSet(5);
    BitSet id2 = new BitSet(5);
    int secretvalue;
    BitSet poleOperacji = new BitSet(3);
    BitSet poleOdpowiedzi = new BitSet(3);
    static String p1; //id
    static String p2;
    static String p3;
    static String p4;
    int valueclient1;
    int clientvalue;
    BitSet clientBSvalue1=new BitSet(8);
    int valueclient2;
    BitSet clientBSvalue2=new BitSet(8);
    static boolean wygrana_klient_1=false;
    static boolean wygrana_klient_2=false;
        
	static BufferedReader input1;
	static BufferedReader input2;
	static PrintWriter out1;
	static PrintWriter out2;
    //////////////////////////////////////////////
	//Instancje klas potrzebnych do komunikacji
    static OutputStream OutputStream1;
    static InputStream InputStream1;
    static BufferedOutputStream mBufferedOutputStream1;
        
    static  OutputStream OutputStream2;
    static  InputStream InputStream2;
    static BufferedOutputStream mBufferedOutputStream2;
    ///////////////////////////////////////////////
    
	
	/*Odczytywanie wartości z wiadomości i wstawienie jej do pola klasy*/
    public void setClientValueFromMessage(String p){
		BitSet sessionid=new BitSet(5);
		BitSet clientBSvalue=new BitSet(8);
		clientvalue=0; 
		if(p.charAt(11)=='1'){ clientvalue+=128; clientBSvalue.set(0);}
			if(p.charAt(12)=='1') {clientvalue+=64 ;clientBSvalue.set(1);}
			if(p.charAt(13)=='1') {clientvalue+=32 ;clientBSvalue.set(2);}
			if(p.charAt(14)=='1'){ clientvalue+=16 ;clientBSvalue.set(3);}
			if(p.charAt(15)=='1'){ clientvalue+=8; clientBSvalue.set(4);}
			 if(p.charAt(16)=='1') {clientvalue+=4 ;clientBSvalue.set(5);}
			if(p.charAt(17)=='1'){ clientvalue+=2 ;clientBSvalue.set(6);}
			if(p.charAt(18)=='1'){ clientvalue+=1; clientBSvalue.set(7);}
			
		System.out.println(clientvalue);
		
		if(p.charAt(6)=='1')sessionid.set(0);
			if(p.charAt(7)=='1')sessionid.set(1);
			if(p.charAt(8)=='1')sessionid.set(2);
			if(p.charAt(9)=='1')sessionid.set(3);
			if(p.charAt(10)=='1')sessionid.set(4);
		if(sessionid.equals(controller.id1)){
			System.out.print("odebrano wiadomosc od klienta 1 o ID :");
			controller.showId1();
			valueclient1=clientvalue;
			clientBSvalue1=clientBSvalue;
		}
		if(sessionid.equals(controller.id2)){
			System.out.print("odebrano wiadomosc od klienta 2 o ID :");
			controller.showId2();
			valueclient2=clientvalue;
			clientBSvalue2=clientBSvalue;
		}    
    }
	
    public static final int byteArrayToInt(byte[] value) {
		int ret = ((value[0] & 0xFF) << 24) | ((value[1] & 0xFF) << 16) |
					((value[2] & 0xFF) << 8) | (value[3] & 0xFF);
	 
		return ret;
	}
    
    static public String ByteTabtoString(byte[] tab){
		byte b1 = tab[0];
		String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
		//System.out.println(s1+"first"); // 10000001

		byte b2 = tab[1];
		String s2 = String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
	   /// System.out.println(s2+"second"); // 00000010
		
		byte b3 = tab[2];
		String s3 = String.format("%8s", Integer.toBinaryString(b3 & 0xFF)).replace(' ', '0');
	   /// System.out.println(s3+"last"); // 00000010

		StringBuilder sb = new StringBuilder();
		sb.append(s1);
		sb.append(s2);
		sb.append(s3);
		String value = sb.toString();
			
		return value; 
    }
	
	/*Ustawianie wewnetrznych zmiennych pomocniczych*/
    public void setStrings(String p){
        StringBuilder d = new StringBuilder();
        d.append(p.charAt(6));
        d.append(p.charAt(7));
        d.append(p.charAt(8));
        d.append(p.charAt(9));
        d.append(p.charAt(10));
        
        p1= d.toString();
        d = new StringBuilder();
        
        d.append(p.charAt(0));
        d.append(p.charAt(1));
        d.append(p.charAt(2));
        
        p2= d.toString();
        d = new StringBuilder();
        
        d.append(p.charAt(3));
        d.append(p.charAt(4));
        d.append(p.charAt(5));
        
        p3= d.toString();
        d = new StringBuilder();
        
        d.append(p.charAt(11));
        d.append(p.charAt(12));
        d.append(p.charAt(13));
        d.append(p.charAt(14));
        d.append(p.charAt(15));
        d.append(p.charAt(16));
        d.append(p.charAt(17));
        d.append(p.charAt(18));
        
        p4= d.toString();
    }
	
    private void setId1(){
        int s =(int)(System.currentTimeMillis()%32);
        
        for(int i=0; i<5; i++){
            if(s%2==1) id1.set(i);
            s/=2;
        }        
    }
    
    static ServerSocket listener;  
	static Socket socket1;  
	static Socket socket2 = new Socket();
	static Controler controller = new Controler(); 


	/*Wątek obsługujący klienta 1*/
    public static class GetMessageThread1 extends Serwer implements Runnable  {
        boolean mes;
        int val;

        public GetMessageThread1(InputStream InputStream1,BufferedOutputStream mBufferedOutputStream1){
            mes = false;
			this.InputStream1=InputStream1;
			this.mBufferedOutputStream1=mBufferedOutputStream1;
        }

        public void setMes(boolean mes) {
            this.mes = mes;
        }

        public boolean getMes() {
            return mes;
        }

        public int getVal() {
            return val;
        }

        @Override
        synchronized public void run() {
            while(true){
                try {
                   byte[] responseBytes1=new byte[3];
                   int value;
                   value=0;
                    if((value=InputStream1.read(responseBytes1, 0,3))==3) //oczytanie 3 znaków (długość wiadomości)
                    {
						String answer=ByteTabtoString(responseBytes1);
						System.out.println(answer);            
					
						System.out.println("klient 1 odczytano");
						setStrings(answer);
						setClientValueFromMessage(answer);

						int secretnumberFromPlayer = Integer.parseInt(p4);
						int realsectervalue = controller.getSecretvalue();
                  
					    if(clientBSvalue1.equals(controller.val)){ //jeżeli odgadnięto liczbę
							System.out.print("Klient o id ");
							controller.showId1();
							System.out.println("Wyslal liczbe "+valueclient1+" sekretna liczba to "+controller.secretvalue);
							System.out.print("WYGRANA klienta1 id session: ");
							controller.showId1();
							mBufferedOutputStream1.write(new Message(controller.getId1(),"011","001","00000000").toByteTab()); //wysłanie wiadomości
							mBufferedOutputStream1.flush();
							wygrana_klient_1=true;
							break;//win message
						}
						else{
							System.out.print("PRZEGRANA klienta1 id session: ");
							System.out.println("Klient o id ");
							controller.showId1();
							System.out.println("Wyslal liczbe"+valueclient1+"sekretna liczba to"+controller.secretvalue);
							mBufferedOutputStream1.write(new Message(controller.getId1(),"011","000","00000000").toByteTab()); //wysłanie wiadomości
							mBufferedOutputStream1.flush();
						}
                    }  
                }
                catch(Exception e){}
            }
        }
    }
	
	/*Wątek obsługujący klienta 2*/
    public static class GetMessageThread2 extends Serwer implements Runnable  {
        boolean mes;
        int val;
     
        public GetMessageThread2(InputStream InputStream2,BufferedOutputStream mBufferedOutputStream2){
            mes = false;
            this.InputStream2=InputStream2;
			this.mBufferedOutputStream2=mBufferedOutputStream2;
        }

        public void setMes(boolean mes) {
            this.mes = mes;
        }

        public boolean getMes() {
            return mes;
        }

        public int getVal() {
            return val;
        }

        @Override
         synchronized public void run() {
            while(true){
                try {
                   byte[] responseBytes1=new byte[3];
                   int value=0;
				   
                   if(value=InputStream2.read(responseBytes1, 0,3)==3){ //oczytanie 3 znaków (długość wiadomości)
						String answer=ByteTabtoString(responseBytes1);
						System.out.println(answer);            
						System.out.println("klient 2 odczytano");
						setStrings(answer);
						setClientValueFromMessage(answer);

						int secretnumberFromPlayer = Integer.parseInt(p4);
						int realsectervalue = controller.getSecretvalue();

						if(clientBSvalue2.equals(controller.val)){ //jeżeli odgadnięto liczbę
							System.out.print("Klient o id ");
							controller.showId2();
							System.out.println("Wyslal liczbe"+valueclient2+"sekretna liczba to"+controller.secretvalue);
							System.out.print("WYGRANA klienta2 id session: ");
							controller.showId2();
							mBufferedOutputStream2.write(new Message(controller.getId2(),"011","010","00000000").toByteTab()); //wysłanie wiadomości
							mBufferedOutputStream2.flush();
							wygrana_klient_2=true;
							break;//win message
						}	
						else{
							System.out.print("PRZEGRANA klienta1 id session: ");
							System.out.println("Klient o id ");
							controller.showId1();
							System.out.println("Wyslal liczbe "+valueclient1+" sekretna liczba to "+controller.secretvalue);
							mBufferedOutputStream2.write(new Message(controller.getId2(),"011","000","00000000").toByteTab()); //wysłanie wiadomości
							mBufferedOutputStream2.flush();
						}
                    }  
                }
                catch(Exception e){}
            }
        }
    }
        
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
			/*Otwarcie socketa*/
            listener = new ServerSocket(9090);
            socket1 = listener.accept();  
			
			/*Nasłuchiwanie*/
			OutputStream1=socket1.getOutputStream();
			mBufferedOutputStream1 = new BufferedOutputStream(OutputStream1);
			InputStream1=socket1.getInputStream();
		  
			byte[] responseBytes = new byte[3];
                    
			/*Oczekiwanie na wiadomość*/
		    while(true){if(InputStream1.read(responseBytes, 0,3)==3)break;}
			
			/*Obsługa przypisania ID*/
			String fromclient1=ByteTabtoString(responseBytes);
			System.out.println(fromclient1);
			controller.idSesionSet();
			String w = controller.bitsetToString(controller.getCl());                   
            System.out.println(w);
                    
			System.out.println("Wyslano ID sesji");
			mBufferedOutputStream1.write(new Message(controller.getId1(),"000","001","00000000").toByteTab()); //wysłanie ID klientowi 1
			mBufferedOutputStream1.flush();

			controller.incCl();
			
			/*Nasłuchiwanie*/
			System.out.println("czekam!");
			socket2 = listener.accept(); 
			System.out.println("dostalem socket2");
			OutputStream2=socket2.getOutputStream();
			mBufferedOutputStream2 = new BufferedOutputStream(OutputStream2);
			InputStream2=socket2.getInputStream();
			
			responseBytes = new byte[3];
                    
			/*Oczekiwanie na wiadomość*/
            while(true){if(InputStream2.read(responseBytes, 0,3)==3)break;}
            
			/*Obsługa przypisania ID*/
			fromclient1=ByteTabtoString(responseBytes);
			System.out.println(fromclient1);
			controller.idSesionSet();
			w = controller.bitsetToString(controller.getCl());
			System.out.println(w);

			System.out.println("Wyslano ID sesji");
			mBufferedOutputStream2.write(new Message(controller.getId2(),"000","001","00000000").toByteTab()); //wysłanie ID klientowi 2
			mBufferedOutputStream2.flush();

			/*Wysłanie obu klientom wiadomości o rozpoczęciu gry*/
            mBufferedOutputStream1.write(new Message(controller.getId1(),"011","100","00000000").toByteTab());
            mBufferedOutputStream1.flush();
            Thread.sleep(150);
            mBufferedOutputStream2.write(new Message(controller.getId2(),"011","100","00000000").toByteTab());
            mBufferedOutputStream2.flush();
			
            System.out.println("Start gry");
            
			/*Ustawienie czasu gry i wylosowanie liczby*/
            controller.setSecretValue();
            controller.setGameTime();
            System.out.println("Game time set :"+controller.maxTime);
			
			/*Uruchomienie wątku czasowego*/
            TimeThread tim = new TimeThread(controller.maxTime);
            Thread t = new Thread(tim);
            t.start();
			
			/*Uruchomienie wątków obsługujących wiadomości od klientów*/
            GetMessageThread1 getter=new GetMessageThread1(InputStream1,mBufferedOutputStream1);
            Thread get = new Thread(getter);
			get.start();
			
            GetMessageThread2 getter2=new GetMessageThread2(InputStream2,mBufferedOutputStream2);
            Thread get2 = new Thread(getter2);
            get2.start();
			
			/*Obsługa flagi kontrolera o potrzebie wysłania komunikatu o międzyczasie*/
            while(true){
                System.out.print("");
                if(tim.getDone()){
                    if(tim.getTime()<=0){
                        System.out.println("Wysyłam komunikat o czasie 1"); //koniec gry
                        tim.setDone(false);
						
                        if(wygrana_klient_1!=true){
							mBufferedOutputStream1.write(new Message(controller.getId1(),"010","000","00000000").toByteTab());
							mBufferedOutputStream1.flush();
						}
                        Thread.sleep(150);
                        if(wygrana_klient_2!=true){
							mBufferedOutputStream2.write(new Message(controller.getId2(),"010","000","00000000").toByteTab());
							mBufferedOutputStream2.flush(); 
                        }
                    }
                    else{
                        System.out.println("Wysyłam komunikat o czasie 2"); //międzyczas
                        tim.setDone(false);
						
                        if(wygrana_klient_1!=true){
							mBufferedOutputStream1.write(new Message(controller.getId1(),"010","001",tim.getStringTime()).toByteTab());
							mBufferedOutputStream1.flush();    
                        }
                        Thread.sleep(150);
                        if(wygrana_klient_2!=true){
							mBufferedOutputStream2.write(new Message(controller.getId2(),"010","001",tim.getStringTime()).toByteTab());
							mBufferedOutputStream2.flush(); 
                        }
                    }
                }                
            }
        }
        finally {          
            listener.close();
            socket1.close();
            socket2.close();
        }   
    }
}

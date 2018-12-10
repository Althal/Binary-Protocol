/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.BitSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import serwer.Message;


public class Klient {
    static Boolean flag_run1=true;
	
    static public Boolean getFlag_run1() {
        return flag_run1;
    }

    static public void setFlag_run1(Boolean flag_run) {
        Klient.flag_run1 = flag_run;
    }
	
    static String idSesji;
    int buffer_number;
    static String p1; //id
    static String p2;
    static String p3;
    static String p4;
    
    static Socket socket;
    PrintWriter out;
    BufferedReader input;
    Scanner in = new Scanner(System.in);
    static OutputStream OutputStream;
    static InputStream InputStream;
    static BufferedOutputStream BufferedOutputStream;


    public Klient(){}

    public void setServerTimeValueFromMessage(String p){
		BitSet buffer1=new BitSet(8);
		BitSet buffer2=new BitSet(8);
		
		buffer_number=0;
		if(p.charAt(11)=='1'){ buffer_number+=128; buffer2.set(0);}
        if(p.charAt(12)=='1') {buffer_number+=64 ;buffer2.set(1);}
        if(p.charAt(13)=='1') {buffer_number+=32 ;buffer2.set(2);}
        if(p.charAt(14)=='1'){ buffer_number+=16 ;buffer2.set(3);}
        if(p.charAt(15)=='1'){ buffer_number+=8; buffer2.set(4);}
        if(p.charAt(16)=='1') {buffer_number+=4 ;buffer2.set(5);}
        if(p.charAt(17)=='1'){ buffer_number+=2 ;buffer2.set(6);}
        if(p.charAt(18)=='1'){ buffer_number+=1; buffer2.set(7);}
    }
    
    /*Wątek obsługujący odbiór wiadomości*/
    public static class GetMessageThread extends Klient implements Runnable  {
        boolean mes;
        int val;

        public GetMessageThread(InputStream InputStream){
            this.InputStream=InputStream;
            mes = false;
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
        public synchronized void run() {
            while(getFlag_run1()){
                try {
                    byte[] responseBytes1=new byte[3];
                    int value;
                    value=0;
                    if((value=InputStream.read(responseBytes1, 0,3))==3) //odbiór 3 bajtów (długość wiadomości)
                    { 
						System.out.println("Odebralem wiadomosc");
						String fromserver=ByteTabtoString(responseBytes1);
						System.out.println(fromserver);            
						setStrings(fromserver);
						setServerTimeValueFromMessage(fromserver);

						/*Odczyt wiadomości i wyświetlenie treści na koncoli w zależności od odczytanych znaków*/
						if(p2.equals("010")&&p3.equals("001")){                    
							System.out.println("Pozostalo czasu: "+buffer_number);
						}
						 
						if(p2.equals("010")&&p3.equals("000")){
							System.out.println("Czas na odgadniecie liczby uplynal");
							setFlag_run1(false);
							System.out.println("Wprowadz dowolny klawisz i nacisnij Enter");
							break;
						}

						if(p2.equals("011")&&p3.equals("001")){
							System.out.println("Wygrana Gracza 1!");
							setFlag_run1(false);
							System.out.println("Wprowadz dowolny klawisz i nacisnij Enter");
							break;
						}
					
						if(p2.equals("011")&&p3.equals("010")){
							System.out.println("Wygrana Gracza 2!");
							setFlag_run1(false);
							System.out.println("Wprowadz dowolny klawisz i nacisnij Enter");
							break; 
						}
					
						if(p2.equals("011")&&p3.equals("000")){
							System.out.println("Graj Dalej!");
						}
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
	/*Wątek obsługujący wysyłanie wiadomości*/
	public static class SendMessageThread extends Klient implements Runnable {
		boolean mes;
		int val;
		
		/*Konstruktor przyjmujący output stream*/
		public SendMessageThread(BufferedOutputStream BufferedOutputStream){
			mes = false;
			this.BufferedOutputStream=BufferedOutputStream;
		}

		@Override
		synchronized public void run() {
			while(getFlag_run1()){
				/*Sczytanie liczby*/
				Scanner in = new Scanner(System.in);

				try{
					val = in.nextInt();					
					int sa = val;
				    BitSet bs = new BitSet(8);
					
					for(int i=7; i>=0; i--){
						if(sa%2==1){
							bs.set(i);
						}
					   sa/=2;
					}
					if(getFlag_run1()==false){break;}

				    BufferedOutputStream.write((new Message(p1,"001","010",bs)).toByteTab()); //wysłanie liczby
					BufferedOutputStream.flush();
				}				
				catch(Exception e){System.out.print("Send message exception"); break;}			
			}
		}

}

    static public String ByteTabtoString(byte[] tab){
		byte b1 = tab[0];
		String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');

		byte b2 = tab[1];
		String s2 = String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
		
		byte b3 = tab[2];
		String s3 = String.format("%8s", Integer.toBinaryString(b3 & 0xFF)).replace(' ', '0');

		StringBuilder sb = new StringBuilder();
		sb.append(s1);
		sb.append(s2);
		sb.append(s3);
		String value = sb.toString();
			
		return value; 
    }
	
    private static void setStrings(String p){
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
    
	
    public static void main(String[] args) throws IOException {
		/*Otwarcie socketa*/		
        Socket socket = new Socket("localhost", 9090);
        PrintWriter out;
        OutputStream = socket.getOutputStream();
        BufferedOutputStream = new BufferedOutputStream(OutputStream);
        InputStream = socket.getInputStream();

		/*Wysłanie wiadomości powitalnej*/
		Message m1 = new Message("00000","000","000","00000000");
		m1.toString();
		m1.toByteTab();
		
		BufferedOutputStream.write(m1.bytes);
		BufferedOutputStream.flush();
		System.out.println("Wysłano powitanie");
	
		/*Oczekiwanie na odpowiedź od serwera*/
		byte[] responseBytes1 = new byte[3];		
	    if(InputStream.read(responseBytes1, 0,3)==3){System.out.println("");}
		
		String fromserver=ByteTabtoString(responseBytes1);
		System.out.println(fromserver);            
		setStrings(fromserver);

		if(p3.equals("001")){
			idSesji = p1;
			System.out.println("Ustawiono ID sesji");		
		}

		/*Oczekiwanie na wiadomość o starcie gry*/
        while(true){
            byte[] responseBytes2 = new byte[3];
			if(InputStream.read(responseBytes2, 0,3)==3){
				fromserver=ByteTabtoString(responseBytes2);         
				setStrings(fromserver);
				
				if(p2.equals("011")&& p3.equals("100")) break;
			}
        }       
        System.out.println("Start!");
        
		/*Uruchomienie wątków*/
        SendMessageThread s = new SendMessageThread(BufferedOutputStream);
        Thread send = new Thread(s);
        send.start();
        
        GetMessageThread g = new GetMessageThread(InputStream);
        Thread get = new Thread(g);
        get.start();
	}
}

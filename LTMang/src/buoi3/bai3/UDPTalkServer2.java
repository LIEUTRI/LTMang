package buoi3.bai3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPTalkServer2 {
	public static void main(String []args) {
		try {
			int port = Integer.parseInt(args[0]);
			
			DatagramSocket ds = new DatagramSocket(port);
			
			System.out.println("Da khoi tao xong UDP Socket !!!");
			
			String friend="";
						
			byte[]buffer = new byte[60000];
			
			while(true) {
				DatagramPacket in = new DatagramPacket(buffer,buffer.length);
				ds.receive(in);
				
				String str = new String(in.getData(),0,in.getLength());
				str = str.trim(); // "172.31.4.91;Hello"
				
				friend = str.substring(0, str.indexOf(";")) ; // "172.31.4.91"
				
				str = str.substring(str.indexOf(";") + 1) ; //  "Hello"
				
				System.out.println("\n Da gui den >>" + friend + ":" + str );
				
				InetAddress  address = InetAddress.getByName(friend);		

				
				DatagramPacket out = new DatagramPacket(str.getBytes(),str.length(), address, 8);
				ds.send(out);
			}
		}
		catch(IOException e) {
			System.err.print(e);
		}
	}

}

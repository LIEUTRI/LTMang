package buoi3.bai3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPTalkClient2 {
	
	public static void main(String[]args) {
		try {
			if(args.length ==0) {
				System.out.print("Syntax:java UDPClient HostName");return;
			}
			
			
			InetAddress server = InetAddress.getByName(args[0]);	
			int serverPort = Integer.parseInt(args[1]);
			String  friend = args[2];
			
			Scanner sc = new Scanner (System.in);
			System.out.print("Nhap nickname cua ban:");
			String name = sc.nextLine();
			String outString="",inString="" ;
			
			DatagramSocket ds = new DatagramSocket(8);
			
			while(true) {
				
				System.out.print(name+ " : ");
				outString = friend + ";" + sc.nextLine() ; // "172.31.4.91;Hello"

				byte[] data = outString.getBytes();

				DatagramPacket out = new DatagramPacket (data,data.length, server, serverPort );
				ds.send(out);
				
				byte[]buffer = new byte[6000];
				DatagramPacket in = new DatagramPacket(buffer, buffer.length);
				ds.receive(in);

				inString = new String(in.getData()); 
				inString = inString .trim();
				
				System.out.println(friend+ " : " +inString);
			}
		}
		catch(IOException e) {
		}
	}
}

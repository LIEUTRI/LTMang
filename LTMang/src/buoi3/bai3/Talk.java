package buoi3.bai3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Talk {

	private final static int PORT = 13;	
	private final static String HOST = "127.0.0.1";
	
	public static void main(String[] args) throws IOException {
		
		DatagramSocket ds = new DatagramSocket(69);
		Scanner sc = new Scanner(System.in);
		while (true) {
			byte data[] = new byte[6000];
			System.out.print("Nhap vao tin nhan: ");
			String message = sc.nextLine();
			data = message.getBytes();
			DatagramPacket outData = new DatagramPacket(data, data.length, 
					InetAddress.getByName(HOST), PORT);
			
			ds.send(outData);
			
			byte buffer[] = new byte[6000];
			DatagramPacket inData = new DatagramPacket(buffer, buffer.length);
			ds.receive(inData);
			System.out.println(inData.getAddress() + ": " + new String(inData.getData()).trim());
		}

	}

	
}

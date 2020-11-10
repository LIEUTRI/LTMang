package buoi3.bai3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

	public static void main(String[] args) throws IOException {
		
		byte connectBuffer[] = new byte[6000];
		DatagramSocket ds = new DatagramSocket(13);
		System.out.println("Server is starting...");
		DatagramPacket connectData = new DatagramPacket(connectBuffer, connectBuffer.length);
		
		InetAddress firstClient = null;
		InetAddress secondClient = null;
		
		while (firstClient == null || secondClient == null) {
			ds.receive(connectData);
			String connect = new String(connectData.getData()).trim();
			if (connect.equals("c1") && firstClient == null) {
				firstClient = connectData.getAddress();
				byte connectString[] = "Connected as client 1".getBytes();
				DatagramPacket outData = new DatagramPacket(connectString, connectString.length, 
						connectData.getAddress(), connectData.getPort());
				ds.send(outData);
			} else if (connect.equals("c2") && secondClient == null) {
				secondClient = connectData.getAddress();
				byte connectString[] = "Connected as client 2".getBytes();
				DatagramPacket outData = new DatagramPacket(connectString, connectString.length, 
						connectData.getAddress(), connectData.getPort());
				ds.send(outData);
			}
		}	
		
		while (true) {		
			byte buffer[] = new byte[6000];
			DatagramPacket inData = new DatagramPacket(buffer, buffer.length);
			ds.receive(inData);
			
			String message = new String(inData.getData());
			System.out.println(firstClient + " - " + secondClient);
			System.out.println(inData.getAddress() + ": " + message.trim());
			
			byte data[] = message.getBytes();

			if (inData.getAddress().equals(firstClient)) {
				DatagramPacket outData = new DatagramPacket(data, data.length,
					secondClient, inData.getPort());
				ds.send(outData);
				System.out.println("c1");
			} else {
				DatagramPacket outData = new DatagramPacket(data, data.length,
						firstClient, inData.getPort());
				ds.send(outData);
				System.out.println("c2");
			}
			
			
		}
	}
	
}

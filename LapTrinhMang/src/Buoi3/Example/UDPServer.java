package Buoi3.Example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

	public static void main(String[] args) throws IOException {
		
		byte buffer[] = new byte[6000];
		DatagramSocket ds = new DatagramSocket(69);
		System.out.println("Server is starting...");
		while (true) {
			DatagramPacket inData = new DatagramPacket(buffer, buffer.length);
			ds.receive(inData);
			
			String message = new String(inData.getData());
			System.out.println(message.trim());
			message = message.trim().toUpperCase();
			
			byte data[] = message.getBytes();
			DatagramPacket outData = new DatagramPacket(data, data.length,
					inData.getAddress(), inData.getPort());
			
			ds.send(outData);
		}
	}

}

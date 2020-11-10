package Buoi3.Bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TimeClient {

	private final static int PORT = 13;
	private final static String HOST = "172.31.4.40";
	
	public static void main(String[] args) throws IOException {
		
		String greating = "";
		byte data[] = greating.getBytes();
		DatagramSocket ds = new DatagramSocket();
		DatagramPacket outData = new DatagramPacket(data, data.length, 
				InetAddress.getByName(HOST), PORT);
		
		ds.send(outData);
		
		byte buffer[] = new byte[6000];
		DatagramPacket inData = new DatagramPacket(buffer, buffer.length);
		ds.receive(inData);
		System.out.println("Current time: " +new String(inData.getData()).trim());

	}

}

package buoi3.Example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {

	private final static int PORT = 69;
	private final static String HOST = "127.0.0.1";
	
	public static void main(String[] args) throws IOException {
		
		String greating = "Hello World";
		byte data[] = greating.getBytes();
		DatagramSocket ds = new DatagramSocket();
		DatagramPacket outData = new DatagramPacket(data, data.length, 
				InetAddress.getByName(HOST), PORT);
		
		ds.send(outData);
		
		byte buffer[] = new byte[6000];
		DatagramPacket inData = new DatagramPacket(buffer, buffer.length);
		ds.receive(inData);
		System.out.println(new String(inData.getData()).trim());

	}

}

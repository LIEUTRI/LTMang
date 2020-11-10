package buoi3.bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class UDPDatetimeClient {

	public static void main(String[] args) {

		String host = args.length>0 ? args[0] : "localhost";
		int port = 13;
		
		System.out.println("Started !");
		
		try {
			DatagramSocket ds = new DatagramSocket();
			
			DatagramPacket out = new DatagramPacket("".getBytes(), 0, InetAddress.getByName(host), port);
			ds.send(out);

			byte[] p = new byte[1000];
			DatagramPacket in = new DatagramPacket(p, p.length);
			ds.receive(in);
			String data = new String(in.getData()).trim();
			System.out.println("Time now: " + data);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

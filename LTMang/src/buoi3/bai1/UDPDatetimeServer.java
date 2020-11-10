package buoi3.bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class UDPDatetimeServer {

	public static void main(String[] args) {
		
		int port = 13;
		
		System.out.println("Server is listening..........................");
		
		try {
			DatagramSocket ds = new DatagramSocket(port);
			
			while(true) {
				byte[] p = new byte[1000];
				DatagramPacket in = new DatagramPacket(p, p.length);
				ds.receive(in);
				String data = new String(in.getData()).trim();
				if (data.equals("")) {
					System.out.println("Client request DATETIME");
					String datetime = new Date().toString();
					DatagramPacket out = new DatagramPacket(datetime.getBytes(), datetime.length(), in.getAddress(), in.getPort());
					ds.send(out);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

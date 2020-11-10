package Buoi3.Bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TimeServer {

	public static void main(String[] args) throws IOException {
		
		byte buffer[] = new byte[6000];
		DatagramSocket ds = new DatagramSocket(13);
		System.out.println("Server is starting...");
		while (true) {
			DatagramPacket inData = new DatagramPacket(buffer, buffer.length);
			ds.receive(inData);
			
			String message = new String(inData.getData());
			System.out.println(message.trim());
			
			String time = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(Calendar.getInstance().getTime());
//			String time = ZonedDateTime.now().format(	DateTimeFormatter.RFC_1123_DATE_TIME);

			byte data[] = time.getBytes();
			DatagramPacket outData = new DatagramPacket(data, data.length,
					inData.getAddress(), inData.getPort());
			
			ds.send(outData);
		}
	}

}
package buoi1.bai1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
	
	public static void main (String[] args) {
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		try {
			Socket socket = new Socket(host, port);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			int result = 0;
			
			while (true) {
				System.out.println("Nhap 1 so: ");
				int number = System.in.read();
				if (number=='@') break;
				System.in.skip(2);
				
				outputStream.write(number);
				byte b[] = new byte[1000];
				result = inputStream.read(b);
				String chuoi = new String(b);
				chuoi = chuoi.trim();
				System.out.println("Ket qua:" + chuoi);
			}
			
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

package bai2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	private static int PORT = 1998;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PORT = Integer.parseInt(args[0]);
		System.out.println("Server is listening.............");
		ServerSocket ss = null;
		Socket s = null;
		InputStream is;
		OutputStream os = null;
		
		boolean running = true;
		
		while(running) {
			try {
				ss = new ServerSocket(PORT);
				s = ss.accept();
				System.out.println("Connected to " + s.getInetAddress());
				is = s.getInputStream();
				os = s.getOutputStream();
				int ch = 0;
				String dataStr = "";
				while(s.isBound()) {
					byte[] dataByte = new byte[100];
					ch = is.read(dataByte);
					dataStr = new String(dataByte);
					dataStr = dataStr.trim();
					if(dataStr.equals("shutdown")) {
						running = false;
						break;
					}
					System.out.println("Client: " + dataStr);
					try {
						os.write(Integer.toBinaryString(Integer.parseInt(dataStr)).getBytes());
					}catch (NumberFormatException e) {
						os.write("Khong phai so nguyen".getBytes());
					}
				}
			} catch (IOException e) {
				System.out.println(s.getInetAddress() + " disconnected!");
				try {
					s.close();
					ss.close();
				} catch (IOException e1) {
				}
			}
		}
	}

}

package bai1;

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
		OutputStream os;
		
		while(true) {
			try {
				ss = new ServerSocket(PORT);
				s = ss.accept();
				System.out.println("Connected to " + s.getInetAddress());
				is = s.getInputStream();
				os = s.getOutputStream();
				int ch = 0;
				while(true) {
					ch = is.read();
					if(ch == '@') break;
					System.out.println("Client: " + (char)ch);
					switch(ch) {
						case '0': 
							os.write("khong".getBytes()); 
							break;
						case '1': 
							os.write("mot".getBytes()); 
							break;
						case '2': 
							os.write("hai".getBytes()); 
							break;
						case '3': 
							os.write("ba".getBytes()); 
							break;
						case '4': 
							os.write("bon".getBytes()); 
							break;
						case '5': 
							os.write("nam".getBytes()); 
							break;
						case '6': 
							os.write("sau".getBytes()); 
							break;
						case '7': 
							os.write("bay".getBytes()); 
							break;
						case '8': 
							os.write("tam".getBytes()); 
							break;
						case '9': 
							os.write("chin".getBytes()); 
							break;
						default: os.write("Khong phai so nguyen".getBytes()); 
					}
				}
				System.out.println(s.getInetAddress() + " disconnected!");
				s.close();
				is.close();
				os.close();
			} catch (IOException e) {
			}
		}
	}
}

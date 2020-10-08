package bai1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
	private static int PORT = 1998;
	private static String HOST = "localhost";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HOST = args[0]; 
		PORT = Integer.parseInt(args[1]);
		System.out.println("Client started !");
		Scanner sc = new Scanner(System.in);
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			s = new Socket(HOST, PORT);
			is = s.getInputStream();
			os = s.getOutputStream();
			while(s.isBound()) {
				System.out.println("Nhap so nguyen: ");
				int ch = sc.nextLine().charAt(0);
				os.write(ch);
				byte[] strByte = new byte[100];
				is.read(strByte);
				System.out.println("Server: " + new String(strByte));
			}
		}catch(IOException e) {
			System.out.println("Client stopped!");
		}
	}

}

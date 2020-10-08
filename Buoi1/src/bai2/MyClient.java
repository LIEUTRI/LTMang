package bai2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyClient {

	private static int PORT = 1998;
	private static String HOST = "localhost";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PORT = Integer.parseInt(args[1]);
		HOST = args[0];
		System.out.println("Client started !");
		Scanner sc = new Scanner(System.in);
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			s = new Socket(HOST, PORT);
			is = s.getInputStream();
			os = s.getOutputStream();
		}catch(IOException e) {
			System.out.println("Khong the ket noi! Error: "+e.getMessage());
			return;
		}
		
		while(true) {
			System.out.println("Nhap chuoi so nguyen: ");
			try {
				String input = sc.nextLine();
				if(input.equals("@")) {
					System.out.println("Client stopped!");
					is.close();
					os.close();
					s.close();
				}
				os.write(input.getBytes());
				byte[] dataByte = new byte[1000];
				is.read(dataByte);
				String dataStr = new String(dataByte);
				dataStr = dataStr.trim();
				System.out.println("Server reply: " + dataStr);
			}catch(IOException e) {
				break;
			}
		}
	}
}

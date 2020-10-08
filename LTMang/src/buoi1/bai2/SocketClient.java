package buoi1.bai2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

	public static void main(String[] args) {

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		try {
			Socket socket = new Socket(host, port);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			Scanner scanner = new Scanner(System.in);
			int result = 0;
			
			while (true) {
				//Input a number
				System.out.print("Nhap so nguyen: ");
				String number = scanner.nextLine();
				number = number.trim();
				if (number.equals("exit") && number.equals("shutdown")) break;
				if (number.equals("")) continue;
				outputStream.write(number.getBytes());
				
				//Read result from server
				byte b[] = new byte[1000];
				result = inputStream.read(b);
				
				String chuoi = new String(b);
				System.out.println("Chuoi nhi phan: " + chuoi.trim() + "\n");
			}
			
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

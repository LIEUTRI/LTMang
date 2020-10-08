package buoi2.bai1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NameClient {

	public static void main(String[] args) {
		String HOST = "localhost";
		int PORT = 1998;
		try {
			HOST = args[0];
			PORT = Integer.parseInt(args[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		Socket s = null;
		Scanner sc = new Scanner(System.in);
		try {
			s = new Socket(HOST, PORT);
			int status = 0;
			while(true) {
				InputStream is = s.getInputStream();
				OutputStream os = s.getOutputStream();
				System.out.println("Enter your full name: ");
				String fullname = sc.nextLine();
				os.write(fullname.getBytes());
				
				byte[] b = new byte[1000];
				status = is.read(b);
				String name = new String(b).trim();
				System.out.println("Your name: "+name);
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

}

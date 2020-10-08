package buoi2.bai2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class DirectoryClient {

	public static void main(String[] args) {
		String HOST = "localhost";
		int PORT = 1998;
		String REQUEST = "LIST";
		String PATH = "/";
		try {
			HOST = args[0];
			PORT = Integer.parseInt(args[1]);
			REQUEST = args[2];
			PATH = args[3];
		} catch (ArrayIndexOutOfBoundsException e) {}
		Socket s = null;
		Scanner sc = new Scanner(System.in);
		try {
			s = new Socket(HOST, PORT);
			int status = 0;
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
//			System.out.println("Enter your full name: ");
//			String fullname = sc.nextLine();
			String cmd = REQUEST+" "+PATH;
			os.write(cmd.getBytes());
			
			byte[] b = new byte[1000];
			status = is.read(b);
			String file = new String(b).trim();
			System.out.println(""+file);
		} catch (IOException e) {
		}
	}

}

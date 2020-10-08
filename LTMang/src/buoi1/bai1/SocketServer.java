package buoi1.bai1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		
		try {			
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server is ready...");
			while(true) {
				try {
					Socket socket = serverSocket.accept();
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					
					int numberFromClient;
					
					while (true) {
						numberFromClient = inputStream.read();
						System.out.println("Nhan so tu client ");
						if (numberFromClient == '@') break;
						
						String resultString = numberToString(numberFromClient);
						outputStream.write(resultString.getBytes());
					}
					socket.close();
					
				} catch (IOException ioException) {
					System.out.println(ioException);
				}
			}			
			
		} catch (IOException ioException) {
			System.out.println(ioException);
		}
		
	}
	
	public static String numberToString(int number) {
		String resultString = "";
		switch (number) {
		case '0':
			resultString = "Khong";
			break;
		case '1':
			resultString = "Mot";
			break;
		case '2':
			resultString = "Hai";
			break;
		case '3':
			resultString = "Ba";
			break;
		case '4':
			resultString = "Bon";
			break;
		case '5':
			resultString = "Nam";
			break;
		case '6':
			resultString = "Sau";
			break;
		case '7':
			resultString = "Bay";
			break;
		case '8':
			resultString = "Tam";
			break;
		case '9':
			resultString = "Chin";
			break;
		default:
			resultString = "Khong phai so nguyen";
			break;
		}
		
		return resultString;
	}

}

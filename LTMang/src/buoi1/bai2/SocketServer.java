package buoi1.bai2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) {

		final int port = Integer.parseInt(args[0]);
		boolean serverStatus = true;
		try {			
			ServerSocket serverSocket = new ServerSocket(port);			
			System.out.println("Waiting connection from client...");
			
			while(serverStatus) {
				try {
					Socket socket = serverSocket.accept();
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();

					System.out.println(socket.getInetAddress() + " connected");
					int numberFromClient;					
					String resultString;
					
					while (true) {
						byte b[] = new byte[1000];
						numberFromClient = inputStream.read(b);
						String inputString = new String(b);
						inputString = inputString.trim();
						if (!inputString.equals("")) {
							System.out.println("Client input: " + inputString);
						}
						
						if (inputString.equals("shutdown")) {
							serverStatus = false;
							break;
						}
						
						try {
							resultString= Integer.toBinaryString(Integer.parseInt(inputString));
						} catch (NumberFormatException e) {
							resultString = "Khong phai so nguyen!";
						}
						
						outputStream.write(resultString.getBytes());
					}
					socket.close();
					
				} catch (IOException ioException) {
					System.out.println("Client disconnected\n");
				}
			}			
			
		} catch (IOException ioException) {
			System.out.println(ioException);
		}

	}

}

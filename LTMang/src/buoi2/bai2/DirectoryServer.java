package buoi2.bai2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import buoi2.bai1.TaskGetName;

public class DirectoryServer {

	public static void main(String[] args) {
		int PORT = 1998;
		try {
			PORT = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		ServerSocket ss = null;
		Socket s = null;
		try {
			ss = new ServerSocket(PORT);
			System.out.println("Server is listening on port "+PORT);
			while(true) {
				s = ss.accept();
				System.out.println("Connected to "+s.getInetAddress());
				TaskReadDir task = new TaskReadDir(s);
				task.start();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

}

package buoi2.bai1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TaskGetName extends Thread{
	private Socket s;
	public TaskGetName(Socket s) {
		this.s = s;
	}
	@Override
	public void run() {
		try {
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			int status = 0;
			while(true) {
				byte[] b = new byte[1000];
				status = is.read(b);
				String name = new String(b).trim();
				System.out.println("Client: " + name);
				name = name.substring(name.lastIndexOf(" ")+1);
				os.write(name.getBytes());
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}

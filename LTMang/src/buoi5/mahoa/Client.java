package mahoa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		String host = args[0];
		String task = args[1];
		String data = args[2];
		int port = 77;
		
		Socket s = null;
		try {
			s = new Socket(host, port);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			
			data = task + " " + data;
			os.write(data.getBytes());
			
			byte[] b = new byte[6000];
			is.read(b);
			String result = new String(b).trim();
			System.out.println("result: " + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

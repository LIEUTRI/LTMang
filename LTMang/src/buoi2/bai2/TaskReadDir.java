package buoi2.bai2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TaskReadDir extends Thread{
	private Socket s;
	public TaskReadDir(Socket s) {
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
				String cmd = new String(b).trim();
				if(cmd.indexOf("LIST") >= 0) {
					String request = cmd.substring(0, cmd.indexOf(" "));
					String path = cmd.substring(cmd.lastIndexOf(" ")+1);
					System.out.println("["+request+"]Path: " + path);
					File file = new File(path);
					File[] files = file.listFiles();
					String filesName = "";
					for(File f: files) {
						filesName += f.getName()+"\n";
					}
					os.write(filesName.getBytes());
				}
			}
		} catch (IOException e) {
		}
	}
}

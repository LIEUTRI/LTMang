package buoi2.bai3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TaskReadFile extends Thread{
	private Socket s;
	public TaskReadFile(Socket s) {
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
				if(cmd.indexOf("READ") >= 0) {
					String request = cmd.substring(0, cmd.indexOf(" "));
					String path = cmd.substring(cmd.lastIndexOf(" ")+1);
					System.out.println("["+request+"]Path: " + path);
					File file = new File(path);
					System.out.println("File name: "+file.getName());
					Scanner sc = new Scanner(file);
					String content = "";
					while(sc.hasNextLine()) {
						content += sc.nextLine()+"\n";
					}
					os.write(content.getBytes());
				}
			}
		} catch (IOException e) {
		}
	}
}

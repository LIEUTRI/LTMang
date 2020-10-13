package com.laptrinhmang.atm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

public class ATMServer {
	public static int port = 1998;
	public static void main(String[] args) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream fileConfig = classloader.getResourceAsStream("server.conf");
		
		try {
			Scanner sc = new Scanner(fileConfig);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf("PORT") >= 0) {
					port = Integer.parseInt(s.substring(s.indexOf("=")+1));
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		/////////////////////////////////////////////////////////////
		
		ServerSocket ss = null;
		Socket s = null;
		try {
			ss = new ServerSocket(port);
			System.out.println("Server is listening on port "+port);
			while(true) {
				s = ss.accept();
				System.out.println("Connected to "+s.getInetAddress());
				
				ServerTask task = new ServerTask(s);
				task.start();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

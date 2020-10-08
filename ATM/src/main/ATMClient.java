package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Scanner;

public class ATMClient {
	public static String host = "127.0.0.1";
	public static int port = 1998;
	public static void main(String[] args) {
		File fileConfig = new File(new File("resources").getAbsolutePath()+"/client.conf");
		
		try {
			Scanner sc = new Scanner(fileConfig);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf("PORT") >= 0) {
					port = Integer.parseInt(s.substring(s.indexOf("=")+1));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error! File "+new File("resources").getAbsolutePath()+"/client.conf not found!");
			return;
		}
		/////////////////////////////////////////////////////////////////
		
		String phonenumber = getUserPhoneNumber();
		String request = "KT";
		String amount = "0";
		try {
			request = args[0];
			amount = args[1];
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		try {
			Socket s = new Socket(host, port);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			
			String cmd = phonenumber+" "+request+" "+amount;
			os.write(cmd.getBytes());
			
			int check = 0;
			byte[] b = new byte[1000];
			check = is.read(b);
			String str = new String(b).trim();
			System.out.println(str);
		} catch (IOException e) {
			System.out.println("Cannot connect to "+host+":"+port);
		}
	}
	
	private static String getUserPhoneNumber() {
		File fileUserData = new File(new File("resources").getAbsolutePath()+"/userdata.txt");
		try {
			Scanner sc = new Scanner(fileUserData);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf("PHONE") >= 0) {
					return s.substring(s.indexOf("=")+1);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error! File "+new File("resources").getAbsolutePath()+"/userdata.conf not found!");
		}
		return "";
	}
}

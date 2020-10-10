package com.laptrinhmang.atm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ATMClient {
	public static String host = "127.0.0.1";
	public static int port = 1998;
	public static void main(String[] args) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream fileConfig = classloader.getResourceAsStream("client.conf");
		
		try {
			Scanner sc = new Scanner(fileConfig);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf("PORT") >= 0) {
					port = Integer.parseInt(s.substring(s.indexOf("=")+1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		/////////////////////////////////////////////////////////////////
		
		String amount = "0";
		
		try {
			Socket s = new Socket(host, port);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			Scanner sc = new Scanner(System.in);
			
			// login
			System.out.println("Nhap so the: ");
			String sothe = sc.nextLine();
			System.out.println("Nhap ma PIN: ");
			String maPIN = sc.nextLine();
			String cmd = "CARDNUMBER"+sothe+"ENDCARDNUMBER PIN"+maPIN+"ENDPIN"+" REQUESTLOGINENDREQUEST";
			os.write(cmd.getBytes());
			
			int check = 0;
			byte[] dataByte = new byte[100];
			check = is.read(dataByte);
			String response = new String(dataByte).trim();
			if(response.equals("1")) {
				System.out.println("Dang nhap thanh cong!");
			} else {
				System.out.println("Sai ma PIN!");
				return;
			}
			
			// query
			System.out.println("\t1. Kiem tra so du");
			System.out.println("\t2. Rut tien");
			
			while(true) {
				String input = sc.nextLine();
				if(input.equals("1")) {
					cmd = "REQUESTKTENDREQUEST";
					break;
				} else if(input.equals("2")) {
					System.out.println("Nhap so tien: ");
					amount = sc.nextLine();
					cmd = "CARDNUMBER"+sothe+"ENDCARDNUMBER REQUESTRUTENDREQUEST AMOUNT"+amount+"ENDAMOUNT";
					break;
				} else {
					System.out.println("Yeu cau khong hop le. Vui long nhap lai!");
				}
			}
						
			os.write(cmd.getBytes());
			
			check = 0;
			byte[] b = new byte[1000];
			check = is.read(b);
			String str = new String(b).trim();
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	private static String getUserPhoneNumber() {
//		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//		InputStream fileUserData = classloader.getResourceAsStream("userdata.txt");
//		try {
//			Scanner sc = new Scanner(fileUserData);
//			while(sc.hasNextLine()) {
//				String s = sc.nextLine().trim();
//				if(s.indexOf("CARDNUMBER") >= 0) {
//					return s.substring(s.indexOf("=")+1);
//				}
//			}
//			sc.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//	private static String getUserPINCode() {
//		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//		InputStream fileUserData = classloader.getResourceAsStream("userdata.txt");
//		try {
//			Scanner sc = new Scanner(fileUserData);
//			while(sc.hasNextLine()) {
//				String s = sc.nextLine().trim();
//				if(s.indexOf("PIN") >= 0) {
//					return s.substring(s.indexOf("=")+1);
//				}
//			}
//			sc.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
}

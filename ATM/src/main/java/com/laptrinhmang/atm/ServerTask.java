package com.laptrinhmang.atm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import components.AccountInfo;

public class ServerTask extends Thread {
	private Socket s;
	public ServerTask(Socket s){
		this.s = s;
	}
	public void run() {
		try {
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			int check = 0;
			
			while(true) {
				double sotien = 0;
				int _500 = 0;
				int _200 = 0;
				int _100 = 0;
				int _50 = 0;
				
				byte[] b = new byte[1000];
				check = is.read(b);
				String cmd = new String(b).trim();
				String cardnumber = "";
				String pin = "";
				String request = "";
				String amount = "";
				
				if(cmd.indexOf("CARDNUMBER") >= 0){
					cardnumber = cmd.substring(cmd.indexOf("CARDNUMBER")+10, cmd.indexOf("ENDCARDNUMBER"));
				}
				if(cmd.indexOf("PIN") >= 0){
					pin = cmd.substring(cmd.indexOf("PIN")+3, cmd.indexOf("ENDPIN"));
				}
				if(cmd.indexOf("REQUEST") >= 0){
					request = cmd.substring(cmd.indexOf("REQUEST")+7, cmd.indexOf("ENDREQUEST"));
				}
				if(cmd.indexOf("AMOUNT") >= 0){
					amount = cmd.substring(cmd.indexOf("AMOUNT")+6, cmd.indexOf("ENDAMOUNT"));
					sotien = Double.parseDouble(amount);
				}
				System.out.println("card number: "+cardnumber+", PIN: "+pin+", request: "+request+", amount: "+amount);

		        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		        dfs.setCurrencySymbol("VND");
		        dfs.setGroupingSeparator('.');
		        dfs.setMonetaryDecimalSeparator(',');
		        numberFormat.setMaximumFractionDigits(0);
		        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(dfs);
		        				
		        if(cmd.indexOf("LOGIN") >= 0) {
		        	String notify = "0";
		        	if(isLoginValid(cardnumber, pin)) {
		        		notify = "1";
		        	}
		        	os.write(notify.getBytes());
		        } else if(cmd.indexOf("RUT") >= 0) {
					String notify = "";
					if(Integer.parseInt(getAccountBalance(cardnumber)) >= sotien) {
						if(sotien == 0) {
							System.out.println("Failed!");
							notify = "So tien da nhap khong hop le!";
							os.write(notify.getBytes());
							return;
						}
						while(sotien > 0) {
							if(sotien >= 500000) {
								sotien -= 500000;
								_500++;
							} else if(sotien < 500000 && sotien >= 200000) {
								sotien -= 200000;
								_200++;
							} else if(sotien < 200000 && sotien >= 100000) {
								sotien -= 100000;
								_100++;
							} else if(sotien == 50000) {
								sotien -= 50000;
								_50++;
							} else {
								System.out.println("Failed!");
								notify = "So tien da nhap khong hop le!";
								os.write(notify.getBytes());
								return;
							}
						}
						System.out.println("Successfully!");
						updateAccountBalance(cardnumber, amount);
						notify = "Amount: "+numberFormat.format(Double.parseDouble(amount))+"\n"+"Balance: "+numberFormat.format(Double.parseDouble(getAccountBalance(cardnumber)))+
								"\nSo to 500: "+_500+"\nSo to 200: "+_200+"\nSo to 100: "+_100+"\nSo to  50: "+_50 + 
								"\nTong so to: "+(_500+_200+_100+_50);
					} else {
						System.out.println("Failed!");
						notify = "So tien trong tai khoan khong du!";
					}
					os.write(notify.getBytes());
				} else if(cmd.indexOf("KT") >= 0) {
					double B = 0;
					B = Double.parseDouble(getAccountBalance(cardnumber));
					String report = "Balance: " + numberFormat.format(B);
					os.write(report.getBytes());
				}
			}
		} catch (IOException e) {
		}
	}
	
	private boolean isLoginValid(String cardnumber, String PIN) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream fileAccount = classloader.getResourceAsStream("account.txt");
		try {
			Scanner sc = new Scanner(fileAccount);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf(cardnumber) >= 0) {
					String sysPIN = s.substring(3, s.indexOf(" "));
					return sysPIN.equals(PIN);
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getAccountBalance(String cardnumber) {
		
//		for(int i=1; i<=20; i++) {
//			try {
//				System.out.println("Count " + i);
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream fileAccount = classloader.getResourceAsStream("account.txt");
		try {
			Scanner sc = new Scanner(fileAccount);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf(cardnumber) >= 0) {
					return s.substring(s.indexOf("BALANCE")+7);
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private void updateAccountBalance(String cardnumber, String amount) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream fileAccount = classloader.getResourceAsStream("account.txt");
		try {
			Scanner sc = new Scanner(fileAccount);
			String oldBalance = "";
			String newBalance = "";
			while(sc.hasNextLine()) {
				String s = sc.nextLine();
				if(s.indexOf(cardnumber) >= 0) {
					oldBalance = s.substring(s.indexOf("BALANCE")+7);
					newBalance = (Integer.parseInt(oldBalance)-Integer.parseInt(amount))+"";
				}
			}
			
			String p = classloader.getResource("account.txt").getPath().substring(1);
			Path path = Paths.get(p);
			
			String content;
			try {
				content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
				String oldString = "CARDNUMBER"+cardnumber+" BALANCE"+oldBalance;
				String newString = "CARDNUMBER"+cardnumber+" BALANCE"+newBalance;
				content = content.replace(oldString, newString);								
				Files.write(path, content.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

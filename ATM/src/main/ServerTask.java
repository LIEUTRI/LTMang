package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

public class ServerTask extends Thread {
	private Socket s;
	private ServerTask instance;
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
				int total = 0;
				
				byte[] b = new byte[1000];
				check = is.read(b);
				String cmd = new String(b).trim();
				String phonenumber = cmd.substring(0,cmd.indexOf(" "));
				String request = cmd.substring(cmd.indexOf(" ")+1, cmd.lastIndexOf(" "));
				String amount = cmd.substring(cmd.lastIndexOf(" ")+1);
				System.out.println("phone: "+phonenumber+", request: "+request+", amount: "+amount);
				sotien = Double.parseDouble(amount);

		        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		        dfs.setCurrencySymbol("VND");
		        dfs.setGroupingSeparator('.');
		        dfs.setMonetaryDecimalSeparator(',');
		        numberFormat.setMaximumFractionDigits(0);
		        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(dfs);
		        				
				if(cmd.indexOf("RUT") >= 0) {
					String notify = "";
					if(Integer.parseInt(getAccountBalance(phonenumber)) >= sotien) {
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
						updateAccountBalance(phonenumber, amount);
						notify = "Amount: "+numberFormat.format(Double.parseDouble(amount))+"\n"+"Balance: "+numberFormat.format(Double.parseDouble(getAccountBalance(phonenumber)))+
								"\nSo to 500: "+_500+"\nSo to 200: "+_200+"\nSo to 100: "+_100+"\nSo to 50: "+_50 + 
								"\nTong so to: "+(_500+_200+_100+_50);
					} else {
						System.out.println("Failed!");
						notify = "So tien trong tai khoan khong du!";
					}
					os.write(notify.getBytes());
				} else if(cmd.indexOf("KT") >= 0) {
					String report = "Balance: " + numberFormat.format(Double.parseDouble(getAccountBalance(phonenumber)));
					os.write(report.getBytes());
				}
			}
		} catch (IOException e) {
		}
	}
	
	private String getAccountBalance(String phone) {
		File file = new File(new File("resources").getAbsolutePath()+"/account.txt");
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf(phone) >= 0) {
					return s.substring(s.indexOf("BALANCE")+7);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error! File "+new File("resources").getAbsolutePath()+"/account.conf not found!");
		}
	
		return "";
	}
	
	private void updateAccountBalance(String phone, String amount) {
		File file = new File(new File("resources").getAbsolutePath()+"/account.txt");
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String s = sc.nextLine().trim();
				if(s.indexOf(phone) >= 0) {
					String oldBalance = s.substring(s.indexOf("BALANCE")+7);
					String newBalance = (Integer.parseInt(oldBalance)-Integer.parseInt(amount))+"";
					
					Path path = Paths.get(new File("resources").getAbsolutePath()+"/account.txt");
					String content;
					try {
						content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
						content = content.replace("PHONE"+phone+" BALANCE"+oldBalance, "PHONE"+phone+" BALANCE"+newBalance);
						Files.write(path, content.getBytes(StandardCharsets.UTF_8));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error! File "+new File("resources").getAbsolutePath()+"/account.conf not found!");
		}
	}
}

package components;

import java.io.InputStream;
import java.util.Scanner;

public class AccountInfo {
	private String cardnumber;
	private String balance;
	public AccountInfo(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	
public synchronized String getBalance() {
		
		for(int i=1; i<=20; i++) {
			try {
				System.out.println("Count " + i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
}

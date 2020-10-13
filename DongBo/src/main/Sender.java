package main;

//A Class used to send a message 
class Sender 
{ 
 public synchronized void send(String msg) 
 { 
     System.out.println("Sending\t"  + msg ); 
     for(int i=1; i<=20; i++) {
			try {
				System.out.println("Count " + i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
     System.out.println("\n" + msg + "Sent"); 
 } 
} 
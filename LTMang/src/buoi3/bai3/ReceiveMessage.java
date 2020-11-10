package buoi3.bai3;

import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

 

public class ReceiveMessage extends Thread {

	

	private DatagramSocket ds;

	public ReceiveMessage(DatagramSocket ds) {

		this.ds = ds;

	}

	public void run() {

		try {

			while(true) {

				byte[] dataByte = new byte[60000];

				DatagramPacket in = new DatagramPacket(dataByte, dataByte.length);

				ds.receive(in);

				String dataString = new String(in.getData()).trim();

				String h = in.getAddress().getHostAddress();

				if(!h.equals(InetAddress.getLocalHost().getHostAddress())) {

					System.out.println(in.getAddress().getHostAddress() + ": "+dataString);

				}

				System.out.println(in.getAddress().getHostAddress() + ": "+dataString);

			}

		} catch (SocketException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch(IOException e) {

			e.printStackTrace();

		}

	}

}
package Buoi3.Bai3;

import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

import java.net.UnknownHostException;

import java.util.Scanner;

 

public class TalkClient {

 

	public static void main(String[] args) {

		System.out.println("Started !!!");

		InetAddress host = null;

		try {

			host = InetAddress.getByName(args.length > 0 ? args[0] : "172.31.4.40");

		} catch (UnknownHostException e1) {

			e1.printStackTrace();

		}

		int port = args.length > 0 ? Integer.parseInt(args[1]) : 1998;

		

		try {

			DatagramSocket ds = new DatagramSocket();

			Scanner sc = new Scanner(System.in);

			

			ReceiveMessage task = new ReceiveMessage(ds);

			task.start();

			while(true) {

				System.out.println("Type a message: ");

				String input = sc.nextLine();

				

				DatagramPacket out = new DatagramPacket(input.getBytes(), input.length(), host, port);

				ds.send(out);

			}

		} catch (SocketException e) {

			e.printStackTrace();

		} catch(UnknownHostException e) {

			e.printStackTrace();

		} catch(IOException e) {

			e.printStackTrace();

		}

	}

 

}
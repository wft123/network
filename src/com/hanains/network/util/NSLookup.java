package com.hanains.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String hostName = scanner.nextLine();
			if(hostName.trim().equals("exit")) return;
			try {
				InetAddress[] allHostName = InetAddress.getAllByName(hostName);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

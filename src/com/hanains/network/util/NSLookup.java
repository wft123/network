package com.hanains.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print("> ");
			String hostName = scanner.nextLine();
			if("exit".trim().equals(hostName)) return;
			try {
				InetAddress[] allHost = InetAddress.getAllByName(hostName);
				for(InetAddress host : allHost){
					System.out.println(host.getHostName()+" : " + host.getHostAddress());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}
}

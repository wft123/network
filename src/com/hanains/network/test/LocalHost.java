package com.hanains.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	public static void main(String[] args) {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("Host 이름:"+localHost.getHostName());
			System.out.println("Host IP Address:"+localHost.getHostAddress());
			
			byte[] addresses = localHost.getAddress();
			for(byte b : addresses){
				System.out.print(b&0xff);
				if(b!=addresses[addresses.length-1]){System.out.print(".");};
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}

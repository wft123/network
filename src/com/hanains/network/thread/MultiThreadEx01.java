package com.hanains.network.thread;

public class MultiThreadEx01 {
	public static void main(String[] args) {
		
		new DigitThread().start();
		
		for(char c='A'; c<='Z' ; c++){
			System.out.print(c);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

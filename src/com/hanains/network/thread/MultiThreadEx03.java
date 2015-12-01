package com.hanains.network.thread;

public class MultiThreadEx03 {
	public static void main(String[] args) {
		Thread thread =new Thread(new DigitRunnubleImpl());
		thread.start();
		
		new LowerCaseAlphabetThread().start();
		
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

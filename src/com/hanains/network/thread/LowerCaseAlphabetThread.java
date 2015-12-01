package com.hanains.network.thread;

public class LowerCaseAlphabetThread extends Thread{
	@Override
	public void run() {
		for(char c='a'; c<='z' ; c++){
			System.out.print(c);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

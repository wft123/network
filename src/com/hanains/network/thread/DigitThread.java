package com.hanains.network.thread;

public class DigitThread extends Thread {
	@Override
	public void run() {
		for(int i = 0; i<10;i++){
			System.out.print(i);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package com.hanains.network.thread;

public class DigitRunnubleImpl implements Runnable{
	@Override
	public void run() {
		for(int i = 0; i<10;i++){
			System.out.print(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread{
	
	private BufferedReader bufferedReader;
	
	public ChatClientThread( BufferedReader bufferedReader){
		this.bufferedReader = bufferedReader;
	}
	@Override
	public void run() {
		String request="";
		while(true){
			try {
				request = bufferedReader.readLine();
				if(request==null){
					break;
				}
				String[] tokens = request.split(":");
				if("message".equalsIgnoreCase(tokens[0])){
					System.out.println(tokens[1]);
				}else if("quit".equalsIgnoreCase(tokens[0])){
					System.out.println(tokens[1]);
					break;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

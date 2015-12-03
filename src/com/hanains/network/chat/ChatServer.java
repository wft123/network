package com.hanains.network.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class ChatServer {
	private static final int PORT = 5050;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Hashtable<String, Client> clientTable = new Hashtable<String, Client>();

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();
		
			// 2. 바인딩
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log("연결 기다림 " + hostAddress + ":" + PORT);

			// 3. 요청 대기
			while (true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, clientTable).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String msg){
		System.out.println(msg);
	}
}

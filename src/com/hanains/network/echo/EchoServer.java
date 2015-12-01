package com.hanains.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class EchoServer {
	private static final int PORT = 5050;
	private static List<Socket> socketList = new Vector<Socket>();
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1.서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩
			InetAddress host = InetAddress.getLocalHost();
			serverSocket.bind(new InetSocketAddress(host.getHostAddress(),PORT));
			System.out.println("[서버]바인딩: "+host.getHostAddress()+":"+PORT);
			
			while(true){
				//3. 연결요청대기(accept)
				Socket socket = serverSocket.accept();
				
				//4. 연결 성공
				InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
				int remoteHostPort = inetSocketAddress.getPort();
				System.out.println("[서버] connected from_"+remoteHostAddress+":"+remoteHostPort);
				socketList.add(socket);
				new EchoServerReceiveThread(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

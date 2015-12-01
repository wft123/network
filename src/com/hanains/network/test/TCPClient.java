package com.hanains.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP="192.168.1.14";
	private static final int PORT = 5050;
	
	public static void main(String[] args) {
		Socket socket = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			//1. 소켓 생성
			socket = new Socket();
			
			//2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, PORT));
			System.out.println("[클라이언트]서버연결 성공");
			
			//3. IOStream 받아오기
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			//4. 쓰기/읽기
			String data = "Hello World";
			outputStream.write(data.getBytes("UTF-8"));
			
			byte[] buffer = new byte[256];
			int readByteCount = inputStream.read(buffer);
			
			data = new String(buffer,0,readByteCount,"UTF-8");
			System.out.println(">> "+data);
			
		} catch (IOException e) {
			System.out.println("[클라이언트]에러: "+e);
		}finally{
			try {
				if(outputStream!=null) outputStream.close();
				if(inputStream!=null) inputStream.close();
//				if(socket!=null && !socket.isClosed()) socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

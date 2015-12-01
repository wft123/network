package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP="192.168.1.14";
	private static final int PORT = 5050;
	private static Socket socket = null;
	
	public static void main(String[] args) {
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
			
			Scanner scanner = new Scanner(System.in);
			
			while(true){
				String data = scanner.nextLine();
				if("exit".equals(data.trim())) break;
				
				//4. 쓰기/읽기
				outputStream.write(data.getBytes("UTF-8"));
				outputStream.flush();
				
				byte[] buffer = new byte[256];
				int readByteCount = inputStream.read(buffer);
				
				data = new String(buffer,0,readByteCount,"UTF-8");
				System.out.println(">> "+data);
			}
			
		} catch (IOException e) {
			System.out.println("[클라이언트]에러: "+e);
		}finally{
			try {
				if(outputStream!=null) outputStream.close();
				if(inputStream!=null) inputStream.close();
				if(socket!=null && !socket.isClosed()) socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

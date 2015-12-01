package com.hanains.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.print.DocFlavor.INPUT_STREAM;

public class TCPServer {

	private static final int PORT = 5050;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			//1.서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩
			InetAddress host = InetAddress.getLocalHost();
			serverSocket.bind(new InetSocketAddress(host.getHostAddress(),PORT));
			System.out.println("[서버]바인딩: "+host.getHostAddress()+":"+PORT);
			
			//3. 연결요청대기(accept)
			Socket socket = serverSocket.accept();
			
			//4. 연결 성공
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[서버] connected from_"+remoteHostAddress+":"+remoteHostPort);
			
			//5. IOStream 받아오기
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			//6. 데이터 받기
			try{
				byte[] buffer = new byte[256];
				while(true){
					int readByte = is.read(buffer);
					if(readByte<0){
						System.out.println("종료되었습니다.");
						break;
					}
					String data = new String(buffer,0,readByte);
					System.out.println("[서버]수신 데이터:"+data);
					
					//7. 데이터 보내기
					os.write(data.getBytes("UTF-8"));
					os.flush();
				}
			}catch(IOException ex){
				System.out.println("[서버]에러: "+ex);
			}finally{
				//8. 자원 정리
				if(os!=null) os.close();
				if(is!=null) is.close();
				if(socket!=null && !socket.isClosed()) socket.close();
			}
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}

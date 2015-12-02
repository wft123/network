package com.hanains.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {
	
	Socket socket = null;
	BroadCast bc = BroadCast.getInstance();
	
	public EchoServerReceiveThread (Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		//4. 연결 성공
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
		int remoteHostPort = inetSocketAddress.getPort();
		System.out.println("[서버] connected from_"+remoteHostAddress+":"+remoteHostPort);
		
		//5. IOStream 받아오기
		PrintWriter printWriter=null;
		BufferedReader bufferedReader=null;
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//6. 데이터 받기
			try{
				String clientInfo = socket.getInetAddress().getHostAddress()+":"+socket.getPort();
				while(true){
					String data = bufferedReader.readLine();
					if(data==null){
						System.out.println("["+clientInfo+"]가 종료되었습니다.");
						break;
					}
					System.out.println("["+clientInfo+"]: "+data);
					
					//7. 데이터 보내기
					printWriter.println(data);
					printWriter.flush();
				}
			}catch(IOException ex){
				System.out.println("[서버]에러: "+ex);
			}finally{
				//8. 자원 정리
				if(printWriter!=null) printWriter.close();
				if(bufferedReader!=null) bufferedReader.close();
				if(socket!=null && !socket.isClosed()) socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {
	
	Socket socket = null;
	BroadCast bc = BroadCast.getInstance();
	
	public EchoServerReceiveThread (Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		//5. IOStream 받아오기
		OutputStream outputStream=null;
		InputStream inputStream=null;
		try {
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			
			//6. 데이터 받기
			try{
				byte[] buffer = new byte[256];
				while(true){
					int readByte = inputStream.read(buffer);
					if(readByte<0){
						System.out.println("종료되었습니다.");
						break;
					}
					String data = new String(buffer,0,readByte);
					String clientInfo = socket.getInetAddress().getHostAddress()+":"+socket.getPort();
					System.out.println("["+clientInfo+"]:"+data);
					
					//7. 데이터 보내기
					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
				}
			}catch(IOException ex){
				System.out.println("[서버]에러: "+ex);
			}finally{
				//8. 자원 정리
				if(outputStream!=null) outputStream.close();
				if(inputStream!=null) inputStream.close();
				if(socket!=null && !socket.isClosed()) socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

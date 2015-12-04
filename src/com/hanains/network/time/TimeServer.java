package com.hanains.network.time;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private static final int PORT = 9090;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		
		try {
			//1. UDP 소켓 생성
			datagramSocket = new DatagramSocket(PORT);
			
			while(true){
				log("TimeServer 대기중...");
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				datagramSocket.receive(receivePacket);
				
				//3. 데이터 받음
				String data = new String(receivePacket.getData(),0, receivePacket.getLength(),"UTF-8");
				log("요청!");
				
				if(data.trim().length()==0){
					SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
					byte[] sendData = format.format( new Date() ).getBytes("UTF-8");
					//4. 데이터 전송
					DatagramPacket sendPacket = new DatagramPacket(
							sendData,
							sendData.length,
							receivePacket.getAddress(),
							receivePacket.getPort());
					
					//5. 데이터 전송
					datagramSocket.send(sendPacket);
				}
				
			}
			
		} catch (Exception e) {
			log("error:"+e);
		} finally {
			if(datagramSocket!=null) datagramSocket.close();
		}
		
	}
	
	public static void log(String message){
		System.out.println("[TimeServer]"+message);
	}
}

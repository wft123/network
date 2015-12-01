package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class BroadCast {
	private List<Socket> socketList=null;
	private static BroadCast bc = null;
	
	private BroadCast(){
		this.socketList = new Vector<Socket>();
	}
	
	public static BroadCast getInstance(){
		if(bc==null) bc = new BroadCast();
		return bc;
	}
	
	public void add(Socket socket){
		if(socket!=null){
			socketList.add(socket);
			System.out.println("클라이언트 현재: "+socketList.size()+"명");
		}
	}
	
	public void sendAll(String msg){
		OutputStream outputStream=null;
			try {
				for(Socket socket : socketList){
				outputStream = socket.getOutputStream();
				outputStream.write(msg.getBytes("UTF-8"));
				outputStream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(outputStream != null)
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
	}
	
	
}

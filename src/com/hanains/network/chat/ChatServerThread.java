package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

public class ChatServerThread extends Thread {

	private Client client;
	private Socket socket;
	private Hashtable<String, Client> clientTable;
	

	public ChatServerThread(Socket socket, Hashtable<String, Client> clientTable) {
		this.socket = socket;
		this.clientTable = clientTable;
	}

	@Override
	public void run() {
		//5. IOStream 받아오기
		PrintWriter printWriter=null;
		BufferedReader bufferedReader=null;
		
		try {
			bufferedReader = 	new BufferedReader( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
			printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );
			
			while(true){
				String request = bufferedReader.readLine();
				
				if(request==null){
					ChatServer.log( client.getName()+"님으로 부터 연결 끊김" );
					doQuit(printWriter);
					break;
				}
				String[] tokens = request.split( ":" );
				
				if( "join".equalsIgnoreCase( tokens[0] ) ) {
					doJoin( tokens[1], printWriter );
				}else if("talk".equalsIgnoreCase(tokens[1])){
					doTalk(tokens[2],tokens[3]);
				}else if( "message".equalsIgnoreCase( tokens[0] ) ) {
					doMessage(tokens[1]);
				} else if( "quit".equalsIgnoreCase( tokens[0] ) ) {
					doQuit(printWriter);
					break;
				} else {
					ChatServer.log( "에러:알수 없는 요청(" + tokens[0] + ")" );
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//8. 자원 정리
				try {
					if(printWriter!=null) printWriter.close();
					if(bufferedReader!=null)	bufferedReader.close();
					if(socket!=null && !socket.isClosed()) socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	private void doJoin(String token, PrintWriter printWriter){
		String ack="";
		if(!clientTable.containsKey(token)){
			client = new Client(token, printWriter);
			String data = "message:"+token + "님이 참여하였습니다."; 
			System.out.println(data.split(":")[1]);
			broadcast( data );
			clientTable.put(token, client);
			ack = "join:ok";
		}else{
			ack = "join:fail:중복된 닉네임입니다.";
		}
		printWriter.println(ack);
		printWriter.flush();
	}
	
	private void doTalk(String name, String msg){
		if(clientTable.containsKey(name)){
			PrintWriter pw = clientTable.get(name).getPrintWriter();
			pw.println("message:("+client.getName()+")>>"+msg);
			pw.flush();
		}else{
			client.getPrintWriter().println("message:접속하지 않은 ID입니다");
			client.getPrintWriter().flush();
		}
	}
	
	private void doMessage(String message){
		broadcast( "Message:"+client.getName()+">>"+message );
	}
	
	private void doQuit(PrintWriter printWriter){
		String quit ="quit:안녕히가세요~";
		printWriter.println(quit);
		printWriter.flush();
		clientTable.remove(client.getName());
		String data = "message:"+client.getName() + "님이 퇴장 하였습니다."; 
		System.out.println(data.split(":")[1]);
		broadcast(data);
	}
	
	private void broadcast(String data) {
		for (String name: clientTable.keySet()) {
			PrintWriter printWriter = clientTable.get(name).getPrintWriter();
			printWriter.println(data);
			printWriter.flush();
		}
	}
}

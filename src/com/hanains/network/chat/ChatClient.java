package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP = "192.168.1.14";
	private static int PORT = 5050;

	public static void main(String[] args) {
		if (args.length > 0) {
			SERVER_IP = args[0];
			PORT = Integer.parseInt(args[1]);
		}

		Socket socket = null;
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;

		try	{
			Scanner scanner = new Scanner(System.in);
			// 1. 소켓 생성
			socket = new Socket();

			// 2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, PORT));
			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);
			
			while(true){
				System.out.print("닉네임 >> ");
				String nickName=scanner.nextLine();
				printWriter.println( "join:" + nickName );
				printWriter.flush();
				String[] tokens = bufferedReader.readLine().split(":");
				if("ok".equals(tokens[1])){
					break;
				}else{
					System.out.println(tokens[2]);
				}
			}
			
			new ChatClientThread(bufferedReader).start();
			
			while (true) {
				String input = scanner.nextLine();
				if( "quit".equals( input ) == true ) {
					doQuit(printWriter);
					break;
				} else {
					doMessage(input,printWriter);
				}
			}
			
		}catch (IOException e){
			e.printStackTrace();
		} finally{
			// 8. 자원 정리
				try {
					if (printWriter != null) printWriter.close();
					if (bufferedReader != null)	bufferedReader.close();
					if (socket != null && !socket.isClosed()) 	socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	private static void doQuit(PrintWriter printWriter){
		printWriter.println( "quit:퇴장하였습니다." );
		printWriter.flush();
	}
	
	private static void doMessage(String msg, PrintWriter printWriter){
		printWriter.println( "Message:"+ msg );
		printWriter.flush();
	}
}

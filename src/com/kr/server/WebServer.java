package com.kr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

	private static final Logger log = LoggerFactory.getLogger(WebServer.class);
	// 포트번호
	private static final int PORT = 12345;
	
	public static void main(String[] args) {
		log.info("main start debug");
		
		try {
			// 서버소켓 오픈(클라이언트에서 접속 가능)
			ServerSocket serverSocket = new ServerSocket(PORT);

			Socket socket;
			// 대기 중
			while ( (socket = serverSocket.accept()) != null  ) { // 서버소켓 동작 중 
				
				RequestHandler requestHandler = new RequestHandler(socket);
				requestHandler.start();
			}
		
		} catch (IOException e) {
			log.info("exception ServerSocket");
			e.printStackTrace();
		} finally {
			
		}
		
	}
}

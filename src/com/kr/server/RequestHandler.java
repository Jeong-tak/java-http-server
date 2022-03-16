package com.kr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

	public static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	private Socket socket;
	
	public RequestHandler(Socket connectedSocket) {
		this.socket = connectedSocket;
	}
	
	@Override
	public void run() {
		
		try {
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader( new InputStreamReader(in, "UTF-8") );
			
			String line;
			while( (line = br.readLine()) != null ) {
				log.info("socket getLineOfData :: " + line);
			}
			
		} catch(IOException e) {
			
			log.info("exception socket");
		}
	}
}

package com.kr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kr.util.RequestUtil;

public class RequestHandler extends Thread {

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	private Socket socket;
	
	public RequestHandler(Socket connectedSocket) {
		this.socket = connectedSocket;
	}
	
	@Override
	public void run() {
		
		try( InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream(); ) {
			log.info("Create new connection[Connected IP :: {}, PORT :: {}]", socket.getInetAddress(), socket.getPort());
			
			BufferedReader br = new BufferedReader( new InputStreamReader(in, "UTF-8") );

			String line1 = br.readLine();
			log.info("First Line :: {}", line1);
			
			Map<String, String> headerMap = RequestUtil.readHeader(br);
			String contentLenStr = headerMap.get("Content-Length");
			if ( contentLenStr != null && !"".equals(contentLenStr) ) {
				
				log.info("Content-Length :: {}", contentLenStr);
				
				String requestBody = RequestUtil.readData(br, Integer.valueOf(contentLenStr));
				
				log.info("requestBody :: {}", requestBody.trim());
				
			} else {
				log.error("No Content-Length");
			}
			
			
		} catch(IOException e) {
			
			log.info("exception socket");
		}
	}
}

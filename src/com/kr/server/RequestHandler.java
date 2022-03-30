package com.kr.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kr.util.RequestUtil;
import com.kr.util.ResponseUtil;

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

			String firstLine = br.readLine();
			String[] firstLineArr = firstLine.split(" ");
			if ( firstLineArr.length == 3 ) {
				String httpMethod 	= firstLineArr[0];
				String httpUrl	  	= "/".equals(firstLineArr[1]) ? "/index.html" : firstLineArr[1];
				String httpProtocol = firstLineArr[2];
				log.info("HTTP Method 	:: {}", httpMethod);
				log.info("HTTP Url 		:: {}", httpUrl);
				log.info("HTTP Protocol	:: {}", httpProtocol);
				
				Map<String, String> headerMap = RequestUtil.readHeader(br);
				
				if ( !"GET".equals(httpMethod) ) {
					
					String contentLenStr = headerMap.get("Content-Length");
					log.info("Content-Length : {}", contentLenStr);

					String requestBody = RequestUtil.readData(br, Integer.valueOf(contentLenStr));
					log.info("requestBody :: {}", requestBody.trim());
				}
				
				DataOutputStream dos = new DataOutputStream(out);

				File pageFile = new File("./webContent" + httpUrl);
				
				//byte[] body = "Hello Welcom To The World".getBytes();
				byte[] body;
				
				if ( pageFile.exists() ) {
					
					body = Files.readAllBytes(pageFile.toPath());
				} else {
					body = Files.readAllBytes( new File("./webContent/errorPage.html").toPath());
				}
				
				ResponseUtil.response200Header(dos, body.length);
				ResponseUtil.responseBody(dos, body);
				
			} else {
				throw new Exception("Invalid Header Format");
			}
			
		} catch(IOException e) {
			
			log.info("exception socket :: {}", e);
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
}

package com.kr.util;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtil {

	public static Logger log = LoggerFactory.getLogger(ResponseUtil.class);
	
	
	public static void response200Header(DataOutputStream dos, int lenOfbodyContent) {
		
		try {
			
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-length: " + lenOfbodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch(IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void responseBody(DataOutputStream dos, byte[] body) {
		
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch(IOException e) {
			log.error(e.getMessage());
		}
	}
	
}

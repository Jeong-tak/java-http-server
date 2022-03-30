package com.kr.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilTest {

	private static Logger log = LoggerFactory.getLogger(UtilTest.class);
	
	public static void main(String[] args) throws IOException {
		
		String test = null;
		System.out.println( test.isEmpty());
	
		String sampleStr = "";
		sampleStr += "GET / HTTP/1.1\n";
		sampleStr += "Host: localhost:12345\n";
		sampleStr += "Connection: keep-alive\n";
		sampleStr += "Cache-Control: max-age=0\n";
		sampleStr += "sec-ch-ua: \" Not A;Brand\";v=\"99\", \"Chromium\";v=\"99\", \"Google Chrome\";v=\"99\"\n";
		sampleStr += "sec-ch-ua-mobile: ?0\n";
		sampleStr += "sec-ch-ua-platform: \"Windows\"\n";
		sampleStr += "Upgrade-Insecure-Requests: 1\n";
		sampleStr += "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36\n";
		sampleStr += "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n";
		sampleStr += "Sec-Fetch-Site: none\n";
		sampleStr += "Sec-Fetch-Mode: navigate\n";
		sampleStr += "Sec-Fetch-User: ?1\n";
		sampleStr += "Sec-Fetch-Dest: document\n";
		sampleStr += "Accept-Encoding: gzip, deflate, br\n";
		sampleStr += "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6,zh-TW;q=0.5,zh;q=0.4";
		
		StringReader sr = new StringReader(sampleStr);
		// (method) readData
		//String returnData = readData(sr, sampleStr.length());

		// (method) getHeaderData
		InputStream in =  (InputStream) new ByteArrayInputStream(sampleStr.getBytes());
		Map<String, String> headerData = readHeader(in);
		
		
	}
	
	/**
	 * Reader를 통해 읽어낸 데이터 String 형태로 변환
	 * 
	 *  */
	public static String readData(StringReader sr, int contentLen) throws IOException {
		
		char[] body = new char[contentLen];

		sr.read(body, 0, contentLen);
		
		log.info("[readData] return {}", String.copyValueOf(body));
		
		return String.copyValueOf(body);
	}
	
	public static Map<String, String> readHeader(InputStream in) throws IOException {
		
		BufferedReader br = new BufferedReader( new InputStreamReader(in) );
		Map<String, String> map = new HashMap<String, String>();
		
		// Map 형태로 변환
		String line;
		while((line = br.readLine()) != null) {
			
			log.info(line);
			
			if ( !"".equals(line) ) {
				String[] splitedLine = line.split(":");
				if ( splitedLine.length > 1 ) {
					
					String key = splitedLine[0];
					String value = splitedLine[1];
					map.put(key, value);
				} else {
					log.error("[readHeader] :: {}", line);
					continue;
				}
			} else {
				break;
			}
		}
		
		// [ValidationCheck Logic] Map 값 검증
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		
		int sizeOfMap = map.size();
		for (int i = 0; i < sizeOfMap; i++ ) {
			String next = iterator.next();
			log.info("Map Data :: key: {}, value: {}", next, map.get(next));
		}
		
		log.info("[readHeader] return {}", map.toString());
		
		return map;
	}
	
	public static void response200Header(DataOutputStream dos, int lenOfBodyContent) throws IOException {
		
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
		dos.writeBytes("Content-Length: " + lenOfBodyContent + "\r\n");
		dos.writeBytes("\r\n");
	}
	
	public static void responseBody(DataOutputStream dos, byte[] body) throws IOException {
		
		dos.write(body, 0, body.length);
		dos.flush();
	}
}

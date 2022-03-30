package com.kr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {

	private static Logger log = LoggerFactory.getLogger(RequestUtil.class);
	
	public static String readData(BufferedReader br, int contentLength) throws IOException {
		
		char[] charArr = new char[contentLength];
		br.read(charArr, 0, contentLength);
		log.debug("[readData] return {}", String.copyValueOf(charArr));
		return String.copyValueOf(charArr);
	}
	
	public static Map<String, String> readHeader(BufferedReader br) throws IOException {
		
		Map<String, String> map = new HashMap<String, String>();
		
		String line;
		while((line = br.readLine()) != null ) {
			
			log.debug(line);
			if ( !"".equals(line) ) {
				
				String[] splitedStr = line.split(":");
				if ( splitedStr.length > 1 ) {
				
					String key = splitedStr[0];
					String value = splitedStr[1].trim();
					
					map.put(key, value);
				} else {
					
					log.error("[readHeader] split string :: {}", line);
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
			log.debug("Map Data :: key: {}, value: {}", next, map.get(next));
		}
		
		log.debug("[readHeader] return {}", map.toString());
		return map;
	}

}

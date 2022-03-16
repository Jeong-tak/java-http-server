package com.kr.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTest {

	public static final Logger log = LoggerFactory.getLogger(LoggingTest.class);
	
	public static void main(String[] args) {
		log.info("Logging Test");
		System.out.println("werwerwe");
	}
}

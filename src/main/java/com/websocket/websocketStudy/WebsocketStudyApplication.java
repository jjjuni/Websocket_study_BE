package com.websocket.websocketStudy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class WebsocketStudyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketStudyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println("Server running at: " + inetAddress.getHostAddress() + ":8080");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}

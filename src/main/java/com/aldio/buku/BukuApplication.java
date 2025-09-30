package com.aldio.buku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BukuApplication {

	public static void main(String[] args) {
		SpringApplication.run(BukuApplication.class, args);
	}

}

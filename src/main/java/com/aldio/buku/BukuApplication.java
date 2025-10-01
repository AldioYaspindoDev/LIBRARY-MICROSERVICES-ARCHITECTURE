package com.aldio.buku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableKafka
@EnableAsync
public class BukuApplication {

	public static void main(String[] args) {
		SpringApplication.run(BukuApplication.class, args);
	}

}

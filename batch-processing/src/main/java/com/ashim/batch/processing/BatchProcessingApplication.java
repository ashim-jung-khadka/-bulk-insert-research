package com.ashim.batch.processing;

import com.ashim.batch.processing.runner.Processing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author ashimjk on 11/24/2018
 */
@SpringBootApplication
public class BatchProcessingApplication {

	@Autowired private Processing processing;

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			processing.run();
			System.exit(0);
		};
	}
}

package com.ashim.fxdeals;

import com.ashim.fxdeals.service.StorageService;
import com.ashim.fxdeals.util.FxDealConfig;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.io.PrintWriter;

/**
 * @author ashimjk on 12/1/2018
 */
@SpringBootApplication
@EnableConfigurationProperties(FxDealConfig.class)
public class FxDealsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxDealsApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> {
			// clean upload directory
			storageService.deleteAll();
			storageService.init();

			// clear log
			PrintWriter pw = new PrintWriter("logs/fxdeal.log");
			pw.close();
		};
	}

	@Bean
	public StatelessSession statelessSession(EntityManagerFactory entityManager) {
		return entityManager.unwrap(SessionFactory.class).getSessionFactory().openStatelessSession();
	}

}

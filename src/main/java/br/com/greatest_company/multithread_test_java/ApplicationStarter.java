package br.com.greatest_company.multithread_test_java;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ApplicationStarter implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("EXECUTEI COM SUCESSO");
	}

}

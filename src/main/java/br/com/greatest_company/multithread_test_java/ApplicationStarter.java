package br.com.greatest_company.multithread_test_java;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GithubUserHTTPRepository;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.services.GithubUserServiceImpl;
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
		var repo = new GithubUserHTTPRepository();
		var service = new GithubUserServiceImpl(repo);
		var result = service.get(10, 0);
		for(GithubUserDTO dto : result) {
			log.info(dto.toString());
		}
	}

}

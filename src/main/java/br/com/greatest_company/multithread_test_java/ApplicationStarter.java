package br.com.greatest_company.multithread_test_java;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GithubUserHTTPRepository;
import br.com.greatest_company.multithread_test_java.adapters.repositories.GithubUserMockRepository;
import br.com.greatest_company.multithread_test_java.adapters.repositories.GreatestUserMySQLRepository;
import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.orchestrators.HireGithubUsersOrchestrator;
import br.com.greatest_company.multithread_test_java.app.domain.services.GithubUserServiceImpl;
import br.com.greatest_company.multithread_test_java.app.domain.services.GreatestUserServiceImpl;
import br.com.greatest_company.multithread_test_java.configs.JobConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class ApplicationStarter implements CommandLineRunner{

	@Autowired
	@Qualifier(value = "dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier(value = "jobConfiguration")
	JobConfiguration jobConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			log.info(String.format("JOB with process ID %s starting now: %s", System.getProperty("PID"), LocalDateTime.now()));
			var startTime = Instant.now();
			var githubUserRepo = new GithubUserMockRepository();
			var githubUserService = new GithubUserServiceImpl(githubUserRepo);
			var result = githubUserService.get(10, 0);

			var greatestUserRepo = new GreatestUserMySQLRepository(dataSource);
			var greatestUserService = new GreatestUserServiceImpl(greatestUserRepo);

			new HireGithubUsersOrchestrator(githubUserService, greatestUserService, jobConfiguration).execute();
			var endTime = Instant.now();
			log.info(String.format("JOB finish at: %s", LocalDateTime.now()));
			log.info(String.format("total time secs: %d", Duration.between(startTime, endTime).toSeconds()));
			System.exit(0);
		}catch (Exception e) {
			log.error(e.getMessage());
			System.exit(1);
		}
	}

}

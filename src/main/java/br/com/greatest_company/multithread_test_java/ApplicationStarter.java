package br.com.greatest_company.multithread_test_java;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.greatest_company.multithread_test_java.app.domain.orchestrators.Orchestrator;
import br.com.greatest_company.multithread_test_java.configs.JobConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ApplicationStarter implements CommandLineRunner{

	@Autowired
	@Qualifier(value = "dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier(value = "jobConfiguration")
	JobConfiguration jobConfiguration;

	@Autowired
	Orchestrator orchestrator;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			log.info(String.format("JOB with process ID %s starting now: %s", System.getProperty("PID"), LocalDateTime.now()));
			var startTime = Instant.now();

			orchestrator.execute();
			var endTime = Instant.now();
			log.info(String.format("JOB finish at: %s", LocalDateTime.now()));
			log.info(String.format("total time secs: %d", Duration.between(startTime, endTime).toSeconds()));
			System.exit(0);
		}catch (Exception  e) {
			log.error(e.getMessage());
			System.exit(1);
		}
	}
}

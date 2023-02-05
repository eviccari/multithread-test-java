package br.com.greatest_company.multithread_test_java.adapters.infra;

import br.com.greatest_company.multithread_test_java.configs.JobConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConnection {

    @Autowired
    @Qualifier(value = "jobConfiguration")
    JobConfiguration jobConfiguration;

    @Bean(name = "dataSource")
    public DataSource dataSource() throws Exception{
        var configuration = new HikariConfig();
        configuration.setUsername(jobConfiguration.getDBUser());
        configuration.setPassword(jobConfiguration.getDBPassword());
        configuration.setJdbcUrl(jobConfiguration.getDbURL());
        configuration.setDriverClassName(jobConfiguration.getDbDriverClassName());
        configuration.setMaximumPoolSize(100);

        DataSource dataSource = new HikariDataSource(configuration);
        return dataSource;
    }
}

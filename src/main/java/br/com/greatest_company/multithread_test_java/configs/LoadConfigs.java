package br.com.greatest_company.multithread_test_java.configs;

import br.com.greatest_company.multithread_test_java.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LoadConfigs {

    @Autowired
    Environment env;

    @Bean(name = "jobConfiguration")
    public JobConfiguration configuration() throws Exception{
        var jobConfiguration = JobConfiguration.builder().build();

        jobConfiguration.setEnvironment(System.getenv("ENVIRONMENT"));
        if (StringUtils.isEmpty(jobConfiguration.getEnvironment()))
            jobConfiguration.setEnvironment(env.getProperty("app-params.environment"));

        jobConfiguration.setGithubUsersQuantity(
                System.getenv("GITHUB_USERS_QUANTITY") != null ? Integer.valueOf(System.getenv("GITHUB_USERS_QUANTITY")) : 0
        );
        if(jobConfiguration.getGithubUsersQuantity() == 0)
            jobConfiguration.setGithubUsersQuantity(Integer.valueOf(env.getProperty("app-params.githubUsersQuantity")));

        jobConfiguration.setMultiThreadSize(
                System.getenv("MULTITHREAD_SIZE") != null ? Integer.valueOf(System.getenv("MULTITHREAD_SIZE")) : 0
        );
        if(jobConfiguration.getMultiThreadSize() == 0)
            jobConfiguration.setMultiThreadSize(Integer.parseInt(env.getProperty("app-params.multiThreadSize")));

        jobConfiguration.setGithubAPIMaxPageSize(
                System.getenv("GITHUB_API_MAX_PAGE_SIZE") != null ? Integer.valueOf(System.getenv("GITHUB_API_MAX_PAGE_SIZE")) : 0
        );
        if(jobConfiguration.getGithubAPIMaxPageSize() == 0)
            jobConfiguration.setGithubAPIMaxPageSize(Integer.valueOf(env.getProperty("app-params.githubAPIMaxPageSize")));

        jobConfiguration.setDBUser(System.getenv("DATABASE_USER_NAME"));
        if(StringUtils.isEmpty(jobConfiguration.getDBUser()))
            jobConfiguration.setDBUser(env.getProperty("app-params.dbUser"));

        jobConfiguration.setDBPassword(System.getenv("DATABASE_USER_PASS"));
        if(StringUtils.isEmpty(jobConfiguration.getDBPassword()))
            jobConfiguration.setDBPassword(env.getProperty("app-params.dbPassword"));

        jobConfiguration.setDBHostName(System.getenv("DATABASE_HOST_NAME"));
        if(StringUtils.isEmpty(jobConfiguration.getDBHostName()))
            jobConfiguration.setDBHostName(env.getProperty("app-params.dbHostName"));

        jobConfiguration.setDBPort(System.getenv("DATABASE_PORT"));
        if(StringUtils.isEmpty(jobConfiguration.getDBPort()))
            jobConfiguration.setDBPort(env.getProperty("app-params.dbPort"));

        jobConfiguration.setDBEngine(System.getenv("DATABASE_ENGINE"));
        if(StringUtils.isEmpty(jobConfiguration.getDBEngine()))
            jobConfiguration.setDBEngine(env.getProperty("app-params.dbEngine"));

        jobConfiguration.setDBName(System.getenv("DATABASE_NAME"));
        if(StringUtils.isEmpty(jobConfiguration.getDBName()))
            jobConfiguration.setDBName(env.getProperty("app-params.dbName"));

        jobConfiguration.setDbURL(System.getenv("DATABASE_URL"));
        if(StringUtils.isEmpty(jobConfiguration.getDbURL()))
            jobConfiguration.setDbURL(env.getProperty("app-params.dbURL"));

        jobConfiguration.setDbDriverClassName(System.getenv("DATABASE_DRIVER_CLASS_NAME"));
        if(StringUtils.isEmpty(jobConfiguration.getDbDriverClassName()))
            jobConfiguration.setDbDriverClassName(env.getProperty("app-params.dbDriverClassName"));

        return jobConfiguration;
    }
}

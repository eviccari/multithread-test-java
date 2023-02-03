package br.com.greatest_company.multithread_test_java.configs;

import com.google.gson.Gson;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobConfiguration {

    @Getter
    @Setter
    private String environment;

    @Getter
    @Setter
    private int GithubUsersQuantity;

    @Getter
    @Setter
    private int MultiThreadSize;

    @Getter
    @Setter
    private int GithubAPIMaxPageSize;

    @Getter
    @Setter
    private String DBUser;

    @Getter
    @Setter
    private String DBPassword;

    @Getter
    @Setter
    private String DBHostName;

    @Getter
    @Setter
    private String DBPort;

    @Getter
    @Setter
    private String DBEngine;

    @Getter
    @Setter
    private String DBName;

    @Getter
    @Setter
    private String dbURL;

    @Getter
    @Setter
    private String dbDriverClassName;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

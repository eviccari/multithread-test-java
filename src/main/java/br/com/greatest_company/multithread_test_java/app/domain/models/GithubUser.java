package br.com.greatest_company.multithread_test_java.app.domain.models;

import java.io.Serializable;

import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GithubUser implements Serializable, Validatable{

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String nodeID;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String htmlUrl;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public void validate() throws UnprocessableEntityException {
        var pm = new GithubUserPoliciesManager(this);
        pm.apply();
    }
}
package br.com.greatest_company.multithread_test_java.app.domain.models;

import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class GithubUserPoliciesManager {

    private GithubUser guser;

    public GithubUserPoliciesManager(GithubUser guser) {
        this.guser = guser;
    }

    public Map<String, Predicate<GithubUser>> getPolicies() {
        var polices = new HashMap<String, Predicate<GithubUser>>();
        polices.put("IDCannotBeEmpty", (guser) -> {return guser.getId() > 0;});
        polices.put("URLCannotBeEmpty", (guser) -> {return !StringUtils.isEmpty(guser.getUrl());});
        polices.put("LoginCannotBeEmpty", (guser) -> {return !StringUtils.isEmpty(guser.getLogin());});
        polices.put("NodeIDCannotBeEmpty", (guser) -> {return !StringUtils.isEmpty(guser.getNodeID());});
        polices.put("HTMLUrlCannotBeEmpty", (guser) -> {return !StringUtils.isEmpty(guser.getHtmlUrl());});

        return polices;
    }

    public void apply() throws UnprocessableEntityException {

        var msg = new StringBuilder();

        for(Map.Entry<String, Predicate<GithubUser>> policy : this.getPolicies().entrySet()) {
            msg.append(
                !policy.getValue().test(this.guser) ? String.format("policy:%s failed;", policy.getKey()) : ""
            );
        }

        if(!StringUtils.isEmpty(msg.toString()))
            throw new UnprocessableEntityException(msg.toString());
    }
}

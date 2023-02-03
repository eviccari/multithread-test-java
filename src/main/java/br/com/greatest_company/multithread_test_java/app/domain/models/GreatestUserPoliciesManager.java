package br.com.greatest_company.multithread_test_java.app.domain.models;

import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class GreatestUserPoliciesManager {

    private GreatestUser guser;

    public GreatestUserPoliciesManager(GreatestUser guser) {
        this.guser = guser;
    }

    public Map<String, Predicate<GreatestUser>> getPolicies() {
        var polices = new HashMap<String, Predicate<GreatestUser>>();
        polices.put("IDCannotBeEmpty", (guser) -> {return StringUtils.isNotEmpty(guser.getId());});
        polices.put("LegacyLoginCannotBeEmpty", (guser) -> {return StringUtils.isNotEmpty(guser.getLegacyLogin());});
        polices.put("LegacyIDCannotBeEmpty", (guser) -> {return guser.getLegacyID() > 0;});
        polices.put("LegacyNodeIDCannotBeEmpty", (guser) -> {return StringUtils.isNotEmpty(guser.getLegacyNodeID());});
        polices.put("LegacyURLCannotBeEmpty", (guser) -> {return StringUtils.isNotEmpty(guser.getLegacyURL());});
        polices.put("LegacyHtmlUrlCannotBeEmpty", (guser) -> {return StringUtils.isNotEmpty(guser.getLegacyHtmlUrl());});
        polices.put("NewEmailCannotBeEmpty", (guser) -> {return StringUtils.isNotEmpty(guser.getNewEmail());});

        return polices;
    }

    public void apply() throws UnprocessableEntityException {

        var msg = new StringBuilder();

        for(Map.Entry<String, Predicate<GreatestUser>> policy : this.getPolicies().entrySet()) {
            msg.append(
                !policy.getValue().test(this.guser) ? String.format("policy:%s failed;", policy.getKey()) : ""
            );
        }

        if(!StringUtils.isEmpty(msg.toString()))
            throw new UnprocessableEntityException(msg.toString());
    }
}

package br.com.greatest_company.multithread_test_java.app.domain.orchestrators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.app.domain.services.GithubUserService;
import br.com.greatest_company.multithread_test_java.app.domain.services.GreatestUserService;
import br.com.greatest_company.multithread_test_java.configs.JobConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HireGithubUsersOrchestrator implements Orchestrator{

    @Autowired
    GithubUserService githubUserService;

    @Autowired
    GreatestUserService greatestUserService;

    @Autowired
    JobConfiguration jobConfiguration;

    @Override
    public void execute() throws InternalServerErrorException, BadRequestException, UnprocessableEntityException {
        log.info("EXECUTING WITH MULTI-THREAD");
        log.info(String.format("GITHUB_USERS_QUANTITY: %d", this.jobConfiguration.getGithubUsersQuantity()));
        log.info(String.format("MAX-THREAD-QUANTITY..: %d", this.jobConfiguration.getMultiThreadSize()));
        greatestUserService.setEmpty();

        int pageSize = this.jobConfiguration.getGithubUsersQuantity() < this.jobConfiguration.getGithubAPIMaxPageSize() ?
                this.jobConfiguration.getGithubUsersQuantity() :
                this.jobConfiguration.getGithubAPIMaxPageSize();

        if(pageSize == 0)
            return;

        int since = 0;
        int hired = 0;

        while(hired < this.jobConfiguration.getGithubUsersQuantity()){
            var githubUsers = githubUserService.get(pageSize, since);
            if(githubUsers.isEmpty())
                return;

            since = githubUsers.get(githubUsers.size() -1).getId();

            while(!githubUsers.isEmpty()) {
                var chunk = this.getChunk(githubUsers);
                var futures = new ArrayList<CompletableFuture<String>>();

                for(GithubUserDTO guser : chunk) {
                    var future = greatestUserService.create(DomainFactory.buildNewFromGithubUserDTO(guser));
                    futures.add(future);
                    hired++;
                }

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
                githubUsers.removeAll(chunk);
            }
        }
    }

    private List<GithubUserDTO> getChunk(List<GithubUserDTO> gusers) {
        var size = this.jobConfiguration.getMultiThreadSize() < gusers.size() ? this.jobConfiguration.getMultiThreadSize() : gusers.size();
        return gusers.subList(0, size);
    }
}

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
        log.info("EXECUTING WITH SINGLE THREAD");
        greatestUserService.setEmpty();

        int pageSize = this.jobConfiguration.getGithubUsersQuantity() < this.jobConfiguration.getGithubAPIMaxPageSize() ?
                this.jobConfiguration.getGithubUsersQuantity() :
                this.jobConfiguration.getGithubAPIMaxPageSize();

        if(pageSize == 0)
            return;

        int since = 0;
        int hired = 0;

        do{
            var githubUsers = githubUserService.get(pageSize, since);
            if(githubUsers.isEmpty())
                return;

            since = githubUsers.get(githubUsers.size() -1).getId();
            for (GithubUserDTO dto: githubUsers) {
                greatestUserService.create(DomainFactory.buildNewFromGithubUserDTO(dto));
                hired++;
            }
        }while (hired < this.jobConfiguration.getGithubUsersQuantity());
    }
}

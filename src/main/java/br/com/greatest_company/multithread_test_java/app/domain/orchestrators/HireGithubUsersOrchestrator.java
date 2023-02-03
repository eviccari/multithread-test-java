package br.com.greatest_company.multithread_test_java.app.domain.orchestrators;

import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.app.domain.services.GithubUserService;
import br.com.greatest_company.multithread_test_java.app.domain.services.GreatestUserService;
import br.com.greatest_company.multithread_test_java.configs.JobConfiguration;

public class HireGithubUsersOrchestrator implements Orchestrator{

    private GithubUserService githubUserService;
    private GreatestUserService greatestUserService;
    private JobConfiguration jobConfiguration;
    private int pageSize;
    private int since;
    private int hired;

    public HireGithubUsersOrchestrator(GithubUserService githubUserService, GreatestUserService greatestUserService, JobConfiguration jobConfiguration) {
        this.githubUserService = githubUserService;
        this.greatestUserService = greatestUserService;
        this.jobConfiguration = jobConfiguration;
    }

    @Override
    public void execute() throws InternalServerErrorException, BadRequestException, UnprocessableEntityException {
        greatestUserService.setEmpty();

        pageSize = this.jobConfiguration.getGithubUsersQuantity() < this.jobConfiguration.getGithubAPIMaxPageSize() ?
                this.jobConfiguration.getGithubUsersQuantity() :
                this.jobConfiguration.getGithubAPIMaxPageSize();

        if(pageSize == 0)
            return;

        since = 0;
        hired = 0;

        do{
            var githubUsers = githubUserService.get(pageSize, since);
            if(githubUsers.size() == 0)
                return;

            since = githubUsers.get(githubUsers.size() -1).getId();
            for (GithubUserDTO dto: githubUsers) {
                greatestUserService.create(DomainFactory.buildNewFromGithubUserDTO(dto));
                hired++;
            }
        }while (hired < this.jobConfiguration.getGithubUsersQuantity());
    }
}

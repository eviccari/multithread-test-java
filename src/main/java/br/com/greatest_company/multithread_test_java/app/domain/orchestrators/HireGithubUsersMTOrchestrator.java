package br.com.greatest_company.multithread_test_java.app.domain.orchestrators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GreatestUserMySQLRepository;
import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.app.domain.services.GithubUserService;
import br.com.greatest_company.multithread_test_java.app.domain.services.GreatestUserServiceImpl;
import br.com.greatest_company.multithread_test_java.configs.JobConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HireGithubUsersMTOrchestrator implements Orchestrator{

    private GithubUserService githubUserService;
    private DataSource dataSource;
    private JobConfiguration jobConfiguration;

    public HireGithubUsersMTOrchestrator(GithubUserService githubUserService, DataSource dataSource, JobConfiguration jobConfiguration) {
        this.githubUserService = githubUserService;
        this.dataSource = dataSource;
        this.jobConfiguration = jobConfiguration;
    }

    @Override
    public void execute() throws InternalServerErrorException, BadRequestException, UnprocessableEntityException {
        log.info("EXECUTING WITH MULTI THREAD");
        new GreatestUserServiceImpl(new GreatestUserMySQLRepository(this.dataSource)).setEmpty();

        var pageSize = this.jobConfiguration.getGithubUsersQuantity() < this.jobConfiguration.getGithubAPIMaxPageSize() ?
                this.jobConfiguration.getGithubUsersQuantity() :
                this.jobConfiguration.getGithubAPIMaxPageSize();

        if(pageSize == 0)        
            return;

        var since = 0;
        var hired = 0;

        do{
            var githubUsers = githubUserService.get(pageSize, since);
            if(githubUsers.isEmpty())
                return;
            since = githubUsers.get(githubUsers.size() -1).getId();

            while (!githubUsers.isEmpty()) {
                var chunk = this.getChunk(this.jobConfiguration.getMultiThreadSize(), githubUsers);
                try{
                    hired += this.doAsynchronous(chunk);
                }catch(Exception e) {
                    throw new InternalServerErrorException(e.getMessage());
                }
                githubUsers.removeAll(chunk);
            }
            
        }while (hired < this.jobConfiguration.getGithubUsersQuantity());
    }

    private int doAsynchronous(List<GithubUserDTO> chunk) throws Exception {
        var hired = 0;
        var futures = new ArrayList<Future<String>>();
        var executorService = Executors.newFixedThreadPool(chunk.size());

        for (GithubUserDTO dto : chunk) {
            var greatestUserDTO = DomainFactory.buildNewFromGithubUserDTO(dto);
            var service = new GreatestUserServiceImpl(new GreatestUserMySQLRepository(this.dataSource));
            futures.add(executorService.submit(new AtomicGreatestUserService(greatestUserDTO, service)));
        }

        for(Future<String> future : futures) {
            var generatedID = future.get();
            hired++;
            log.info(String.format("new user with id %s created", generatedID));
        }

        return hired;
    }

    private List<GithubUserDTO> getChunk(int multiThreadQuantity, List<GithubUserDTO> gusers) {
        var size = multiThreadQuantity > gusers.size() ? gusers.size() : multiThreadQuantity;
        return gusers.subList(0, size);
    }
}

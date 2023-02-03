package br.com.greatest_company.multithread_test_java.app.domain.services;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GithubUserRepository;
import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.app.domain.models.GithubUserPoliciesManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;



@Slf4j
public class GithubUserServiceImpl implements GithubUserService{

    private GithubUserRepository repo;

    public GithubUserServiceImpl(GithubUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<GithubUserDTO> get(int pageSize, int since) throws BadRequestException, InternalServerErrorException {
        if(pageSize == 0 || since < 0)
            throw new BadRequestException("page size and since parameters are required");

        var dirtyList = this.repo.get(pageSize, since);
        var result = new ArrayList<GithubUserDTO>();

        for(GithubUserDTO guserDTO : dirtyList) {
            var model = DomainFactory.buildFromDTO(guserDTO);
            var pm = new GithubUserPoliciesManager(model);

            try {
                pm.apply();
                result.add(guserDTO);
            } catch (UnprocessableEntityException e) {
                log.info(String.format(
                        "user: %s have failed with policies: %s",
                        guserDTO.getLogin() != null ? guserDTO.getLogin() : "NA",
                        e.getMessage())
                );
            }
        }

        return result;
    }
}

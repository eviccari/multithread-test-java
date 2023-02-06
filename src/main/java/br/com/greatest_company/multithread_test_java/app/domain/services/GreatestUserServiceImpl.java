package br.com.greatest_company.multithread_test_java.app.domain.services;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GreatestUserRepository;
import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.utils.TimeUtils;

@Service
public class GreatestUserServiceImpl implements GreatestUserService{

    @Autowired
    GreatestUserRepository repo;

    @Override
    @Async
    public CompletableFuture<String> create(GreatestUserDTO dto) throws InternalServerErrorException, UnprocessableEntityException {
        var model = DomainFactory.buildFromDTO(dto);
        model.generateID();
        model.setCreatedAt(Date.from(TimeUtils.now()));
        model.setNewEmail(String.format("%s@greatestuser.com", model.getId()));
        model.validate();

        this.repo.create(DomainFactory.buildFromModel(model));
        return CompletableFuture.completedFuture(model.getId());
    }

    @Override
    public void setEmpty() throws InternalServerErrorException {
        this.repo.setEmpty();
    }
}

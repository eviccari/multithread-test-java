package br.com.greatest_company.multithread_test_java.app.domain.services;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GreatestUserRepository;
import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.utils.TimeUtils;

@Service
@Slf4j
public class GreatestUserServiceSingleThreadImpl implements GreatestUserService{

    @Autowired
    GreatestUserRepository repo;

    @Override
    public String create(GreatestUserDTO dto) throws InternalServerErrorException, UnprocessableEntityException {
        var model = DomainFactory.buildFromDTO(dto);
        model.generateID();
        model.setCreatedAt(Date.from(TimeUtils.now()));
        model.setNewEmail(String.format("%s@greatestuser.com", model.getId()));
        model.validate();
        log.info(String.format("new user with id %s created", model.getId()));

        this.repo.create(DomainFactory.buildFromModel(model));
        return model.getId();
    }

    @Override
    public void setEmpty() throws InternalServerErrorException {
        this.repo.setEmpty();
    }
    
}

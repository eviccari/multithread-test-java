package br.com.greatest_company.multithread_test_java.app.domain.services;

import br.com.greatest_company.multithread_test_java.adapters.repositories.GreatestUserRepository;
import br.com.greatest_company.multithread_test_java.app.domain.DomainFactory;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;
import br.com.greatest_company.multithread_test_java.utils.TimeUtils;

import java.util.Date;

public class GreatestUserServiceImpl implements GreatestUserService{

    GreatestUserRepository repo;

    public GreatestUserServiceImpl(GreatestUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public void create(GreatestUserDTO dto) throws InternalServerErrorException, UnprocessableEntityException, BadRequestException {
        var model = DomainFactory.buildFromDTO(dto);
        model.generateID();
        model.setCreatedAt(Date.from(TimeUtils.now()));
        model.setNewEmail(String.format("%s@greatestuser.com", model.getId()));
        model.validate();

        this.repo.create(DomainFactory.buildFromModel(model));
    }

    @Override
    public void setEmpty() throws InternalServerErrorException {
        this.repo.setEmpty();
    }
}

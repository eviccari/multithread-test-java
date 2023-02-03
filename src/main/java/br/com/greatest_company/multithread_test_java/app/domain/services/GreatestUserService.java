package br.com.greatest_company.multithread_test_java.app.domain.services;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;

public interface GreatestUserService {
    void create(GreatestUserDTO dto) throws InternalServerErrorException, UnprocessableEntityException, BadRequestException;
    void setEmpty() throws InternalServerErrorException;
}

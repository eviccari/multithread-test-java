package br.com.greatest_company.multithread_test_java.app.domain.services;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;

public interface GreatestUserService {
    String create(GreatestUserDTO dto) throws InternalServerErrorException, UnprocessableEntityException;
    void setEmpty() throws InternalServerErrorException;
}

package br.com.greatest_company.multithread_test_java.adapters.repositories;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;

public interface GreatestUserRepository {
    void create(GreatestUserDTO dto) throws InternalServerErrorException;
    void setEmpty() throws InternalServerErrorException;
}

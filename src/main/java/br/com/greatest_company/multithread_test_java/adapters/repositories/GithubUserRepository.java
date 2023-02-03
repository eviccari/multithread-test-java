package br.com.greatest_company.multithread_test_java.adapters.repositories;

import java.util.List;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;

public interface GithubUserRepository {
    List<GithubUserDTO> get(int pageSize, int since) throws InternalServerErrorException;
}

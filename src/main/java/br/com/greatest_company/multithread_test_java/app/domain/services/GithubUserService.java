package br.com.greatest_company.multithread_test_java.app.domain.services;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;

import java.util.List;

public interface GithubUserService {
    List<GithubUserDTO> get(int pageSize, int since) throws BadRequestException, InternalServerErrorException;
}

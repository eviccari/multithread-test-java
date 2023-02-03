package br.com.greatest_company.multithread_test_java.app.domain.orchestrators;

import br.com.greatest_company.multithread_test_java.app.domain.exceptions.BadRequestException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;

public interface Orchestrator {
    void execute() throws InternalServerErrorException, BadRequestException, UnprocessableEntityException;
}

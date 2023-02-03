package br.com.greatest_company.multithread_test_java.app.domain.models;

import br.com.greatest_company.multithread_test_java.app.domain.exceptions.UnprocessableEntityException;

public interface Validatable {
    void validate() throws UnprocessableEntityException;
}

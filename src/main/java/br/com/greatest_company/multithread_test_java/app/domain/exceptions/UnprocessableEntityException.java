package br.com.greatest_company.multithread_test_java.app.domain.exceptions;

public class UnprocessableEntityException extends Exception implements CustomException<Integer>{

    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 422;
    }
}

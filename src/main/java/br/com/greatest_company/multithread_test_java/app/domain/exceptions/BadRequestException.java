package br.com.greatest_company.multithread_test_java.app.domain.exceptions;

public class BadRequestException extends Exception implements CustomException<Integer>{

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}

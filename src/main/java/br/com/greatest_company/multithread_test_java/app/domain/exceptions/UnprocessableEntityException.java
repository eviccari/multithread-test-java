package br.com.greatest_company.multithread_test_java.app.domain.exceptions;

public class UnprocessableEntityException extends Exception implements CustomException{

    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    public Object getStatusCode() {
        return 422;
    }
}

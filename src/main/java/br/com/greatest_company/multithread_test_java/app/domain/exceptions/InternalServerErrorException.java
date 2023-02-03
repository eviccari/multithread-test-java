package br.com.greatest_company.multithread_test_java.app.domain.exceptions;

public class InternalServerErrorException extends Exception implements CustomException<Integer>{

    public InternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 500;
    }
    
}

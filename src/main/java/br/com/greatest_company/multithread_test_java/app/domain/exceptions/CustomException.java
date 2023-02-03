package br.com.greatest_company.multithread_test_java.app.domain.exceptions;

public interface CustomException<T> {
    String getMessage();
    T getStatusCode();
}

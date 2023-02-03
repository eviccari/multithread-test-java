package br.com.greatest_company.multithread_test_java.utils;

public class StringUtils {

    private StringUtils() {}

    public static final boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.isBlank() || s.trim() == "";
    }
}

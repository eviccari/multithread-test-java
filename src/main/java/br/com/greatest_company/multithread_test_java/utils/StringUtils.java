package br.com.greatest_company.multithread_test_java.utils;

public class StringUtils {

    private StringUtils() {}

    public static final boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.isBlank() || s.trim().equals("");
    }

    public static final boolean isNotEmpty(String s) {
        return !s.isEmpty() || !s.isBlank() || s.trim().equals("");
    }

}

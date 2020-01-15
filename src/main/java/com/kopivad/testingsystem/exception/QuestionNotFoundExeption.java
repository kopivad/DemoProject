package com.kopivad.testingsystem.exception;

public class QuestionNotFoundExeption extends Exception {
    public QuestionNotFoundExeption(String message) {
        super(message);
    }

    public QuestionNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}

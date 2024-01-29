package ru.jengine.jenginegdx.utils.exceptions;

public class JEngineGdxException extends RuntimeException {
    public JEngineGdxException(String message) {
        super(message);
    }

    public JEngineGdxException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

package com.javo.hoster.exception;

public class AccessException extends RuntimeException{

    public AccessException(String message){
        super(message);
    }

    public AccessException(String message, Throwable exception){
        super(message, exception);
    }

}

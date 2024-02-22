package com.lcwd.electronic.store.exceptions;

public class BadApiRequest extends RuntimeException {

    public BadApiRequest(){
        super("Bad api request!!");
    }
    public BadApiRequest(String message) {
        super(message);
    }
}

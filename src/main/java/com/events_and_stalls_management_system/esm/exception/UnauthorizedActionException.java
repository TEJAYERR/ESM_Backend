package com.events_and_stalls_management_system.esm.exception;

public class UnauthorizedActionException extends RuntimeException{

    public UnauthorizedActionException(String message){
        super(message);
    }
}

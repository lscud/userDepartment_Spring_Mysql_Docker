package com.luisscudeler.userdep.service.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Object id){
        super("Reource not Found. Id " + id);
    }
}

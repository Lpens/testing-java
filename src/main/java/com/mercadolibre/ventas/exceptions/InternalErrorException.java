package com.mercadolibre.ventas.exceptions;

public class InternalErrorException extends CustomException{
    public InternalErrorException()
    {
        super("There was an internal server error, contact sys admin", 500);
    }
}

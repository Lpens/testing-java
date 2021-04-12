package com.mercadolibre.ventas.exceptions;

public class RepeatedClientException extends CustomException{
    public RepeatedClientException(String msg)
    {
        super("there is already an user with the same DNI for the user: " + msg, 400 );
    }
}

package com.mercadolibre.ventas.exceptions;

public class WrongClientInputException extends CustomException{
    public WrongClientInputException(String msg)
    {
        super("the Input for this Client is incorrect please add a valid DNI, name, and province Client: " + msg, 400);
    }
}

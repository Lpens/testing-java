package com.mercadolibre.ventas.exceptions;

public class WrongArticuleException extends CustomException{
    public WrongArticuleException(String articule)
    {
        super("The Articule provided does not match with the Id on Database. Articule: " + articule, 400);
    }
}

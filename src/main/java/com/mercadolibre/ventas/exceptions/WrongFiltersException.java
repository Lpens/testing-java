package com.mercadolibre.ventas.exceptions;

public class WrongFiltersException extends CustomException{
    public WrongFiltersException()
    {
        super("You applied more than 2 filters on this request please select only 2", 400);
    }
}

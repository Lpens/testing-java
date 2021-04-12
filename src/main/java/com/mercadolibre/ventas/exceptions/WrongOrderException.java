package com.mercadolibre.ventas.exceptions;

public class WrongOrderException extends CustomException{
    public WrongOrderException(String order)
    {
        super("the order is out of range, the order types should go from 0 to 3.  Order Provided: " + order,400);
    }
}

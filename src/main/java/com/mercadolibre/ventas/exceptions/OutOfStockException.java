package com.mercadolibre.ventas.exceptions;

public class OutOfStockException extends CustomException{
    public OutOfStockException(String msg)
    {
        super("the item provided is out of stock or there is less products that the amount you asked for Product: " + msg
        ,400);
    }
}

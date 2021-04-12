package com.mercadolibre.ventas.exceptions;

public class NoCartsFoundException extends CustomException{
    public NoCartsFoundException()
    {
        super("there is no carts on the cart list.", 400);
    }
}

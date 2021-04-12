package com.mercadolibre.ventas.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{
    public NotFoundException(String message)
    {
        super("we can't found the product with the id: " + message, 404);
    }
}

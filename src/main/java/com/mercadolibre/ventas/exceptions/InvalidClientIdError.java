package com.mercadolibre.ventas.exceptions;

public class InvalidClientIdError extends CustomException{
    public InvalidClientIdError (String id)
    {
        super("El id enviado es invalido, Id: " + id, 400);
    }
}

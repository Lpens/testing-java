package com.mercadolibre.ventas.exceptions;

public class NoClientException extends CustomException{
    public NoClientException(int id)
    {
        super("El cliente con el id: "+id+" no pudo ser encontrado",404);
    }
}

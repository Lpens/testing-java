package com.mercadolibre.ventas.exceptions;

import com.mercadolibre.ventas.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

//este exception guarda un codigo int y genera el Http Status correspondiente de esta forma se manejan las excepciones
//custom de forma dinamica.
public class CustomException extends Exception{
    private int code;
    private HttpStatus status;
    public CustomException(String msg, int code)
    {
        super(msg);
        this.setCode(code);
    }
    public int getCode()
    {
        return this.code;
    }
    public void setCode(int code)
    {
        this.code = code;
        switch (code)
        {
            case 400:
                this.status = HttpStatus.BAD_REQUEST;
                break;
            case 404:
                this.status = HttpStatus.NOT_FOUND;
                break;
            default:
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
    }
    public HttpStatus getStatus()
    {
        return this.status;
    }

    public ErrorDTO getError() {
        ErrorDTO error = new ErrorDTO();
        error.setCode(this.getCode());
        error.setMessage(this.getMessage());
        return error;
    }
}

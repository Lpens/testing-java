package com.mercadolibre.ventas.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private int code;
    private String message;
}

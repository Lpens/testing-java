package com.mercadolibre.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusCodeDTO {
    private  int code;
    private String message;
}

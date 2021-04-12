package com.mercadolibre.ventas.dto;

import lombok.Data;

@Data
public class TicketResponseDto {
    private TicketDTO ticket;
    private StatusCodeDTO statusCode;
}

package com.mercadolibre.ventas.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketDTO {
    private int id;
    private List<ArticleDTO> articles;
    private int total;
}

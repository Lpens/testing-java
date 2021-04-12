package com.mercadolibre.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequestDTO {
    private List<ArticleDTO> articles;
}

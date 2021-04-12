package com.mercadolibre.ventas.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private int productId;
    private String name;
    private String brand;
    private int quantity;
}

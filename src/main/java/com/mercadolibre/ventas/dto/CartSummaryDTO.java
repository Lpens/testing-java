package com.mercadolibre.ventas.dto;

import lombok.Data;

import java.util.List;
@Data
public class CartSummaryDTO {
    List<TicketDTO> purchaseList;
    int total;
}

package com.mercadolibre.ventas.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private String name;
    private String lastname;
    private int id;
    private int telephone;
    private String province;
    private  int dni;
}

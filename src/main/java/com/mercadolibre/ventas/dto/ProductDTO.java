package com.mercadolibre.ventas.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "id","name", "category", "brand", "price", "cuantity", "freeShipping", "prestige" })
public class ProductDTO {

    private  int id;
    private String name;
    private String category;
    private String brand;
    private String price;
    private int cuantity;
    private boolean freeShipping;
    private String prestige;

    public String getProperty(String propertyName)
    {
        String propertyValue ;
        switch (propertyName)
        {
            case "name":
                propertyValue = this.getName();
                break;
            case "category":
                propertyValue = this.getCategory();
                break;
            case "brand":
                propertyValue = this.getBrand();
                break;
            default:
                propertyValue ="";
        }
        return propertyValue;
    }

    public void setFreeShipping(String shipping)
    {
        this.freeShipping = shipping == "YES";
    }
}

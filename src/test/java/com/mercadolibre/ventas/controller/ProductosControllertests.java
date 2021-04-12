package com.mercadolibre.ventas.controller;

import com.mercadolibre.ventas.dto.ProductDTO;
import com.mercadolibre.ventas.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProductosController.class)
public class ProductosControllertests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;
    
    public List<ProductDTO> createProducts()
    {
        List<ProductDTO> prodcutmock = new ArrayList<>();
        ProductDTO newProduct = new ProductDTO();
        newProduct.setBrand("addidas");
        newProduct.setCategory("Deporte");
        newProduct.setCuantity(25);
        newProduct.setPrestige("*****");
        newProduct.setName("zapatillas");
        newProduct.setFreeShipping("YES");
        newProduct.setId(0);
        prodcutmock.add(newProduct);
        return prodcutmock;
    }
    @Test
    @DisplayName("1.get a list of Articles")
    public void getArticlesTest() throws Exception
    {
        List<ProductDTO> productos = this.createProducts();

        when(productService.getAllArticles()).thenReturn(productos);
        this.mvc.perform(get("/articles")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
}

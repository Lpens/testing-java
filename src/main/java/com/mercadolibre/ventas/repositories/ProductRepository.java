package com.mercadolibre.ventas.repositories;

import com.mercadolibre.ventas.dto.ProductDTO;
import com.mercadolibre.ventas.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;

public interface ProductRepository {
    List<ProductDTO> getAll();
    List<ProductDTO> getProductsByfilter(HashMap<String ,String> filters);
    ProductDTO findProductById(Integer id) throws NotFoundException;
}

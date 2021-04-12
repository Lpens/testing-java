package com.mercadolibre.ventas.comparators;

import com.mercadolibre.ventas.dto.ProductDTO;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public class ProductComparatorByPrice implements Comparator<ProductDTO> {
    private  boolean inv= false;

    public ProductComparatorByPrice(boolean isInv)
    {
        super();
        inv = isInv;
    }

    public int compare(ProductDTO a, ProductDTO b)
    {
        int result;
        if (!inv)
        {
            result = a.getPrice().compareTo(b.getPrice());
        }
        else
        {
            result = b.getPrice().compareTo(a.getPrice());
        }
        return result;
    }
}

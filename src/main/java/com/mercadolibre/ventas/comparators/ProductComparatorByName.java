package com.mercadolibre.ventas.comparators;

import com.mercadolibre.ventas.dto.ProductDTO;

import java.util.Comparator;

public class ProductComparatorByName implements Comparator<ProductDTO> {
    private  boolean inv= false;

    public ProductComparatorByName(boolean isInv)
    {
        super();
        inv = isInv;
    }

    public int compare(ProductDTO a, ProductDTO b)
    {
        int result;
        if (!inv)
        {
            result = a.getName().compareTo(b.getName());
        }
        else
        {
            result = b.getName().compareTo(a.getName());
        }
        return result;
    }
}

package com.mercadolibre.ventas.repositories;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mercadolibre.ventas.dto.ProductDTO;
import com.mercadolibre.ventas.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    List<ProductDTO> listaProductos = new ArrayList<>();
    public ProductRepositoryImpl()
    {
        this.listaProductos = this.loadDatabase();
    }

    //devuelve un alista con todos los productos
    @Override
    public List<ProductDTO> getAll() {
        return this.listaProductos;

    }

    //devuelve una lista de productos filtrado por freeShipping.
    private List<ProductDTO> filterByShipping(String value, List<ProductDTO> list)
    {
        boolean shipping = value.equals("YES") || value.equals("true");
        List<ProductDTO> productList = new ArrayList<>();
        productList = (list.stream()
                .filter(productDTO -> productDTO.isFreeShipping() == shipping)
                .collect(Collectors.toList()));
        return productList;
    }

    //devuelve una lista de productos aplicando filtros de forma dinamica que obtiene de una lista
    //se obtienen los valores dependiendo del nombre de la propiedad conuna funcion almacenaa en la clase del producto.
    @Override
    public List<ProductDTO> getProductsByfilter(HashMap<String, String> filters) {
        AtomicReference<List<ProductDTO>> productList =new AtomicReference<List<ProductDTO>>();
        AtomicInteger count = new AtomicInteger();
        filters.forEach((k,v)->{

            productList.set(productList.get() != null? productList.get() : this.listaProductos);

            if(k.equals("shipping")) productList.set(filterByShipping(v, productList.get()));
            else {
                productList.set(productList.get().stream()
                        .filter(productDTO -> productDTO.getProperty(k).contains(v))
                        .collect(Collectors.toList()));
            }

        });
        return productList.get();
    }

    //devuelve un producto dependiendo del id solicitado o un error en caso de no ser encontrado
    @Override
    public ProductDTO findProductById(Integer id) throws NotFoundException {
        Optional<ProductDTO> producto = null;
        producto = this.listaProductos.stream()
            .filter(productDTO -> productDTO.getId() == id)
            .findFirst();

        if (!producto.isPresent()) throw new NotFoundException(Integer.toString(id));
        return producto.get();
    }

    //carga la base de datos.
    private List<ProductDTO> loadDatabase(){
        MappingIterator<ProductDTO> personIter = null;
        try {

            String basePath = new File("").getAbsolutePath();
            File testFile = ResourceUtils.getFile(basePath +"/src/main/resources/dbProductos.csv");


            CsvMapper mapper = new CsvMapper();

            CsvSchema sclema = mapper.schemaFor(ProductDTO.class)
                    .withSkipFirstDataRow(true)
                    .withColumnSeparator(',').withoutQuoteChar();

            personIter= mapper
                    .readerFor(ProductDTO.class)
                    .with(sclema).readValues(testFile);


            return personIter.readAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ArrayList<ProductDTO>();

    }
}


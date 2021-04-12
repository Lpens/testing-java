package com.mercadolibre.ventas.services;

import com.mercadolibre.ventas.comparators.ProductComparatorByName;
import com.mercadolibre.ventas.comparators.ProductComparatorByPrice;
import com.mercadolibre.ventas.dto.*;
import com.mercadolibre.ventas.exceptions.*;
import com.mercadolibre.ventas.repositories.CompraRepository;
import com.mercadolibre.ventas.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompraRepository compraRepository;

    private int count =0;

    //obtiene todos los posibles filtros y crea una lista para solicitar el filtrado en el repository
    //devuelve error si los filtros son mayores a 2 (para hacer match con los request del documento)
    //realiza el ordenamiento solicitado.
    @Override
    public List<ProductDTO> getArticlesFiltered(String category, String freeShipping, String product, String brand, String order) throws WrongFiltersException {
        HashMap<String,String> filters = new HashMap<>();
        if (!category.equals("")) filters.put("category",category);
        if (!freeShipping.equals("")) filters.put("shipping",freeShipping);
        if (!brand.equals("")) filters.put("brand",brand);
        if (!product.equals("")) filters.put("name",product);
        if (filters.size()>2) throw new WrongFiltersException();
        List<ProductDTO> productList = this.productRepository.getProductsByfilter(filters);
        if(order.equals("1"))
        {
            Collections.sort(productList, new ProductComparatorByName(true));

        }
        this.sortList(Integer.parseInt(order), productList);
        return productList;
    }

    //devuelve todos los articulos de la Db
    @Override
    public List<ProductDTO> getAllArticles() {
        return productRepository.getAll();
    }

    //genera un ticker de un purchase y lo guarda en su respectivo cart.
    //tambien valida que no se puedan pedir mas productos de los que se tienen en stock o que el producto
    //no concuerde con el que se tiene en la BD
    @Override
    public TicketResponseDto generateTicket(PurchaseRequestDTO purchase, int cartId) throws NotFoundException, WrongArticuleException, OutOfStockException, InternalErrorException {

        int suma = 0;
        TicketResponseDto response = new TicketResponseDto();

        suma = this.calculatePrice(purchase.getArticles());

        //save the purchase.

        TicketDTO ticket = new TicketDTO();
        ticket.setArticles(purchase.getArticles());
        ticket.setId(count++);
        ticket.setTotal(suma);
        StatusCodeDTO scode = new StatusCodeDTO(200,"La solicitud de compra se completó con éxito");
        response.setTicket(ticket);
        response.setStatusCode(scode);
        this.compraRepository.saveArticleList(ticket,cartId);

        return response;

    }

    //devuelve todas las purchases de un cart especifico y su monto total
    @Override
    public CartSummaryDTO getAllPurchases(int id) throws WrongArticuleException, OutOfStockException, NotFoundException, InternalErrorException, NoCartsFoundException {

        List<TicketDTO> articleList = this.compraRepository.getArticles(id);

        CartSummaryDTO response = new CartSummaryDTO();
        response.setPurchaseList(articleList);
        int total = 0;
        for (TicketDTO purchase : articleList)
        {
            total += this.calculatePrice(purchase.getArticles());
        }
        response.setTotal(total);
        return response;
    }

    //funcion para calcular el precio de un determinado producto.
    private int calculatePrice(List<ArticleDTO> articleList) throws NotFoundException, WrongArticuleException, OutOfStockException, InternalErrorException {
        int suma = 0;

        for(ArticleDTO article: articleList)
        {
            ProductDTO p = this.productRepository.findProductById(article.getProductId());
            //safety checks
            //product exist or is valid
            if (!p.getName().equals(article.getName()) || !article.getBrand().equals(p.getBrand()))
                throw new WrongArticuleException(article.toString());

            //cuantity check
            if(p.getCuantity() < article.getQuantity()) throw new OutOfStockException(p.toString());

            try {
                double formatedPrice = Double.parseDouble(p.getPrice().replaceAll("[^a-zA-Z0-9]", ""));
                suma += formatedPrice * article.getQuantity();
            }
            catch (Exception e)
            {
                throw new InternalErrorException();
            }

        }
        return suma;
    }

    //funcion para ordenar
    private List<ProductDTO> sortList(int sort, List<ProductDTO> listToSort)
    {
        switch (sort)
        {
            case 0:
                Collections.sort(listToSort, new ProductComparatorByName(false));
                break;
            case 1:
                Collections.sort(listToSort, new ProductComparatorByName(true));
                break;
            case 2:
                Collections.sort(listToSort, new ProductComparatorByPrice(false));
                break;
            case 3:
                Collections.sort(listToSort, new ProductComparatorByPrice(true));
                break;
        }
        return listToSort;
    }
}

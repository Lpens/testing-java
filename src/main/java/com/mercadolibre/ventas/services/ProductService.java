package com.mercadolibre.ventas.services;

import com.mercadolibre.ventas.dto.CartSummaryDTO;
import com.mercadolibre.ventas.dto.ProductDTO;
import com.mercadolibre.ventas.dto.PurchaseRequestDTO;
import com.mercadolibre.ventas.dto.TicketResponseDto;
import com.mercadolibre.ventas.exceptions.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getArticlesFiltered(String category, String freeShipping, String product, String brand, String order) throws WrongFiltersException;
    List<ProductDTO> getAllArticles();
    TicketResponseDto generateTicket(PurchaseRequestDTO purchase, int CartId) throws ParseException, InternalErrorException, NotFoundException, WrongArticuleException, OutOfStockException;
    CartSummaryDTO getAllPurchases(int id) throws WrongArticuleException, OutOfStockException, NotFoundException, InternalErrorException, NoCartsFoundException;
}

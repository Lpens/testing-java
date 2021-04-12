package com.mercadolibre.ventas.repositories;

import com.mercadolibre.ventas.dto.ArticleDTO;
import com.mercadolibre.ventas.dto.PurchaseRequestDTO;
import com.mercadolibre.ventas.dto.TicketDTO;
import com.mercadolibre.ventas.exceptions.NoCartsFoundException;

import java.util.List;

public interface CompraRepository {

    void saveArticleList(TicketDTO purchase, int id);
    List<TicketDTO> getArticles(int id) throws NoCartsFoundException;

}

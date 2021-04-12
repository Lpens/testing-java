package com.mercadolibre.ventas.repositories;

import com.mercadolibre.ventas.dto.ArticleDTO;
import com.mercadolibre.ventas.dto.PurchaseRequestDTO;
import com.mercadolibre.ventas.dto.TicketDTO;
import com.mercadolibre.ventas.exceptions.NoCartsFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class CompraRepositoryImpl implements CompraRepository{
    private HashMap<Integer, List<TicketDTO>> cartList;


    //guarda una lista de articulos en un cart.
    @Override
    public void saveArticleList(TicketDTO purchase, int id) {
        List<TicketDTO> purchaseList = null;
        if (cartList != null) {
            purchaseList= cartList.get(id);
        }
        else
        {
            cartList = new HashMap<>();
        }
        if (purchaseList == null || purchaseList.isEmpty()) purchaseList = new ArrayList<>();
        purchaseList.add(purchase);
        cartList.put(id,purchaseList);
    }

    //devuelve la lista de purchases en un determinado cart.
    @Override
    public List<TicketDTO> getArticles(int id) throws NoCartsFoundException {
        if (cartList == null) throw new NoCartsFoundException();
        List<TicketDTO> purchases = cartList.get(id);
        if (purchases.isEmpty()) purchases = new ArrayList<>();
        return purchases;
    }
}

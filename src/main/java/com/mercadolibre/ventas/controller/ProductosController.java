package com.mercadolibre.ventas.controller;

import com.mercadolibre.ventas.dto.*;
import com.mercadolibre.ventas.exceptions.*;
import com.mercadolibre.ventas.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductosController {
    @Autowired
    private ProductService productService;

    //este es el endpoint para llamar a todos los articulos o para filtrarlo o ordenarlo dependiendo de los inputs
    //aca valido si las opciones de order vienen de forma correcta y llamo a la funcion del repository correcta
    //dependiendo de si tengo filtros o no
    @GetMapping("/articles")
    public ResponseEntity getArticles(@RequestParam(defaultValue = "") String category
            , @RequestParam(defaultValue = "") String freeShipping
            ,@RequestParam(defaultValue = "") String product
            ,@RequestParam(defaultValue = "") String brand
            ,@RequestParam(defaultValue = "0") String order) throws WrongOrderException, WrongFiltersException {

        List<ProductDTO> productos;

        //prevent to call anything if the order param is bad
        int orderInt = Integer.parseInt(order);
        if (orderInt< 0 || orderInt >3) throw new WrongOrderException(order);


        if(category.equals("") && freeShipping.equals("") && product.equals("") && brand.equals(""))
        {
            productos = this.productService.getAllArticles();
        }
        else
        {
            productos = this.productService.getArticlesFiltered(category,freeShipping,product,brand,order);
        }

        return new ResponseEntity(productos, HttpStatus.OK);
    }

    //parte del Bonus del ejercicio aca podemos obtener uno de los shopping carts con todos los tickets asociados
    //y el total del monto de la compra
    @GetMapping("/cart/{cartId}")
    public ResponseEntity getShoppingCart(@PathVariable int cartId) throws WrongArticuleException
            , OutOfStockException
            , NotFoundException
            , InternalErrorException
            , NoCartsFoundException {
        CartSummaryDTO finalticket = this.productService.getAllPurchases(cartId);
        return new ResponseEntity(finalticket, HttpStatus.OK);
    }

    //Endpoint para guardar un purchase request y verificar si se quiere guardar en un cart especifico
    //si no se envia un cart id se guarda en el cart por defecto (el 0)
    @PostMapping("/purchase-request")
    public ResponseEntity purchaseRequest(@RequestBody PurchaseRequestDTO request
            ,@RequestParam(defaultValue = "") String cartId) throws ParseException
            , InternalErrorException
            , NotFoundException
            , WrongArticuleException
            , OutOfStockException {
        int formatedCartId = 0;
        if(!cartId.equals(""))
        {
            formatedCartId = Integer.parseInt(cartId);
        }
        TicketResponseDto ticket = this.productService.generateTicket(request, formatedCartId);

        return new ResponseEntity(ticket,HttpStatus.OK);
    }

    //aca manejamos de forma dinamica todas las exceptions creadas por mi que heredan de CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity customException(CustomException cException)
    {
        return new ResponseEntity(cException.getError(), cException.getStatus());
    }
}
package com.mercadolibre.ventas.controller;

import com.mercadolibre.ventas.dto.ClientDTO;
import com.mercadolibre.ventas.exceptions.*;
import com.mercadolibre.ventas.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/v1/client")
public class ClientesController {

    ClientService clientService;
    public ClientesController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    //Obtiene todos los cleintes o los filtra dependiendo de si le enviamos o no el parametro de provincia.
    @GetMapping("")
    public ResponseEntity getClientes(@RequestParam(defaultValue = "") String province)
    {
        List<ClientDTO> listaClientes = province.isEmpty() ? this.clientService.getAllClients():
                this.clientService.getClientsByProvince(province);

        return  new ResponseEntity(listaClientes, HttpStatus.OK);
    }

    //obtiene 1 cliente especifico o devuelve error si no consigue ninguno
    
    @GetMapping("/{clientId}")
    public ResponseEntity getClient(@PathVariable String clientId) throws NoClientException, InvalidClientIdError {
        int id = Integer.parseInt(clientId);
        if(id < 0 ) throw new InvalidClientIdError(clientId);
        return new ResponseEntity(this.clientService.getSingleClient(id), HttpStatus.OK);
    }


    //guarda un nuevo cliente y valida que los datos del cliente sean correctos
    @PostMapping("")
    public ResponseEntity saveNewClient(@RequestBody ClientDTO client) throws IOException, RepeatedClientException, NoClientException, WrongClientInputException, InternalErrorException {
        Pattern p = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");//. represents single character
        Matcher name = p.matcher(client.getName());
        Matcher lastname = p.matcher(client.getLastname());
        Matcher province = p.matcher(client.getProvince());
        boolean l = lastname.matches();
        boolean pr = province.matches();
        boolean n = name.matches();
        if (client.getDni() == 0
                || client.getName().isEmpty()
                || client.getProvince().isEmpty()
                || !name.matches()
                || !lastname.matches()
                || !province.matches())
            throw new WrongClientInputException(client.toString());
        return new ResponseEntity(this.clientService.saveClient(client), HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity customException(CustomException cException)
    {
        return new ResponseEntity(cException.getError(), cException.getStatus());
    }
}

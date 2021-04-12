package com.mercadolibre.ventas.repositories;

import com.mercadolibre.ventas.dto.ClientDTO;
import com.mercadolibre.ventas.dto.ClientResponseDTO;
import com.mercadolibre.ventas.exceptions.InternalErrorException;
import com.mercadolibre.ventas.exceptions.NoClientException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public interface ClientRepository {
    public List<ClientDTO> getallClients();
    public List<ClientDTO> getClientsByProvince(String province);
    public ClientDTO getSpecificClient(int id) throws NoClientException;
    public ClientDTO getClientByDni(int dni) throws NoClientException;
    public ClientResponseDTO saveClient(ClientDTO client) throws IOException, InternalErrorException;



}

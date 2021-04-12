package com.mercadolibre.ventas.services;

import com.mercadolibre.ventas.dto.ClientDTO;
import com.mercadolibre.ventas.dto.ClientResponseDTO;
import com.mercadolibre.ventas.exceptions.InternalErrorException;
import com.mercadolibre.ventas.exceptions.NoClientException;
import com.mercadolibre.ventas.exceptions.RepeatedClientException;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();
    List<ClientDTO> getClientsByProvince(String province);
    ClientDTO getSingleClient(int id) throws NoClientException;
    ClientResponseDTO saveClient(ClientDTO client) throws IOException, NoClientException, RepeatedClientException, InternalErrorException;
}

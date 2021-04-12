package com.mercadolibre.ventas.services;

import com.mercadolibre.ventas.dto.ClientDTO;
import com.mercadolibre.ventas.dto.ClientResponseDTO;
import com.mercadolibre.ventas.exceptions.InternalErrorException;
import com.mercadolibre.ventas.exceptions.NoClientException;
import com.mercadolibre.ventas.exceptions.RepeatedClientException;
import com.mercadolibre.ventas.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{
    ClientRepository clientRepository;
    public ClientServiceImpl(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    //obtiene todos los clientes de la base de datos
    @Override
    public List<ClientDTO> getAllClients() {
        return this.clientRepository.getallClients();
    }

    //filtra clientes por provincia
    @Override
    public List<ClientDTO> getClientsByProvince(String province) {
        return this.clientRepository.getClientsByProvince(province);
    }

    //obtiene 1 cliente por id
    @Override
    public ClientDTO getSingleClient(int id) throws NoClientException {
        return this.clientRepository.getSpecificClient(id);
    }

    //guarda un nuevo cliente en la bd
    //y verifica que el cliente no exista comprobando por dni.
    @Override
    public ClientResponseDTO saveClient(ClientDTO client) throws IOException, NoClientException, RepeatedClientException, InternalErrorException {
        ClientDTO prevClient = null;
        prevClient = this.clientRepository.getClientByDni(client.getDni());
        if (prevClient!= null) throw new RepeatedClientException(client.toString());

        return this.clientRepository.saveClient(client);
    }
}

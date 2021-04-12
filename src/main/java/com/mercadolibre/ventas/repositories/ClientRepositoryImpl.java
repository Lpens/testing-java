package com.mercadolibre.ventas.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mercadolibre.ventas.dto.ClientDTO;
import com.mercadolibre.ventas.dto.ClientResponseDTO;
import com.mercadolibre.ventas.dto.ProductDTO;
import com.mercadolibre.ventas.exceptions.InternalErrorException;
import com.mercadolibre.ventas.exceptions.NoClientException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClientRepository{
    private List<ClientDTO> clientes;

    public ClientRepositoryImpl()
    {
        this.clientes = this.loadDataBase();
    }

    //obtiene todos los cleintes
    @Override
    public List<ClientDTO> getallClients() {
        return this.clientes;
    }

    //obtiene ta un cliente con un determinado ID o un error de no encontrado.
    @Override
    public ClientDTO getSpecificClient(int id) throws NoClientException {
        ClientDTO cliente = null;
        Optional<ClientDTO> filterClient = this.getallClients().stream()
                .filter(clientDTO -> clientDTO.getId() == id)
                .findFirst();
        if (filterClient.isEmpty()) throw new NoClientException(id);
        cliente = filterClient.get();
        return cliente;
    }

    //obtiene un cliente dependiendo de su DNI
    @Override
    public ClientDTO getClientByDni(int dni) throws NoClientException {
        ClientDTO cliente = null;
        Optional<ClientDTO> filterClient = this.getallClients().stream()
                .filter(clientDTO -> clientDTO.getDni() == dni)
                .findFirst();
        if (!filterClient.isEmpty())
        cliente = filterClient.get();
        return cliente;
    }

    //guarda un cliente nuevo en la BD
    @Override
    public ClientResponseDTO saveClient(ClientDTO client) throws IOException, InternalErrorException {

        int id = this.clientes.size();

        try {
            client.setId(id);
            this.clientes.add(client);
            ObjectMapper objectMapper = new ObjectMapper();
            String basePath = new File("").getAbsolutePath();
            File file = ResourceUtils.getFile(basePath +"/src/main/resources/dbClient.json");
            objectMapper.writeValue(file, this.clientes);
        }
        catch (IOException e)
        {
            throw new InternalErrorException();
        }

        ClientResponseDTO response = new ClientResponseDTO();

        response.setCliente(client);
        response.setMsg("saved with id: " + id);
        return response;
    }

    //filtra los clientes por provincia
    @Override
    public List<ClientDTO> getClientsByProvince(String province) {
        List<ClientDTO> clientes = clientes = this.getallClients().stream()
                .filter(clientDTO -> clientDTO.getProvince().contains(province)).collect(Collectors.toList());
        return clientes;
    }

    //carga la base de datos.
    public List<ClientDTO> loadDataBase() {
        File file = null;
        try {
            String basePath = new File("").getAbsolutePath();

            file = ResourceUtils.getFile(basePath +"/src/main/resources/dbClient.json");
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<ClientDTO>> typeRef = new TypeReference<List<ClientDTO>>() {};
        List<ClientDTO> clientList = null;

        try {
            clientList = objectMapper.readValue(file, typeRef);

        }catch (Exception e){
            e.printStackTrace();
        }
        return clientList;

    }

}

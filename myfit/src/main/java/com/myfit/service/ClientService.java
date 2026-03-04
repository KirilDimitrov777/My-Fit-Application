package com.myfit.service;

import com.myfit.entity.Client;
import com.myfit.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client c) {
        return clientRepository.save(c);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email).orElseThrow();
    }

    // 📌 ДОБАВЕНО: за статистиката
    public long countClients() {
        return clientRepository.count();
    }
}

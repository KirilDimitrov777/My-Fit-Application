package com.myfit.service;

import com.myfit.entity.ClientPhoto;
import com.myfit.repository.ClientPhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientPhotoService {

    private final ClientPhotoRepository repo;

    public ClientPhotoService(ClientPhotoRepository repo) {
        this.repo = repo;
    }

    public ClientPhoto save(ClientPhoto photo) {
        return repo.save(photo);
    }
}

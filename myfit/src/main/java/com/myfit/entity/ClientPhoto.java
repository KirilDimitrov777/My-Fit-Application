package com.myfit.entity;

import jakarta.persistence.*;

@Entity
public class ClientPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientPhoto() {}

    public ClientPhoto(String fileName, String filePath, Client client) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.client = client;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}

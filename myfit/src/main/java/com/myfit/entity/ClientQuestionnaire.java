package com.myfit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questionnaires")
public class ClientQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goal;
    private String experienceLevel;
    private String healthNotes;
    private Double weight;
    private Double height;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientQuestionnaire() {}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getHealthNotes() { return healthNotes; }
    public void setHealthNotes(String healthNotes) { this.healthNotes = healthNotes; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}

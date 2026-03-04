package com.myfit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trainer_assignments")
public class TrainerAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String status; // "NEW" or "ACTIVE"

    @Lob
    private String programText; // тренировъчна програма

    @Lob
    private String clientNoteToTrainer; // какво иска клиентът

    public TrainerAssignment() {}

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getProgramText() { return programText; }
    public void setProgramText(String programText) { this.programText = programText; }

    public String getClientNoteToTrainer() { return clientNoteToTrainer; }
    public void setClientNoteToTrainer(String clientNoteToTrainer) { this.clientNoteToTrainer = clientNoteToTrainer; }
}

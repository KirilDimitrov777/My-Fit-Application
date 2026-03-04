package com.myfit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userAccount;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<ClientPhoto> photos = new java.util.ArrayList<>();

    public java.util.List<ClientPhoto> getPhotos() {
        return photos;
    }


    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private ClientQuestionnaire questionnaire;

    public Client() {}

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public User getUserAccount() { return userAccount; }
    public void setUserAccount(User userAccount) { this.userAccount = userAccount; }

    public ClientQuestionnaire getQuestionnaire() { return questionnaire; }
    public void setQuestionnaire(ClientQuestionnaire questionnaire) { this.questionnaire = questionnaire; }

}

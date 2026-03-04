package com.myfit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private Double salary;
    private String role; // специализация, не security role

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userAccount;

    public Trainer() {}

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public User getUserAccount() { return userAccount; }
    public void setUserAccount(User userAccount) { this.userAccount = userAccount; }
}

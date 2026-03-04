package com.myfit.repository;

import com.myfit.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    // За търсачката по име/роля
    List<Trainer> findByFullNameContainingIgnoreCaseOrRoleContainingIgnoreCase(String name, String role);

    // За съвпадение по email (нужно на DataLoader)
    Optional<Trainer> findByEmail(String email);
}

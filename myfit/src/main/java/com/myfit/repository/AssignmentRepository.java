package com.myfit.repository;

import com.myfit.entity.TrainerAssignment;
import com.myfit.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<TrainerAssignment, Long> {
    List<TrainerAssignment> findByTrainer(Trainer trainer);

    List<TrainerAssignment> findByClient_Id(Long clientId);
}

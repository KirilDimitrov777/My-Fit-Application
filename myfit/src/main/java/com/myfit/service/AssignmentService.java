package com.myfit.service;

import com.myfit.entity.Trainer;
import com.myfit.entity.TrainerAssignment;
import com.myfit.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<TrainerAssignment> getAssignmentsForTrainer(Trainer trainer) {
        return assignmentRepository.findByTrainer(trainer);
    }

    public TrainerAssignment getById(Long id) {
        return assignmentRepository.findById(id).orElseThrow();
    }

    public void acceptClient(Long id) {
        TrainerAssignment a = assignmentRepository.findById(id).orElseThrow();
        a.setStatus("ACTIVE");
        assignmentRepository.save(a);
    }

    public void removeClient(Long id) {
        assignmentRepository.deleteById(id);
    }

    public void updateProgram(Long id, String text) {
        TrainerAssignment a = assignmentRepository.findById(id).orElseThrow();
        a.setProgramText(text);
        assignmentRepository.save(a);
    }

    public TrainerAssignment createAssignment(TrainerAssignment a) {
        return assignmentRepository.save(a);
    }

    public List<TrainerAssignment> getAssignmentsByClientId(Long clientId) {
        return assignmentRepository.findByClient_Id(clientId);
    }
}

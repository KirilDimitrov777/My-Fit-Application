package com.myfit.service;

import com.myfit.entity.Trainer;
import com.myfit.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    // 🔥 НОВО — връща броя треньори
    public long countTrainers() {
        return trainerRepository.count();
    }

    public List<Trainer> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return trainerRepository.findAll();
        }
        return trainerRepository.findByFullNameContainingIgnoreCaseOrRoleContainingIgnoreCase(keyword, keyword);
    }

    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public Trainer updateTrainer(Long id, Trainer updated) {
        Trainer t = trainerRepository.findById(id).orElseThrow();
        t.setFullName(updated.getFullName());
        t.setEmail(updated.getEmail());
        t.setPhone(updated.getPhone());
        t.setGender(updated.getGender());
        t.setSalary(updated.getSalary());
        t.setRole(updated.getRole());
        return trainerRepository.save(t);
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    public void deleteAllTrainers() {
        trainerRepository.deleteAll();
    }

    public Trainer getById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }
}

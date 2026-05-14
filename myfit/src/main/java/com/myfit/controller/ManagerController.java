package com.myfit.controller;

import com.myfit.entity.Trainer;
import com.myfit.entity.TrainerAssignment;
import com.myfit.service.AssignmentService;
import com.myfit.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final TrainerService trainerService;
    private final AssignmentService assignmentService;

    public ManagerController(TrainerService trainerService,
                             AssignmentService assignmentService) {
        this.trainerService = trainerService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String keyword, Model model) {

        model.addAttribute("trainers", trainerService.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);

        model.addAttribute("trainer", null);

        return "manager/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editTrainer(@PathVariable Long id,
                              @RequestParam(required = false) String keyword,
                              Model model) {

        Trainer trainer = trainerService.getById(id);

        model.addAttribute("trainers", trainerService.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("trainer", trainer); // този ще се ползва да попълни формата

        return "manager/dashboard";
    }

    @PostMapping("/save")
    public String saveTrainer(@ModelAttribute Trainer trainer) {
        trainerService.saveTrainer(trainer); // repo.save() -> insert или update
        return "redirect:/manager/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable Long id) {
        Trainer trainer = trainerService.getById(id);

        List<TrainerAssignment> assignments = assignmentService.getAssignmentsForTrainer(trainer);
        for (TrainerAssignment a : assignments) {
            assignmentService.removeClient(a.getId());
        }

        trainerService.deleteTrainer(id);
        return "redirect:/manager/dashboard";
    }

    @GetMapping("/delete-all")
    public String deleteAll() {
        List<Trainer> trainers = trainerService.getAllTrainers();


        for (Trainer t : trainers) {
            List<TrainerAssignment> assignments = assignmentService.getAssignmentsForTrainer(t);
            for (TrainerAssignment a : assignments) {
                assignmentService.removeClient(a.getId());
            }
        }

        trainerService.deleteAllTrainers();
        return "redirect:/manager/dashboard";
    }
}

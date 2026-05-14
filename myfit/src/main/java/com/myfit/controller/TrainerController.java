package com.myfit.controller;

import com.myfit.entity.Client;
import com.myfit.entity.Trainer;
import com.myfit.entity.TrainerAssignment;
import com.myfit.entity.Message;
import com.myfit.service.AssignmentService;
import com.myfit.service.ClientService;
import com.myfit.service.TrainerService;
import com.myfit.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    private final AssignmentService assignmentService;
    private final TrainerService trainerService;
    private final ClientService clientService;
    private final MessageService messageService;

    public TrainerController(AssignmentService assignmentService,
                             TrainerService trainerService,
                             ClientService clientService,
                             MessageService messageService) {
        this.assignmentService = assignmentService;
        this.trainerService = trainerService;
        this.clientService = clientService;
        this.messageService = messageService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Trainer trainer = trainerService.getAllTrainers().get(0);
        model.addAttribute("assignments", assignmentService.getAssignmentsForTrainer(trainer));
        return "trainer/dashboard";
    }

    @PostMapping("/accept/{id}")
    public String acceptClient(@PathVariable Long id) {
        assignmentService.acceptClient(id);
        return "redirect:/trainer/dashboard";
    }

    @PostMapping("/remove/{id}")
    public String removeClient(@PathVariable Long id) {
        assignmentService.removeClient(id);
        return "redirect:/trainer/dashboard";
    }

    @GetMapping("/client/{id}")
    public String clientDetails(@PathVariable Long id, Model model) {
        TrainerAssignment a = assignmentService.getById(id);
        model.addAttribute("assignment", a);
        return "trainer/client-details";
    }

    @PostMapping("/client/{id}/program")
    public String updateProgram(@PathVariable Long id, @RequestParam String programText) {
        assignmentService.updateProgram(id, programText);
        assignmentService.acceptClient(id);
        return "redirect:/trainer/client/" + id;
    }

    @GetMapping("/client/{assignmentId}/photos")
    public String viewClientPhotos(@PathVariable Long assignmentId, Model model) {
        TrainerAssignment a = assignmentService.getById(assignmentId);
        Client client = a.getClient();

        model.addAttribute("client", client);
        model.addAttribute("assignment", a);
        model.addAttribute("photos", client.getPhotos());

        return "trainer/client-photos";
    }

    // CHAT — Trainer View
    @GetMapping("/chat/{assignmentId}")
    public String trainerChat(@PathVariable Long assignmentId, Model model) {
        TrainerAssignment a = assignmentService.getById(assignmentId);

        model.addAttribute("messages", messageService.chat(a.getClient(), a.getTrainer()));
        model.addAttribute("clientId", a.getClient().getId());
        model.addAttribute("trainerId", a.getTrainer().getId());
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("sender", "TRAINER");
        return "trainer/chat";
    }

    @PostMapping("/chat/send")
    public String sendTrainerMessage(
            @RequestParam Long assignmentId,
            @RequestParam Long clientId,
            @RequestParam Long trainerId,
            @RequestParam String text
    ) {
        Message m = new Message();
        m.setClient(clientService.getById(clientId));
        m.setTrainer(trainerService.getById(trainerId));
        m.setSender("TRAINER");
        m.setText(text);

        messageService.send(m);

        return "redirect:/trainer/chat/" + assignmentId;
    }
}

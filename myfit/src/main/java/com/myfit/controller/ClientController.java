package com.myfit.controller;

import com.myfit.entity.*;
import com.myfit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final TrainerService trainerService;
    private final QuestionnaireService questionnaireService;
    private final AssignmentService assignmentService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ClientPhotoService photoService;

    @Autowired
    private MessageService messageService;

    public ClientController(
            ClientService clientService,
            TrainerService trainerService,
            QuestionnaireService questionnaireService,
            AssignmentService assignmentService,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.clientService = clientService;
        this.trainerService = trainerService;
        this.questionnaireService = questionnaireService;
        this.assignmentService = assignmentService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "client/register";
    }

    @PostMapping("/register")
    public String registerClient(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password
    ) {
        User u = new User(email, passwordEncoder.encode(password), "CLIENT", fullName);
        u = userService.save(u);

        Client c = new Client();
        c.setFullName(fullName);
        c.setEmail(email);
        c.setPhone(phone);
        c.setUserAccount(u);
        clientService.save(c);

        return "redirect:/client/questionnaire?clientId=" + c.getId();
    }

    @GetMapping("/questionnaire")
    public String questionnaire(@RequestParam Long clientId, Model model) {
        model.addAttribute("clientId", clientId);
        return "client/questionnaire";
    }

    @PostMapping("/questionnaire")
    public String submitQuestionnaire(
            @RequestParam Long clientId,
            @RequestParam String goal,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) String healthNotes,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double height
    ) {
        Client c = clientService.getById(clientId);

        ClientQuestionnaire q = new ClientQuestionnaire();
        q.setGoal(goal);
        q.setExperienceLevel(experienceLevel);
        q.setHealthNotes(healthNotes);
        q.setWeight(weight);
        q.setHeight(height);

        questionnaireService.saveQuestionnaire(c, q);

        return "redirect:/client/choose?clientId=" + clientId;
    }


    @GetMapping("/choose")
    public String chooseTrainer(@RequestParam Long clientId, Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "client/choose";
    }

    @PostMapping("/choose/{trainerId}")
    public String choose(
            @PathVariable Long trainerId,
            @RequestParam Long clientId,
            @RequestParam String note
    ) {
        Trainer trainer = trainerService.getById(trainerId);
        Client client = clientService.getById(clientId);

        TrainerAssignment a = new TrainerAssignment();
        a.setTrainer(trainer);
        a.setClient(client);
        a.setStatus("NEW");
        a.setClientNoteToTrainer(note);
        a.setProgramText("");
        assignmentService.createAssignment(a);

        return "redirect:/client/request-sent";

    }
    @GetMapping("/request-sent")
    public String requestSent() {
        return "client/request-sent";
    }
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Long clientId, Model model) {
        Client client = clientService.getById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("assignments", assignmentService.getAssignmentsByClientId(clientId));
        return "client/dashboard";
    }

    @PostMapping("/upload-photo")
    public String uploadPhoto(
            @RequestParam Long clientId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        if (file.isEmpty()) return "redirect:/client/dashboard?clientId=" + clientId;

        String baseDir = System.getProperty("user.home") + "/myfit_uploads/client/" + clientId;
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(baseDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        java.nio.file.Path filePath = java.nio.file.Paths.get(baseDir).resolve(fileName);
        file.transferTo(filePath.toFile());

        String webPath = "/uploads/client/" + clientId + "/" + fileName;
        Client client = clientService.getById(clientId);
        photoService.save(new ClientPhoto(fileName, webPath, client));

        return "redirect:/client/dashboard?clientId=" + clientId;
    }

    @GetMapping("/chat")
    public String clientChat(@RequestParam Long clientId, Model model) {
        Client client = clientService.getById(clientId);
        TrainerAssignment a = assignmentService.getAssignmentsByClientId(clientId).get(0);
        Trainer trainer = a.getTrainer();

        model.addAttribute("messages", messageService.chat(client, trainer));
        model.addAttribute("clientId", clientId);
        model.addAttribute("trainerId", trainer.getId());
        model.addAttribute("sender", "CLIENT");
        return "client/chat";
    }

    @PostMapping("/chat/send")
    public String sendClientMessage(
            @RequestParam Long clientId,
            @RequestParam Long trainerId,
            @RequestParam String text
    ) {
        Message m = new Message();
        m.setClient(clientService.getById(clientId));
        m.setTrainer(trainerService.getById(trainerId));
        m.setSender("CLIENT");
        m.setText(text);

        messageService.send(m);

        return "redirect:/client/chat?clientId=" + clientId;
    }
}

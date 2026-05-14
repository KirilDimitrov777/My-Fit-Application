package com.myfit.controller;

import com.myfit.service.ClientService;
import com.myfit.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    private final ClientService clientService;
    private final TrainerService trainerService;

    public AuthController(ClientService clientService, TrainerService trainerService) {
        this.clientService = clientService;
        this.trainerService = trainerService;
    }

    @GetMapping("/")
    public String landing(Model model) {

        long activeClients = clientService.countClients();
        long onlineTrainers = trainerService.countTrainers();   // 🔥 реален брой треньори
        double averageRating = 4.9;                             // по-късно може да стане реално

        model.addAttribute("activeClients", activeClients);
        model.addAttribute("onlineTrainers", onlineTrainers);
        model.addAttribute("averageRating", averageRating);

        return "auth/login-hub";
    }

    @GetMapping("/login/manager")
    public String managerLogin() {
        return "auth/login-manager";
    }

    @GetMapping("/login/trainer")
    public String trainerLogin() {
        return "auth/login-trainer";
    }

    @GetMapping("/login/client")
    public String clientLogin() {
        return "auth/login-client";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        return "auth/register";
    }
}

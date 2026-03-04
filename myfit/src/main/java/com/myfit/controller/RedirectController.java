package com.myfit.controller;

import com.myfit.entity.Client;
import com.myfit.service.ClientService;
import com.myfit.service.AssignmentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    private final ClientService clientService;
    private final AssignmentService assignmentService;

    public RedirectController(ClientService clientService, AssignmentService assignmentService) {
        this.clientService = clientService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/redirect-after-login")
    public String redirectAfterLogin(Authentication auth) {

        String email = auth.getName();

        // ✅ Manager
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            return "redirect:/manager/dashboard";
        }

        // ✅ Trainer
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TRAINER"))) {
            return "redirect:/trainer/dashboard";
        }

        // ✅ Client logic
        Client c = clientService.getByEmail(email);

        // няма попълнен въпросник
        if (c.getQuestionnaire() == null) {
            return "redirect:/client/questionnaire?clientId=" + c.getId();
        }

        // няма избран треньор
        if (assignmentService.getAssignmentsByClientId(c.getId()).isEmpty()) {
            return "redirect:/client/choose?clientId=" + c.getId();
        }

        // иначе → dashboard
        return "redirect:/client/dashboard?clientId=" + c.getId();
    }
}

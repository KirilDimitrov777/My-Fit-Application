package com.myfit.config;

import com.myfit.entity.Trainer;
import com.myfit.entity.User;
import com.myfit.repository.TrainerRepository;
import com.myfit.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TrainerRepository trainerRepository;

    public DataLoader(UserService userService,
                      PasswordEncoder passwordEncoder,
                      TrainerRepository trainerRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void run(String... args) {

        // 1) Manager
        ensureManager();

        // 2) Demo trainer
        ensureDemoTrainer();
    }

    private void ensureManager() {
        boolean managerExists = true;
        try {
            userService.loadUserByUsername("manager@myfit.com");
        } catch (UsernameNotFoundException e) {
            managerExists = false;
        }

        if (!managerExists) {
            User manager = new User(
                    "manager@myfit.com",
                    passwordEncoder.encode("manager1914"),
                    "MANAGER",
                    "Main Manager"
            );
            userService.save(manager);
            System.out.println("✅ Created MANAGER: manager@myfit.com / manager1914");
        } else {
            System.out.println("ℹ MANAGER already exists.");
        }
    }

    private void ensureDemoTrainer() {
        boolean trainerUserExists = true;
        try {
            userService.loadUserByUsername("trainer1@myfit.com");
        } catch (UsernameNotFoundException e) {
            trainerUserExists = false;
        }

        if (!trainerUserExists) {
            // 1) създаваме User за треньора
            User trainerUser = new User(
                    "trainer1@myfit.com",
                    passwordEncoder.encode("trainer1914"),
                    "TRAINER",
                    "Demo Trainer"
            );
            trainerUser = userService.save(trainerUser);

            // 2) създаваме Trainer профил, за да се вижда в таблицата при мениджъра
            Trainer trainerProfile = new Trainer();
            trainerProfile.setFullName("Demo Trainer");
            trainerProfile.setEmail("trainer1@myfit.com");
            trainerProfile.setPhone("0887000000");
            trainerProfile.setGender("Male");
            trainerProfile.setSalary(2500.0);
            trainerProfile.setRole("Strength Coach");
            trainerProfile.setUserAccount(trainerUser);

            trainerRepository.save(trainerProfile);

            System.out.println("✅ Created TRAINER: trainer1@myfit.com / trainer1914");
        } else {
            System.out.println("ℹ TRAINER already exists.");
        }
    }
}

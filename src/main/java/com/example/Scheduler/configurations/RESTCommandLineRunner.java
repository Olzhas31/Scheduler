package com.example.Scheduler.configurations;

import com.example.Scheduler.domains.Role;
import com.example.Scheduler.domains.User;
import com.example.Scheduler.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RESTCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        role = roleRepository.saveAndFlush(role);
//
        if (!userRepository.existsUserByUsername("admin")) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(encoder.encode("100"));
            user.setRole(Role.ROLE_ADMIN);
            userRepository.saveAndFlush(user);
        }

    }
}

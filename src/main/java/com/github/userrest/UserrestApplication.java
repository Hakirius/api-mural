package com.github.userrest;

import com.github.userrest.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserrestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserrestApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedData(UserService userService) {
        return args -> {
            userService.ensureUser("admin", "admin", "ADMIN");
            userService.ensureUser("user", "user", "USER");
        };
    }
}

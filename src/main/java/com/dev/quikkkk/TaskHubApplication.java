package com.dev.quikkkk;

import com.dev.quikkkk.entity.Role;
import com.dev.quikkkk.repository.IRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
@Slf4j
public class TaskHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskHubApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(IRoleRepository repository) {
        return args -> {
            createRoleIfNotExists(repository, "ROLE_ADMIN");
            createRoleIfNotExists(repository, "ROLE_MANAGER");
            createRoleIfNotExists(repository, "ROLE_USER");
        };
    }

    private void createRoleIfNotExists(IRoleRepository repository, String roleName) {
        Optional<Role> existingRole = repository.findByName(roleName);

        if (existingRole.isEmpty()) {
            Role role = Role
                    .builder()
                    .name(roleName)
                    .createdBy("system")
                    .build();

            repository.save(role);
            log.info("Created role: {}", roleName);
        }
    }
}

package com.my.watermelon;

import com.my.watermelon.entity.User;
import com.my.watermelon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1111"))
                    .name("관리자")
                    .email("admin@example.com")
                    .roles(Set.of("ROLE_ADMIN","ROLE_USER"))
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin / 1111");
        }
    }
}

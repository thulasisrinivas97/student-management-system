package com.platformcommons.sms;

import com.platformcommons.sms.entity.Admin;
import com.platformcommons.sms.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }

    /**
     * Seeds a default admin user on startup so the assignment's
     * "Admin Login" flow is testable via Postman out of the box.
     * Default admin credentials are configured through the ADMIN_PASSWORD environment variable.
     */
    @Value("${app.admin.password}")
    private String adminPassword;
    @Bean
    CommandLineRunner seedAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (adminRepository.findByUsername("admin").isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                adminRepository.save(admin);
                System.out.println(">>> Seeded default admin user: admin");
            }
        };
    }
}

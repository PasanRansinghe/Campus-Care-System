package com.ruhuna.campuscaresystem;

import com.ruhuna.campuscaresystem.model.Issue;
import com.ruhuna.campuscaresystem.model.User;
import com.ruhuna.campuscaresystem.repository.IssueRepository;
import com.ruhuna.campuscaresystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(IssueRepository issueRepository, UserRepository userRepository,
                      BCryptPasswordEncoder passwordEncoder) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Load sample issues
            loadSampleIssues();

            // Load default users
            loadDefaultUsers();

        } catch (Exception e) {
            logger.error("Error loading sample data: {}", e.getMessage());
            // Don't throw the exception to allow the application to continue running
        }
    }

    private void loadSampleIssues() {
        // Check if issues already exist
        long issueCount = issueRepository.count();

        if (issueCount == 0) {
            logger.info("Loading sample issues into database...");

            // Add sample issue 1
            Issue issue1 = new Issue();
            issue1.setTitle("A WINDOW IS BROKEN...!");
            issue1.setCategory("Broken");
            issue1.setDescription("A window on the right side of the library is broken.");
            issue1.setLocation("Library");
            issue1.setDate(LocalDate.of(2025, 3, 1));
            issue1.setStatus("Pending");

            // Add sample issue 2
            Issue issue2 = new Issue();
            issue2.setTitle("NEED A TAEKWONDO SET...!");
            issue2.setCategory("Needed");
            issue2.setDescription("Not enough taekwondo equipment to practice.");
            issue2.setLocation("Taekwondo Gymnasium");
            issue2.setDate(LocalDate.of(2025, 4, 7));
            issue2.setStatus("Finished");

            // Save to database
            issueRepository.save(issue1);
            issueRepository.save(issue2);

            logger.info("Sample issues loaded successfully!");
        } else {
            logger.info("Database already contains {} issue records. Skipping issue loading.", issueCount);
        }
    }

    private void loadDefaultUsers() {
        // Check if users already exist
        long userCount = userRepository.count();

        if (userCount == 0) {
            logger.info("Loading default users into database...");

            // Create admin user
            User adminUser = new User();
            adminUser.setName("System Administrator");
            adminUser.setEmail("admin@ruh.ac.lk");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(adminUser);

            // Create sample regular user
            User regularUser = new User();
            regularUser.setName("John Doe");
            regularUser.setEmail("john@example.com");
            regularUser.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(regularUser);

            logger.info("Default users loaded successfully!");
            logger.info("Admin credentials - Email: admin@ruh.ac.lk, Password: admin123");
            logger.info("User credentials - Email: john@example.com, Password: password123");
        } else {
            logger.info("Database already contains {} user records. Skipping user loading.", userCount);
        }
    }
}
package com.rvg.data;

import com.rvg.SoftwareEngineer;
import com.rvg.repository.SoftwareEngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataLoader is a component that implements CommandLineRunner to load initial data
 * into the SoftwareEngineerRepository when the application starts.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SoftwareEngineerRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) {
            System.out.println("⏭️  Database already has data. Skipping initial load.");
            return;
        }

        System.out.println("📦 Loading initial data...");

        /* Predefined list of software engineers to be loaded into the repository */
        List<SoftwareEngineer> engineers = List.of(
                createEngineer("Ricardo Hernandez",
                        List.of("Java", "Spring Boot", "Kafka", "AWS", "Kubernetes")),
                createEngineer("Maria Garcia",
                        List.of("Python", "Django", "PostgreSQL", "Docker", "Redis")),
                createEngineer("Carlos Lopez",
                        List.of("JavaScript", "React", "Node.js", "MongoDB", "TypeScript")),
                createEngineer("Ana Martinez",
                        List.of("Go", "Kubernetes", "Docker", "Terraform", "AWS")),
                createEngineer("Luis Rodriguez",
                        List.of("C#", ".NET Core", "Azure", "SQL Server", "Microservices"))
        );

        repository.saveAll(engineers);
        System.out.println("✅ Loaded " + engineers.size() + " engineers (without AI recommendations)");
        System.out.println("💡 Use POST to create new engineers with AI-generated learning paths");
    }

    private SoftwareEngineer createEngineer(String name, List<String> techStack) {
        SoftwareEngineer engineer = new SoftwareEngineer(null, name, techStack, null);
        engineer.setLearningPathRecommendations(null);
        return engineer;
    }
}

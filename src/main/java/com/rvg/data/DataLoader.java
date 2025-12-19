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
        repository.deleteAll();

        /* Predefined list of software engineers to be loaded into the repository */
        List<SoftwareEngineer> engineers = List.of(
                new SoftwareEngineer(
                        null,
                        "Ricardo Hernandez",
                        List.of("Java", "Spring Boot", "Kafka", "AWS", "Kubernetes")
                ),
                new SoftwareEngineer(
                        null,
                        "Maria Garcia",
                        List.of("Python", "Django", "PostgreSQL", "Docker", "Redis")
                ),
                new SoftwareEngineer(
                        null,
                        "Carlos Lopez",
                        List.of("JavaScript", "React", "Node.js", "MongoDB", "TypeScript")
                ),
                new SoftwareEngineer(
                        null,
                        "Ana Martinez",
                        List.of("Go", "Kubernetes", "Docker", "Terraform", "AWS")
                ),
                new SoftwareEngineer(
                        null,
                        "Luis Rodriguez",
                        List.of("C#", ".NET Core", "Azure", "SQL Server", "Microservices")
                )
        );

        repository.saveAll(engineers);
    }
}

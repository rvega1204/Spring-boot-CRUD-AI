package com.rvg;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * SoftwareEngineer is an entity class representing a software engineer with an ID, name, and tech stack.
 */
@Entity
public class SoftwareEngineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ElementCollection
    @CollectionTable(name = "software_engineer_tech_stack",
            joinColumns = @JoinColumn(name = "software_engineer_id"))
    @Column(name = "tech_stack")
    private List<String> techStack;
    @Column(columnDefinition = "TEXT")
    private String learningPathRecommendations;

    public SoftwareEngineer() {
    }

    public SoftwareEngineer(Integer id, String name, List<String> techStack, String learningPathRecommendations) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
        this.learningPathRecommendations = learningPathRecommendations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTechStack() {
        return techStack;
    }

    public String getLearningPathRecommendations() {
        return learningPathRecommendations;
    }

    public void setLearningPathRecommendations(String learningPathRecommendations) {
        this.learningPathRecommendations = learningPathRecommendations;
    }

    public void setTechStack(List<String> techStack) {
        this.techStack = techStack != null ? new ArrayList<>(techStack) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SoftwareEngineer that = (SoftwareEngineer) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(techStack, that.techStack) && Objects.equals(learningPathRecommendations, that.learningPathRecommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, techStack, learningPathRecommendations);
    }
}

package com.rvg.dto;

import com.rvg.SoftwareEngineer;

import java.util.List;

/*
 * Data Transfer Object (DTO) for SoftwareEngineer entity.
 * Used to transfer data between layers without exposing the entity directly.
 */
public record SoftwareEngineerDTO(
        Integer id,
        String name,
        List<String> techStack,
        String learningPathRecommendations
) {

    public SoftwareEngineerDTO(SoftwareEngineer engineer) {
        this(
                engineer.getId(),
                engineer.getName(),
                engineer.getTechStack(),
                engineer.getLearningPathRecommendations()
        );
    }
}

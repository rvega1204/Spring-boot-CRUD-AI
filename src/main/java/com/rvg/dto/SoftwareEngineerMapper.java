package com.rvg.dto;

import com.rvg.SoftwareEngineer;

import java.util.List;

/**
 * Mapper class for converting between SoftwareEngineer entities and SoftwareEngineerDTOs.
 */
public class SoftwareEngineerMapper {

    public static SoftwareEngineerDTO toDTO(SoftwareEngineer engineer) {
        return new SoftwareEngineerDTO(engineer);
    }

    /* Converts a SoftwareEngineerDTO to a SoftwareEngineer entity.
     *
     * @param dto The SoftwareEngineerDTO to convert.
     * @return The corresponding SoftwareEngineer entity.
     */
    public static SoftwareEngineer toEntity(SoftwareEngineerDTO dto) {
        return new SoftwareEngineer(
                dto.id(),
                dto.name(),
                dto.techStack(),
                dto.learningPathRecommendations()
        );
    }

    /* Converts a list of SoftwareEngineer entities to a list of SoftwareEngineerDTOs.
     *
     * @param engineers The list of SoftwareEngineer entities to convert.
     * @return The corresponding list of SoftwareEngineerDTOs.
     */
    public static List<SoftwareEngineerDTO> toDTOList(List<SoftwareEngineer> engineers) {
        return engineers.stream()
                .map(SoftwareEngineerDTO::new)
                .toList();
    }
}

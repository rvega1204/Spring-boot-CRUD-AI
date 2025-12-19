package com.rvg.controller;

import com.rvg.SoftwareEngineer;
import com.rvg.dto.SoftwareEngineerDTO;
import com.rvg.dto.SoftwareEngineerMapper;
import com.rvg.service.SoftwareEngineerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing Software Engineer entities.
 * Provides endpoints for CRUD operations.
 */
@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineers) {
        this.softwareEngineerService = softwareEngineers;
    }

    /**
     * Retrieves all Software Engineers.
     *
     * @return A list of Software Engineer DTOs.
     */
    @GetMapping
    public ResponseEntity<List<SoftwareEngineerDTO>> getAllEngineers() {
        List<SoftwareEngineer> engineers = softwareEngineerService.getAllSoftwareEngineers();
        return ResponseEntity.ok(SoftwareEngineerMapper.toDTOList(engineers));
    }

    /**
     * Retrieves a Software Engineer by ID.
     *
     * @param id The ID of the Software Engineer.
     * @return The Software Engineer DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SoftwareEngineerDTO> getEngineerById(@PathVariable Integer id) {
        SoftwareEngineer engineer = softwareEngineerService.findById(id);
        return ResponseEntity.ok(SoftwareEngineerMapper.toDTO(engineer));
    }

    /**
     * Creates a new Software Engineer.
     *
     * @param dto The Software Engineer DTO.
     * @return The created Software Engineer DTO.
     */
    @PostMapping
    public ResponseEntity<SoftwareEngineerDTO> createEngineer(@RequestBody SoftwareEngineerDTO dto) {
        SoftwareEngineer entity = SoftwareEngineerMapper.toEntity(dto);
        SoftwareEngineer saved = softwareEngineerService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SoftwareEngineerMapper.toDTO(saved));
    }

    /**
     * Updates an existing Software Engineer.
     *
     * @param id  The ID of the Software Engineer to update.
     * @param dto The Software Engineer DTO with updated information.
     * @return The updated Software Engineer DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SoftwareEngineerDTO> updateEngineer(
            @PathVariable Integer id,
            @RequestBody SoftwareEngineerDTO dto) {
        SoftwareEngineer entity = SoftwareEngineerMapper.toEntity(dto);
        entity.setId(id);
        SoftwareEngineer updated = softwareEngineerService.update(entity);
        return ResponseEntity.ok(SoftwareEngineerMapper.toDTO(updated));
    }

    /**
     * Deletes a Software Engineer by ID.
     *
     * @param id The ID of the Software Engineer to delete.
     * @return A response entity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngineer(@PathVariable Integer id) {
        softwareEngineerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

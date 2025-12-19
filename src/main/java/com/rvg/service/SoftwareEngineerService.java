package com.rvg.service;

import com.rvg.SoftwareEngineer;
import com.rvg.errors.NotFoundException;
import com.rvg.repository.SoftwareEngineerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Software Engineer entities.
 * Provides methods for CRUD operations.
 */
@Service
public class SoftwareEngineerService {

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    private final SoftwareEngineerRepository softwareEngineerRepository;

    /**
     * Retrieves all Software Engineers.
     *
     * @return A list of Software Engineer entities.
     */
    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }

    /**
     * Finds a Software Engineer by ID.
     *
     * @param id The ID of the Software Engineer.
     * @return The Software Engineer entity.
     * @throws NotFoundException if the Software Engineer with the given ID does not exist.
     */
    public SoftwareEngineer findById(Integer id) {
        return softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Engineer not found with id: " + id));
    }

    /**
     * Saves a new Software Engineer.
     *
     * @param engineer The Software Engineer entity to save.
     * @return The saved Software Engineer entity.
     */
    public SoftwareEngineer save(SoftwareEngineer engineer) {
        return softwareEngineerRepository.save(engineer);
    }

    /**
     * Updates an existing Software Engineer.
     *
     * @param engineer The Software Engineer entity with updated information.
     * @return The updated Software Engineer entity.
     * @throws NotFoundException if the Software Engineer with the given ID does not exist.
     */
    public SoftwareEngineer update(SoftwareEngineer engineer) {
        if (!softwareEngineerRepository.existsById(engineer.getId())) {
            throw new NotFoundException("Engineer not found with id: " + engineer.getId());
        }

        return softwareEngineerRepository.save(engineer);
    }

    /**
     * Deletes a Software Engineer by ID.
     *
     * @param id The ID of the Software Engineer to delete.
     * @throws NotFoundException if the Software Engineer with the given ID does not exist.
     */
    public void deleteById(Integer id) {
        if (!softwareEngineerRepository.existsById(id)) {
            throw new NotFoundException("Engineer not found with id: " + id);
        }

        softwareEngineerRepository.deleteById(id);
    }

}

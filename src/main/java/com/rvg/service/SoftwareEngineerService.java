package com.rvg.service;

import com.rvg.SoftwareEngineer;
import com.rvg.ai.AiService;
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

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final AiService aiService;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, AiService aiService) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.aiService = aiService;
    }

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
     * Saves a new Software Engineer and generates AI-based learning path recommendations.
     *
     * @param engineer The Software Engineer entity to save.
     * @return The saved Software Engineer entity with learning path recommendations.
     */
    public SoftwareEngineer save(SoftwareEngineer engineer) {
        String prompt = """
                Create a technical learning roadmap for %s
                Current tech stack: %s
    
                ## 🚀 Next Skills to Learn (Top 3)
                Recommend complementary technologies based on their stack.
                Prioritize by: market demand, career growth, and synergy with current skills.
    
                ## 📖 Learning Path
                For each recommended skill:
                - Best free resource (docs/tutorial)
                - Top paid course (with platform name)
                - Practice recommendation
    
                ## 🛠️ Portfolio Projects
                Suggest 3 hands-on projects:
                1. **Beginner:** Simple but impressive
                2. **Intermediate:** Combines multiple skills
                3. **Advanced:** Interview-worthy complexity
    
                Include tech stack for each project.
    
                Keep response under 300 words, actionable, and markdown-formatted.
                """.formatted(engineer.getTechStack(), engineer.getName());

        String chatRes = aiService.chat(prompt);
        engineer.setLearningPathRecommendations(chatRes);
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

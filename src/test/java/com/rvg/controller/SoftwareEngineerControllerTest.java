package com.rvg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvg.SoftwareEngineer;
import com.rvg.dto.SoftwareEngineerDTO;
import com.rvg.errors.GlobalExceptionHandler;
import com.rvg.errors.NotFoundException;
import com.rvg.service.SoftwareEngineerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for SoftwareEngineerController using MockMvc and Mockito.
 */
@WebMvcTest(SoftwareEngineerController.class)
@Import(GlobalExceptionHandler.class)
class SoftwareEngineerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SoftwareEngineerService softwareEngineerService;

    @Autowired
    private ObjectMapper objectMapper;

    private SoftwareEngineer testEngineer;
    private SoftwareEngineerDTO testEngineerDTO;

    @BeforeEach
    void setUp() {
        testEngineer = new SoftwareEngineer(
                1,
                "John Doe",
                Arrays.asList("Java", "Spring Boot", "PostgreSQL"),
                null
        );
        testEngineerDTO = new SoftwareEngineerDTO(
                1,
                "John Doe",
                Arrays.asList("Java", "Spring Boot", "PostgreSQL"),
                null
        );
    }

    @Test
    void getAllEngineers_ShouldReturnListOfEngineers() throws Exception {
        // Arrange
        SoftwareEngineer engineer2 = new SoftwareEngineer(
                2,
                "Jane Smith",
                Arrays.asList("Python", "Django", "MongoDB"),
                null
        );
        List<SoftwareEngineer> engineers = Arrays.asList(testEngineer, engineer2);
        when(softwareEngineerService.getAllSoftwareEngineers()).thenReturn(engineers);

        // Act & Assert
        mockMvc.perform(get("/api/v1/software-engineers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].techStack", hasSize(3)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jane Smith")));

        verify(softwareEngineerService, times(1)).getAllSoftwareEngineers();
    }

    @Test
    void getAllEngineers_WhenEmpty_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(softwareEngineerService.getAllSoftwareEngineers()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/software-engineers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(softwareEngineerService, times(1)).getAllSoftwareEngineers();
    }

    @Test
    void getEngineerById_WhenEngineerExists_ShouldReturnEngineer() throws Exception {
        // Arrange
        when(softwareEngineerService.findById(1)).thenReturn(testEngineer);

        // Act & Assert
        mockMvc.perform(get("/api/v1/software-engineers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.techStack", hasSize(3)))
                .andExpect(jsonPath("$.techStack[0]", is("Java")))
                .andExpect(jsonPath("$.techStack[1]", is("Spring Boot")))
                .andExpect(jsonPath("$.techStack[2]", is("PostgreSQL")));

        verify(softwareEngineerService, times(1)).findById(1);
    }

    @Test
    void getEngineerById_WhenEngineerDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(softwareEngineerService.findById(999))
                .thenThrow(new NotFoundException("Engineer not found with id: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/software-engineers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(softwareEngineerService, times(1)).findById(999);
    }

    @Test
    void createEngineer_WithValidData_ShouldReturnCreatedEngineer() throws Exception {
        // Arrange
        SoftwareEngineerDTO newEngineerDTO = new SoftwareEngineerDTO(
                null,
                "Alice Johnson",
                Arrays.asList("JavaScript", "React", "Node.js"),
                null
        );
        SoftwareEngineer savedEngineer = new SoftwareEngineer(
                3,
                "Alice Johnson",
                Arrays.asList("JavaScript", "React", "Node.js"),
                null
        );
        when(softwareEngineerService.save(any(SoftwareEngineer.class))).thenReturn(savedEngineer);

        // Act & Assert
        mockMvc.perform(post("/api/v1/software-engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEngineerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Alice Johnson")))
                .andExpect(jsonPath("$.techStack", hasSize(3)))
                .andExpect(jsonPath("$.techStack[0]", is("JavaScript")));

        verify(softwareEngineerService, times(1)).save(any(SoftwareEngineer.class));
    }

    @Test
    void updateEngineer_WhenEngineerExists_ShouldReturnUpdatedEngineer() throws Exception {
        // Arrange
        SoftwareEngineerDTO updatedDTO = new SoftwareEngineerDTO(
                1,
                "John Doe Updated",
                Arrays.asList("Java", "Spring Boot", "PostgreSQL", "Docker"),
                null
        );
        SoftwareEngineer updatedEngineer = new SoftwareEngineer(
                1,
                "John Doe Updated",
                Arrays.asList("Java", "Spring Boot", "PostgreSQL", "Docker"),
                null
        );
        when(softwareEngineerService.update(any(SoftwareEngineer.class))).thenReturn(updatedEngineer);

        // Act & Assert
        mockMvc.perform(put("/api/v1/software-engineers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe Updated")))
                .andExpect(jsonPath("$.techStack", hasSize(4)));

        verify(softwareEngineerService, times(1)).update(any(SoftwareEngineer.class));
    }

    @Test
    void updateEngineer_WhenEngineerDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        SoftwareEngineerDTO updatedDTO = new SoftwareEngineerDTO(
                999,
                "Non Existent",
                Arrays.asList("Tech"),
                null
        );
        when(softwareEngineerService.update(any(SoftwareEngineer.class)))
                .thenThrow(new NotFoundException("Engineer not found with id: 999"));

        // Act & Assert
        mockMvc.perform(put("/api/v1/software-engineers/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isNotFound());

        verify(softwareEngineerService, times(1)).update(any(SoftwareEngineer.class));
    }

    @Test
    void deleteEngineer_WhenEngineerExists_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(softwareEngineerService).deleteById(1);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/software-engineers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(softwareEngineerService, times(1)).deleteById(1);
    }

    @Test
    void deleteEngineer_WhenEngineerDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        doThrow(new NotFoundException("Engineer not found with id: 999"))
                .when(softwareEngineerService).deleteById(999);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/software-engineers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(softwareEngineerService, times(1)).deleteById(999);
    }

    @Test
    void createEngineer_WithEmptyTechStack_ShouldReturnCreatedEngineer() throws Exception {
        // Arrange
        SoftwareEngineerDTO newEngineerDTO = new SoftwareEngineerDTO(
                null,
                "Bob Wilson",
                Arrays.asList(),
                null
        );
        SoftwareEngineer savedEngineer = new SoftwareEngineer(
                4,
                "Bob Wilson",
                Arrays.asList(),
                null
        );
        when(softwareEngineerService.save(any(SoftwareEngineer.class))).thenReturn(savedEngineer);

        // Act & Assert
        mockMvc.perform(post("/api/v1/software-engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEngineerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("Bob Wilson")))
                .andExpect(jsonPath("$.techStack", hasSize(0)));

        verify(softwareEngineerService, times(1)).save(any(SoftwareEngineer.class));
    }
}

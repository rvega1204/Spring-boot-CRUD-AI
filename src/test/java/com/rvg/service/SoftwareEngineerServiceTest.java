package com.rvg.service;

import com.rvg.SoftwareEngineer;
import com.rvg.errors.NotFoundException;
import com.rvg.repository.SoftwareEngineerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SoftwareEngineerService using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class SoftwareEngineerServiceTest {

    @Mock
    private SoftwareEngineerRepository softwareEngineerRepository;

    @InjectMocks
    private SoftwareEngineerService softwareEngineerService;

    private SoftwareEngineer testEngineer;

    @BeforeEach
    void setUp() {
        testEngineer = new SoftwareEngineer(
                1,
                "John Doe",
                Arrays.asList("Java", "Spring Boot", "PostgreSQL")
        );
    }

    @Test
    void getAllSoftwareEngineers_ShouldReturnListOfEngineers() {
        // Arrange
        SoftwareEngineer engineer2 = new SoftwareEngineer(
                2,
                "Jane Smith",
                Arrays.asList("Python", "Django", "MongoDB")
        );
        List<SoftwareEngineer> expectedEngineers = Arrays.asList(testEngineer, engineer2);
        when(softwareEngineerRepository.findAll()).thenReturn(expectedEngineers);

        // Act
        List<SoftwareEngineer> result = softwareEngineerService.getAllSoftwareEngineers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedEngineers, result);
        verify(softwareEngineerRepository, times(1)).findAll();
    }

    @Test
    void getAllSoftwareEngineers_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(softwareEngineerRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<SoftwareEngineer> result = softwareEngineerService.getAllSoftwareEngineers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(softwareEngineerRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenEngineerExists_ShouldReturnEngineer() {
        // Arrange
        when(softwareEngineerRepository.findById(1)).thenReturn(Optional.of(testEngineer));

        // Act
        SoftwareEngineer result = softwareEngineerService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testEngineer.getId(), result.getId());
        assertEquals(testEngineer.getName(), result.getName());
        assertEquals(testEngineer.getTechStack(), result.getTechStack());
        verify(softwareEngineerRepository, times(1)).findById(1);
    }

    @Test
    void findById_WhenEngineerDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        when(softwareEngineerRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> softwareEngineerService.findById(999)
        );
        assertEquals("Engineer not found with id: 999", exception.getMessage());
        verify(softwareEngineerRepository, times(1)).findById(999);
    }

    @Test
    void save_ShouldReturnSavedEngineer() {
        // Arrange
        SoftwareEngineer newEngineer = new SoftwareEngineer(
                null,
                "Alice Johnson",
                Arrays.asList("JavaScript", "React", "Node.js")
        );
        SoftwareEngineer savedEngineer = new SoftwareEngineer(
                3,
                "Alice Johnson",
                Arrays.asList("JavaScript", "React", "Node.js")
        );
        when(softwareEngineerRepository.save(newEngineer)).thenReturn(savedEngineer);

        // Act
        SoftwareEngineer result = softwareEngineerService.save(newEngineer);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(3, result.getId());
        assertEquals("Alice Johnson", result.getName());
        assertEquals(3, result.getTechStack().size());
        verify(softwareEngineerRepository, times(1)).save(newEngineer);
    }

    @Test
    void update_WhenEngineerExists_ShouldReturnUpdatedEngineer() {
        // Arrange
        SoftwareEngineer updatedEngineer = new SoftwareEngineer(
                1,
                "John Doe Updated",
                Arrays.asList("Java", "Spring Boot", "PostgreSQL", "Docker")
        );
        when(softwareEngineerRepository.existsById(1)).thenReturn(true);
        when(softwareEngineerRepository.save(updatedEngineer)).thenReturn(updatedEngineer);

        // Act
        SoftwareEngineer result = softwareEngineerService.update(updatedEngineer);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe Updated", result.getName());
        assertEquals(4, result.getTechStack().size());
        verify(softwareEngineerRepository, times(1)).existsById(1);
        verify(softwareEngineerRepository, times(1)).save(updatedEngineer);
    }

    @Test
    void update_WhenEngineerDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        SoftwareEngineer nonExistentEngineer = new SoftwareEngineer(
                999,
                "Non Existent",
                Arrays.asList("Tech")
        );
        when(softwareEngineerRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> softwareEngineerService.update(nonExistentEngineer)
        );
        assertEquals("Engineer not found with id: 999", exception.getMessage());
        verify(softwareEngineerRepository, times(1)).existsById(999);
        verify(softwareEngineerRepository, never()).save(any());
    }

    @Test
    void deleteById_WhenEngineerExists_ShouldDeleteEngineer() {
        // Arrange
        when(softwareEngineerRepository.existsById(1)).thenReturn(true);
        doNothing().when(softwareEngineerRepository).deleteById(1);

        // Act
        softwareEngineerService.deleteById(1);

        // Assert
        verify(softwareEngineerRepository, times(1)).existsById(1);
        verify(softwareEngineerRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteById_WhenEngineerDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        when(softwareEngineerRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> softwareEngineerService.deleteById(999)
        );
        assertEquals("Engineer not found with id: 999", exception.getMessage());
        verify(softwareEngineerRepository, times(1)).existsById(999);
        verify(softwareEngineerRepository, never()).deleteById(any());
    }
}

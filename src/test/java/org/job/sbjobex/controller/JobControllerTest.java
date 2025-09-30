package org.job.sbjobex.controller;

import org.job.sbjobex.dto.JobDTO;
import org.job.sbjobex.model.JobEntity;
import org.job.sbjobex.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class JobControllerTest {

    private JobController jobController;
    private JobService jobService;

    @BeforeEach
    void setUp() {
        jobService = Mockito.mock(JobService.class);
        jobController = new JobController(jobService);
    }

    @Test
    void testIsFirstTimePoster_True() {
        // Arrange
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle("Sample Job Title");
        jobDTO.setDescription("Sample Job Description");
        jobDTO.setEmail("sample@example.com");
        jobDTO.setId(1L);

        JobEntity jobEntity = new JobEntity();
        jobEntity.setTitle("Sample Job Title");
        jobEntity.setDescription("Sample Job Description");
        jobEntity.setEmail("sample@example.com");

        when(jobService.isFirstTimePoster(anyString())).thenReturn(true);
        when(jobService.saveJob(jobDTO)).thenReturn(jobEntity);
        // Act
        ResponseEntity<String> response = jobController.postJob(jobDTO);

        // Assert
        assertTrue(response.getBody().contains("Job posted successfully with ID"));
        verify(jobService, times(1)).isFirstTimePoster("sample@example.com");
    }

    @Test
    void testSaveJob() {
        // Arrange
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle("Sample Job Title");
        jobDTO.setDescription("Sample Job Description");
        jobDTO.setEmail("sample@example.com");
        jobDTO.setId(1L);

        JobEntity jobEntity = new JobEntity();
        jobEntity.setTitle("Sample Job Title");
        jobEntity.setDescription("Sample Job Description");
        jobEntity.setEmail("sample@example.com");

        when(jobService.isFirstTimePoster(anyString())).thenReturn(true);
        when(jobService.saveJob(jobDTO)).thenReturn(jobEntity);

        // Act
        ResponseEntity<String> response = jobController.postJob(jobDTO);

        // Assert
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Job posted successfully with ID"));
        verify(jobService, times(1)).saveJob(jobDTO);
    }

    @Test
    void testApproveJob() {
        // Arrange
        Long jobId = 1L;

        // Act
        ResponseEntity<String> response = jobController.approveJob(jobId);

        // Assert
        assertEquals("Job approved successfully.", response.getBody());
        verify(jobService, times(1)).approveJob(jobId);
    }

    @Test
    void testMarkJobAsSpam() {
        // Arrange
        Long jobId = 1L;

        // Act
        ResponseEntity<String> response = jobController.markJobAsSpam(jobId);

        // Assert
        assertEquals("Job marked as spam successfully.", response.getBody());
        verify(jobService, times(1)).markJobAsSpam(jobId);
    }
}
package org.job.sbjobex.service;

import org.job.sbjobex.dto.JobDTO;
import org.job.sbjobex.model.JobEntity;
import org.job.sbjobex.repository.InMemoryJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceTest {

    private JobService jobService;
    private InMemoryJobRepository jobRepository;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        jobRepository = Mockito.mock(InMemoryJobRepository.class);
        emailService = Mockito.mock(EmailService.class);
        jobService = new JobService(jobRepository, emailService);
    }

    @Test
    void testIsFirstTimePoster_True() {
        // Arrange
        String email = "test@example.com";
        when(jobRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = jobService.isFirstTimePoster(email);

        // Assert
        assertTrue(result);
        verify(jobRepository, times(1)).findByEmail(email);
    }

    @Test
    void testIsFirstTimePoster_False() {
        // Arrange
        String email = "test@example.com";
        when(jobRepository.findByEmail(email)).thenReturn(Optional.of(new JobEntity()));

        // Act
        boolean result = jobService.isFirstTimePoster(email);

        // Assert
        assertFalse(result);
        verify(jobRepository, times(1)).findByEmail(email);
    }

    @Test
    void testSaveJob() {
        // Arrange
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle("Test Job");
        jobDTO.setEmail("test@example.com");
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(1L);
        when(jobRepository.save(any(JobEntity.class))).thenReturn(jobEntity);

        // Act
        JobEntity result = jobService.saveJob(jobDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jobRepository, times(1)).save(any(JobEntity.class));
    }

    @Test
    void testApproveJob() {
        // Arrange
        Long jobId = 1L;
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(jobId);
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobEntity));

        // Act
        jobService.approveJob(jobId);

        // Assert
        assertTrue(jobEntity.isApproved());
        verify(jobRepository, times(1)).findById(jobId);
    }

    @Test
    void testMarkJobAsSpam() {
        // Arrange
        Long jobId = 1L;
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(jobId);
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobEntity));

        // Act
        jobService.markJobAsSpam(jobId);

        // Assert
        assertTrue(jobEntity.isSpam());
        verify(jobRepository, times(1)).findById(jobId);
    }
}
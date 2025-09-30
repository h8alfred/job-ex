package org.job.sbjobex.repository;

import org.job.sbjobex.model.JobEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryJobRepositoryTest {

    private InMemoryJobRepository jobRepository;

    @BeforeEach
    void setUp() {
        jobRepository = new InMemoryJobRepository();
    }

    @Test
    void testSave() {
        // Arrange
        JobEntity job = new JobEntity();
        job.setTitle("Test Job");
        job.setEmail("test@example.com");

        // Act
        JobEntity savedJob = jobRepository.save(job);

        // Assert
        assertNotNull(savedJob.getId());
        assertEquals("Test Job", savedJob.getTitle());
        assertEquals("test@example.com", savedJob.getEmail());
    }

    @Test
    void testFindById() {
        // Arrange
        JobEntity job = new JobEntity();
        job.setTitle("Test Job");
        job.setEmail("test@example.com");
        JobEntity savedJob = jobRepository.save(job);

        // Act
        Optional<JobEntity> foundJob = jobRepository.findById(savedJob.getId());

        // Assert
        assertTrue(foundJob.isPresent());
        assertEquals(savedJob.getId(), foundJob.get().getId());
    }

    @Test
    void testFindByEmail() {
        // Arrange
        JobEntity job = new JobEntity();
        job.setTitle("Test Job");
        job.setEmail("test@example.com");
        jobRepository.save(job);

        // Act
        Optional<JobEntity> foundJob = jobRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(foundJob.isPresent());
        assertEquals("test@example.com", foundJob.get().getEmail());
    }

    @Test
    void testFindAll() {
        // Arrange
        JobEntity job1 = new JobEntity();
        job1.setTitle("Job 1");
        job1.setEmail("job1@example.com");
        jobRepository.save(job1);

        JobEntity job2 = new JobEntity();
        job2.setTitle("Job 2");
        job2.setEmail("job2@example.com");
        jobRepository.save(job2);

        // Act
        List<JobEntity> jobs = jobRepository.findAll();

        // Assert
        assertEquals(2, jobs.size());
    }
}
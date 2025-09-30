package org.job.sbjobex.service;

import jakarta.mail.MessagingException;
import org.job.sbjobex.dto.JobDTO;
import org.job.sbjobex.model.JobEntity;
import org.job.sbjobex.repository.InMemoryJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class JobService {

    private final InMemoryJobRepository jobRepository;
    private final EmailService emailService;

    public JobService(InMemoryJobRepository jobRepository, EmailService emailService) {
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    public boolean isFirstTimePoster(String email) {
        return jobRepository.findByEmail(email).isEmpty();
    }

    public JobEntity saveJob(JobDTO jobDTO) {
        JobEntity job = new JobEntity();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setEmail(jobDTO.getEmail());
        job.setApproved(false);
        return jobRepository.save(job);
    }

    public void approveJob(Long jobId) {
        JobEntity existingJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job with ID " + jobId + " not found"));

        existingJob.setApproved(true);
        jobRepository.save(existingJob);
    }

    public void markJobAsSpam(Long jobId) {
        JobEntity existingJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job with ID " + jobId + " not found"));

        existingJob.setSpam(true);
        jobRepository.save(existingJob);
    }


    public void notifyModerator(JobDTO jobDTO) throws IOException {
        String subject = "New Job Submission";

        ModelMap model = new ModelMap();
        model.addAttribute("title", jobDTO.getTitle());
        model.addAttribute("description", jobDTO.getDescription());
        model.addAttribute("email", jobDTO.getEmail());
        model.addAttribute("jobId", Optional.ofNullable(jobDTO.getId()).orElse(-1L));

        String body;
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/templates/job.mustache")), StandardCharsets.UTF_8)) {
            Mustache.Compiler compiler = Mustache.compiler();
            Template template = compiler.compile(reader);
            body = template.execute(model);
        }

        try {
            emailService.sendEmail("moderator@example.com", subject, body);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
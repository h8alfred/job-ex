package org.job.sbjobex.controller;

import lombok.extern.slf4j.Slf4j;
import org.job.sbjobex.dto.JobNotificationDTO;
import org.job.sbjobex.model.JobEntity;
import org.job.sbjobex.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<String> postJob(@RequestBody JobNotificationDTO jobNotificationDTO) {
        try {
            JobEntity savedJob = null;
            if (jobService.isFirstTimePoster(jobNotificationDTO.getEmail())) {
                savedJob = jobService.saveJob(jobNotificationDTO);
                jobNotificationDTO.setId(savedJob.getId());
                jobService.notifyModerator(jobNotificationDTO);
            }
            assert savedJob != null;
            return ResponseEntity.ok("Job posted successfully with ID: " + savedJob.getId());
        } catch (Exception e) {
            log.error("Error posting job: ", e);
            return ResponseEntity.status(500).body("An error occurred while posting the job: " + e.getMessage());
        }
    }

    @PostMapping("/{jobId}/approve")
    public ResponseEntity<String> approveJob(@PathVariable Long jobId) {
        try {
            jobService.approveJob(jobId);
            return ResponseEntity.ok("Job approved successfully.");
        } catch (Exception e) {
            log.error("Error approving job: ", e);
            return ResponseEntity.status(500).body("An error occurred while approving the job: " + e.getMessage());
        }
    }

    @PostMapping("/{jobId}/spam")
    public ResponseEntity<String> markJobAsSpam(@PathVariable Long jobId) {
        try {
            jobService.markJobAsSpam(jobId);
            return ResponseEntity.ok("Job marked as spam successfully.");
        } catch (Exception e) {
            log.error("Error marking job as spam: ", e);
            return ResponseEntity.status(500).body("An error occurred while marking the job as spam: " + e.getMessage());
        }
    }
}